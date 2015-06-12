package ic.tac;

import ic.ast.Decl;
import ic.error.TACError;

public class ProgramVariable extends TACVar {
	Decl resolved; 
	int offset;
	
	public ProgramVariable(Decl resolved, int offset) {
		assert offset != 0;
		this.resolved = resolved;
		this.offset = offset;
	}

	@Override
	public <UpType> UpType accept(TACPropagatingUpVisitor<UpType> v) {
		return v.visit(this);
	}
	

    public boolean involves(TACVar other) {
    	return this.equals(other); 
    }

	public void accept(TACVisitor v) throws TACError {
		v.visit(this);
	}

	public String getName() {
		return resolved.getName();
	}
	
	public String toString() {
		return resolved.getName();
	}

	@Override
	public int getOffset() {
		return offset; 
	}
	
//	@Override 
//	public boolean equals(Object other) {
//		if(other instanceof ProgramVariable) {
//			return ((ProgramVariable)other).getName().equals(this.getName());  
//		} else {
//			return false; 
//		}
//	}
	
	@Override
	public <UpType, DownType> UpType accept(TACPropagatingVisitor<UpType, DownType> v,
			DownType t) {
		return v.visit(this, t);
	}
	
	@Override
	public <UpType, DownType1, DownType2> UpType accept(TACPropagateThreeVisitor<UpType, DownType1, DownType2> v,
			DownType1 t1, DownType2 t2) {
		return v.visit(this, t1, t2);
	}

	@Override
	public Decl getResolved() {
		return resolved; 
	}
}
