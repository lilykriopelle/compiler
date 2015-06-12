package ic.ast;

public interface PropagatingVisitor<UpType, DownType> {
	
	UpType visit(Program x, DownType context) throws Exception; 
	
	/* Literals */ 
	UpType visit(BoolLiteralNode x, DownType y) throws Exception;
	UpType visit(IntLiteralNode x, DownType y) throws Exception;
	UpType visit(StringLiteralNode x, DownType y) throws Exception;
	UpType visit(NullNode x, DownType y) throws Exception;
	
	/* Access */
	UpType visit(AmbiguousAccessNode x, DownType y) throws Exception; 
	UpType visit(ArrayAccessNode x, DownType y) throws Exception;
	UpType visit(FieldAccessNode x, DownType y) throws Exception;
	UpType visit(VarAccessNode x, DownType y);
	
	/* Decls */
	UpType visit(ClassDecl x, DownType y) throws Exception;
	UpType visit(FieldDecl x, DownType y);
	UpType visit(MethodDecl x, DownType y) throws Exception;
	UpType visit(VarDecl x, DownType y);
	
	/* Type Nodes */
	UpType visit(ArrayTypeNode x, DownType y) throws Exception; 
	UpType visit(BoolTypeNode x, DownType y) throws Exception;
	UpType visit(ClassIDNode x, DownType y) throws Exception;
	UpType visit(ThisNode x, DownType y) throws Exception;
	UpType visit(StringTypeNode x, DownType y) throws Exception;
	UpType visit(VoidTypeNode x, DownType y) throws Exception; 
	UpType visit(IntTypeNode x, DownType y) throws Exception; 
	
	/* Expr nodes */
	UpType visit(AssignmentNode x, DownType y) throws Exception;
	UpType visit(InitNode x, DownType y) throws Exception;
	UpType visit(UnExprNode x, DownType y) throws Exception;
	UpType visit(BinExprNode x, DownType y) throws Exception; 
	UpType visit(NewArrayNode x, DownType y) throws Exception; 
	
	/* Stmt Nodes */
	UpType visit(BlockNode x, DownType y) throws Exception; 
	UpType visit(BreakNode x, DownType y) throws Exception;
	UpType visit(ContinueNode x, DownType y) throws Exception;
	UpType visit(IfNode x, DownType y) throws Exception;
	UpType visit(InstantiationNode x, DownType y) throws Exception;
	UpType visit(WhileNode x, DownType y) throws Exception;
	UpType visit(ReturnNode x, DownType y) throws Exception;
	UpType visit(CallStmtNode x, DownType y) throws Exception; 
	UpType visit(EmptyStatementNode x, DownType y) throws Exception; 
	
	/* Call nodes */
	UpType visit(LibCallNode x, DownType y) throws Exception;
	UpType visit(VirtualCallNode x, DownType y) throws Exception;
	
}
