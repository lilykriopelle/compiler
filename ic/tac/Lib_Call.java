package ic.tac;

import ic.error.TACError;

import java.util.Vector;

public class Lib_Call extends TACInstr {

	String methodName; 
	Vector<Operand> arguments;
	TACVar dest; 
	
	public Lib_Call(String methodName, Vector<Operand> arguments, TACVar dest) {
		this.methodName = methodName; 
		this.arguments = arguments; 
		this.dest = dest;
	}
	
	public <UpType> UpType accept(TACPropagatingUpVisitor<UpType> v) {
		return v.visit(this);
	}
	
	public void accept(TACVisitor v) throws TACError {
		v.visit(this);
	}

	public String getMethodName() {
		return methodName;
	}

	public Vector<Operand> getArgs() {
		return arguments;
	}

	public TACVar getDest() {
		return dest; 
	}
	
	public <UpType, DownType> UpType accept(TACPropagatingVisitor<UpType, DownType> v,
			DownType t) {
		return v.visit(this, t);
	}
	
	public <UpType, DownType1, DownType2> UpType accept(TACPropagateThreeVisitor<UpType, DownType1, DownType2> v,
			DownType1 t1, DownType2 t2) {
		return v.visit(this, t1, t2);
	}

	public void setParams(Vector<Operand> updatedParams) {
		arguments = updatedParams; 
	}
	
}
