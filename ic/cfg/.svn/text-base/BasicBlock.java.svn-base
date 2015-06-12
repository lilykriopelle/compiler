package ic.cfg;

import ic.tac.TACInstr;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents one BasicBlock in a CFG.  This implementation
 * permits only a single instruction per block.  Each block
 * has an id number to enable you to distinguish blocks in client
 * code, a single instruction, and a set of successor and
 * predecessor blocks.
 */
public class BasicBlock  {

	protected final int id;
	protected final TACInstr instr;
	protected final Set<BasicBlock> successors = new HashSet<BasicBlock>();
	protected final Set<BasicBlock> predecessors = new HashSet<BasicBlock>();
 
	/**
	 * Create a new block with the given id and instruction.
	 */
	public BasicBlock(int id, TACInstr instr) {
		this.instr = instr;
		this.id = id;
	}

	/**
	 * Add an edge from this block to successor.  This method
	 * properly maintains the predecessor list as well.
	 */
	public void addEdge(BasicBlock successor) {
		successors.add(successor);
		successor.predecessors.add(this);
	}

	/**
	 * Return the id of the block.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Return the instruction in the block.
	 */
	public TACInstr getInstr() {
		return instr;
	}

	/**
	 * Return a list of predecessors that you can iterator over.
	 */
	public Iterable<BasicBlock> getPredecessors() {
		return predecessors;
	}

	/**
	 * Return a list of successors that you can iterator over.
	 */
	public Iterable<BasicBlock> getSuccessors() {
		return successors;
	}
	
	/**
	 * A printable rep for a block.  Change as you see fit.
	 */
	public String toString() {
		String result = String.format("Block %-3d: %-20s       [pred=%-15s,succ=%-15s]", id, instr, blockListToString(predecessors), blockListToString(successors));
		return result;
	}
	
	private String blockListToString(Iterable<BasicBlock> blocks) {
		String result = "{";
		for (BasicBlock b : blocks) {
			if (result.length() > 1) {
				result += ", ";
			}
			result += b.getId();
		}
		result = result + "}";
		return result;
	}
}
