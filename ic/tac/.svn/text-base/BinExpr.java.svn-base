package ic.tac;

import ic.ast.BinOp;
import ic.error.OptimizationError;
import ic.error.TACError;

public class BinExpr extends TACInstr implements AvailableExpr {

    BinOp op;
    Operand left; 
    Operand right;
    TACVar destination;
	
    public BinExpr(Operand left, BinOp op, Operand right, TACVar destination) {
	this.op = op;
	this.left = left; 
	this.right = right; 
	this.destination = destination; 
    }

    public boolean accessesField(String field) {
	return false; 
    }
    
    @Override
    public boolean involves(TACVar var) {
	return left.involves(var) || right.involves(var); 
    }

    public String toString() {
    	int indexOfExpr = destination.toString().length() + 3; // The three is for " = "  
    	return ((String)this.accept(new TAC_StringGenerator())).substring(indexOfExpr); 
    }
    
	// Since toString only prints the right side of the instruction, this is
	// what we want to be hashing on in available expressions.
    @Override 
    public int hashCode() {
    	return toString().hashCode();
    }

    @Override 
    public boolean equals(Object other) {
	if(other instanceof BinExpr) {
	    BinExpr otherExpr = (BinExpr)other; 
	    if(otherExpr.getOp() == op) {
		// The same operands are involved, possibly in reverse order. 
		return (left.equals(otherExpr.getLeft()) && right.equals(otherExpr.getRight()))
		    || (left.equals(otherExpr.getRight()) && right.equals(otherExpr.getLeft()));
	    } else {
	    	return false;
	    }
	} else {
	    return false; 
	}
    }
    
    public BinOp getOp() {
	return op;
    }
	
    public Operand getLeft() {
	return left; 
    }
	
    public Operand getRight() {
	return right; 
    }
	
    public TACVar getDest() {
	return destination; 
    }
	
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

    public void setLeft(Operand left) {
	this.left = left; 
    }

    public void setRight(Operand right) {
	this.right = right; 
    }

	@Override
	public void setDest(TACVar dest) {
		this.destination = dest; 
	}

	public boolean isConstant() {
		return left instanceof Constant && right instanceof Constant; 
	}

}
