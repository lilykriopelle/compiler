package ic.tac;

import ic.ast.Decl;
import ic.error.TACError;

public abstract class TACVar extends Operand {
	@Override
	public boolean equals(Object other) {
		if(other instanceof TACVar) {
			return ((TACVar)other).getName().equals(this.getName());
		} else {
			return false; 
		}
	}
	
	// So that hashing works properly we hash on the string name of the variable. 
	@Override
	public int hashCode() {
		return this.getName().hashCode();
	}
	
	public abstract String getName(); 
	public abstract <UpType> UpType accept(TACPropagatingUpVisitor<UpType> v);
	
	public abstract void accept(TACVisitor v) throws TACError;
	
	public abstract <UpType, DownType> UpType accept(TACPropagatingVisitor<UpType, DownType> v,
			DownType t);
	
	public abstract <UpType, DownType1, DownType2> UpType accept(TACPropagateThreeVisitor<UpType, DownType1, DownType2> v,
			DownType1 t1, DownType2 t2);
	
	public abstract int getOffset();
	public abstract Decl getResolved(); 
}
