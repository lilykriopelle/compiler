package ic.opt;

import java.util.LinkedList;
import java.util.Vector;

import ic.ast.MethodDecl;
import ic.cfg.CFG_Builder;
import ic.dfa.LiveVariableAnalysis;
import ic.error.OptimizationError;
import ic.error.TACError;
import ic.tac.*;

public class DeadCodeElimination extends Optimization implements TACVisitor {
	
	private LiveVariableAnalysis lv; 
	private TACList method; 
	private boolean printDFA; 
	
	public DeadCodeElimination(boolean printDFA) {
		this.printDFA = printDFA; 
	}
	
	public String getName() {
		return "DCE"; 
	}
	
	@Override
	public void optimize(MethodDecl md) {
		lv = new LiveVariableAnalysis(md.getCFG()); 
		lv.solve();
		
		method = md.getTAC(); 
		
		if(printDFA) {
			// Print dfa
			TAC_Printer lvPrinter = new TAC_Printer(lv); 
			System.out.println("\nTAC of " + md.getName() + " with Live Variable Information");
			method.print(lvPrinter); 
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
		System.out.println("\nTAC of " + md.getName() + " optimized to eliminate Dead Code");
		TAC_Printer dcePrinter = new TAC_Printer(); 
		TACList optTAC = md.getTAC(); 
		optTAC.print(dcePrinter); 
		
	}
	
	@Override
	public void visit(Load x) throws TACError {
		if(x.getDest().equals(x.getValue())) {
			method.removeInstr(x); 
		} else {
			Vector<TACVar> liveVariables = lv.out(x); 
			if( ! liveVariables.contains(x.getDest())) {
				method.removeInstr(x);
			}
		}
	}

	@Override
	public void visit(Store x) throws TACError {
		if(x.getDest().equals(x.getValue())) {
			method.removeInstr(x); 
		} else {
			Vector<TACVar> liveVariables = lv.out(x); 
			if( ! liveVariables.contains(x.getDest())) {
				method.removeInstr(x);
			}
		}
	}
	
	@Override
	public void visit(Alloc_Array x) throws TACError {
		Vector<TACVar> liveVariables = lv.out(x); 
		if( ! liveVariables.contains(x.getDest())) {
			method.removeInstr(x);
		}

	}

	@Override
	public void visit(Alloc_Obj x) throws TACError {
		Vector<TACVar> liveVariables = lv.out(x); 
		if( ! liveVariables.contains(x.getDest())) {
			method.removeInstr(x);
		}

	}

	@Override
	public void visit(Array_Load x) throws TACError {
		Vector<TACVar> liveVariables = lv.out(x); 
		if( ! liveVariables.contains(x.getDest())) {
			method.removeInstr(x);
		}
	}

	@Override
	public void visit(Array_Store x) throws TACError {}
	

	@Override
	public void visit(Field_Load x) throws TACError {
		Vector<TACVar> liveVariables = lv.out(x); 
		if( ! liveVariables.contains(x.getDest())) {
			method.removeInstr(x);
		}
	}

	@Override
	public void visit(Field_Store x) throws TACError {}

	@Override
	public void visit(BinExpr x) throws TACError {
		Vector<TACVar> liveVariables = lv.out(x); 
		if( ! liveVariables.contains(x.getDest())) {
			method.removeInstr(x);
		}
	}
	
	@Override
	public void visit(UnExpr x) throws TACError {
		Vector<TACVar> liveVariables = lv.out(x); 
		if( ! liveVariables.contains(x.getDest())) {
			method.removeInstr(x);
		}
	}


	// Since these checks do not define variables, we leave them alone.
	@Override
	public void visit(Check_Bounds x) throws TACError {}

	@Override
	public void visit(Check_Null x) throws TACError {}
	
	@Override
	public void visit(Check_Positive x) throws TACError {}
	

	// Since calls may have unknown side effects, even if we don't use the destination,
	// we may need the other effects of the call.
	@Override
	public void visit(Fun_Call x) throws TACError {}
	
	@Override
	public void visit(Lib_Call x) throws TACError {}
	
	// We don't include jumping operations as part of our live variable analysis 
	@Override
	public void visit(Cond_Jump x) throws TACError {}
	
	@Override
	public void visit(Jump x) {}
	
	@Override
	public void visit(Label x) {}	
	
	@Override
	public void visit(Return x) throws TACError {}


	
	//We don't need to do anything for TAC_Comments or NoOps.
	@Override
	public void visit(TAC_Comment x) {}
	
	//We now filter out the NoOps
	@Override
	public void visit(NoOp x) {
		method.removeInstr(x);
	}

	@Override
	public void visit(TACInstr x) throws TACError {
		throw new OptimizationError("Applying dead code elimination to an abstract TACInstr.");
	}

	@Override
	public void visit(Call x) throws TACError {
		throw new OptimizationError("Applying dead code elimination to an abstract TACInstr.");

	}
	
	@Override
	public void visit(Operand x) throws TACError {
		throw new OptimizationError("Applying dead code elimination to an operand.");
	}

	@Override
	public void visit(ProgramVariable x) throws TACError {
		throw new OptimizationError("Applying dead code elimination to a program variable.");
	}
	

	@Override
	public void visit(TempVariable x) {
		throw new OptimizationError("Applying dead code elimination to a temp variable.");
	}
	
	@Override
	public void visit(Constant x) {
		throw new OptimizationError("Applying dead code elimination to a constant.");
	}

	@Override
	public void visit(String_Constant x) {
		throw new OptimizationError("Applying dead code elimination to a string constant.");
	}
	
	@Override
	public void visit(Array_Access x) throws TACError {
		throw new OptimizationError("Applying dead code elimination to an array access.");
	}

	@Override
	public void visit(Field_Access x) throws TACError {
		throw new OptimizationError("Applying dead code elimination to a field access.");
	}


}
