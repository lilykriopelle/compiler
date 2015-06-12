package ic.ast;

public class VarAccessNode extends LocationNode {

	VarDecl resolved = null;
	
	public VarAccessNode(int line, String name) {
		super(line, name);
	}
	
	public String toString() {
		return name; 
	}
	
	 
	public void accept(Visitor v) {
		v.visit(this);
	}
	
	 
	public <UpType,DownType> UpType accept(PropagatingVisitor<UpType,DownType> v, DownType t) {
		return v.visit(this, t);
	}
	
	 
	public <DownType> void accept(PropagateDownVisitor<DownType> v, DownType t) throws Exception {
		v.visit(this, t); 
	}

	public void resolve(VarDecl resolved) {
		this.resolved = resolved; 
		type = resolved.getType();
	}
	
	public VarDecl getResolved() {
		return resolved; 
	}
	
	 
	public <UpType> UpType accept(PropagateUpVisitor<UpType> v) throws Exception {
		return v.visit(this); 
	}
	
	
}
