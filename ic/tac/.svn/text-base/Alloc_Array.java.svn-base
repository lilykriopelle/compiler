package ic.tac;

import ic.error.TACError;
import ic.error.OptimizationError; 

public class Alloc_Array extends Allocation {
	
	Operand length; 
	TACVar dest; 
	
	public Alloc_Array(Operand length, TACVar dest) {
		this.length = length; 
		this.dest = dest; 
	}

    public boolean involves(TACVar other) {
	throw new OptimizationError("Allocating an array should not be an available expression");
    }
	
	public TACVar getDest() {
		return dest; 
	}
	
	public <UpType> UpType accept(TACPropagatingUpVisitor<UpType> v) {
		return v.visit(this);
	}
	
	public void accept(TACVisitor v) throws TACError {
		v.visit(this);
	}

	public Operand getLength() {
		return length;
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
		this.length = length; 
		
	}
	
}
