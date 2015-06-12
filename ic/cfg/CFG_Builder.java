package ic.cfg;


import ic.tac.*;

public class CFG_Builder {

	public ControlFlowGraph buildCFG(TACList method) {
		
		ControlFlowGraph cfg = new ControlFlowGraph();
		BasicBlock enter = cfg.newBlock(new TAC_Comment("ENTER NODE"));
		cfg.setEnter(enter);
		
		/* This pass generates the basic blocks, adds them to the cfg,
		 * and creates the straight line path through the code. */
		BasicBlock lastInstr = enter; 
		for (TACInstr i : method.getInstrs()) {
			BasicBlock currentInstr = cfg.newBlock(i);
			if( ! (lastInstr.getInstr() instanceof Jump)) {
				lastInstr.addEdge(currentInstr); 
			}
			lastInstr = currentInstr; 
		}
		
		BasicBlock exit = cfg.newBlock(new TAC_Comment("EXIT NODE"));
		lastInstr.addEdge(exit);		
		cfg.setExit(exit);
		
		/* This pass deals with the extra edges needed by jump instructions. */
		for (TACInstr i : method.getInstrs()) {
			if (i instanceof Jumper) {
				BasicBlock jump = cfg.getBlock(i);
				BasicBlock label = cfg.getBlock(((Jumper) i).getLabel());
				jump.addEdge(label);
			} 
			if(i instanceof Return) {
				BasicBlock returnB = cfg.getBlock(i); 
				returnB.addEdge(exit);
			}
		}
		
		return cfg;
		
	}
	
	public void printCFG(ControlFlowGraph cfg, String filename) {
		cfg.dotToFile(filename);
	}
	
	
}
