package ic.tac;

import ic.ast.FieldDecl;
import ic.error.CodeError;
import ic.error.TACError;

public class Field_Access extends Operand {
	FieldDecl resolved; 
	Operand receiver;
	TACList checks; 
	
	public Field_Access(Operand receiver, FieldDecl resolved, TACList checks) {
		this.receiver = receiver; 
		this.resolved = resolved; 
		this.checks = checks;
	}

    public boolean involves(TACVar other) {
    	return receiver.involves(other); 
    }

    @Override
    public boolean equals(Object other) {
	if(other instanceof Field_Access) {
	    Field_Access otherF = (Field_Access)other; 
	    return this.getField().equals(otherF.getField())
		&& receiver.equals(otherF.getReceiver()); 
	} else {
	    return false; 
	}
    }

	public Operand getReceiver() {
		return receiver; 
	}
	
	public String getField() {
		return resolved.getName();
	}
	
	public TACList getChecks() {
		return checks; 
	}
	
	public int getFieldOffset() {
		return resolved.getOffset();
	}
	
	public <UpType> UpType accept(TACPropagatingUpVisitor<UpType> v) {
		return v.visit(this);
	}
	
	public void accept(TACVisitor v) throws TACError {
		v.visit(this);
	}
	
	public int getOffset() throws CodeError {
		throw new CodeError("Trying to call get offset on a field access.");
	}
	
	public <UpType, DownType> UpType accept(TACPropagatingVisitor<UpType, DownType> v,
			DownType t) {
		return v.visit(this, t);
	}
	
	public <UpType, DownType1, DownType2> UpType accept(TACPropagateThreeVisitor<UpType, DownType1, DownType2> v,
			DownType1 t1, DownType2 t2) {
		return v.visit(this, t1, t2);
	}

	public void setReceiver(TACVar receiver) {
		this.receiver = receiver; 
	}
}
