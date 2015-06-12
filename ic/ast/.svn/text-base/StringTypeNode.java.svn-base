package ic.ast;

import ic.error.ScopingError;

public class StringTypeNode extends TypeNode {

	public StringTypeNode(int line) {
		super(line);	
	}
	
	 
	public boolean isEqualTo(TypeNode t) {
		if(t instanceof StringTypeNode) {
			return true; 
		} else {
			return false; 
		}
	}

	 
	public String toString() {
		return "string";
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

