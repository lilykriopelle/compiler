package ic.opt;

import java.util.LinkedList;
import java.util.Vector;

import ic.ast.MethodDecl;
import ic.cfg.CFG_Builder;
import ic.dfa.ReachingCopies;
import ic.error.OptimizationError;
import ic.error.TACError;
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
import ic.tac.Copy;
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
import ic.tac.TACList;
import ic.tac.TACVar;
import ic.tac.TACVisitor;
import ic.tac.TAC_Comment;
import ic.tac.TAC_Printer;
import ic.tac.TempVariable;
import ic.tac.UnExpr;

/* 
 * Our copy propagator implements the TACVisitor interface.  For each TACInstr, it checks
 * whether a variable used in the instruction was part of a reaching copy.  If so, we
 * replace that variable with its known alias.  Additionally, we remove any instance of a 
 * load or store that is a copy from one variable to another. 
*/
public class CopyPropagation extends Optimization implements TACVisitor {

	private TACList method;
	private ReachingCopies rc;
	private boolean printDFA; 
	
	public CopyPropagation(boolean printDFA) {
		this.printDFA = printDFA; 
	}
	
	public String getName() {
		return "CPP"; 
	}
	
	@Override
	public void optimize(MethodDecl md) {
		rc = new ReachingCopies(md.getCFG(), md.getTAC()); 
		rc.solve();
		
		method = md.getTAC(); 
		
		if(printDFA) {
			// Print dfa
			TAC_Printer rcPrinter = new TAC_Printer(rc); 
			System.out.println("\nTAC of " + md.getName() + " with Reaching Copies Information");
			method.print(rcPrinter); 
		}		
		
		// Visit all of the instrs in the method, replacing 
		// instructions that define variables which aren't used
		// after the block with NoOps. 
		LinkedList<TACInstr> oldInstructions = new LinkedList<TACInstr>(method.getInstrs());
		for(TACInstr cur: oldInstructions) {
			cur.accept(this); 
		}
		md.setInstrs(method); 
		md.setCFG((new CFG_Builder()).buildCFG(method)); 
		
		// Print out optimized TAC
		System.out.println("\nTAC of " + md.getName() + " optimized to eliminate unnecessary copies");
		TAC_Printer cppPrinter = new TAC_Printer(); 
		TACList optTAC = md.getTAC(); 
		optTAC.print(cppPrinter); 
	}

	
	private Copy findCopy(Vector<Copy> copies, TACVar v) {
		for (Copy c : copies) {
			if (c.getLeft().equals(v)) {
				return c;
			}
		}
		return null;
	}
	
	private Copy replaceOperand(Vector<Copy> copies, Operand op) {
		if(op instanceof TACVar) {
			return findCopy(copies, (TACVar)op);
		} else {
			return null; 
		}
	}
	
	@Override
	public void visit(Alloc_Array x) throws TACError {
		Copy length = replaceOperand(rc.in(x), x.getLength()); 
		if(length != null) {
			x.setLength(length.getRight());
		}
	}

	@Override
	public void visit(Alloc_Obj x) throws TACError {
		// do nothing
	}

	@Override
	public void visit(Array_Load x) throws TACError {
		Vector<Copy> copies = rc.in(x); 
		Array_Access arrayAccess = x.getArray(); 
		Copy array = replaceOperand(copies, arrayAccess.getArray());
		if(array != null) {
			arrayAccess.setArray(array.getRight());
		}
		Copy index = replaceOperand(copies, arrayAccess.getIndex());
		if(index != null) {
			arrayAccess.setIndex(index.getRight());
		}
		
	}

	@Override
	public void visit(Array_Store x) throws TACError {
		Vector<Copy> copies = rc.in(x); 
		Array_Access arrayAccess = x.getArray(); 
		Copy array = replaceOperand(copies, arrayAccess.getArray());
		if(array != null) {
			arrayAccess.setArray(array.getRight());
		}
		Copy index = replaceOperand(copies, arrayAccess.getIndex());
		if(index != null) {
			arrayAccess.setIndex(index.getRight());
		}		
		Copy value = replaceOperand(copies, x.getValue()); 
		if(value != null) {
			x.setValue(value.getRight());
		}
	}

	@Override
	public void visit(BinExpr x) throws TACError {
		Vector<Copy> copies = rc.in(x);
		Copy left = replaceOperand(copies, x.getLeft()); 
		if(left != null) {
			x.setLeft(left.getRight());
		}
		Copy right = replaceOperand(copies, x.getRight()); 
		if(right != null) {
			x.setRight(right.getRight());
		}
	}
	
