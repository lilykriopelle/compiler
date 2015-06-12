package ic.tac;

import java.util.Vector;

import ic.ast.UnOp;
import ic.dfa.DataFlowAnalysis;
import ic.error.TACError;

public class TAC_Printer implements TACVisitor {
	
	private DataFlowAnalysis dfa; 
	boolean optimize;
	
	public TAC_Printer() {
		optimize = false; 
	}
	
	public TAC_Printer(DataFlowAnalysis dfa) {
		this.dfa = dfa; 
		optimize = true; 
	}
	
	// A helper method to print the in before the instruction.  
	public void printIn(TACInstr x) {
		if(optimize) {
			System.out.println("\t\t\t" + dfa.getName() + " IN: " + dfa.in(x)); 
		}
	}
	
	// A helper method to print the out after the instruction.  
	public void printOut(TACInstr x) {
		if(optimize) {
			System.out.println("\n\t\t\t" + dfa.getName() + " OUT: " + dfa.out(x)); 
		}
	}
	
	@Override
	public void visit(Alloc_Array x) throws TACError {
		printIn(x); 
		x.getDest().accept(this);
		System.out.print(" = call _LIB__allocateArray("); 
		x.getLength().accept(this);
		System.out.print(");");
		printOut(x); 
		System.out.println(); 
		
	}

	@Override
	public void visit(Alloc_Obj x) throws TACError {
		printIn(x); 
		x.getDest().accept(this);
		System.out.print(" = call _LIB__allocateObject(" + x.getSize() + ");");
		printOut(x); 
		System.out.println(); 
	}

	@Override
	public void visit(String_Constant x) {
		System.out.print(x); 
		
	}

	@Override
	public void visit(Array_Load x) throws TACError {
		printIn(x); 
		x.getDest().accept(this); 
		System.out.print(" = ");  
		x.getArray().accept(this); 
		System.out.print(";");  
		printOut(x); 
		System.out.println(); 
	}

	@Override
	public void visit(Array_Store x) throws TACError {
		printIn(x); 
		x.getArray().accept(this); 
		System.out.print(" = ");
		x.getValue().accept(this); 
		System.out.print(";");
		printOut(x); 
		System.out.println(); 
	}

	@Override
	public void visit(BinExpr x) throws TACError {
		printIn(x); 
		x.getDest().accept(this);
		System.out.print(" = "); 
		x.getLeft().accept(this); 
		System.out.print(" " + x.getOp().toString() + " ");
		x.getRight().accept(this);
		System.out.print(";");
		printOut(x); 
		System.out.println(); 
	}

	@Override
	public void visit(Call x) throws TACError {
		throw new TACError(0, "Trying to print the TAC of an abstract class.");
	}

	@Override
	public void visit(Check_Bounds x) throws TACError {
		printIn(x); 
		System.out.print("*Bounds Check: "); 
		x.getIndex().accept(this);
		System.out.print(" < length of ");
		x.getArray().accept(this); 
		System.out.print("*");
		printOut(x); 
		System.out.println(); 
	}
	
	@Override
	public void visit(Check_Positive x) throws TACError {
		printIn(x); 
		System.out.print("*Check Positive: ");
		x.getLength().accept(this);
		System.out.print(" > 0");
		System.out.print("*");
		printOut(x); 
		System.out.println(); 
	}

	@Override
	public void visit(Check_Null x) throws TACError {
		printIn(x); 
		System.out.print("*Null Check on "); 
		x.getToBeChecked().accept(this); 
		System.out.print("*");
		printOut(x); 
		System.out.println(); 
	}

	@Override
	public void visit(Cond_Jump x) throws TACError {
		printIn(x); 
		System.out.print("cjump ");
		x.getCond().accept(this);
		System.out.print(" " + x.getLabel().getLabelName() + ";");
		printOut(x); 
		System.out.println(); 
	}

	@Override
	public void visit(Constant x) {
		System.out.print(x.getValue());
	}

	@Override
	public void visit(Field_Load x) throws TACError {
		printIn(x); 
		x.getDest().accept(this); 
		System.out.print(" = ");
		x.getField().accept(this);
		System.out.print(";");
		printOut(x); 
		System.out.println(); 
	}

