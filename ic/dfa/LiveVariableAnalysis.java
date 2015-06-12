package ic.dfa;

import java.util.Vector;

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
import ic.tac.TACPropagateThreeVisitor;
import ic.tac.TACVar;
import ic.tac.TAC_Comment;
import ic.tac.TempVariable;
import ic.tac.UnExpr;

/* 
 * Live variable analysis determines the variables which are alive in the sense that they are used sometime later 
 * in the program before they are redefined. To represent this information we keep a Vector of Variables which can 
 * be either program variables or temporary variables. As a result, the analysis flows backward with the ins of the 
 * successors determining the outs of the predecessors. Since we never want to eliminate a variable that's value 
 * will be used along some path of the control flow graph, our meet operator is union and our top element is the 
 * empty set. The boundary, which represents the in[exit] is also the empty set because no variables are alive 
 * at the end of the program. 
 * 
 * In our transfer functions, we visit program variables and temporary variables with booleans DEF and USE. 
 * On DEFs, we remove the variable from the Vector of live variables and on USEs, we add the variable. 
*/
public class LiveVariableAnalysis extends DataFlowAnalysis<Vector<TACVar>> implements TACPropagateThreeVisitor<Vector<TACVar>, Vector<TACVar>, Boolean> {

	private static final Vector<TACVar> emptySet = new Vector<TACVar>();
	
	// Whenever a variable is defined, we want to remove it from the live variables. 
	public static Boolean DEF = false; 
	// Whenever a variable is used, we want to add it to the live variables. 
	public static Boolean USE = true; 
	
	public LiveVariableAnalysis(ControlFlowGraph cfg) {
		super(cfg);
	}

	@Override
	public String getName() {
		return "LV";
	}
	
	@Override
	public boolean isForward() {
		return false;
	}

	/**
	 * Initial value for in[exit], since this DFA is backwards.
	 * Since nothing is live at the end of the program, this is
	 * just the empty set. 
	 */
	@Override
	public Vector<TACVar> boundary() {
		return emptySet;
	}

	/**
	 * Top value in the lattice of T elements.
	 * Since union is the meet operator, the top element
	 * will be the empty set. Any element unioned with
	 * the empty set will return the element.  
	 */
	@Override
	public Vector<TACVar> top() {
		return emptySet;
	}

	/**
	 * Return the meet of branch1 and branch2 in the lattice.
	 * This will be the union of the two sets. 
	 */
	@Override
	public Vector<TACVar> meet(Vector<TACVar> branch1, Vector<TACVar> branch2) {
		Vector<TACVar> union = new Vector<TACVar>(); 
		for(TACVar cur: branch1) {
			if(!union.contains(cur)) {
				union.add(cur);
			}
		}
		
		for(TACVar cur: branch2) {
			if(!union.contains(cur)) {
				union.add(cur);
			}
		}
		
		return union;
	}

	/**
	 * Return true if t1 and t2 are equivalent.
	 */
	@Override
	public boolean equals(Vector<TACVar> t1, Vector<TACVar> t2) {
		if(t1.size() == 0 && t2.size() == 0) return true; 
		if(t1.size() != t2.size()) {
			return false; 
		}
		
		for(TACVar curOp: t2) {
			 if( ! t1.contains(curOp)) {
				 return false; 
			 }
		}
		return true;
	}

	/**
	 * Return the result of applying the transfer function for
	 * instr to t.
	 */
	@Override
	public Vector<TACVar> transfer(TACInstr instr, Vector<TACVar> t) {
		Vector<TACVar> live = new Vector<TACVar>(t);
		return instr.accept(this, live, USE);
	}
	
	public void printCur(Vector<TACVar> lv) {
		if(lv != null) {
		System.out.print("[");
		for(TACVar i: lv) {
			System.out.print(i + ", ");
		}
		System.out.println("]");
		}
	}

	@Override
	public Vector<TACVar> visit(TempVariable x, Vector<TACVar> lv, Boolean add) {
		if(add) { 
			if (!lv.contains(x)) {
				lv.add(x);
			} 
		} else {
			lv.remove(x); 
		}
		return lv;	
	}
	
	@Override
	public Vector<TACVar> visit(ProgramVariable x, Vector<TACVar> lv, Boolean add) {
		if(add) { 
			if (!lv.contains(x)) {
				lv.add(x);
			} 
		} else {
			lv.remove(x); 
		}
		return lv;	
	}
	

	@Override
	public Vector<TACVar> visit(Alloc_Array x, Vector<TACVar> lv, Boolean add) {
		x.getDest().accept(this, lv, DEF);
		x.getLength().accept(this, lv, USE); 
		return lv;
	}

	@Override
	public Vector<TACVar> visit(Alloc_Obj x, Vector<TACVar> lv, Boolean add) {
		return lv;
	}

	@Override
	public Vector<TACVar> visit(Array_Load x, Vector<TACVar> lv, Boolean add) {
		x.getDest().accept(this, lv, DEF);
		x.getArray().accept(this, lv, USE); 
		return lv;
	}

	@Override
	public Vector<TACVar> visit(Array_Store x, Vector<TACVar> lv, Boolean add) {
		x.getArray().accept(this, lv, USE); 
		x.getValue().accept(this, lv, USE); 
		return lv;
	}

