package ic.tac;

import ic.error.OptimizationError;
import ic.error.TACError;


public class Field_Store extends TACInstr implements AvailableExpr {

    Operand value; 
    Field_Access field; 
	
    public Field_Store(Field_Access field, Operand value) {
	this.field = field; 
	this.value = value;
    }

    /** For available expressions, we want to consider field stores
     * and field loads the same way. */
    @Override
    public boolean equals (Object other) {
    	return this.hashCode() == other.hashCode(); 
    }
    
    @Override 
    public String toString() {
    	return (String)field.accept(new TAC_StringGenerator()); 
    }
    
    // Since toString only prints the field access of the instruction, this is
   	// what we want to be hashing on in available expressions.
    @Override 
     public int hashCode() {
    	return toString().hashCode();
     }
    
    // Used by available expressions. 
    public TACVar getDest() {
    	if(value instanceof TACVar) return (TACVar)value; 
		else return null; 
    }

    public boolean accessesField(String f) {
	return field.getField().equals(f);
    }
    
    public boolean involves(TACVar var) {
		return false;
    }

    public <UpType> UpType accept(TACPropagatingUpVisitor<UpType> v) {
	return v.visit(this);
    }
	
    public void accept(TACVisitor v) throws TACError {
	v.visit(this);
    }
	
    public Field_Access getField() {
    	return field; 
    }
	
    public Operand getValue() {
	return value;
    }
	
    public <UpType, DownType> UpType accept(TACPropagatingVisitor<UpType, DownType> v,
					    DownType t) {
	return v.visit(this, t);
    }

    public <UpType, DownType1, DownType2> UpType accept(TACPropagateThreeVisitor<UpType, DownType1, DownType2> v,
							DownType1 t1, DownType2 t2) {
	return v.visit(this, t1, t2);
    }

	@Override
	public void setDest(TACVar dest) {
		if(value instanceof TACVar) {
			value = dest; 
		} else {
			throw new OptimizationError("Trying to update the value into a field store that is not a TACVar.");
		}
	}

//	public void setValue(TACVar value) {
//		this.value = value; 
//	}
}