	@Override
	public void visit(Field_Store x) throws TACError {
		printIn(x); 
		x.getField().accept(this); 
		System.out.print(" = ");
		x.getValue().accept(this); 
		System.out.print(";");
		printOut(x); 
		System.out.println(); 
	}

	@Override
	public void visit(Fun_Call x) throws TACError {
		printIn(x); 
		x.getDest().accept(this); 
		System.out.print(" = call " + x.getMethodName() + "("); 
		Vector<Operand> params = x.getParams();
		if(params.size() > 0) params.get(0).accept(this); 
		for(int i = 1; i < params.size(); ++i) {
			System.out.print(","); 
			params.get(i).accept(this); 
		}
		System.out.print(");");
		printOut(x); 
		System.out.println(); 
		
	}

	@Override
	public void visit(Jump x) {
		printIn(x); 
		System.out.print("jump " + x.getLabel().getLabelName() + ";");
		printOut(x); 
		System.out.println(); 
	}

	@Override
	public void visit(Label x) {
		printIn(x); 
		System.out.print("label " + x.getLabelName() + ";");
		printOut(x); 
		System.out.println(); 
	}

	@Override
	public void visit(Lib_Call x) throws TACError {
		printIn(x); 
		// If the destination is non-empty
		if (x.getDest() != null) {
			if( ! (x.getDest() instanceof TempVariable && ((TempVariable)x.getDest()).isEmpty())) {
				x.getDest().accept(this);
				System.out.print(" = ");
			}
		}
		System.out.print("call __LIB_"); 
		System.out.print(x.getMethodName());
		System.out.print("(");
		
		Vector<Operand> params = x.getArgs();
		if(params.size() > 0) {
			for(int i = 0; i < params.size() - 1; ++i) {
				params.get(i).accept(this); 
				System.out.print(","); 
			}
			params.get(params.size() - 1).accept(this); 
		}
		System.out.print(");");
		printOut(x); 
		System.out.println(); 
	}

	@Override
	public void visit(Load x) throws TACError {
		printIn(x); 
		x.getDest().accept(this); 
		System.out.print(" = ");
		x.getValue().accept(this); 
		System.out.print(";");
		printOut(x); 
		System.out.println(); 
	}

	@Override
	public void visit(Operand x) throws TACError {
		// Since Operand is an abstract class this shouldn't happen
		throw new TACError(0, "Trying to print an abstract operand.");
	}

	@Override
	public void visit(ProgramVariable x) {
		System.out.print(x.getName());
	}

	@Override
	public void visit(Return x) throws TACError {
		printIn(x); 
		System.out.print("return "); 
		if(x.getExpr() != null) {
			x.getExpr().accept(this);
		}
		System.out.print(";");
		printOut(x); 
		System.out.println(); 
	}

	@Override
	public void visit(Store x) throws TACError {
		printIn(x); 
		x.getDest().accept(this); 
		System.out.print(" = ");
		x.getValue().accept(this); 
		System.out.print(";");
		printOut(x); 
		System.out.println(); 
	}


	@Override
	public void visit(TempVariable x) {
		if(x.isThis()) System.out.print("this");
		else System.out.print(x.getName()); 
	}

	@Override
	public void visit(UnExpr x) throws TACError {
		printIn(x); 
		x.getDest().accept(this);
		System.out.print(" = ");
		
		if(x.getOp() != UnOp.LENGTH) {
			System.out.print(x.getOp() + " ");
			x.getExpr().accept(this); 
		} else {
			x.getExpr().accept(this); 
			System.out.print(".length");
		}
		
		System.out.print(";");
		printOut(x); 
		System.out.println(); 
	}

	@Override
	public void visit(TACInstr x) throws TACError {
		// since TACInstr is an abstract variable this shouldn't happen
		throw new TACError(0, "Trying to print an abstract TAC instruction.");
		
	}

	@Override
	public void visit(TAC_Comment x) {
		System.out.println("# " + x.getComment());
	}

	@Override
	public void visit(Array_Access x) throws TACError {
		x.getArray().accept(this); 
		System.out.print("[");
		x.getIndex().accept(this); 
		System.out.print("]");
	}

	@Override
	public void visit(Field_Access x) throws TACError {
		x.getReceiver().accept(this);
		System.out.print("." + x.getField()); 
	}

	@Override
	public void visit(NoOp x) {
		//System.out.println("NO OP");
	}

}