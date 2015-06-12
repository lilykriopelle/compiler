package ic.ast;

import ic.error.ScopingError;
import ic.error.TACError;

public class StringLiteralNode extends ExprNode implements Literal {
	String value;

	public StringLiteralNode(int line, String value) {
		super(line); 
		super.setType(new StringTypeNode(line));
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	 
	public String toString() {
		return "\""+ value + "\"";
	}	
	
	 
	public void accept(Visitor v) {
		v.visit(this);
	}
	
	 
	public <UpType,DownType> UpType accept(PropagatingVisitor<UpType,DownType> v, DownType t) throws Exception {
		return v.visit(this, t);
	}
	
	 
	public <DownType> void accept(PropagateDownVisitor<DownType> v, DownType t) throws Exception {
		v.visit(this, t); 
	}
	
	 
	public <UpType> UpType accept(PropagateUpVisitor<UpType> v) throws Exception {
		return v.visit(this); 
	}
	
	
}
