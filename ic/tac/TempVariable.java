package ic.tac;
import ic.ast.Decl;

public class TempVariable extends TACVar {
	String register; 
	int offset; 
	boolean empty = false; 
	Decl resolved = null; 
	boolean isParam; 
	
	public TempVariable(int register, boolean isParam) {
		this.register = "t" + register; 
		this.isParam = isParam; 
		if(isParam) {
			this.offset = register * 8;
		} else {
			this.offset = -8 - register * 8;
		}
	}

    public boolean involves(TACVar other) {
    	return this.equals(other); 
    }
    
    // Returns true if the temporary variable refers to 
    // the current object ie is "this"
    public boolean isThis() {
    	return offset == 16; 
    }

	// Used when constructing a temporary variable that should never be used 
	public TempVariable(String message) {
		register = message;
		empty = true; 
	}
	
	public void resolve(Decl resolved) {
		this.resolved = resolved;  
	}


	public String getName() {
		return register; 
	}
	
	public <UpType> UpType accept(TACPropagatingUpVisitor<UpType> v) {
		return v.visit(this);
	}
	
	public <UpType, DownType> UpType accept(TACPropagatingVisitor<UpType, DownType> v,
			DownType t) {
		return v.visit(this, t);
	}
	
	public void accept(TACVisitor v) {
		v.visit(this);
	}
	
	public boolean isEmpty() {
		return empty; 
	}
	
	public String toString() {
		return register; 
	}

	@Override
	public int getOffset() {
		return offset; 
	}
	
	public <UpType, DownType1, DownType2> UpType accept(TACPropagateThreeVisitor<UpType, DownType1, DownType2> v,
			DownType1 t1, DownType2 t2) {
		return v.visit(this, t1, t2);
	}

	@Override
	public Decl getResolved() {
		return resolved;
	}

	public boolean isParam() {
		return isParam;
	}
	
}
