package ic.ast;

public interface PropagateUpVisitor<UpType> {

		UpType visit(Program x) throws Exception; 
		
		/* Literals */ 
		UpType visit(BoolLiteralNode x) throws Exception;
		UpType visit(IntLiteralNode x) throws Exception;
		UpType visit(StringLiteralNode x) throws Exception;
		UpType visit(NullNode x) throws Exception;
		
		/* Access */
		UpType visit(AmbiguousAccessNode x) throws Exception; 
		UpType visit(ArrayAccessNode x) throws Exception;
		UpType visit(FieldAccessNode x) throws Exception;
		UpType visit(VarAccessNode x) throws Exception;
		
		/* Decls */
		UpType visit(ClassDecl x) throws Exception;
		UpType visit(FieldDecl x) throws Exception;
		UpType visit(MethodDecl x) throws Exception;
		UpType visit(VarDecl x) throws Exception;
		
		/* Type Nodes */
		UpType visit(ArrayTypeNode x) throws Exception; 
		UpType visit(BoolTypeNode x);
		UpType visit(ClassIDNode x);
		UpType visit(ThisNode x) throws Exception;
		UpType visit(StringTypeNode x);
		UpType visit(VoidTypeNode x); 
		UpType visit(IntTypeNode x); 
		
		/* Expr nodes */
		UpType visit(UnExprNode x) throws Exception;
		UpType visit(BinExprNode x) throws Exception; 
		UpType visit(NewArrayNode x) throws Exception, Exception;
		UpType visit(InstantiationNode x) throws Exception; 
		
		/* Stmt Nodes */
		UpType visit(AssignmentNode x) throws Exception;
		UpType visit(InitNode x) throws Exception;
		UpType visit(BlockNode x) throws Exception; 
		UpType visit(BreakNode x);
		UpType visit(ContinueNode x);
		UpType visit(IfNode x) throws Exception;
		UpType visit(WhileNode x) throws Exception;
		UpType visit(ReturnNode x) throws Exception;
		UpType visit(CallStmtNode x) throws Exception; 
		UpType visit(EmptyStatementNode x); 
		
		/* Call nodes */
		UpType visit(LibCallNode x) throws Exception;
		UpType visit(VirtualCallNode x) throws Exception;
		
	}

