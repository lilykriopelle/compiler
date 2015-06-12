package ic.tac;

import ic.error.OptimizationError;
import ic.error.TACError;

/** This check ensures that the value of the index is less than or
 *  equal to that of the array length. If array access is flag is
 *  false, then this check is being used to see if the length of a
 *  newly instantiated array is positive.  */
public class Check_Bounds extends Check {
    Operand index; 
    Operand array;
	
    public Check_Bounds(Operand index, Operand array) {
	this.index = index; 
	this.array = array; 
    }
	
    public <UpType> UpType accept(TACPropagatingUpVisitor<UpType> v) {
	return v.visit(this);
    }

    public String toString() {
	return (String)this.accept(new TAC_StringGenerator()); 
    }

    @Override
    public boolean involves(TACVar var) {
    	return index.involves(var) || array.involves(var); 
    }
    
     @Override 
     public int hashCode() {
     	return toString().hashCode();
     }

    @Override
    public boolean equals(Object other) {
	if(other instanceof Check_Bounds) {
	    if(array.equals(((Check_Bounds)other).getArray())) {
		Operand otherIndex = ((Check_Bounds)other).getIndex();
		if(index.equals(otherIndex)) {
		    return true; 
		} else {
		    // If the other index is greater than the current
		    // one, then it effectively performs this check as
		    // well.
		    if(index instanceof Constant && otherIndex instanceof Constant) {
		    	return ((Integer)((Constant)otherIndex).getValue()) >= ((Integer)((Constant)index).getValue()); 
		    } else {
		    	return false; 
		    }
		}
	    } else {
		return false;
	    }
	} else {
	    return false; 
	}
    }

    public void accept(TACVisitor v) throws TACError {
	v.visit(this);
    }

    public Operand getArray() {
	return array; 
    }

    public Operand getIndex() {
	return index; 
    }
	
    public <UpType, DownType> UpType accept(TACPropagatingVisitor<UpType, DownType> v,
					    DownType t) {
	return v.visit(this, t);
    }
	
    public <UpType, DownType1, DownType2> UpType accept(TACPropagateThreeVisitor<UpType, DownType1, DownType2> v,
							DownType1 t1, DownType2 t2) {
	return v.visit(this, t1, t2);
    }

    public void setIndex(Operand index) {
	this.index = index; 		
    }

    public void setArray(Operand array) {
	this.array = array;
    }

}
