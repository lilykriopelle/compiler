package ic.tac;

public interface LdStr {
	public TACVar getDest();
	public Operand getValue(); 
	public boolean isCopy();
	public Constant getConstant(); 
}
