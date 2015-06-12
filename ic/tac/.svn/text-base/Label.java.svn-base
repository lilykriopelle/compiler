package ic.tac;

public class Label extends TACInstr {

	String labelName; 
	
	public Label(String labelName) {
		this.labelName = labelName; 
	}
	
	public String getLabelName() {
		return labelName;
	}
	
	public String toString() {
		return labelName; 
	}
	
	public <UpType> UpType accept(TACPropagatingUpVisitor<UpType> v) {
		return v.visit(this);
	}
	
	public void accept(TACVisitor v) {
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

}
