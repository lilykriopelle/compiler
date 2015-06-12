package ic.tac;

import ic.error.OptimizationError;
import ic.error.TACError;
import ic.ast.ArrayTypeNode; 

public class Array_Load extends TACInstr implements AvailableExpr {

    Array_Access array;
    TACVar dest; 

    public Array_Load(TACVar dest, Array_Access array) {
	this.dest = dest; 
	this.array = array;
    }
	
    public <UpType> UpType accept(TACPropagatingUpVisitor<UpType> v) {
	return v.visit(this);
    }

    public boolean accessesField(String field) {
	return false; 
    }

    // Since toString only prints the array access of the instruction, this is
   	// what we want to be hashing on in available expressions.	
    public String toString() {
    	return ((String)array.accept(new TAC_StringGenerator())); 
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
    
    @Override 
    public int hashCode() {
    	return toString().hashCode();
    }
    
    @Override
    public boolean involves(TACVar var) {
    	// If an array is being redefined or the operand containing the value being stored
		// into the array is being redefined, we must remove this expression. 
    	return var.getResolved() != null && var.getResolved().getType() instanceof ArrayTypeNode; 
    }
	
    public void accept(TACVisitor v) throws TACError {
	v.visit(this);
    }

    public Array_Access getArray() {
	return array;
    }

    public TACVar getDest() {
    	return dest; 
    }
	
    public <UpType, DownType> UpType accept(TACPropagatingVisitor<UpType, DownType> v,
					    DownType t) {
	return v.visit(this, t);
    }
	
    public <UpType, DownType1, DownType2> UpType accept(TACPropagateThreeVisitor<UpType, DownType1, DownType2> v, DownType1 t1, DownType2 t2) {
	return v.visit(this, t1, t2);
    }

	@Override
	public void setDest(TACVar dest) {
		this.dest = dest; 			
	}
	
}
