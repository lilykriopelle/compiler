package ic.dfa;

import ic.cfg.ControlFlowGraph;
import ic.tac.Alloc_Array;
import ic.tac.Alloc_Obj;
import ic.tac.Array_Access;
import ic.tac.Array_Load;
import ic.tac.Array_Store;
import ic.tac.BinExpr;
import ic.tac.Call;
import ic.tac.Check_Bounds;
import ic.tac.Check_Null;
import ic.tac.Check_Positive;
import ic.tac.Cond_Jump;
import ic.tac.Constant;
import ic.tac.Copy;
import ic.tac.Field_Access;
import ic.tac.Field_Load;
import ic.tac.Field_Store;
import ic.tac.Fun_Call;
import ic.tac.Jump;
import ic.tac.Label;
import ic.tac.Lib_Call;
import ic.tac.Load;
import ic.tac.NoOp;
import ic.tac.Operand;
import ic.tac.ProgramVariable;
import ic.tac.Return;
import ic.tac.Store;
import ic.tac.String_Constant;
import ic.tac.TACInstr;
import ic.tac.TACPropagatingVisitor;
import ic.tac.TAC_Comment;
import ic.tac.TempVariable;
import ic.tac.UnExpr;

/**
 * Same as ReachableAnalysis, but illustrates how to leverage the TAC
 * visitor pattern (if you have one) to implement the transfer functions
 * in a relatively elegant way.
 */
public class ReachableAnalysisWithVisitor extends DataFlowAnalysis<Boolean> implements TACPropagatingVisitor<Boolean,Boolean> {

	public ReachableAnalysisWithVisitor(ControlFlowGraph cfg) {
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
		return instr.accept(this, t);
	}

	// Just write each transfer function for an instruction 
	// form as a visit method: 
	
	public Boolean visit(BinExpr x, Boolean t) {
		return t;
	}

	public Boolean visit(Copy x, Boolean t) {
		return t;
	}

	@Override
	public Boolean visit(TACInstr x, Boolean y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean visit(Alloc_Array x, Boolean y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean visit(Alloc_Obj x, Boolean y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean visit(Array_Load x, Boolean y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean visit(Array_Store x, Boolean y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean visit(Call x, Boolean y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean visit(Check_Bounds x, Boolean y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean visit(Check_Null x, Boolean y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean visit(Cond_Jump x, Boolean y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean visit(Constant x, Boolean y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean visit(Field_Load x, Boolean y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean visit(Field_Store x, Boolean y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean visit(Fun_Call x, Boolean y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean visit(Jump x, Boolean y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean visit(Label x, Boolean y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean visit(Lib_Call x, Boolean y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean visit(Load x, Boolean y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean visit(Operand x, Boolean y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean visit(ProgramVariable x, Boolean y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean visit(Return x, Boolean y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean visit(Store x, Boolean y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean visit(TempVariable x, Boolean y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean visit(UnExpr x, Boolean y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean visit(String_Constant x, Boolean y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean visit(TAC_Comment x, Boolean y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean visit(Array_Access x, Boolean y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean visit(Field_Access x, Boolean y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean visit(Check_Positive x, Boolean y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean visit(NoOp x, Boolean y) {
		// TODO Auto-generated method stub
		return null;
	}

	// ... Rest of visit methods here ...

}
