
package ic.opt; 

import java.util.LinkedList;
import java.util.Vector;

import ic.tac.*;
import ic.cfg.CFG_Builder;
import ic.dfa.AvailableExpressions; 
import ic.dfa.GeneratingInstrs;
import ic.error.OptimizationError;
import ic.error.TACError;
import ic.ast.MethodDecl; 

/** 
  	In the first pass, we ensure that if multiple branches define the expression 
	they all store the expression in the same temporary variable. 
 	
 	In the second pass, we replace expressions we have already computed with a load
 	from the temporary in which the value of the expression has been stored into the 
 	old destination. Every time we use an expression we must go back and add a load to 
 	a temporary variable to ensure that the destination of the available expression 
 	does not get overwritten in the meantime. 
*/
public class CommonSubexpressionElimination extends Optimization implements TACVisitor {

    private AvailableExpressions ae; 
    private TACList method; 
    private boolean printDFA; 

    public CommonSubexpressionElimination(boolean printDFA) {
    	this.printDFA = printDFA; 
    }
    
    public String getName() {
		return "CSE"; 
	}
    
    @Override 
    public void optimize(MethodDecl md) {
    	method = md.getTAC(); 

    	ae = new AvailableExpressions(md.getCFG(), method); 
		ae.solve(); 

		if(printDFA) {
		    TAC_Printer aePrinter = new TAC_Printer(ae); 
		    System.out.println("\nTAC of " + md.getName() + " with Available Expressions Information"); 
		    method.print(aePrinter); 
		}
		
		// First pass: If multiple branches define the expression, make sure that 
		// each branch stores the expression in the same temporary variable. 
		LinkedList<TACInstr> oldInstructions1 = new LinkedList<TACInstr>(method.getInstrs());
		for (TACInstr cur: oldInstructions1) {
			if (cur instanceof AvailableExpr && !(cur instanceof Check)) {
				AvailableExpr curExpr = (AvailableExpr)cur;
				GeneratingInstrs redundant = ae.in(cur).get(curExpr); 
				if(redundant != null && redundant.getInstrs().size() > 0) {
					ae.in(cur).put(curExpr, updateGeneratingInstrs(curExpr, redundant));
				}
			}
		}
		
		
		md.setInstrs(method);
		md.setCFG((new CFG_Builder()).buildCFG(method)); 
		
		// Second pass: Replace expressions already computed with the variable
		// in which the result is stored		
		LinkedList<TACInstr> oldInstructions2 = new LinkedList<TACInstr>(method.getInstrs());
		for (TACInstr cur: oldInstructions2) {
			cur.accept(this); 
		}
		
		md.setInstrs(method);
		md.setCFG((new CFG_Builder()).buildCFG(method)); 

		// Print out optimized TAC
		System.out.println("\nTAC of " + md.getName() + " optimized to eliminate common subexpressions.");
		
		TAC_Printer csePrinter = new TAC_Printer();
		method.print(csePrinter);
	
    }
    
    // This helper method makes sure that an available expression computed across 
    // different branches is always stored into the same temporary. We utilize the 
    // introduce new temp variable to introduce the same temp along all paths. 
    private GeneratingInstrs updateGeneratingInstrs(AvailableExpr x, GeneratingInstrs redundant) {
    	TempVariable temp = method.getRegister(); 
		Vector<AvailableExpr> exprs = redundant.getInstrs();
		for (int i = 0; i < exprs.size(); ++i) {
			AvailableExpr cur = exprs.get(i);
			method.introduceNewTemp(cur, temp);  									
		}
		return new GeneratingInstrs(new Load(temp, temp)); 
    }
    
    // This helper method introduces a new temp to store the value to ensure that 
    // it will not be overwritten before we can use it and then updates the instruction
    // with a load of the introduced temp into the old destination.
	private void updateLoad(AvailableExpr x, GeneratingInstrs redundant) {
		if(redundant.getInstrs().size() == 1) {
			method.updateInstr((TACInstr)x, new Load(x.getDest(), redundant.get()));
		}
	}

