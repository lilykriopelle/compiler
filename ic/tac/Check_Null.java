package ic.tac;

import ic.error.OptimizationError;
import ic.error.TACError;

public class Check_Null extends Check {

    Operand operand; 
	
    public Check_Null(Operand operand) {
	assert operand.getOffset() != 0;
	this.operand = operand; 
    }
    
    public String toString() {
	return (String)this.accept(new TAC_StringGenerator()); 
    }

    public <UpType> UpType accept(TACPropagatingUpVisitor<UpType> v) {
	return v.visit(this);
    }
	
    public void accept(TACVisitor v) throws TACError {
	v.visit(this);
    }

    public boolean involves(TACVar var) {
    	System.out.println("VAR: " + var);
    	System.out.println("OPERAND: " + operand);
    	System.out.println("EQUALS? " + operand.involves(var));
    	return operand.involves(var); 
    }

    @Override 
    public int hashCode() {
    	return toString().hashCode();
    }

    @Override
    public boolean equals(Object other) {
	return other instanceof Check_Null 
	    && operand.equals(((Check_Null)other).getToBeChecked()); 
    }

    public Operand getToBeChecked() {
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

    public void setToBeChecked(TACVar check) {
	operand = check; 
    }

	public void setToBeChecked(Constant check) {
		operand = check; 
	}
}
