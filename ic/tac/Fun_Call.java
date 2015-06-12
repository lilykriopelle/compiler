package ic.tac;

import ic.ast.MethodDecl;
import ic.error.TACError;

import java.util.Vector;

public class Fun_Call extends TACInstr {

	Vector<Operand> paramRegs; 
	MethodDecl methodDecl;
	TACVar dest; 
	Operand receiver; 
	private static int numFuns = 0; 
	private int id; 
	
	public Fun_Call(Operand receiver, MethodDecl resolvedMethod, Vector<Operand> paramRegs, TACVar dest) {
		this.receiver = receiver; 
		this.paramRegs = paramRegs;
		this.methodDecl = resolvedMethod; 
		this.dest = dest;
		id = numFuns; 
		numFuns++;
	}
	
	public int getFrameSize() {
		return methodDecl.getFrameSize(); 
	}
	
	public Operand getReceiver() {
		return receiver; 
	}

	public int getMethodNum() {
		return methodDecl.getNum(); 
	}
	
	public Vector<Operand> getParams() {
		return paramRegs; 
	}
	
	public String getMethodName() {
		return methodDecl.getScope().getEnclosingScope().getName() + "_" + methodDecl.getName(); 
	}
	
	public int getMethodOffset() {
		return methodDecl.getOffset();
	}
	
	public <UpType, DownType1, DownType2> UpType accept(TACPropagateThreeVisitor<UpType, DownType1, DownType2> v,
			DownType1 t1, DownType2 t2) {
		return v.visit(this, t1, t2);
	}
	
	public <UpType> UpType accept(TACPropagatingUpVisitor<UpType> v) {
		return v.visit(this);
	}
	
	public void accept(TACVisitor v) throws TACError {
		v.visit(this);
	}


	public TACVar getDest() {
		return dest;
	}
	
	public <UpType, DownType> UpType accept(TACPropagatingVisitor<UpType, DownType> v,
			DownType t) {
		return v.visit(this, t);
	}

	public void setReceiver(TACVar receiver) {
		this.receiver = receiver; 
	}

	public void setParams(Vector<Operand> updatedParams) {
		paramRegs = updatedParams; 
		
	}

	public int getFunCallID() {
		return id;
	}
}
