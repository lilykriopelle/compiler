package ic.tac;

import java.util.Vector;

import ic.ast.UnOp;
import ic.error.TACError;

public class TAC_StringGenerator implements TACPropagatingUpVisitor {
	
	@Override
	public String visit(Alloc_Array x) throws TACError {
		String toString = ""; 
		toString += toString += x.getDest().accept(this);
		toString += " = call _LIB__allocateArray("; 
		toString += toString += x.getLength().accept(this);
		return toString + ");";
	}

	@Override
	public String visit(Alloc_Obj x) throws TACError {
		String toString = ""; 
		toString += x.getDest().accept(this);
		return " = call _LIB__allocateObject(" + x.getSize() + ");";
	}

	@Override
	public String visit(String_Constant x) {
		return x.toString(); 
		
	}

	@Override
	public String visit(Array_Load x) throws TACError {
		String toString = ""; 
		toString += x.getDest().accept(this); 
		toString += " = ";  
		toString += x.getArray().accept(this); 
		return toString ;  
	}

	@Override
	public String visit(Array_Store x) throws TACError {
		String toString = ""; 
		toString += x.getArray().accept(this); 
		toString += " = ";
		toString += x.getValue().accept(this); 
		return toString ;
	}

	@Override
	public String visit(BinExpr x) throws TACError {
		String toString = ""; 
		toString += x.getDest().accept(this);
		toString += " = "; 
		toString += x.getLeft().accept(this); 
		toString += " " + x.getOp().toString() + " ";
		toString += x.getRight().accept(this);
		return toString ;
		
	}

	@Override
	public String visit(Call x) throws TACError {
		throw new TACError(0, "Trying to print the TAC of an abstract class.");
	}

	@Override
	public String visit(Check_Bounds x) throws TACError {
		String toString = ""; 
		toString += "Bounds Check: "; 
		toString += x.getIndex().accept(this);
		toString += " < length of ";
		toString += x.getArray().accept(this); 
		return toString;
	}
	
	@Override
	public String visit(Check_Positive x) throws TACError {
		String toString = ""; 
		toString += "Check Positive: ";
		toString += x.getLength().accept(this);
		toString += " > 0";
		return toString;
	}

	@Override
	public String visit(Check_Null x) throws TACError {
		String toString = ""; 
		toString += "Null Check on "; 
		toString += x.getToBeChecked().accept(this); 
		return toString;
		
	}

	@Override
	public String visit(Cond_Jump x) throws TACError {
		String toString = ""; 
		toString += "cjump ";
		toString += x.getCond().accept(this);
		return toString + " " + x.getLabel().getLabelName() ;
	}

	@Override
	public String visit(Constant x) {
		if(x.getValue() == null) return "null";
		else return x.getValue().toString();
	}

	@Override
	public String visit(Copy x) throws TACError {
		String toString = ""; 
		toString += ((Operand)x.getLeft()).accept(this);
		toString += " = ";
		toString += ((Operand)x.getRight()).accept(this);
		return toString ; 
	}

	@Override
	public String visit(Field_Load x) throws TACError {
		String toString = ""; 
		toString += x.getDest().accept(this); 
		toString += " = ";
		toString += x.getField().accept(this);
		return toString ;
		
	}

	@Override
	public String visit(Field_Store x) throws TACError {
		String toString = ""; 
		toString += x.getField().accept(this); 
		toString += " = ";
		toString += x.getValue().accept(this); 
		return toString ;
	}

	@Override
	public String visit(Fun_Call x) throws TACError {
		String toString = ""; 
		toString += x.getDest().accept(this); 
		toString += " = call " + x.getMethodName() + "("; 
		Vector<Operand> params = x.getParams();
		if(params.size() > 0) params.get(0).accept(this); 
		for(int i = 1; i < params.size(); ++i) {
			toString += ","; 
			params.get(i).accept(this); 
		}
		return toString + ");";
		
		
	}

	@Override
	public String visit(Jump x) {
		return "jump " + x.getLabel().getLabelName() ;
		
	}

	@Override
	public String visit(Label x) {
		return "label " + x.getLabelName() ;
		
	}

	@Override
	public String visit(Lib_Call x) throws TACError {
		String toString = ""; 
		// If the destination is non-empty
		if (x.getDest() != null) {
			if( ! (x.getDest() instanceof TempVariable && ((TempVariable)x.getDest()).isEmpty())) {
				toString += x.getDest().accept(this);
				toString += " = ";
			}
		}
		toString += "call __LIB_"; 
		toString += x.getMethodName();
		toString += "(";
		
		Vector<Operand> params = x.getArgs();
		if(params.size() > 0) {
			for(int i = 0; i < params.size() - 1; ++i) {
				toString += params.get(i).accept(this); 
				toString += ","; 
			}
			toString += params.get(params.size() - 1).accept(this); 
		}
		return toString + ")";
		
	}

	@Override
	public String visit(Load x) throws TACError {
		String toString = ""; 
		toString += x.getDest().accept(this); 
		toString += " = ";
		toString += x.getValue().accept(this); 
		return toString ;
		
	}

	@Override
	public String visit(Operand x) throws TACError {
		// Since Operand is an abstract class this shouldn't happen
		throw new TACError(0, "Trying to print an abstract operand.");
	}

	@Override
	public String visit(ProgramVariable x) {
		return x.getName();
	}

	@Override
	public String visit(Return x) throws TACError {
		String toString = ""; 
		toString += "return "; 
		if(x.getExpr() != null) {
			toString += x.getExpr().accept(this);
		}
		return toString ;
	}

	@Override
	public String visit(Store x) throws TACError {
		String toString = ""; 
		toString += x.getDest().accept(this); 
		toString += " = ";
		toString += x.getValue().accept(this); 
		return toString ;
	}


	@Override
	public String visit(TempVariable x) {
		return x.getName(); 
	}

	@Override
	public String visit(UnExpr x) throws TACError {
		String toString = ""; 
		toString += x.getDest().accept(this);
		toString += " = ";
		
		if(x.getOp() != UnOp.LENGTH) {
			toString += x.getOp() + " ";
			toString += x.getExpr().accept(this); 
		} else {
			toString += x.getExpr().accept(this); 
			toString += ".length";
		}
		
		return toString ;
		
		
	}

	@Override
	public String visit(TACInstr x) throws TACError {
		// since TACInstr is an abstract variable this shouldn't happen
		throw new TACError(0, "Trying to print an abstract TAC instruction.");
		
	}

	@Override
	public String visit(TAC_Comment x) {
		return "# " + x.getComment();
	}

	@Override
	public String visit(Array_Access x) throws TACError {
		String toString = ""; 
		toString += x.getArray().accept(this); 
		toString += "[";
		toString += x.getIndex().accept(this); 
		toString += "]";
		return toString; 
	}

	@Override
	public String visit(Field_Access x) throws TACError {
		String toString = ""; 
		toString += x.getReceiver().accept(this);
		toString += "." + x.getField();
		return toString; 
	}

	@Override
	public String visit(NoOp x) {
		return "";
	}

}