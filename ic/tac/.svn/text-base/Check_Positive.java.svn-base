package ic.tac;

import ic.error.OptimizationError;
import ic.error.TACError;

public class Check_Positive extends Check {

    Operand operand; 
    private boolean isNegative = false; 
	
    public Check_Positive(Operand operand) {
	this.operand = operand; 
    }
	
    public <UpType> UpType accept(TACPropagatingUpVisitor<UpType> v) {
	return v.visit(this);
    }

    public String toString() {
	return (String)this.accept(new TAC_StringGenerator()); 
    }

    public boolean involves(TACVar var) {
	return operand.involves(var); 
    }
    
     @Override 
     public int hashCode() {
     	return toString().hashCode();
     }

    @Override 
	public boolean equals(Object other) {
	return (other instanceof Check_Positive) 
	    && operand.equals(((Check_Positive)other).getLength());  
    }
    public void accept(TACVisitor v) throws TACError {
	v.visit(this);
    }

    public Operand getLength() {
	return operand;
    }
	
    public <UpType, DownType> UpType accept(TACPropagatingVisitor<UpType, DownType> v,
					    DownType t) {
	return v.visit(this, t);
    }
	
    public <UpType, DownType1, DownType2> UpType accept(TACPropagateThreeVisitor<UpType, DownType1, DownType2> v,
							DownType1 t1, DownType2 t2) {
	return v.visit(this, t1, t2);
    }

    public void setLength(Operand length) {
	this.operand = length; 
    }

	public void setNegative() {
		isNegative = true; 
	}
	
	public boolean isNegative() {
		return isNegative; 
	}
	
}
