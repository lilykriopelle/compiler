package ic.ast;

import ic.error.ScopingError;

public class EmptyStatementNode extends StmtNode {
	public EmptyStatementNode(int line) {
		super(line);
	}
	public String toString() {
		return ""; 
	}
	
	public void accept(Visitor v) throws Exception {
		v.visit(this);
	}
	
	public <UpType,DownType> UpType accept(PropagatingVisitor<UpType,DownType> v, DownType t) throws Exception {
		return v.visit(this, t);
	}
	
	public <DownType> void accept(PropagateDownVisitor<DownType> v, DownType t) throws Exception {
		v.visit(this, t); 
	}
	
	public <UpType> UpType accept(PropagateUpVisitor<UpType> v) {
		return v.visit(this); 
	}
	
}
