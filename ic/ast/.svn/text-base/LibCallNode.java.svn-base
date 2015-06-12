package ic.ast;

import ic.error.ScopingError;
import ic.error.TACError;
import ic.error.TypeError;

import java.util.Vector;

public class LibCallNode extends ExprNode {
	String methodName; 
	Vector<ExprNode> actualParams; 
	public LibCallNode(int line,
			String methodName, Vector<ExprNode> actualParams) {
		super(line);
		this.methodName = methodName;
		this.actualParams = actualParams;
	}
	
	public String getMethod() {
		return methodName;
	}
	
	public Vector<ExprNode> getArguments() {
		return actualParams;
	}
	
	 
	public String toString() {
		String toString = "Library." + methodName + "(" + actualParams.get(0).toString(); 
		for (int i = 1; i < actualParams.size(); ++i) {
			toString += ", " + actualParams.get(i).toString(); 
		}
		toString += ")";
		return toString; 
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
