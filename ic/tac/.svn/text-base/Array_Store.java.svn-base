package ic.tac;

import ic.error.OptimizationError;
import ic.error.TACError;
import ic.ast.ArrayTypeNode; 

public class Array_Store extends TACInstr implements AvailableExpr {

    Array_Access array;
    Operand value;

    public Array_Store(Array_Access array, Operand value) {
		this.array = array; 
		this.value = value; 
    }

    // Used by available expressions.
    public TACVar getDest() {
	if(value instanceof TACVar) {
	    return (TACVar)value; 
	} else {
	    return null;
	} 
    }

    public String toString() {
    	return ((String)array.accept(new TAC_StringGenerator())); 
    }

    public boolean accessesField(String field) {
	return false; 
    }

    /** For available expressions, we want to consider array stores
     * and array loads the same way. */
    @Override
    public boolean equals (Object other) {
	if(other instanceof Array_Store) {
	    return array.equals(((Array_Store)other).getArray()); 
	} else if (other instanceof Array_Load) {
	    return array.equals(((Array_Load)other).getArray()); 
	} else {
	    return false; 
	}
    } 
    
    // Since toString only prints the array access of the instruction, this is
   	// what we want to be hashing on in available expressions.
    @Override 
    public int hashCode() {
    	return toString().hashCode();
    }
    
    public boolean involves(TACVar var) {
	// If an array is being redefined or the operand containing the value being stored
	// into the array is being redefined, we must remove this expression. 
	return var.getResolved() != null && var.getResolved().getType() instanceof ArrayTypeNode;// || value.involves(var); 
    } 
    
    public <UpType> UpType accept(TACPropagatingUpVisitor<UpType> v) {
	return v.visit(this);
    }
    
    public void accept(TACVisitor v) throws TACError {
	v.visit(this);
    }

    public Array_Access getArray() {
	return array;
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

    public void setValue(TACVar value) {
    	this.value = value; 	
    }

	@Override
	public void setDest(TACVar dest) {
		if(value instanceof TACVar) {
			value = dest; 
		} else {
			throw new OptimizationError("Trying to update the value into an array store that is not a TACVar.");
		}
	}
}
