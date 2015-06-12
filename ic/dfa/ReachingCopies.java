package ic.dfa;

import ic.ast.ClassIDNode;
import ic.cfg.ControlFlowGraph;
import ic.error.OptimizationError;
import ic.tac.Alloc_Array;
import ic.tac.Alloc_Obj;
import ic.tac.Array_Access;
import ic.tac.Array_Load;
import ic.tac.Array_Store;
import ic.tac.BinExpr;
import ic.tac.Call;
import ic.tac.Check_Bounds;
import ic.tac.Check_Null;
import ic.tac.Check_Positive;
import ic.tac.Cond_Jump;
import ic.tac.Constant;
import ic.tac.Field_Access;
import ic.tac.Field_Load;
import ic.tac.Field_Store;
import ic.tac.LdStr;
import ic.tac.Fun_Call;
import ic.tac.Jump;
import ic.tac.Label;
import ic.tac.Lib_Call;
import ic.tac.Load;
import ic.tac.NoOp;
import ic.tac.Operand;
import ic.tac.ProgramVariable;
import ic.tac.Return;
import ic.tac.Store;
import ic.tac.String_Constant;
import ic.tac.TACInstr;
import ic.tac.TACList;
import ic.tac.TACPropagatingVisitor;
import ic.tac.TAC_Comment;
import ic.tac.TempVariable;
import ic.tac.UnExpr;
import ic.tac.TACVar;

import java.util.Vector; 
import ic.tac.Copy;

/** Reaching copies determines the aliases that are valid along all
 *  paths leading to a particular program point. We keep track of this
 *  information in a Vector of copies, each of which contains two
 *  Variables (which can either be Program Variables or Temporary
 *  Variables). When we merge two different branches of the control
 *  flow graph, we take the intersection of the copy vectors since we
 *  are only interested in copies valid along all possible paths.
 *  
 *  In our transfer functions, we both generate copies and remove them
 *  when they cease to be valid.  When we run across a copy
 *  instruction we add it to the Vector. Whenever we redefine a
 *  variable or some part of it (e.g. a field in an object or a member
 *  of an array), we kill copies involving that variable by removing
 *  them from the Vector.
 * 
 *  */
public class ReachingCopies extends DataFlowAnalysis<Vector<Copy>> implements TACPropagatingVisitor<Vector<Copy>, Vector<Copy>> {

	private TACList method; 
	private Vector<Copy> TOP; 
	
	public ReachingCopies(ControlFlowGraph cfg, TACList method) {
		super(cfg);
		this.method = method; 
		generateTop(); 
	}

	@Override
	public String getName() {
		return "RC";
	}

	@Override
	public boolean isForward() {
		return true;
	}
	

	@Override
	public Vector<Copy> meet(Vector<Copy> branch1, Vector<Copy> branch2) {
		Vector<Copy> intersection = new Vector<Copy>(); 
		
		for(Copy cur: branch1) {
			if(branch2.contains(cur)) {
				intersection.add(cur); 
			}
		}
		
		return intersection; 
	}

	@Override
	public boolean equals(Vector<Copy> t1, Vector<Copy> t2) {
		if(t1.size() != t2.size()) {
			return false; 
		}
		
		for(Copy cur: t2) {
			 if( ! t1.contains(cur)) {
				 return false; 
			 }
		}
		return true;
	}

	@Override
	public Vector<Copy> transfer(TACInstr instr, Vector<Copy> t) {
		// To ensure that the ins and outs aren't aliased 
		Vector<Copy> reachingCopies = new Vector<Copy>(t);
		return instr.accept(this, reachingCopies);
	}

	/**
	 * Initial value for out[enter] or in[exit], depending on direction.
	 */
	@Override
	public Vector<Copy> boundary() {
		return new Vector<Copy>();
	}

	private void generateTop() {
		Vector<Copy> top = new Vector<Copy>();
		for(TACInstr cur: method.getInstrs()) {
			if(cur instanceof LdStr) {
				if(((LdStr)cur).isCopy()) {
					top.add(new Copy((LdStr)cur));
				}
			}
		}
		TOP = top; 
	}
	
	@Override
	public Vector<Copy> top() {
		return TOP; 
	}
	
	// Removes a copy in the reaching copies if one of the variables in the copy 
	// is being redefined by the current instruction.  
	private void removeCopy(TACVar dest, Vector<Copy> reachingCopies) {
		for(int i = 0; i < reachingCopies.size(); ++i) {
			Copy cur = reachingCopies.get(i);
			if(cur.getLeft().equals(dest) || cur.getRight().equals(dest)) { 
				reachingCopies.remove(i);
				break;
			}
		}
	}
	
	@Override
	public Vector<Copy> visit(Load x, Vector<Copy> reachingCopies) {
		removeCopy(x.getDest(), reachingCopies);
		if(x.getValue() instanceof TACVar) {
			Copy load; 
			load = new Copy(x.getDest(), (TACVar)x.getValue());
			if( ! reachingCopies.contains(load)) reachingCopies.add(load);
		}
		return reachingCopies;
	}
	
	@Override
	public Vector<Copy> visit(Store x, Vector<Copy> reachingCopies) {
		removeCopy(x.getDest(), reachingCopies);
		if(x.getValue() instanceof TACVar) {
			Copy load; 
			load = new Copy(x.getDest(), (TACVar)x.getValue());
			if( ! reachingCopies.contains(load)) reachingCopies.add(load);
		}
		return reachingCopies;
	}


