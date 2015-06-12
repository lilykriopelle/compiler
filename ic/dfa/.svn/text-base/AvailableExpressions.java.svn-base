package ic.dfa;

import ic.tac.*;
import java.util.Hashtable;
import ic.error.OptimizationError;
import ic.cfg.BasicBlock;
import ic.cfg.ControlFlowGraph;

public class AvailableExpressions extends DataFlowAnalysis<Hashtable<AvailableExpr, GeneratingInstrs>> implements
		TACPropagatingVisitor<Hashtable<AvailableExpr, GeneratingInstrs>, Hashtable<AvailableExpr, GeneratingInstrs>> {

	private final Hashtable<AvailableExpr, GeneratingInstrs> TOP; 
	
	public AvailableExpressions(ControlFlowGraph cfg, TACList method) {
		super(cfg);
		TOP = generateTop(method); 
	}

	@Override
	public String getName() {
		return "AE";
	}

	/**
	 * Since we are interested in the expressions previously computed by the
	 * program, the analysis is forward.
	 */
	@Override
	public boolean isForward() {
		return true;
	}

	/**
	 * The initial value for out[enter] is an empty vector since no expressions
	 * have previously been computed at the start of the program.
	 */
	public Hashtable<AvailableExpr, GeneratingInstrs> boundary() {
		return new Hashtable<AvailableExpr, GeneratingInstrs>();
	}

	private Hashtable<AvailableExpr, GeneratingInstrs> generateTop(TACList method) {
		Hashtable<AvailableExpr, GeneratingInstrs> top = new Hashtable<AvailableExpr, GeneratingInstrs>();
		for (TACInstr cur: method.getInstrs()) {
			if(cur instanceof AvailableExpr) {
				top.put((AvailableExpr)cur, new GeneratingInstrs());
			}
		}
		return top; 
	}
	
	/**
	 * Since the meet is intersection, the top value is all expressions computed
	 * in the program.
	 */
	public Hashtable<AvailableExpr, GeneratingInstrs> top() {
		return TOP;
	}

	/**
	 * Since we are only interested in expressions available along every path to
	 * the current program point, the meet is intersection.
	 */
	public Hashtable<AvailableExpr, GeneratingInstrs> meet(
			Hashtable<AvailableExpr, GeneratingInstrs> branch1,
			Hashtable<AvailableExpr, GeneratingInstrs> branch2) {
		Hashtable<AvailableExpr, GeneratingInstrs> intersection = new Hashtable<AvailableExpr, GeneratingInstrs>(); 
		for (AvailableExpr cur : branch1.keySet()) {
			GeneratingInstrs branch2Instrs = branch2.get(cur);
			if (branch2Instrs != null) {
				GeneratingInstrs meet = new GeneratingInstrs(); 
				meet.addInstrs(branch2Instrs); 
				meet.addInstrs(branch1.get(cur));
				intersection.put(cur, meet);
			}
		}
		return intersection;
	}

	/**
	 * Return true if branch1 and branch2 are equivalent.
	 */
	public boolean equals(Hashtable<AvailableExpr, GeneratingInstrs> branch1,
			Hashtable<AvailableExpr, GeneratingInstrs> branch2) {
		for (AvailableExpr curExpr : branch1.keySet()) {
			GeneratingInstrs branch2Instrs = branch2.get(curExpr);
			if (branch2Instrs == null) {
				return false;
			} else if( ! branch2Instrs.equals(branch1.get(curExpr))) {
				return false; 
			}
		}
		return true;

	}

	/**
	 * Return the result of applying the transfer function for instr to t.
	 */
	public Hashtable<AvailableExpr, GeneratingInstrs> transfer(TACInstr instr,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		Hashtable<AvailableExpr, GeneratingInstrs> transfer = new Hashtable<AvailableExpr, GeneratingInstrs>(availableExprs);
		return instr.accept(this, transfer);
	}

	/**
	 * This helper method filters out any expressions killed by the current
	 * update to the dest variable.
	 */
	private Hashtable<AvailableExpr, GeneratingInstrs> invalidateExprs(TACVar dest,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs, TACInstr x) {
		
		Hashtable<AvailableExpr, GeneratingInstrs> updatedExprs = new Hashtable<AvailableExpr, GeneratingInstrs>(availableExprs);
		for (AvailableExpr cur : availableExprs.keySet()) {
			if(dest == null) System.out.println("DEST IS NULL in INSTR " + x);
			if (cur.involves(dest)) {
				updatedExprs.remove(cur); 
			}
		} 
		return updatedExprs;
	}

	/**
	 * This helper method deals with the special case of field stores, which
	 * invalidate any expression of the form *.field.
	 */
	private Hashtable<AvailableExpr, GeneratingInstrs> invalidateFieldExprs(
			String field,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		Hashtable<AvailableExpr, GeneratingInstrs> updatedExprs = new Hashtable<AvailableExpr, GeneratingInstrs>(availableExprs);

		for (AvailableExpr cur : availableExprs.keySet()) {
			if ( cur.accessesField(field)) {
				updatedExprs.remove(cur); 
			}
		}
		return updatedExprs;
	}

	/*
	 * Since basic loads and stores do not involve expressions, they cannot
	 * contribute to available expressions. They can however invalidate any
	 * expression involving the destination, being updated.
	 */
	public Hashtable<AvailableExpr, GeneratingInstrs> visit(Load x,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		return invalidateExprs(x.getDest(), availableExprs, x);
	}

	public Hashtable<AvailableExpr, GeneratingInstrs> visit(Store x,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		return invalidateExprs(x.getDest(), availableExprs, x);
	}

	public Hashtable<AvailableExpr, GeneratingInstrs> visit(Field_Load x,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		Hashtable<AvailableExpr, GeneratingInstrs> updatedAvailableExprs = invalidateExprs(
				x.getDest(), availableExprs, x);
		if (updatedAvailableExprs.get(x) != null) {
			updatedAvailableExprs.get(x).add(x);
		} else {
			updatedAvailableExprs.put(x, new GeneratingInstrs(x));
		}
		return updatedAvailableExprs;
	}

	public Hashtable<AvailableExpr, GeneratingInstrs> visit(Field_Store x,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		
		Hashtable<AvailableExpr, GeneratingInstrs> updatedAvailableExprs = invalidateFieldExprs(x.getField().getField(), 
																									availableExprs);
		// Since we have eliminated all exprs involving the field, we
		// know the available expression represented by x will be
		// unique. If the new value being stored is a TACVar, we can
		// use that to refer to the expression.
		if (x.getDest() != null) {
			updatedAvailableExprs.put(x, new GeneratingInstrs(x));
		}
		return updatedAvailableExprs;
	}

	public Hashtable<AvailableExpr, GeneratingInstrs> visit(Array_Load x,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		Hashtable<AvailableExpr, GeneratingInstrs> updatedAvailableExprs = invalidateExprs(x.getDest(), 
																				availableExprs, x);
		if (updatedAvailableExprs.get(x) != null) {
			updatedAvailableExprs.get(x).add(x);
		} else {
			updatedAvailableExprs.put(x, new GeneratingInstrs(x));
		}
		return updatedAvailableExprs;
	}

	/**
	 * This helper method invalidates all expressions involving array acccess
	 * after an array load.
	 */
	private Hashtable<AvailableExpr, GeneratingInstrs> invalidateArrayExprs(
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		//TODO: should this be more specific? Only eliminate arrays of the same type?
		Hashtable<AvailableExpr, GeneratingInstrs> updated = new Hashtable<AvailableExpr, GeneratingInstrs>(availableExprs);
		for (AvailableExpr cur : availableExprs.keySet()) {
			if (cur instanceof Array_Store || cur instanceof Array_Load) {
				updated.remove(cur); 
			}
		}
		return updated;
	}

	public Hashtable<AvailableExpr, GeneratingInstrs> visit(Array_Store x,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		Hashtable<AvailableExpr, GeneratingInstrs> updatedAvailableExprs = invalidateArrayExprs(availableExprs);
		// Since we have eliminated all exprs involving arrays, we
		// know the available expression represented by x will be
		// unique. If the new value being stored is a TACVar, we can
		// use that to refer to the expression.
		if (x.getDest() != null) {
			updatedAvailableExprs.put(x, new GeneratingInstrs(x));
		}
		return updatedAvailableExprs;
	}

	// Expressions
	public Hashtable<AvailableExpr, GeneratingInstrs> visit(BinExpr x,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		Hashtable<AvailableExpr, GeneratingInstrs> updatedAvailableExprs = invalidateExprs(x.getDest(), availableExprs, x);
		if (updatedAvailableExprs.get(x) != null) {
			updatedAvailableExprs.get(x).add(x);
		} else {
			updatedAvailableExprs.put(x, new GeneratingInstrs(x));
		}
		return updatedAvailableExprs;
	}

	public Hashtable<AvailableExpr, GeneratingInstrs> visit(UnExpr x,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		Hashtable<AvailableExpr, GeneratingInstrs> updatedAvailableExprs = invalidateExprs(
				x.getDest(), availableExprs, x);
		if (updatedAvailableExprs.get(x) != null) {
			updatedAvailableExprs.get(x).add(x);
		} else {
			updatedAvailableExprs.put(x, new GeneratingInstrs(x));
		}
		return updatedAvailableExprs;
	}

	// Runtime checks- since the checks do not have unique
	// destinations, if one is already in the Hashtable, we do not
	// need to add another.
	public Hashtable<AvailableExpr, GeneratingInstrs> visit(Check_Positive x,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		Hashtable<AvailableExpr, GeneratingInstrs> updatedAvailableExprs = new Hashtable<AvailableExpr, GeneratingInstrs>(availableExprs); 
		if (availableExprs.get(x) != null) {
			return updatedAvailableExprs;
		} else {
			updatedAvailableExprs.put(x, new GeneratingInstrs(x));
			return updatedAvailableExprs;
		}
	}

	public Hashtable<AvailableExpr, GeneratingInstrs> visit(Check_Bounds x,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		Hashtable<AvailableExpr, GeneratingInstrs> updatedAvailableExprs = new Hashtable<AvailableExpr, GeneratingInstrs>(availableExprs); 
		if (updatedAvailableExprs.get(x) != null) {
			return updatedAvailableExprs;
		} else {
			updatedAvailableExprs.put(x, new GeneratingInstrs(x));
			return updatedAvailableExprs;
		}
	}

	public Hashtable<AvailableExpr, GeneratingInstrs> visit(Check_Null x,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		Hashtable<AvailableExpr, GeneratingInstrs> updatedAvailableExprs = new Hashtable<AvailableExpr, GeneratingInstrs>(availableExprs); 
		if (updatedAvailableExprs.get(x) != null) {
			return updatedAvailableExprs;
		} else {
			updatedAvailableExprs.put(x, new GeneratingInstrs(x));
			return updatedAvailableExprs;
		}
	}

	/**
	 * Update the available expressions to eliminate any expressions involving
	 * pointers.
	 */
	private Hashtable<AvailableExpr, GeneratingInstrs> funCall(
			Hashtable<AvailableExpr, GeneratingInstrs> old) {
		Hashtable<AvailableExpr, GeneratingInstrs> updated = new Hashtable<AvailableExpr, GeneratingInstrs>(old);
		for (AvailableExpr cur : old.keySet()) {
			if (cur instanceof Array_Store || cur instanceof Array_Load
					|| cur instanceof Field_Store || cur instanceof Field_Load || cur instanceof Check) {
				updated.remove(cur);
			}
		}
		return updated;
	}

	// Call Instructions - since call instructions can manipulate pointers
	// and invalidate old expressions, we must eliminate all 
	public Hashtable<AvailableExpr, GeneratingInstrs> visit(Fun_Call x,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		return funCall(availableExprs);
	}
	public Hashtable<AvailableExpr, GeneratingInstrs> visit(Lib_Call x,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		return funCall(availableExprs);
	}

	public Hashtable<AvailableExpr, GeneratingInstrs> visit(Return x,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		return availableExprs;
	}

	// Allocate instrs
	public Hashtable<AvailableExpr, GeneratingInstrs> visit(Alloc_Array x,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		return invalidateExprs(x.getDest(), availableExprs, x);
	}

	public Hashtable<AvailableExpr, GeneratingInstrs> visit(Alloc_Obj x,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		Hashtable<AvailableExpr, GeneratingInstrs> updated = invalidateExprs(x.getDest(), availableExprs, x);
		Check_Null check = new Check_Null(x.getDest());
		updated.put(check, new GeneratingInstrs(check));
		return updated;
	}

	// Jump Instructions
	public Hashtable<AvailableExpr, GeneratingInstrs> visit(Jump x,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		return availableExprs;
	}

	public Hashtable<AvailableExpr, GeneratingInstrs> visit(Cond_Jump x,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		return availableExprs;
	}

	public Hashtable<AvailableExpr, GeneratingInstrs> visit(Label x,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		return availableExprs;
	}

	// Comments and NoOps
	public Hashtable<AvailableExpr, GeneratingInstrs> visit(TAC_Comment x,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		return availableExprs;
	}

	public Hashtable<AvailableExpr, GeneratingInstrs> visit(NoOp x,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		return availableExprs;
	}

	// Complex operands
	public Hashtable<AvailableExpr, GeneratingInstrs> visit(Array_Access x,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		throw new OptimizationError(
				"Trying to generate available expressions out of an operand");
	}

	public Hashtable<AvailableExpr, GeneratingInstrs> visit(Field_Access x,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		throw new OptimizationError(
				"Trying to generate available expressions out of an operand");
	}

	// Simple operands
	public Hashtable<AvailableExpr, GeneratingInstrs> visit(Operand x,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		throw new OptimizationError(
				"Trying to generate available expressions out of an operand");
	}

	public Hashtable<AvailableExpr, GeneratingInstrs> visit(ProgramVariable x,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		throw new OptimizationError(
				"Trying to generate available expressions out of an operand");
	}

	public Hashtable<AvailableExpr, GeneratingInstrs> visit(TempVariable x,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		throw new OptimizationError(
				"Trying to generate available expressions out of an operand");
	}

	public Hashtable<AvailableExpr, GeneratingInstrs> visit(String_Constant x,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		throw new OptimizationError(
				"Trying to generate available expressions out of an operand");
	}

	public Hashtable<AvailableExpr, GeneratingInstrs> visit(Constant x,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		throw new OptimizationError(
				"Trying to generate available expressions out of an operand");
	}

	// Abstract TACInstrs
	public Hashtable<AvailableExpr, GeneratingInstrs> visit(TACInstr x,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		throw new OptimizationError(
				"Trying to generate available expressions out of an abstract TAC Instr.");
	}

	public Hashtable<AvailableExpr, GeneratingInstrs> visit(Call x,
			Hashtable<AvailableExpr, GeneratingInstrs> availableExprs) {
		throw new OptimizationError(
				"Trying to generate available expressions out of an abstract call.");
	}
	
	@Override
	public String toString() {
		String result = "";
		for (BasicBlock b : cfg) {
			result += "BLOCK " + b.getId();
			result += "\n  IN:  " + in(b).keys();
			result += "  " + b;
			result += "\n  OUT: " + out(b).keys();
			result += "\n";
		}
		return result;
	}

}
