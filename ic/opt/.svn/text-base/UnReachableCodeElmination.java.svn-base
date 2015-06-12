package ic.opt;

import ic.ast.*;
import ic.cfg.BasicBlock;
import ic.cfg.ControlFlowGraph;
import ic.dfa.ReachableAnalysis;
import ic.tac.TACInstr;
import ic.tac.TACList;
import ic.tac.TAC_Comment;

/**
 * Illustrates an Optimization that removes unreachable code.
 * This shows how to stage creating a CFG, performing analysis,
 * and then transforming the code based on the results.
 */
public class UnReachableCodeElmination extends Optimization {

	public String getName() {
		return "UR"; 
	}
	
	/**
	 * Apply the optimization to the method md.
	 */
	public void optimize(MethodDecl md) {
		ControlFlowGraph cfg = null; // TODO: fill me in.
		
		ReachableAnalysis reaches = new ReachableAnalysis(cfg);
		reaches.solve();

		TACList optimized = new TACList();
		
		// Map each block to either the original instruction 
		// or a comment if not reachable.  I could have filtered out the
		// dead code too, but it is easier to read and debug the
		// optimization if you leave comments in where you changed the code
		for (TACInstr i : md.getTAC().getInstrs()) {
			BasicBlock b = cfg.getBlock(i);
			if (reaches.in(b)) {
				optimized.add(i);
			} else {
				optimized.add(new TAC_Comment("Removed dead code: " + i));
			}
		}
		
		md.setInstrs(optimized);
	}
	
}
