package ic.tac;

import ic.ast.UnOp;
import ic.error.OptimizationError;
import ic.error.TACError;

public class UnExpr extends TACInstr implements AvailableExpr {

    UnOp op; 
    Operand operand; 
    TACVar dest; 
	
    public UnExpr(UnOp op, Operand operand, TACVar dest) {
	this.op = op; 
	this.operand = operand; 
	this.dest = dest; 
    }

    public boolean accessesField(String field) {
	return false; 
    }

    public String toString() {
    	int indexOfExpr = dest.toString().length() + 3; // The three is for " = "  
    	return ((String)this.accept(new TAC_StringGenerator())).substring(indexOfExpr); 
    }

    public boolean involves(TACVar var) {
	return operand.involves(var);
    }

    @Override
    public boolean equals(Object other) {
	if(other instanceof UnExpr) {
	    UnExpr otherU = (UnExpr)other; 
	    return otherU.getOp() == op && otherU.getExpr().equals(operand);
	} else {
	    return false; 
	}
    }
    
    // Since toString only prints the right side of the instruction, this is
 	// what we want to be hashing on in available expressions.
     @Override 
     public int hashCode() {
    	return toString().hashCode();
     }
    
    public <UpType> UpType accept(TACPropagatingUpVisitor<UpType> v) {
	return v.visit(this);
    }
    
    public void accept(TACVisitor v) throws TACError {
	v.visit(this);
    }
	
    public TACVar getDest() {
	return dest; 
    }
    
    public void setDest(TACVar dest) {
    	this.dest = dest; 
    }

    public Operand getExpr() {
	return operand;
    }

    public UnOp getOp() {
	return op;
    }
	
    public <UpType, DownType1, DownType2> UpType accept(TACPropagateThreeVisitor<UpType, DownType1, DownType2> v,
							DownType1 t1, DownType2 t2) {
	return v.visit(this, t1, t2);
    }
	
    public <UpType, DownType> UpType accept(TACPropagatingVisitor<UpType, DownType> v,
					    DownType t) {
	return v.visit(this, t);
    }

    public void setExpr(Operand expr) {
    	this.operand = expr; 
    }

	public boolean isConstant() {
		return operand instanceof Constant; 
	}

}