	@Override
	public void visit(Array_Load x) throws TACError {
		GeneratingInstrs redundant = ae.in(x).get(x); 
		if( redundant != null) {
			updateLoad(x, redundant); 
		}
	}

	@Override
	public void visit(Field_Load x) throws TACError {
		GeneratingInstrs redundant = ae.in(x).get(x); 
		if( redundant != null) {
			updateLoad(x, redundant); 
		}
	}

	@Override
	public void visit(BinExpr x) throws TACError {
		GeneratingInstrs redundant = ae.in(x).get(x); 
		if( redundant != null) {
			updateLoad(x, redundant); 
		}
	}

	@Override
	public void visit(UnExpr x) throws TACError {
		GeneratingInstrs redundant = ae.in(x).get(x); 
		if( redundant != null) {
			updateLoad(x, redundant); 
		}
	}
	
	// For checks if the check is in the set of generating instructions
	// we know that the check is unnecessary and can be safely removed. 
	@Override
	public void visit(Check_Positive x) throws TACError {
		GeneratingInstrs redundant = ae.in(x).get(x); 
		if( redundant != null && redundant.getInstrs().size() > 0) {
			method.removeInstr(x); 
		}
	}

	@Override
	public void visit(Check_Bounds x) throws TACError {
		GeneratingInstrs redundant = ae.in(x).get(x); 
		if( redundant != null && redundant.getInstrs().size() > 0) {
			method.removeInstr(x); 
		}
	}

	@Override
	public void visit(Check_Null x) throws TACError {
		GeneratingInstrs redundant = ae.in(x).get(x); 
		if( redundant != null && redundant.getInstrs().size() > 0) {
			method.removeInstr(x); 
		}
	}
	
	@Override
	public void visit(Field_Store x) throws TACError {}
	
	@Override
	public void visit(Array_Store x) throws TACError {}

	@Override
	public void visit(Load x) throws TACError {}
	
	@Override
	public void visit(Store x) throws TACError {}
	
	@Override
	public void visit(Alloc_Array x) throws TACError {}

	@Override
	public void visit(Alloc_Obj x) throws TACError {}

	@Override
	public void visit(Return x) throws TACError {}

	@Override
	public void visit(Fun_Call x) throws TACError {}

	@Override
	public void visit(Lib_Call x) throws TACError {}

	@Override
	public void visit(Cond_Jump x) throws TACError {}

	@Override
	public void visit(Jump x) {}

	@Override
	public void visit(Label x) {}

	@Override
	public void visit(NoOp x) {}

	@Override
	public void visit(TAC_Comment x) {}

	@Override
	public void visit(Array_Access x) throws TACError {
		throw new OptimizationError("Trying to call common subexpresison elimination on an operand.");		
	}

	@Override
	public void visit(Field_Access x) throws TACError {
		throw new OptimizationError("Trying to call common subexpresison elimination on an operand.");		
	}

	@Override
	public void visit(TACInstr x) throws TACError {
		throw new OptimizationError("Trying to call common subexpresison elimination on an abstract tac instr.");		
	}

	@Override
	public void visit(Call x) throws TACError {
		throw new OptimizationError("Trying to call common subexpresison elimination on an abstract call.");			
	}

	@Override
	public void visit(Operand x) throws TACError {
		throw new OptimizationError("Trying to call common subexpresison elimination on an operand.");		
	}

	@Override
	public void visit(ProgramVariable x) throws TACError {
		throw new OptimizationError("Trying to call common subexpresison elimination on an operand.");		
	}

	@Override
	public void visit(TempVariable x) {
		throw new OptimizationError("Trying to call common subexpresison elimination on an operand.");		
	}

	@Override
	public void visit(Constant x) {
		throw new OptimizationError("Trying to call common subexpresison elimination on an operand.");		
	}

	@Override
	public void visit(String_Constant x) {
		throw new OptimizationError("Trying to call common subexpresison elimination on an operand.");		
	}
}