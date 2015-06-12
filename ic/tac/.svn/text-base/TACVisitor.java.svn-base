package ic.tac;

import ic.error.TACError;

public interface TACVisitor {
	
		void visit(Load x) throws TACError;
		void visit(Store x) throws TACError;
		
		void visit(Array_Load x) throws TACError;
		void visit(Array_Store x) throws TACError;
		
		void visit(Field_Load x) throws TACError;
		void visit(Field_Store x) throws TACError;
	
		void visit(BinExpr x) throws TACError;
		void visit(UnExpr x) throws TACError;
		
		void visit(Alloc_Array x) throws TACError; 
		void visit(Alloc_Obj x) throws TACError; 
		
		void visit(Return x) throws TACError;
		void visit(Fun_Call x) throws TACError;
		void visit(Lib_Call x) throws TACError;
	
		void visit(Cond_Jump x) throws TACError;
		void visit(Jump x);
		void visit(Label x);
		
		void visit(Check_Positive x) throws TACError;
		void visit(Check_Bounds x) throws TACError;
		void visit(Check_Null x) throws TACError;
		
		void visit(NoOp x); 
		void visit(TAC_Comment x);
		
		void visit(Array_Access x) throws TACError; 
		void visit(Field_Access x) throws TACError;
		
		void visit(TACInstr x) throws TACError; 
		void visit(Call x) throws TACError;
		
		void visit(Operand x) throws TACError;
		void visit(ProgramVariable x) throws TACError;
		void visit(TempVariable x);
		void visit(Constant x);
		void visit(String_Constant x); 

}
