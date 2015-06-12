package ic.dfa;

import ic.cfg.ControlFlowGraph;
import ic.tac.TACInstr;

/**
 * A simple analysis to determine whether there are
 * unreachable statements in a program.  That is it, 
 * determines whether there is a path from the entry to 
 * each program point.  For example:
 * <pre>
 * void main(string[] a) {
 *   boolean b;
 *   int y,z;
 *   if (b) {
 *      return;
 *      z = z + 1;  // NOT REACHABLE
 *   } 
 *   y = y + 1;
 * }
 * </pre>	
 * In this code, the assignment following the return is not reachable.
 * The analysis is defined as follows:
 * <pre>
 *   D  : forward
 *   V  : { true, false }  where true < false.  
 *   Top: false.
 *   /\ : boolean ||
 *   f  : f_I(s) = s for all instructions I
 *   boundary:  OUT[in] = true
 * </pre>
 * After dataflow analysis, unreachable blocks will have IN[b] == false.
 */
public class ReachableAnalysis extends DataFlowAnalysis<Boolean>  {

	public ReachableAnalysis(ControlFlowGraph cfg) {
		super(cfg);
	}
	
	public String getName() {
		return "RA";
	}

	public Boolean boundary() {
		return true;
	}

	public Boolean top() {
		return false;
	}

	public boolean equals(Boolean t1, Boolean t2) {
		return t1.equals(t2);
	}

	public boolean isForward() {
		return true;
	}

	public Boolean meet(Boolean t1, Boolean t2) {
		return t1 || t2;
	}

	public Boolean transfer(TACInstr instr, Boolean t) {
		return t;
	}


}
