package ic.ast;

public class AssignmentNode extends StmtNode {
	LocationNode left; 
	ExprNode right;
	
	public AssignmentNode(int line, LocationNode left, ExprNode right) {
		super(line);
		this.left = left;
		this.right = right;
	} 
	
	public LocationNode getLeft() {
		return left;
	}
	
	public ExprNode getRight() {
		return right;
	}
	
	public void setLeft(LocationNode left) {
		this.left = left; 
	}
	
	public void setType(TypeNode t) {
		left.setType(t); 
	}
	
	public TypeNode getType() {
		return left.getType();
	}
	
	public int getLine() {
		return super.line; 
	}
	
	public LocationNode getLocation() {
		return left; 
	}
	
	
	public void accept(Visitor v) {
		v.visit(this);
	}
	
	public <UpType,DownType> UpType accept(PropagatingVisitor<UpType,DownType> v, DownType t) throws Exception {
		return v.visit(this, t);
	}
	
	public <UpType> UpType accept(PropagateUpVisitor<UpType> v) throws Exception {
		return v.visit(this); 
	}
	
	public <DownType> void accept(PropagateDownVisitor<DownType> v, DownType t) throws Exception {
		v.visit(this, t); 
	}
	
	
}
