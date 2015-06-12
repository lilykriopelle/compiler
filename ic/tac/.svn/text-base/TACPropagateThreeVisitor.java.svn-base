package ic.tac;

public interface TACPropagateThreeVisitor<UpType, DownType1, DownType2> {
	UpType visit(TACInstr x, DownType1 d1, DownType2 d2); 
	UpType visit(Alloc_Array x, DownType1 d1, DownType2 d2); 
	UpType visit(Alloc_Obj x, DownType1 d1, DownType2 d2); 
	UpType visit(Array_Load x, DownType1 d1, DownType2 d2);
	UpType visit(Array_Store x, DownType1 d1, DownType2 d2);
	UpType visit(BinExpr x, DownType1 d1, DownType2 d2);
	UpType visit(Call x, DownType1 d1, DownType2 d2);
	UpType visit(Check_Bounds x, DownType1 d1, DownType2 d2);
	UpType visit(Check_Null x, DownType1 d1, DownType2 d2);
	UpType visit(Cond_Jump x, DownType1 d1, DownType2 d2);
	UpType visit(Constant x, DownType1 d1, DownType2 d2);
	//UpType visit(Copy x, DownType1 d1, DownType2 d2);
	UpType visit(Field_Load x, DownType1 d1, DownType2 d2);
	UpType visit(Field_Store x, DownType1 d1, DownType2 d2);
	UpType visit(Fun_Call x, DownType1 d1, DownType2 d2);
	UpType visit(Jump x, DownType1 d1, DownType2 d2);
	UpType visit(Label x, DownType1 d1, DownType2 d2);
	UpType visit(Lib_Call x, DownType1 d1, DownType2 d2);
	UpType visit(Load x, DownType1 d1, DownType2 d2);
	UpType visit(Operand x, DownType1 d1, DownType2 d2);
	UpType visit(ProgramVariable x, DownType1 d1, DownType2 d2);
	UpType visit(Return x, DownType1 d1, DownType2 d2);
	UpType visit(Store x, DownType1 d1, DownType2 d2);
	UpType visit(TempVariable x, DownType1 d1, DownType2 d2);
	UpType visit(UnExpr x, DownType1 d1, DownType2 d2);
	UpType visit(String_Constant x, DownType1 d1, DownType2 d2);
	UpType visit(TAC_Comment x, DownType1 d1, DownType2 d2); 
	UpType visit(Array_Access x, DownType1 d1, DownType2 d2);
	UpType visit(Field_Access x, DownType1 d1, DownType2 d2); 
	UpType visit(Check_Positive x, DownType1 d1, DownType2 d2); 
	UpType visit(NoOp x, DownType1 d1, DownType2 d2); 
}
