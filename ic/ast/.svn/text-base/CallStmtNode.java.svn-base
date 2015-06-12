package ic.ast;

import ic.error.ScopingError;
import ic.error.TACError;
import ic.error.TypeError;

public class CallStmtNode extends StmtNode {
	ExprNode call;

	public CallStmtNode(int line, ExprNode call) {
		super(line);
		this.call = call;
	} 
	
	public ExprNode getCall() {
		return call;
	}
	
	public String toString() {
		return call.toString() + ";\n"; 
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
	
	public <UpType> UpType accept(PropagateUpVisitor<UpType> v) throws Exception {
		return v.visit(this); 
	}
	
	
}
