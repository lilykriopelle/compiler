package ic.tac;


public interface TACPropagatingUpVisitor<UpType> {
	
	UpType visit(TACInstr x); 
	UpType visit(Alloc_Array x); 
	UpType visit(Alloc_Obj x); 
	UpType visit(Array_Load x);
	UpType visit(Array_Store x);
	UpType visit(BinExpr x);
	UpType visit(Call x);
	UpType visit(Check_Bounds x);
	UpType visit(Check_Null x);
	UpType visit(Cond_Jump x);
	UpType visit(Constant x);
	UpType visit(Copy x);
	UpType visit(Field_Load x);
	UpType visit(Field_Store x);
	UpType visit(Fun_Call x);
	UpType visit(Jump x);
	UpType visit(Label x);
	UpType visit(Lib_Call x);
	UpType visit(Load x);
	UpType visit(Operand x);
	UpType visit(ProgramVariable x);
	UpType visit(Return x);
	UpType visit(Store x);
	UpType visit(TempVariable x);
	UpType visit(UnExpr x);
	UpType visit(String_Constant x);
	UpType visit(TAC_Comment x); 
	UpType visit(Array_Access x);
	UpType visit(Field_Access x); 
	UpType visit(Check_Positive x); 
	UpType visit(NoOp x); 
	
}