	@Override
	public Vector<TACVar> visit(BinExpr x, Vector<TACVar> lv, Boolean add) {
		x.getDest().accept(this, lv, DEF);
		x.getRight().accept(this, lv, USE); 
		x.getLeft().accept(this, lv, USE); 
		return lv;
	}
	
	@Override
	public Vector<TACVar> visit(UnExpr x, Vector<TACVar> lv, Boolean add) {
		x.getDest().accept(this, lv, DEF);
		x.getExpr().accept(this, lv, USE); 
		return lv;
	}
	
	@Override
	public Vector<TACVar> visit(Load x, Vector<TACVar> lv, Boolean add) {
		x.getDest().accept(this, lv, DEF); 
		x.getValue().accept(this, lv, USE); 
		return lv;
	}

	@Override
	public Vector<TACVar> visit(Return x, Vector<TACVar> lv, Boolean add) {
		if(x.getExpr() != null) {
			x.getExpr().accept(this, lv, USE); 
		}
		return lv;
	}

	@Override
	public Vector<TACVar> visit(Store x, Vector<TACVar> lv, Boolean add) {
		x.getDest().accept(this, lv, DEF);
		x.getValue().accept(this, lv, USE); 
		return lv;
	}

	@Override
	public Vector<TACVar> visit(Check_Bounds x, Vector<TACVar> lv, Boolean add) {
		x.getArray().accept(this, lv, USE); 
		x.getIndex().accept(this, lv, USE); 
		return lv;
	}

	@Override
	public Vector<TACVar> visit(Check_Null x, Vector<TACVar> lv, Boolean add) {
		x.getToBeChecked().accept(this, lv, USE); 
		return lv;
	}

	@Override
	public Vector<TACVar> visit(Constant x, Vector<TACVar> lv, Boolean add) {
		return lv; 
	}

	@Override
	public Vector<TACVar> visit(Field_Load x, Vector<TACVar> lv, Boolean add) {
		x.getDest().accept(this, lv, DEF); 
		x.getField().accept(this, lv, USE); 
		return lv;
	}

	@Override
	public Vector<TACVar> visit(Field_Store x, Vector<TACVar> lv, Boolean add) {
		x.getField().accept(this, lv, USE);
		x.getValue().accept(this, lv, USE); 
		return lv;
	}

	@Override
	public Vector<TACVar> visit(Fun_Call x, Vector<TACVar> lv, Boolean add) {
		if(x.getDest() != null) {
			x.getDest().accept(this, lv, DEF);
		}
		
		x.getReceiver().accept(this, lv, USE); 
		for(Operand arg: x.getParams()) {
			arg.accept(this, lv, USE); 
		}
		
		return lv;
	}

	@Override
	public Vector<TACVar> visit(Jump x, Vector<TACVar> lv, Boolean add) {
		return lv;
	}

	@Override
	public Vector<TACVar> visit(Cond_Jump x, Vector<TACVar> lv, Boolean add) {
		x.getCond().accept(this, lv, USE); 
		return lv;
	}
	@Override
	public Vector<TACVar> visit(Label x, Vector<TACVar> lv, Boolean add) {
		return lv;
	}
	
	@Override
	public Vector<TACVar> visit(NoOp x, Vector<TACVar> lv, Boolean add) {
		return lv; 
	}


	@Override
	public Vector<TACVar> visit(Lib_Call x, Vector<TACVar> lv, Boolean add) {
		if(x.getDest() != null) {
			x.getDest().accept(this, lv, DEF);
		}
		for(Operand arg: x.getArgs()) {
			arg.accept(this, lv, USE); 
		}
		return lv;
	}


	@Override
	public Vector<TACVar> visit(String_Constant x, Vector<TACVar> lv, Boolean add) {
		return lv;
	}

	@Override
	public Vector<TACVar> visit(TAC_Comment x, Vector<TACVar> lv, Boolean add) {
		return lv;
	}

	@Override
	public Vector<TACVar> visit(Array_Access x, Vector<TACVar> lv, Boolean add) {
		x.getArray().accept(this, lv, USE);
		// When reading or writing to an array, the index should be live
		x.getIndex().accept(this, lv, USE);
		return lv;
	}

	@Override
	public Vector<TACVar> visit(Field_Access x, Vector<TACVar> lv, Boolean add) {
		x.getReceiver().accept(this, lv, USE); 
		return lv;
	}

	@Override
	public Vector<TACVar> visit(Check_Positive x, Vector<TACVar> lv, Boolean add) {
		x.getLength().accept(this, lv, USE); 
		return lv;
	}
	
	@Override
	public Vector<TACVar> visit(Call x, Vector<TACVar> lv, Boolean add) {
		throw new OptimizationError("Trying to create a transfer function for an abstract class.");
	}
	
	@Override
	public Vector<TACVar> visit(Operand x, Vector<TACVar> lv, Boolean add) {
		throw new OptimizationError("Trying to create a transfer function for an abstract class.");
	}
	
	@Override
	public Vector<TACVar> visit(TACInstr x, Vector<TACVar> lv, Boolean add) {
		throw new OptimizationError("Trying to create a transfer function for an abstract class.");
	}
	
}