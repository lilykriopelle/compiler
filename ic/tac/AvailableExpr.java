package ic.tac;

public interface AvailableExpr {

	@Override
	/** 
	Given another instruction, this method returns true if the expression contained
	in the instruction is equal.  To determine equality, we hash on the string representation
	of the expression and compare the two hash codes.  The only exception to this is that when
	determining the equality of two binary expressions, we compare the equality of their 
	operators and then allow equal operands to appear in either order (so x + y is equal to 
	y + x, for example).  One concern about this choice is that the two expressions will not
	be evaluated as equal if they are being compared by their hashCode, since the string
	representations are not the same.  
	 */
	public boolean equals(Object other);

	/** 
	 * We always hash on the string representation of the available expression.
	 * */
	@Override
	public int hashCode();
	
	/**
	 * Returns true if this available expression contains var, and so should be
	 * killed when var is redefined.
	 */
	public boolean involves(TACVar var);

	/**
	 * This will only be true for field accesses which involve the specified
	 * field. Used when killing available expressions after field stores.
	 */
	public boolean accessesField(String field);

	/**
	 * Returns the variable in which the available expression is stored. For
	 * field and array stores, this is counter-intuitive, because the value
	 * being stored into the array or field is used as the destination if it is
	 * a TACVar.
	 */
	public TACVar getDest();

	/**
	 * Sets the available expression's destination.
	 */
	public void setDest(TACVar dest);

}