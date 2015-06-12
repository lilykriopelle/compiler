package ic.dfa;

import ic.error.OptimizationError;
import ic.tac.Check;
import ic.tac.Operand;
import ic.tac.TACVar;
import java.util.Vector;
import ic.tac.AvailableExpr;

/**
 * This class basically serves as a wrapper of the set of instructions, which
 * produce a particular available expression.
 */

public class GeneratingInstrs {
	private Vector<AvailableExpr> generatingInstrs;

	public GeneratingInstrs() {
		generatingInstrs = new Vector<AvailableExpr>();
	}

	public GeneratingInstrs(AvailableExpr instr) {
		generatingInstrs = new Vector<AvailableExpr>();
		generatingInstrs.add(instr);
	}

	
	// Adds the instruction to the Vector. 
	// Only add this if it is unique i.e. it writes the expression to 
	// a different variable than other generating instructions. 
	public void add(AvailableExpr instr) {
		if(!contains(instr)) {
			generatingInstrs.add(instr);
		}
	}

	// Adds together all instructions that compute a particular
	// expression, used by the meet of Available Expressions.
	// It uses the add of GeneratingInstrs to only add unique instructions. 
	public void addInstrs(GeneratingInstrs others) {
		for(AvailableExpr cur: others.getInstrs()) {
			add(cur);
		}
	}

	public Vector<AvailableExpr> getInstrs() {
		return generatingInstrs;
	}

	// Returns true if another expression in this set computes the
	// same expr and puts it in the same destination.
	public boolean contains(AvailableExpr instr) {
		TACVar dest = instr.getDest();
		for (AvailableExpr cur : generatingInstrs) {
			if (cur.equals(instr) && cur instanceof Check) { 
				return true; 
			} else if (cur.equals(instr) && cur.getDest().equals(dest)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof GeneratingInstrs) {
			GeneratingInstrs other = (GeneratingInstrs) o;
			if (other.getInstrs().size() != generatingInstrs.size()) {
				return false;
			}
			
			for (AvailableExpr cur : generatingInstrs) {
				if (!other.contains(cur)) {
					return false;
				}
			}
			
			return true;
		} else {
			return false;
		}
	}
	
	// This method is used by common subexpression elimination for replacing
	// expressions with the variables in which they have been stored earlier in
	// the program. Since in the first pass, we replace multiple definitions
	// with an assignment to a temporary value, we know that all the generating
	// instructions will only contain the instruction that defines the temporary.
	// We cast this to an Operand since this is what is expected in the place of 
	// expressions. 
	public TACVar get() {
		if(generatingInstrs.size() > 0) {
			return generatingInstrs.get(0).getDest(); 
		} else {
			throw new OptimizationError("Trying to get an element from an empty generating instrs list in cse.");
		}
	}

	public String elementString(AvailableExpr expr) {
		if (expr.getDest() == null) {
			return expr.toString();
		} else {
			return expr.getDest().toString() + " = " + expr.toString();
		}
	}

	@Override
	public String toString() {
		if(generatingInstrs.size() == 0) return "[]"; 
		else {
			String toString = ":: " + elementString(generatingInstrs.get(0));
			for (int i = 1; i < generatingInstrs.size(); i++) {
				toString += ", " + elementString(generatingInstrs.get(i));
			}
			return toString;
		}
	}

	public AvailableExpr get(int i) {
		return generatingInstrs.get(i);
	}
	
}