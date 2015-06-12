package ic.tac;

import ic.error.TACError;

public class TAC_Comment extends TACInstr {

	String comment; 
	
	public TAC_Comment(String comment) {
		this.comment = comment; 
	}
	
	public <UpType> UpType accept(TACPropagatingUpVisitor<UpType> v) {
		return v.visit(this);
	}
	
	public void accept(TACVisitor v) throws TACError {
		v.visit(this);
	}

	public String getComment() {
		return comment; 
	}
	
	public String toString() {
		return "# " + comment; 
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
