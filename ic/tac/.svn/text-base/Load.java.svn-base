package ic.tac;

import ic.error.OptimizationError;
import ic.error.TACError;

public class Load extends TACInstr implements LdStr, AvailableExpr {
	TACVar dest; 
	Operand right; 
	public Load(TACVar dest, Operand right) {
		this.dest = dest; 
		this.right = right;
	}
	
	@Override
	public boolean isCopy() {
		return right instanceof TACVar; 
	}
	
	public TACVar getDest() {
		return dest;
	}
	
	public Operand getValue() {
		return right; 
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

	public void setValue(Operand con) {
		this.right = con; 
		
	}

	@Override
	public Constant getConstant() {
		if(right instanceof Constant) {
			return (Constant)right; 
		} else {
			return null; 
		}
	}

	@Override
	public boolean involves(TACVar var) {
		throw new OptimizationError("Treating load as available expression.");
	}

	@Override
	public boolean accessesField(String field) {
		throw new OptimizationError("Treating load as available expression.");
	}

	@Override
	public void setDest(TACVar dest) {
		this.dest = dest; 
	}
	
}
