package ic.tac;

import ic.error.TACError;

public class Store extends TACInstr implements LdStr {

	Operand value; 
	TACVar reg; 
	
	public Store(TACVar reg, Operand value) {
		this.reg = reg; 
		this.value = value;
	}
	
	@Override
	public boolean isCopy() {
		return value instanceof TACVar; 
	}
	
	public TACVar getDest() {
		return reg;
	}
	
	public Constant getConstant() {
		if(value instanceof Constant) {
			return (Constant)value; 
		} else {
			return null; 
		}
	}
	
	public Operand getValue() {
		return value; 
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

	public void setValue(Operand value) {
		this.value = value; 
	}

}
