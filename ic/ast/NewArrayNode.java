package ic.ast;

import ic.error.ScopingError;
import ic.error.TACError;
import ic.error.TypeError;

public class NewArrayNode extends ExprNode {
	ExprNode size; 
	TypeNode elementType;
	public NewArrayNode(int line, TypeNode type, ExprNode size) {
		super(line);
		elementType = type;
		super.setType(new ArrayTypeNode(line, elementType));
		this.size = size; 
	}

	public TypeNode getElementType() {
		return elementType;
	}
	public ExprNode getSize() {
		return size; 
	}
	
	 
	public String toString() {
		return "new " + ((ArrayTypeNode)super.getType()).getElementType() + "[" + size + "]";
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
