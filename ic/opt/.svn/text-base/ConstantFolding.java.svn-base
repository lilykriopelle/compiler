package ic.opt;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Vector;

import ic.ast.StringLiteralNode;
import ic.ast.Literal;
import ic.ast.BoolLiteralNode;
import ic.ast.IntLiteralNode;
import ic.ast.BinOp;
import ic.ast.UnOp;
import ic.ast.MethodDecl;
import ic.cfg.CFG_Builder;
import ic.cfg.ControlFlowGraph;
import ic.dfa.ReachingConstants;
import ic.error.OptimizationError;
import ic.error.TACError;
import ic.tac.*;

/*
 * Our constant folder implements the TACVisitor interface.  For each TACInstr, it checks 
 * whether a variable used in the instruction is known to have a unique constant value. 
 * If so, we replace that variable with its known value.  
 *
 * When we apply copy propagation, we will be able to remove loads and stores that copy these 
 * constant values into variables, which will now be dead code.  				
 */

public class ConstantFolding extends Optimization implements TACVisitor {

	private TACList method;
	private ReachingConstants rc;
	private boolean printDFA;

	public ConstantFolding(boolean printDFA) {
		this.printDFA = printDFA;
	}

	public String getName() {
		return "CFO"; 
	}
	
	@Override
	public void optimize(MethodDecl md) {
		rc = new ReachingConstants(md.getCFG(), md.getTAC());
		rc.solve();

		method = md.getTAC();

		if (printDFA) {
			// Print dfa
			TAC_Printer rcPrinter = new TAC_Printer(rc);
			System.out.println("\nTAC of " + md.getName()
					+ " with Reaching Constants Information");
			method.print(rcPrinter);
		}

		// Visit all of the instrs in the method, replacing
		// instructions that define variables which aren't used
		// after the block with NoOps.
		LinkedList<TACInstr> oldInstructions = new LinkedList<TACInstr>(
				method.getInstrs());
		for (TACInstr cur : oldInstructions) {
			cur.accept(this);
		}
		md.setInstrs(method);
		
		md.setCFG((new CFG_Builder()).buildCFG(method)); 
		
		// Print out optimized TAC
		System.out.println("\nTAC of " + md.getName()
				+ " optimized to propagate constants.");
		TAC_Printer cfoPrinter = new TAC_Printer();
		method.print(cfoPrinter);
	}

	private Constant getConstant(Hashtable<TACVar, Constant> reachingConstants,
			Operand op) {
		if (op instanceof TACVar) {
			return reachingConstants.get(op);
		} else {
			return null;
		}
	}

	@Override
	public void visit(Load x) throws TACError {
		Constant con = getConstant(rc.in(x), x.getValue());
		if (con != null) {
			x.setValue(con);
		}
	}

	@Override
	public void visit(Store x) throws TACError {
		Constant con = getConstant(rc.in(x), x.getValue());
		if (con != null) {
			x.setValue(con);
		}
	}

	@Override
	public void visit(Array_Load x) throws TACError {
		Constant index = getConstant(rc.in(x), x.getArray().getIndex());
		if (index != null) {
			x.getArray().setIndex(index);
		}
	}

	@Override
	public void visit(Array_Store x) throws TACError {
		Constant index = getConstant(rc.in(x), x.getArray().getIndex());
		if (index != null) {
			x.getArray().setIndex(index);
		}
	}

	@Override
	public void visit(Field_Load x) throws TACError {
	}

	@Override
	public void visit(Field_Store x) throws TACError {
	}

	/*
	 * This static helper method computes the value of a binary expression
	 * that's two operands are both constants. It must also take a line number
	 * so it can properly generate the literal node containing the result of the
	 * expression.
	 */
	public static Constant eval(Constant left, BinOp op, Constant right) {
		//System.out.println("Evaluating Bin Expr");
		Literal lit = null;
		switch (op) {
		case GT:
			lit = new BoolLiteralNode(-1,
					(Integer) left.getValue() > (Integer) right.getValue());
			break;
		case GE:
			lit = new BoolLiteralNode(-1,
					(Integer) left.getValue() >= (Integer) right.getValue());
			break;
		case LT:
			lit = new BoolLiteralNode(-1,
					(Integer) left.getValue() < (Integer) right.getValue());
			break;
		case LE:
			lit = new BoolLiteralNode(-1,
					(Integer) left.getValue() <= (Integer) right.getValue());
			break;
		case NE:
			lit = new BoolLiteralNode(-1, left.getValue() != right.getValue());
			break;
		case EQEQ:
			lit = new BoolLiteralNode(-1, left.getValue() == right.getValue());
			break;
		case PLUS:
			lit = new IntLiteralNode(-1, (Integer) left.getValue()
					+ (Integer) right.getValue());
			break;
		case MINUS:
			lit = new IntLiteralNode(-1, (Integer) left.getValue()
					- (Integer) right.getValue());
			break;
		case DIV:
			lit = new IntLiteralNode(-1, (Integer) left.getValue()
					/ (Integer) right.getValue());
			break;
		case MULT:
			lit = new IntLiteralNode(-1, (Integer) left.getValue()
					* (Integer) right.getValue());
			break;
		case MOD:
			lit = new IntLiteralNode(-1, (Integer) left.getValue()
					% (Integer) right.getValue());
			break;
		case CONCAT:
			lit = new StringLiteralNode(-1, (String) left.getValue()
					+ (String) right.getValue());
			return new String_Constant((StringLiteralNode) lit);
		case AND:
			lit = new BoolLiteralNode(-1, (Boolean) left.getValue()
					&& (Boolean) right.getValue());
			break;
		case OR:
			lit = new BoolLiteralNode(-1, (Boolean) left.getValue()
					|| (Boolean) right.getValue());
			break;
		}
		return new Constant(lit);
	}

