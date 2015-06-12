package ic.tac;

import ic.error.TACError;

public class Return extends TACInstr {
	Operand operand; 
	int sizeOfStack; 
	
	public Return(Operand op, int index) {
		this.operand = op; 
		sizeOfStack = 8 + 8 * index; 
	}

	public <UpType> UpType accept(TACPropagatingUpVisitor<UpType> v) {
		return v.visit(this);
	}
	
	public void accept(TACVisitor v) throws TACError {
		v.visit(this);
	}

	public Operand getExpr() {
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

	public void setExpr(Operand expr) {
		operand = expr; 
	}
}
