package ic.tac;

// Used for reaching copies. 
public class Copy {
	TACVar left; 
	TACVar right;
	
	public Copy(TACVar left, TACVar right) {
		this.left = left; 
		this.right = right; 	
	}
	
	public Copy(LdStr copy) {
		this.left = copy.getDest(); 
		this.right = (TACVar)copy.getValue(); 
	}
	
	@Override 
	public boolean equals(Object other) {
		if (!(other instanceof Copy)) {
			return false;
		} else {
			return left.equals(((Copy)other).getLeft()) && right.equals(((Copy)other).getRight());  
		}
	}
	
	public TACVar getLeft() {
		return left; 
	}
	
	public TACVar getRight() {
		return right; 
	}
	
	@Override
	public String toString() {
		return left.getName() + " = " + right.getName(); 
	}
}
