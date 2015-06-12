package ic.tac;

import ic.error.OptimizationError;
import ic.error.TACError;
import java.util.LinkedList;
import java.util.Stack;

public class TACList {

	LinkedList<TACInstr> instrs = new LinkedList<TACInstr>(); 
	int index = -1; //used when creating new temporary variables in common subexpression elimination. 
	Stack<TACVar> stackFrame; 
	
	public TACList() {
		
	}
	
	public TACList(LinkedList<TACInstr> instrs) {
		this.instrs = instrs;
	}

	
	public TACList(TACInstr t) {
		instrs.add(t); 
	}
	
	public void add(TACInstr instr) {
		instrs.add(instr);
	}
	
	public void add(TACList newInstrs) {
		instrs.addAll(newInstrs.getInstrs());
	}
	
	public TACInstr getLast() {
		return instrs.getLast();
	}
	
	public LinkedList<TACInstr> getInstrs() {
		return instrs; 
	}

	/* Iterates through the instructions in the list, utilizing the TAC_Printer to print
	 * them. 
	 */
	public void print(TAC_Printer p) throws TACError {
		for(TACInstr i: instrs) {
			i.accept(p);
		}
	}

	public void removeInstr(TACInstr old) {
		for(int i = 0; i < instrs.size(); ++i) { 
			TACInstr cur = instrs.get(i); 
			if(cur == old) {
				instrs.remove(i); 
				break; 
			}
		}
	}
	/* Removes the old instruction and replaces it with the new optimized version. 
	 * This is useful for optimizations like dead code elimination, which replaces
	 * unnecessary stores with no-ops. 
	 */
	public void updateInstr(TACInstr old, TACInstr optimizedInstr) {
		// Since we have overridden the .equals of AvailableExprs to only look
		// at the right side of the instruction but we now want to consider the
		// instruction in its entirety we must manually go through the instructions 
		// to find the one which should be optimized. 
		for(int i = 0; i < instrs.size(); ++i) { 
			TACInstr cur = instrs.get(i); 
			if(cur == old) {
				instrs.set(i, optimizedInstr);
				break; 
			}
		}
	}

	
	public TempVariable getRegister() {
		if(index != -1) {
			int oldIndex = index;
			index++; 
			TempVariable temp = new TempVariable(oldIndex, false);
			stackFrame.add(temp); 
			return temp; 
		} else {
			throw new OptimizationError("Generating an invalid TempVariable in the first pass of cse.");
		}
	}
	
	public void setIndex(int index) {
		this.index = index; 
	}

	public void setInstrs(LinkedList<TACInstr> instrs) {
		this.instrs = instrs; 
	}
	
	// Update the destination of the expr to the new temporary variable
	// and then add a load of the temporary into the original destination.
	public void introduceNewTemp(AvailableExpr toUpdate, TempVariable temp) {
		if (toUpdate instanceof Array_Store || toUpdate instanceof Field_Store) {
			introduceNewTempForStore(toUpdate, temp);
		} else {
			introduceNewTempForLoad(toUpdate, temp);
		}
	}
		
	private void introduceNewTempForStore(AvailableExpr toUpdate, TempVariable temp) {

		int i = 0;
		while (i < instrs.size()) {
			TACInstr cur = instrs.get(i);
			if (cur == toUpdate) {
				break;
			}
			i = i + 1;
		}
		/*
		 * temp = originalVarToBeStored; 
		 * o.field = temp;
		 */
		instrs.add(i, new Load(temp, toUpdate.getDest())); // temp = originalVarToBeStored;
		toUpdate.setDest(temp); // o.field = temp;
		
	}
	
	private void introduceNewTempForLoad(AvailableExpr toUpdate, TempVariable temp) {
		if(! (toUpdate instanceof Load)) {	
			int i = 0; 
			while(i < instrs.size()) {
				TACInstr cur = instrs.get(i); 
				if(cur == toUpdate) {
					break;
				}
				i = i + 1; 
			}
			if(i != instrs.size()) { /** NOT FOUND */
			
				/* t = 5 + 3
				 * x = t
				 */
				
				TACVar initialDest = toUpdate.getDest();
				toUpdate.setDest(temp); 
				instrs.add(i, (TACInstr) toUpdate);
				
				Load ld = new Load(initialDest, temp); 
				instrs.set(i + 1, ld);
			} else {
				throw new OptimizationError("\nAVAILABLE EXPR NOT FOUND: " + toUpdate);
			}
		} 
	}

	public Stack<TACVar> getStackFrame() {
		return stackFrame; 
	}

	public void setStackFrame(Stack<TACVar> stackFrame) {
		this.stackFrame = stackFrame; 
	}
	
}