	@Override
	public Vector<Copy> visit(TACInstr x, Vector<Copy> reachingCopies) {
		throw new OptimizationError("Trying to write a transfer function for an abstract class.");
	}

	@Override
	public Vector<Copy> visit(Alloc_Array x, Vector<Copy> reachingCopies) {
		removeCopy(x.getDest(), reachingCopies);
		return reachingCopies;
	}

	@Override
	public Vector<Copy> visit(Alloc_Obj x, Vector<Copy> reachingCopies) {
		removeCopy(x.getDest(), reachingCopies);
		return reachingCopies;
	}

	@Override
	public Vector<Copy> visit(Array_Load x, Vector<Copy> reachingCopies) {
		removeCopy(x.getDest(), reachingCopies);
		return reachingCopies;
	}

	@Override
	public Vector<Copy> visit(Array_Store x, Vector<Copy> reachingCopies) {
		return reachingCopies;
	}


	@Override
	public Vector<Copy> visit(BinExpr x, Vector<Copy> reachingCopies) {
		removeCopy(x.getDest(), reachingCopies);
		return reachingCopies;
	}
	
	@Override
	public Vector<Copy> visit(UnExpr x, Vector<Copy> reachingCopies) {
		removeCopy(x.getDest(), reachingCopies);
		return reachingCopies;
	}


	@Override
	public Vector<Copy> visit(Call x, Vector<Copy> reachingCopies) {
		throw new OptimizationError("Trying to write a transfer function for an abstract class.");
	}

	@Override
	public Vector<Copy> visit(Check_Bounds x, Vector<Copy> reachingCopies) {
		return reachingCopies;
	}

	@Override
	public Vector<Copy> visit(Check_Null x, Vector<Copy> reachingCopies) {
		return reachingCopies;
	}

	@Override
	public Vector<Copy> visit(Cond_Jump x, Vector<Copy> reachingCopies) {
		return reachingCopies;
	}

	@Override
	public Vector<Copy> visit(Constant x, Vector<Copy> reachingCopies) {
		return reachingCopies;
	}

	@Override
	public Vector<Copy> visit(Field_Load x, Vector<Copy> reachingCopies) {
		removeCopy(x.getDest(), reachingCopies);
		return reachingCopies;
	}

	@Override
	public Vector<Copy> visit(Field_Store x, Vector<Copy> reachingCopies) {
		if(x.getField().getReceiver() instanceof TACVar) {
			removeCopy((TACVar)x.getField().getReceiver(), reachingCopies);
			removeObjects(x.getField().getReceiver(), reachingCopies); 
		}
		return reachingCopies;
	}

	private void removeObjects(Operand receiver, Vector<Copy> reachingCopies) {
		for(Copy cur: reachingCopies) {
			if(cur.getRight() instanceof ProgramVariable) {
				if(cur.getRight().getResolved().getType() instanceof ClassIDNode) { 
					reachingCopies.remove(cur);
				}
			}
		}
		
	}

	@Override
	public Vector<Copy> visit(Fun_Call x, Vector<Copy> reachingCopies) {
		removeCopy(x.getDest(), reachingCopies);
		return reachingCopies;
	}

	@Override
	public Vector<Copy> visit(Jump x, Vector<Copy> reachingCopies) {
		return reachingCopies;
	}

	@Override
	public Vector<Copy> visit(Label x, Vector<Copy> reachingCopies) {
		return reachingCopies;
	}

	@Override
	public Vector<Copy> visit(Lib_Call x, Vector<Copy> reachingCopies) {
		removeCopy(x.getDest(), reachingCopies);
		return reachingCopies;
	}


	@Override
	public Vector<Copy> visit(Operand x, Vector<Copy> reachingCopies) {
		throw new OptimizationError("Trying to write a transfer function for an abstract class.");
	}

	@Override
	public Vector<Copy> visit(ProgramVariable x, Vector<Copy> reachingCopies) {
		throw new OptimizationError("Visiting variable.");
	}

	@Override
	public Vector<Copy> visit(Return x, Vector<Copy> reachingCopies) {
		return reachingCopies;
	}
	
	@Override
	public Vector<Copy> visit(TempVariable x, Vector<Copy> reachingCopies) {
		throw new OptimizationError("Visiting variable.");
	}

	
	@Override
	public Vector<Copy> visit(String_Constant x, Vector<Copy> reachingCopies) {
		return reachingCopies;
	}

	@Override
	public Vector<Copy> visit(TAC_Comment x, Vector<Copy> reachingCopies) {
		return reachingCopies;
	}

	@Override
	public Vector<Copy> visit(Array_Access x, Vector<Copy> reachingCopies) {
		return reachingCopies;
	}

	@Override
	public Vector<Copy> visit(Field_Access x, Vector<Copy> reachingCopies) {
		return reachingCopies;
	}

	@Override
	public Vector<Copy> visit(Check_Positive x, Vector<Copy> reachingCopies) {
		return reachingCopies;
	}

	@Override
	public Vector<Copy> visit(NoOp x, Vector<Copy> reachingCopies) {
		return reachingCopies;
	}

}