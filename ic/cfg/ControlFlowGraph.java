package ic.cfg;

import ic.tac.*;

import java.io.File;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Vector;

/**
 * A ControlFlowGraph manages a collection of Basic Blocks and provides
 * some basic operations on them. 
 * <p>
 * Be sure to initialize the enter and exit
 * blocks with the blocks you are using for those nodes.
 * Those blocks should not contain real instructions from the method 
 * being analyzed.  Instead, create two extra blocks with some special
 * instruction values to indicate that they are the enter and exit blocks.
 * See the DataFlowAnalysis documentation for more details.
 * <p>
 * This class implements the Iterable interface, so you can iterate
 * over the blocks in a graph as follows:
 * <pre>
 *   ControlFlowGraph cfg = ...;
 *   for (BasicBlock b :  cfg) {
 *     ...
 *   }
 * </pre>
 */
public class ControlFlowGraph implements Iterable<BasicBlock> {

	protected final Vector<BasicBlock> blocks = new Vector<BasicBlock>();
	protected BasicBlock enter;
	protected BasicBlock exit;


	/**
	 * Allocate a new block that holds the given instruction.
	 * A unique number will be assigned to that block.
	 * Returns the block.
	 */
	public BasicBlock newBlock(TACInstr instr) {
		BasicBlock bb = new BasicBlock(blocks.size(), instr);
		blocks.add(bb);
		return bb;
	}	
	
	/**
	 * Return the block representing enter.
	 */
	public BasicBlock getEnter() {
		return enter;
	}

	/**
	 * Return the block representing exit.
	 */
	public BasicBlock getExit() {
		return exit;
	}

	/**
	 * Set the block representing enter.
	 */
	public void setEnter(BasicBlock enter) {
		this.enter = enter;
	}

	/**
	 * Set the block representing exit.
	 */
	public void setExit(BasicBlock exit) {
		this.exit = exit;
	}
	
	/**
	 * Returns an iterator for the blocks.  The iteration
	 * order is the order in which blocks were allocated.
	 */
	public Iterator<BasicBlock> iterator() {
		return blocks.iterator();
	}
	
	/**
	 * Returns the block for an instruction.  Throws a 
	 * NoSuchElementException if no such block exists.
	 * 
	 */
	public BasicBlock getBlock(TACInstr i) {
		for (BasicBlock b : blocks) {
			if (b.getInstr() == i) { 
				return b;
			}
		}
		throw new NoSuchElementException();
	}

	/**
	 * Return a string rep for a CFG.
	 */
	public String toString() {
		String result = "";
		for (BasicBlock b : blocks) {
			result += b.toString();
			result += "\n";
		}
		return result;
	}

	/**
	 * Writes a dot graph description to the file named fileName.
	 * You can visually examine the graph as follows.  After generating
	 * graph.dot, execute the following on the command line:
	 * <pre>
	 *   dot -Tpdf < graph.pdf > graph.dot
	 * </pre>
	 * Some escape characters and punctuation may confuse dot, in which
	 * case you will need to add additional escaping commands, as I have done
	 * for the few obvious special cases (", \n, etc). 
	 */ 
	public void dotToFile(String fileName) {
		try {
			TAC_StringGenerator printer = new TAC_StringGenerator(); 
			PrintWriter out = new PrintWriter(new File(fileName));
			out.println("digraph G {");
			out.println("   node [shape=record];");
			for (BasicBlock b : blocks) {
				out.print("B" + b.id + "[");
				out.print("label=\"{Block " + b.id + "|");
				TACInstr i = b.getInstr();
				String s = ((String)i.accept(printer));
				out.print(s.replace("<", "\\<").replace(">", "\\>").replace("\n", "\\n").replace("\"", "\\\""));
				out.print("}");
				out.println("\"];");
			}
			for (BasicBlock b : blocks) {
				for (BasicBlock bb: b.successors) {
					out.println("B" + b.id + " -> " + "B" + bb.id + ";");
				}
			}
			out.println("}");
			out.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
