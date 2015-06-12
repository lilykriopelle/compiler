package ic.ast;

import ic.error.ScopingError;

public class VoidTypeNode extends TypeNode {
	public VoidTypeNode(int line) {
		super(line);
	}
	
	public String toString() {
		return "void"; 
	}
	
	  
	public boolean isEqualTo(TypeNode t) {
		if(t instanceof VoidTypeNode) {
			return true;
		} else {
			return false; 
		}
	}
	
	 
	public void accept(Visitor v) {
		v.visit(this);
	}
	
	 
	public <UpType,DownType> UpType accept(PropagatingVisitor<UpType,DownType> v, DownType t) throws Exception {
		return v.visit(this, t);
	}
	
	 
	public <DownType> void accept(PropagateDownVisitor<DownType> v, DownType t) throws ScopingError {
		v.visit(this, t); 
	}
	
	 
	public <UpType> UpType accept(PropagateUpVisitor<UpType> v) {
		return v.visit(this); 
	}
	
}
