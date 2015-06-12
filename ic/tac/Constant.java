package ic.tac;

import ic.ast.Literal;
import ic.error.CodeError;
import ic.error.TACError;

public class Constant extends Operand {
	Literal constant; 
	public Constant(Literal constant) {
		this.constant = constant; 
	}

    public boolean involves(TACVar other) {
    	return false; 
    }
	
	public <UpType> UpType accept(TACPropagatingUpVisitor<UpType> v) {
		return v.visit(this);
	}
	
	public void accept(TACVisitor v) throws TACError {
		v.visit(this);
	}
	
	public Object getValue() {
		return constant.getValue(); 
	}
	
	public String toString() {
		return constant.getValue().toString();
	}
	
	@Override
	public boolean equals(Object other) {
		if(other instanceof Constant) {
			Constant o = (Constant)other;  
			boolean bothNull = (o.getValue() == null && getValue() == null); 
			boolean bothSameConstant = false; 
			if(o.getValue() != null && getValue() != null) {
				bothSameConstant = o.getValue().equals(getValue());
			}
			return bothNull || bothSameConstant; 
		} else {
			return false; 
		}
	}

	@Override
	public int getOffset() throws CodeError {
		//throw new CodeError("Trying to call get offset on a constant.");
		return -1;
	}
	
	public <UpType, DownType> UpType accept(TACPropagatingVisitor<UpType, DownType> v,
			DownType t) {
		return v.visit(this, t);
	}
	
	public <UpType, DownType1, DownType2> UpType accept(TACPropagateThreeVisitor<UpType, DownType1, DownType2> v,
			DownType1 t1, DownType2 t2) {
		return v.visit(this, t1, t2);
	}
}
