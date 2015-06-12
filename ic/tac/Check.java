package ic.tac;

public abstract class Check extends TACInstr implements AvailableExpr {

	@Override
	public abstract boolean involves(TACVar var);

	@Override
	public boolean accessesField(String field) {
		return false;
	}

	@Override
	public TACVar getDest() {
		return null;
	}

	@Override
	public void setDest(TACVar dest) {}

}