	@Override
	public void visit(BinExpr x) throws TACError {
		Hashtable<TACVar, Constant> reachingConstants = rc.in(x);
		Constant left;
		if (x.getLeft() instanceof Constant) {
			left = (Constant) x.getLeft();
		} else {
			left = getConstant(reachingConstants, x.getLeft());
			// If left can be resolved to a constant, update the binary
			// expression to reflect this.
			if (left != null) {
				x.setLeft(left);
			}
		}

		Constant right;
		if (x.getRight() instanceof Constant) {
			right = (Constant) x.getRight();
		} else {
			right = getConstant(reachingConstants, x.getRight());
			// If we will not be able to evaluate the expression but
			// right resolves to a constant, update the Binary Expression
			// to reflect the constant value of right.
			if (left == null && right != null) {
				x.setRight(right);
			}
		}

		// If both sides of the expression are constants we can replace
		// the expression with its constant equivalent.
		if (left != null && right != null) {
			Constant value = eval(left,
					x.getOp(), right);
			Load loadConstant = new Load(x.getDest(), value);
			method.updateInstr(x, loadConstant);
		}
	}

	/*
	 * This static helper method computes the value of a binary expression
	 * that's two operands are both constants. It must also take a line number
	 * so it can properly generate the literal node containing the result of the
	 * expression.
	 */
	public static Constant eval(UnOp op, Constant value) {
		Literal lit = null;
		switch (op) {
		case NOT:
			lit = new BoolLiteralNode(-1, !((Boolean) value.getValue()));
			break;
		case UMINUS:
			lit = new IntLiteralNode(-1, -((Integer) value.getValue()));
			break;
		case LENGTH:
			return null;
		}
		return new Constant(lit);
	}

	@Override
	public void visit(UnExpr x) throws TACError {
		Constant expr = getConstant(rc.in(x), x.getExpr());
		if (expr != null) {
			Constant value = eval(x.getOp(), expr);
			Load loadConstant = new Load(x.getDest(), value);
			method.updateInstr(x, loadConstant);
		}

	}

	@Override
	public void visit(Alloc_Array x) throws TACError {
		Constant length = getConstant(rc.in(x), x.getLength());
		if (length != null) {
			x.setLength(length);
		}
	}

	@Override
	public void visit(Alloc_Obj x) throws TACError {
	}

	@Override
	public void visit(Return x) throws TACError {
		if (x.getExpr() != null) {
			Constant expr = getConstant(rc.in(x), x.getExpr());
			if (expr != null) {
				x.setExpr(expr);
			}
		}
	}

	@Override
	public void visit(Fun_Call x) throws TACError {}

	@Override
	public void visit(Lib_Call x) throws TACError {}

	@Override
	public void visit(Cond_Jump x) throws TACError {
		if (x.getCond() != null) {
			Constant cond = getConstant(rc.in(x), x.getCond());
			if (cond != null) {
				x.setCond(cond);
			}
		}
	}

	@Override
	public void visit(Jump x) {}

	@Override
	public void visit(Label x) {}

	@Override
	public void visit(Check_Positive x) throws TACError {
		Constant length = getConstant(rc.in(x), x.getLength());
		if(length != null) {
			if((Integer)length.getValue() > 0) {
				method.updateInstr(x, new NoOp());
			} else {
				x.setLength(length); 
				x.setNegative();
			}
		}
	}

	@Override
	public void visit(Check_Bounds x) throws TACError {
		Constant index = getConstant(rc.in(x), x.getIndex());
		if (index != null) {
			x.setIndex(index);
		}
	}

	@Override
	public void visit(Check_Null x) throws TACError {
		Constant check = getConstant(rc.in(x), x.getToBeChecked());
		if (check != null) {
			x.setToBeChecked(check);
		}
	}

	@Override
	public void visit(NoOp x) {
	}

	@Override
	public void visit(TAC_Comment x) {
	}

	@Override
	public void visit(Array_Access x) throws TACError {
		throw new OptimizationError(
				"Trying to perform constant folding on an array access.");
	}

	@Override
	public void visit(Field_Access x) throws TACError {
		throw new OptimizationError(
				"Trying to perform constant folding on a field access.");
	}

	@Override
	public void visit(TACInstr x) throws TACError {
		throw new OptimizationError(
				"Trying to perform constant folding on an abstract TACInstr.");
	}

	@Override
	public void visit(Call x) throws TACError {
		throw new OptimizationError(
				"Trying to perform constant folding on an abstract call.");
	}

	@Override
	public void visit(Operand x) throws TACError {
		throw new OptimizationError(
				"Trying to perform constant folding on an operand.");
	}

	@Override
	public void visit(ProgramVariable x) throws TACError {
		throw new OptimizationError(
				"Trying to perform constant folding on a program variable.");
	}

	@Override
	public void visit(TempVariable x) {
		throw new OptimizationError(
				"Trying to perform constant folding on a temp variable.");
	}

	@Override
	public void visit(Constant x) {
		throw new OptimizationError(
				"Trying to perform constant folding on a constant.");
	}

	@Override
	public void visit(String_Constant x) {
		throw new OptimizationError(
				"Trying to perform constant folding on a string constant.");
	}

}