	@Override
	public void visit(UnExpr x) throws TACError {
		Copy expr = replaceOperand(rc.in(x), x.getExpr()); 
		if(expr != null) {
			x.setExpr(expr.getRight());
		}
	}

	@Override
	public void visit(Cond_Jump x) throws TACError {
		Copy cond = replaceOperand(rc.in(x), x.getCond()); 
		if(cond != null) {
			x.setCond(cond.getRight());
		}
	}

	
	@Override
	public void visit(Field_Load x) throws TACError {
		Vector<Copy> copies = rc.in(x); 
		Field_Access field = x.getField(); 
		Copy receiver = replaceOperand(copies, field.getReceiver());
		if(receiver != null) {
			field.setReceiver(receiver.getRight());
		}		
	}

	@Override
	public void visit(Field_Store x) throws TACError {
		Vector<Copy> copies = rc.in(x); 
		Field_Access field = x.getField(); 
		Copy receiver = replaceOperand(copies, field.getReceiver());
		if(receiver != null) {
			field.setReceiver(receiver.getRight());
		}		
	}

	@Override
	public void visit(Fun_Call x) throws TACError {}


	@Override
	public void visit(Lib_Call x) throws TACError {}

	@Override
	public void visit(Load x) throws TACError {
		// TODO: test on params-1 to check that we didn't brak anything by saiing ! isParam
		if(x.getDest() instanceof TempVariable && (!((TempVariable)x.getDest()).isParam())) {	
			Copy right = replaceOperand(rc.in(x), x.getValue()); 
			if(right != null) {
				x.setValue(right.getRight());
			}
		}
	}

	
	@Override
	public void visit(Return x) throws TACError {
		if(x.getExpr() != null) {
			Copy expr = replaceOperand(rc.in(x), x.getExpr()); 
			if(expr != null) {
				x.setExpr(expr.getRight());
			}
		}
	}

	@Override
	public void visit(Store x) throws TACError {
		if(x.getDest() instanceof TempVariable && ((TempVariable)x.getDest()).isParam()) {
			Copy right = replaceOperand(rc.in(x), x.getValue()); 
			if(right != null) {
				x.setValue(right.getRight());
			}
		}
	}

	
	@Override
	public void visit(Jump x) {}

	@Override
	public void visit(Label x) {}

	
	@Override
	public void visit(TACInstr x) throws TACError {
		throw new OptimizationError("Applying copy propagation to an abstract TACInstr.");
	}


	@Override
	public void visit(Call x) throws TACError {
		throw new OptimizationError("Applying copy propagation to a abstract call.");		
	}

	@Override
	public void visit(Check_Bounds x) throws TACError {
		Vector<Copy> copies = rc.in(x); 
		Copy array = replaceOperand(copies, x.getArray());
		if(array != null) {
			x.setArray(array.getRight());
		}
		Copy index = replaceOperand(copies, x.getIndex());
		if(index != null) {
			x.setIndex(index.getRight());
		}
		
	}

	@Override
	public void visit(Check_Null x) throws TACError {
		Copy check = replaceOperand(rc.in(x), x.getToBeChecked());
		if(check != null) {
			x.setToBeChecked(check.getRight());
		}
	}
	
	@Override
	public void visit(TAC_Comment x) {}
	
	@Override
	public void visit(Check_Positive x) throws TACError { 
		Copy length = replaceOperand(rc.in(x), x.getLength());
		if(length != null) {
			x.setLength(length.getRight());
		}
	}

	@Override
	public void visit(NoOp x) {}
	
	
	@Override
	public void visit(Constant x) {
		throw new OptimizationError("Applying copy propagation to a constant.");				
	}

	
	@Override
	public void visit(Operand x) throws TACError {
		throw new OptimizationError("Applying copy propagation to an operand.");				
	}

	@Override
	public void visit(ProgramVariable x) throws TACError {
		throw new OptimizationError("Applying copy propagation to a program variable.");					
	}
	

	@Override
	public void visit(TempVariable x) {
		throw new OptimizationError("Applying copy propagation to a temp variable.");						
	}
	
	@Override
	public void visit(String_Constant x) {
		throw new OptimizationError("Applying copy propagation to a string constant.");								
	}
	
	@Override
	public void visit(Array_Access x) throws TACError {
		throw new OptimizationError("Applying copy propagation to an array access.");					
	}

	@Override
	public void visit(Field_Access x) throws TACError {
		throw new OptimizationError("Applying copy propagation to a field access.");							
	}


}
