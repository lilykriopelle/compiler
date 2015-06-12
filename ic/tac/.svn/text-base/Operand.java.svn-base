package ic.tac;

import ic.HasOffset;
import ic.error.TACError;

/**
 * The main classes are the TACInstr and its subclasses, which
 * represent the different operations of TAC, the TACList, which
 * stores the instructions in the order they appear in the program and
 * is used as a Decoration on each method declaration in the AST, and
 * the Operands and its subclasses, which represent the kinds of
 * operands--program variables, temporary variables, and constants. We
 * will also write another PrettyPrint visitor, which prints the
 * results and annotations from the TAC.
 * 
 * We will handle instructions with a similar approach as in AST
 * except the lines of the program will be associated with
 * corresponding TACInstrs. The instructions will contain their
 * operands and operator, label, or method information passed to the
 * instruction as instance variables.  With this information we will
 * form the TAC instructions and annotations which will be printed out
 * by a PrettyPrinter. To support this we will again implement the
 * visitor design pattern.
 * 
 * We will represent operands as constants, temporary variables, and program variables. 
 * For temporary variables and program variables we will search in the instruction list
 * decoration for the reference. 
 * 
 * */

public abstract class Operand extends TACList implements HasOffset {
    public abstract boolean involves(TACVar other); 
	public <UpType> UpType accept(TACPropagatingUpVisitor<UpType> v) {
		return v.visit(this);
	}
	
	public void accept(TACVisitor v) throws TACError {
		v.visit(this);
	}
	
	public <UpType, DownType> UpType accept(TACPropagatingVisitor<UpType, DownType> v,
			DownType t) {
		return v.visit(this, t);
	}
	
	public <UpType, DownType1, DownType2> UpType accept(TACPropagateThreeVisitor<UpType, DownType1, DownType2> v,
			DownType1 t1, DownType2 t2) {
		return v.visit(this, t1, t2);
	}
    

}
