package ic.tac;

public interface TACPropagatingVisitor<UpType, DownType> {
	
    // Loads and stores
    UpType visit(Load x, DownType y);
    UpType visit(Store x, DownType y);    		
    UpType visit(Field_Load x, DownType y);
    UpType visit(Field_Store x, DownType y);
    UpType visit(Array_Load x, DownType y);
    UpType visit(Array_Store x, DownType y);

    // Expressions 
    UpType visit(BinExpr x, DownType y);
    UpType visit(UnExpr x, DownType y);
 		
    // Runtime checks
    UpType visit(Check_Positive x, DownType y); 
    UpType visit(Check_Bounds x, DownType y);
    UpType visit(Check_Null x, DownType y);		

    // Call Instructions
    UpType visit(Fun_Call x, DownType y);
    UpType visit(Lib_Call x, DownType y);
    UpType visit(Return x, DownType y);
    
    // Allocate instrs
    UpType visit(Alloc_Array x, DownType y); 
    UpType visit(Alloc_Obj x, DownType y); 
    
    // Jump Instructions
    UpType visit(Jump x, DownType y);
    UpType visit(Cond_Jump x, DownType y);
    UpType visit(Label x, DownType y);
	       
    // Complex operands 
    UpType visit(Array_Access x, DownType y);
    UpType visit(Field_Access x, DownType y); 
    		
    UpType visit(TAC_Comment x, DownType y); 
    UpType visit(NoOp x, DownType y); 

    // Simple operands 
    UpType visit(Operand x, DownType y);
    UpType visit(ProgramVariable x, DownType y);
    UpType visit(TempVariable x, DownType y);
    UpType visit(String_Constant x, DownType y);
    UpType visit(Constant x, DownType y);
    
    // Abstract TACInstrs 
    UpType visit(TACInstr x, DownType y); 
    UpType visit(Call x, DownType y);
}


