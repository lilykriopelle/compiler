package ic.dfa;

import java.util.HashMap;
import java.util.Hashtable;

import ic.ast.MethodDecl;
import ic.cfg.BasicBlock;
import ic.cfg.ControlFlowGraph;
import ic.tac.TACInstr;


/**
 * Abstract Dataflow analysis engine. This is a general class for
 * solving dataflow instances. It is parameterized by the type T,
 * which is the type of value contained in the lattice. The solve
 * method is responsible for computing the solution for the CFG passed
 * into the constructor. After calling solve, the in and out methods
 * can be used to access the dataflow facts for each basic block.
 * <p>
 * To use the framework, you extend this class with a new class ---
 * LiveVariableAnalysis, for example --- which defines the six
 * abstract methods describing the lattice, transfer functions, meet
 * operator, boundary value, and direction of the analysis.
 * <p>
 * This implementation assumes the the enter and exit blocks for the CFG
 * do not contain instructions that are part of the code.  It will
 * not apply transfer functions to those blocks.  You can insert a simple
 * "NoOp" TAC Instruction into those blocks.
 * <p>
 * Note that for forward analysis, in[enter] is typically undefined,
 * but I set it to Top for simplicity.  Similarly, for backward
 * analysis, out[exit] is set to Top.
 */
public abstract class DataFlowAnalysis<T> {

	/** The graph to analyze */
	protected ControlFlowGraph cfg;
	

	/** Map for the in values */
	protected final Hashtable<BasicBlock,T> in = new Hashtable<BasicBlock, T>();

	/** Map for the out values */
	protected final Hashtable<BasicBlock,T> out = new Hashtable<BasicBlock, T>();


	/**
	 * Create a new dataflow instance that will compute information
	 * about the given flow graph.
	 */
	public DataFlowAnalysis(ControlFlowGraph cfg) {
		this.cfg = cfg;
	}
	
	// We wanted to be able to access the CFG when we added new temporary
	// variables in common subexpression elimination. We need to add new
	// instructions for their loads and add corresponding blocks to the CFG.
	public ControlFlowGraph getCFG() {
		return cfg; 
	}

	/**
	 * Return in[b].
	 */
	public T in(BasicBlock b) {
		if (!in.containsKey(b)) {
			throw new RuntimeException("Block not found in in map: " + b);
		}
		return in.get(b);
	}

	/**
	 * Return in[b].
	 */
	public T in(TACInstr t) {
		if (!in.containsKey(cfg.getBlock(t))) {
			throw new RuntimeException("Block not found in in map: " + t);
		}
		return in.get(cfg.getBlock(t));
	}

	
	/**
	 * Return out[b].
	 */
	public T out(BasicBlock b) {
		if (!out.containsKey(b)) {
			throw new RuntimeException("Block not found in out map: " + b);
		}
		return out.get(b);
	}
	
	/**
	 * Return out[b].
	 */
	public T out(TACInstr t) {
		if (!out.containsKey(cfg.getBlock(t))) {
			throw new RuntimeException("Block not found in out map: " + t);
		}
		return out.get(cfg.getBlock(t));
	}


	/**
	 * Solve a dataflow instance with the iterative algorithm.
	 */
	public void solve() {
		if (isForward()) {
			solveForward();
		} else {
			solveBackward();
		}
	}

	/**
	 * Solve a forward analysis and set up in and out.
	 */
	protected void solveForward() {
		// initialize out of all blocks to Top, except out[enter], which is
		// the boundary value.
		for (BasicBlock b : cfg) {
			if (b == cfg.getEnter()) {
				out.put(b, boundary());
			} else {
				out.put(b, top());
			}
			in.put(b, top());
		}		

		// Iterate over blocks until no more changes.
		boolean changed = true;
		while (changed) {
			changed = false;

			for (BasicBlock b : cfg) {

				if (b == cfg.getEnter() || b == cfg.getExit()) { // skip the enter/exit block 
					continue;
				}

				// compute the meet of the out[pred] for all pred.
				T oldValue = out.get(b);

				T newValue = top();
				for (BasicBlock pred : b.getPredecessors()) {
					newValue = meet(newValue, out.get(pred));
				}
				
				in.put(b, newValue);
				// apply the transfer function
				newValue = transfer(b.getInstr(), newValue);
				out.put(b, newValue);
				
				// did the value change?
				if (!equals(oldValue,newValue)) {
					changed = true;
				}
			}
		}
	}

	/**
	 * Solve a backward analysis.  This is the same as above, except
	 * we do everything backwards...
	 */
	protected void solveBackward() {
		for (BasicBlock b : cfg) {
			if (b == cfg.getExit()) {
				in.put(b, boundary());
			} else {
				in.put(b, top());
			}
			out.put(b, top());
		}	

		boolean changed = true;
		while (changed) {
			changed = false;
			for (BasicBlock b : cfg) {

				if (b == cfg.getEnter() || b == cfg.getExit()) { // skip the enter/exit block 
					continue;
				}

				T oldValue = in.get(b);
				T newValue = top();
				for (BasicBlock succ : b.getSuccessors()) {
					newValue = meet(newValue, in.get(succ));
				}
				out.put(b, newValue);

				newValue = transfer(b.getInstr(), newValue);
				in.put(b, newValue);

				if (!equals(oldValue,newValue)) {
					changed = true;
				}
			}
		}
	}


	/**
	 * Print out the in/out values for each basic block.
	 */
	public String toString() {
		String result = "";
		for (BasicBlock b : cfg) {
			result += "BLOCK " + b.getId();
			result += "\n  IN:  " + in(b);
			result += "  " + b;
			result += "\n  OUT: " + out(b);
			result += "\n";
		}
		return result;
	}



	/*
	 * These six methods define a dataflow instance and are
	 * defined differently in each subclass.
	 */ 
	
	/** 
	 * Return the name of the analysis 
	 */
	public abstract String getName(); 

	/**
	 * Return true iff the analysis is a forward analysis. 
	 */
	public abstract boolean isForward();

	/**
	 * Initial value for out[enter] or in[exit], depending on direction.
	 */
	public abstract T boundary();

	/**
	 * Top value in the lattice of T elements.
	 */
	public abstract T top();

	/**
	 * Return the meet of t1 and t2 in the lattice.
	 */
	public abstract T meet(T t1, T t2);

	/**
	 * Return true if t1 and t2 are equivalent.
	 */
	public abstract boolean equals(T t1, T t2);

	/**
	 * Return the result of applying the transfer function for
	 * instr to t.
	 */
	public abstract T transfer(TACInstr instr, T t); 
}
