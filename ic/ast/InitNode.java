package ic.ast;

import ic.error.ScopingError;
import ic.error.TACError;
import ic.error.TypeError;

public class InitNode extends StmtNode {

	VarDecl left; 
	ExprNode right;
		
	public InitNode(int line, VarDecl left, ExprNode right) {
		super(line);
		this.left = left;
		this.right = right;
	} 
		
	public VarDecl getLeft() {
		return left;
	}
		
	public ExprNode getRight() {
		return right;
	}
		
	public void setLeft(VarDecl left) {
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
			
	 
	public String toString() {
		if (right != null) {
			return left + " = " + right + ";";
		} else {
			return left + ";"; 
		}
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

