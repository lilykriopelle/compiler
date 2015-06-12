package ic.dfa;

import ic.ast.StringLiteralNode;
import ic.cfg.ControlFlowGraph;
import ic.error.OptimizationError;
import ic.tac.Copy;
import ic.tac.LdStr;
import ic.tac.TACInstr;
import ic.opt.ConstantFolding; 
import java.util.Hashtable; 
import java.util.Vector;

import ic.tac.*;

/* Constant folding maps TAC Variables to constants so we can later
 * replace variables with their constant equivalents.  Since we are
 * only interested in definitions from variables to constants that are
 * valid along all paths through the program, our meet operator is
 * intersection.
 * 
 * To represent the map from variables to constants, we use a
 * hashtable. Since we want to hash on the name of the variable, we
 * override the hashcode of the variable to return the hash computed
 * on underlying string name. We had to perform a similar overriding
 * for equals.
 * 
 * I was having trouble using contains but get and then comparing to
 * null seems to work as expected.  
*/

public class ReachingConstants extends DataFlowAnalysis<Hashtable<TACVar, Constant>> implements
		TACPropagatingVisitor<Hashtable<TACVar, Constant>, Hashtable<TACVar,Constant>> {
	
	private TACList method; 
	private Hashtable<TACVar, Constant> TOP = new Hashtable<TACVar, Constant>(); 
	private Constant UNKNOWN = new Constant(new StringLiteralNode(-1, "UNKNOWN"));
	
	public ReachingConstants(ControlFlowGraph cfg, TACList method) {
		super(cfg);
		this.method = method; 
		generateTop(); 
	}

	@Override
	public String getName() {
		return "CF";
	}

	@Override
	public boolean isForward() {
		return true;
	}

	/**
	 * Initial value for out[enter]. Since no variables will be defined at this point, 
	 * the hashtable should be empty. 
	 */
	@Override
	public Hashtable<TACVar, Constant> boundary() {
		return new Hashtable<TACVar, Constant>();
	}

	public void generateTop() {
		Hashtable<TACVar, Constant> top = new Hashtable<TACVar, Constant>();
		// Add all instructions with constants loads and stores, including
		// binary and unary expressions.
		for(TACInstr cur: method.getInstrs()) {
			if(cur instanceof LdStr) { 
				LdStr ldStr = (LdStr)cur; 
				top.put(ldStr.getDest(), UNKNOWN);
			} else if (cur instanceof BinExpr) {
				BinExpr bin = (BinExpr)cur; 
				top.put(bin.getDest(), UNKNOWN);
			} else if (cur instanceof UnExpr) {
				UnExpr un = (UnExpr)cur; 
				top.put(un.getDest(), UNKNOWN);
			}
		}
		TOP = top; 
	}
	
	/**
	 * Top value in the lattice of T elements.
	 */
	@Override
	public Hashtable<TACVar, Constant> top() {
		return TOP; 
	}

	/**
	 * Return the meet of t1 and t2 in the lattice.
	 * Since we are only interested in the variable-constant 
	 * pairs that are valid along every path to the current program 
	 * point we want to do an intersection of the two hashtables. 
	 */
	@Override
	public Hashtable<TACVar, Constant> meet(Hashtable<TACVar, Constant> branch1,
			Hashtable<TACVar, Constant> branch2) {
		if(branch1 == TOP) return branch2; 
		if(branch2 == TOP) return branch1;
		
		Hashtable<TACVar, Constant> intersection = new Hashtable<TACVar, Constant>(); 
		for(TACVar var: branch1.keySet()) {
			// If both branches contain the variable and associate it with the same 
			// constant, add that pair to the intersection. 
			Constant con = branch1.get(var);
			if(branch2.contains(var) && con.equals(branch2.get(var))) {
				intersection.put(var, con);
			}
		}
		return intersection;
	}

	/**
	 * Return true if t1 and t2 are equivalent.
	 */
	@Override
	public boolean equals(Hashtable<TACVar, Constant> branch1,
			Hashtable<TACVar, Constant> branch2) {
		if(branch1.size() != branch2.size()) {
			return false; 
		} else {
			for(TACVar var: branch1.keySet()) {
				// If both branches contain the variable and associate it with the same 
				// constant, continue. Otherwise return false.
				// For reasons I don't really understand using contains instead of get caused an infinite loop.
				if( branch2.get(var) == null || ! branch1.get(var).equals(branch2.get(var))) {
					return false; 
				}
			}
			//All elements must match 
			return true; 
		}
	}
	
	/**
	 * Return the result of applying the transfer function for
	 * instr to t.
	 */
	@Override
	public Hashtable<TACVar, Constant> transfer(TACInstr instr,
			Hashtable<TACVar, Constant> t) {
		// Create a new copy of the hashtable to avoid aliasing 
		//return instr.accept(this, (Hashtable<Variable, Constant>)t.clone());
		return instr.accept(this, new Hashtable<TACVar, Constant>(t));
	}
	
	// For loads, we want to first remove a pair involving the destination
	// and then if the value loaded into the register is a constant, we want
	// to add that pair back into the map. 
	@Override
	public Hashtable<TACVar, Constant> visit(Load x,
			Hashtable<TACVar, Constant> varToConPairs) {
		varToConPairs.remove(x.getDest());
		if(x.getValue() instanceof Constant) {
			varToConPairs.put(x.getDest(), (Constant)x.getValue());
		}
		return varToConPairs;
	}

	// The same steps for loads apply to stores. 
	@Override
	public Hashtable<TACVar, Constant> visit(Store x,
			Hashtable<TACVar, Constant> varToConPairs) {
		varToConPairs.remove(x.getDest());
		if(x.getValue() instanceof Constant) {
			varToConPairs.put(x.getDest(), (Constant)x.getValue());
		}
		return varToConPairs;
	}
	
	@Override
	public Hashtable<TACVar, Constant> visit(BinExpr x,
			Hashtable<TACVar, Constant> varToConPairs) {
		// This can destroy the pair involving the dest and 
	        // generate a new pair if left and right are constants.
		varToConPairs.remove(x.getDest());

		// We check to see if either left is a constant or a TACVar
		// which resolves to a constant in the varToConPairs. 
		Constant left = null;
		Operand leftOp = x.getLeft(); 
		if(leftOp instanceof Constant) {
		    left = (Constant)leftOp; 
		} else if(leftOp instanceof TACVar) {
		    left = varToConPairs.get((TACVar)leftOp); 
		}

		// Again we see if right is or resolves to a constant.
		Constant right = null; 
		Operand rightOp = x.getRight(); 
		if(rightOp instanceof Constant) {
		    right = (Constant)rightOp; 
		} else if(rightOp instanceof TACVar) {
		    right = varToConPairs.get((TACVar)rightOp); 
		}

		// If both left and right are constants, we can evaluate the 
		// expression and add the destination, constant pair to varToConPairs.
		if(left != null && right != null) {
		    Constant value = ConstantFolding.eval(left, x.getOp(), right);
		    varToConPairs.put(x.getDest(), value); 
		}
		return varToConPairs;
	}
	
	@Override
	public Hashtable<TACVar, Constant> visit(UnExpr x,
			Hashtable<TACVar, Constant> varToConPairs) {
		varToConPairs.remove(x.getDest());
		// We check to see if either left is a constant or a TACVar
		// which resolves to a constant in the varToConPairs. 
		Constant value = null;
		Operand exprOp = x.getExpr(); 
		if(exprOp instanceof Constant) {
		    value = (Constant)exprOp; 
		} else if(exprOp instanceof TACVar) {
		    value = varToConPairs.get((TACVar)exprOp); 
		}

		// If the expr inside the unary expression resolves to a constant, we can directly 
		// evaluate the unary expression. As a result, we can add a pair with the destination 
		// and the result of the expression to varToConPairs.
		if(value != null) {
		    Constant unValue = ConstantFolding.eval(x.getOp(), value);
		    varToConPairs.put(x.getDest(), unValue); 
		}
		return varToConPairs;
	}


	@Override
	public Hashtable<TACVar, Constant> visit(Field_Load x,
			Hashtable<TACVar, Constant> varToConPairs) {
		varToConPairs.remove(x.getDest());
		return varToConPairs;
	}

	@Override
	public Hashtable<TACVar, Constant> visit(Field_Store x,
			Hashtable<TACVar, Constant> varToConPairs) {
		return varToConPairs;
	}
	
	@Override
	public Hashtable<TACVar, Constant> visit(Array_Load x,
			Hashtable<TACVar, Constant> varToConPairs) {
		// Since an array load isn't a constant, this cannot generate any new pairs, but it can
		// kill a pair involving the destination of the load. 
		varToConPairs.remove(x.getDest());
		return varToConPairs;
	}

	@Override
	public Hashtable<TACVar, Constant> visit(Array_Store x,
			Hashtable<TACVar, Constant> varToConPairs) {
		// Since the target is not a variable, this can have no effect on the pairs.  
		return varToConPairs;
	}
	
	
	@Override
	public Hashtable<TACVar, Constant> visit(Alloc_Array x,
			Hashtable<TACVar, Constant> varToConPairs) {
		// Since an array isn't a constant, this cannot generate or kill any pairs. 
		return varToConPairs;
	}

	@Override
	public Hashtable<TACVar, Constant> visit(Alloc_Obj x,
			Hashtable<TACVar, Constant> varToConPairs) {
		// Since an object isn't a constant, this cannot generate or kill any pairs. 
		return varToConPairs;
	}


	@Override
	public Hashtable<TACVar, Constant> visit(Fun_Call x,
			Hashtable<TACVar, Constant> varToConPairs) {
		varToConPairs.remove(x.getDest());
		return varToConPairs;
	}
	
	@Override
	public Hashtable<TACVar, Constant> visit(Lib_Call x,
			Hashtable<TACVar, Constant> varToConPairs) {
		if(x.getDest() != null) {
			varToConPairs.remove(x.getDest());
		}
		return varToConPairs;
	}

	@Override
	public Hashtable<TACVar, Constant> visit(Return x,
			Hashtable<TACVar, Constant> varToConPairs) {
		return varToConPairs;
	}

	@Override
	public Hashtable<TACVar, Constant> visit(TAC_Comment x,
			Hashtable<TACVar, Constant> varToConPairs) {
		return varToConPairs;
	}
	

	@Override
	public Hashtable<TACVar, Constant> visit(Jump x,
			Hashtable<TACVar, Constant> varToConPairs) {
		return varToConPairs;
	}
	

	@Override
	public Hashtable<TACVar, Constant> visit(Cond_Jump x,
			Hashtable<TACVar, Constant> varToConPairs) {
		return varToConPairs;
	}

	@Override
	public Hashtable<TACVar, Constant> visit(Label x,
			Hashtable<TACVar, Constant> varToConPairs) {
		return varToConPairs;
	}
	
	@Override
	public Hashtable<TACVar, Constant> visit(Check_Bounds x,
			Hashtable<TACVar, Constant> varToConPairs) {
		return varToConPairs;
	}

	@Override
	public Hashtable<TACVar, Constant> visit(Check_Null x,
			Hashtable<TACVar, Constant> varToConPairs) {
		return varToConPairs;
	}
	
	@Override
	public Hashtable<TACVar, Constant> visit(Check_Positive x,
			Hashtable<TACVar, Constant> varToConPairs) {
		return varToConPairs;
	}
	
	@Override
	public Hashtable<TACVar, Constant> visit(NoOp x,
			Hashtable<TACVar, Constant> varToConPairs) {
		return varToConPairs; 
	}

	@Override
	public Hashtable<TACVar, Constant> visit(Constant x,
			Hashtable<TACVar, Constant> varToConPairs) {
		throw new OptimizationError("Trying to transfer on a constant instead of a TAC instr in constant folding.");
	}
	
	@Override
	public Hashtable<TACVar, Constant> visit(TempVariable x,
			Hashtable<TACVar, Constant> varToConPairs) {
		throw new OptimizationError("Trying to transfer on a program variable instead of a TAC instr in constant folding.");
	}

	@Override
	public Hashtable<TACVar, Constant> visit(String_Constant x,
			Hashtable<TACVar, Constant> varToConPairs) {
		throw new OptimizationError("Trying to transfer on a string constant instead of a TAC instr in constant folding.");
	}
	
	@Override
	public Hashtable<TACVar, Constant> visit(Operand x,
			Hashtable<TACVar, Constant> varToConPairs) {
		throw new OptimizationError("Trying to transfer on an abstract TAC instr.");
	}

	@Override
	public Hashtable<TACVar, Constant> visit(ProgramVariable x,
			Hashtable<TACVar, Constant> varToConPairs) {
		throw new OptimizationError("Trying to transfer on a program variable instead of a TAC instr in constant folding.");
	}

	@Override
	public Hashtable<TACVar, Constant> visit(Array_Access x,
			Hashtable<TACVar, Constant> varToConPairs) {
		throw new OptimizationError("Trying to transfer on an array access instead of a TAC instr in constant folding.");
	}

	@Override
	public Hashtable<TACVar, Constant> visit(Field_Access x,
			Hashtable<TACVar, Constant> varToConPairs) {
		throw new OptimizationError("Trying to transfer on a field access instead of a TAC instr in constant folding.");
	}
	
	@Override
	public Hashtable<TACVar, Constant> visit(TACInstr x,
			Hashtable<TACVar, Constant> varToConPairs) {
		throw new OptimizationError("Trying to transfer on an abstract TAC instr.");
	}
	
	@Override
	public Hashtable<TACVar, Constant> visit(Call x,
			Hashtable<TACVar, Constant> varToConPairs) {
		throw new OptimizationError("Trying to transfer on an abstract TAC instr.");
	}

	

}