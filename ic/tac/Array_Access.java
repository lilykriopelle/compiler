package ic.tac;

import ic.error.CodeError;
import ic.error.TACError;
import ic.ast.ArrayTypeNode; 

public class Array_Access extends Operand {
	Operand array;
	Operand index;
	TACList checks;
	
	public Array_Access(Operand array, Operand index, TACList checks) {
		this.array = array;
		this.index = index;
		this.checks = checks; 
	}

    public boolean involves(TACVar other) {
    	return array.involves(other) || index.involves(other); 
    }

    @Override
    public boolean equals(Object other) {
	if(other instanceof Array_Access) {
	    return array.equals(((Array_Access)other).getArray()) 
		&& index.equals(((Array_Access)other).getIndex()); 
	} else {
	    return false; 
	}
    }

	public Operand getArray() {
		return array;
	}
	
	public Operand getIndex() {
		return index;
	}
	
	public <UpType> UpType accept(TACPropagatingUpVisitor<UpType> v) {
		return v.visit(this);
	}
	
	public void accept(TACVisitor v) throws TACError {
		v.visit(this);
	}

	public TACList getChecks() {
		return checks;
	}
	
	@Override
	public int getOffset() {
		throw new CodeError("Trying to get the offset of an array access.");
	}
	
	public <UpType, DownType> UpType accept(TACPropagatingVisitor<UpType, DownType> v,
			DownType t) {
		return v.visit(this, t);
	}
	
	public <UpType, DownType1, DownType2> UpType accept(TACPropagateThreeVisitor<UpType, DownType1, DownType2> v,
			DownType1 t1, DownType2 t2) {
		return v.visit(this, t1, t2);
	}

	public void setArray(TACVar array) {
		this.array = array; 
	}

	public void setIndex(Operand index) {
		this.index = index; 
	}
	
}
