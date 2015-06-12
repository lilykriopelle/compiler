package ic.ast;

import ic.error.TACError;

/** 
 * An interface for AST visitors.
 */
public interface Visitor {

	
	void visit(Program x) throws Exception;
	
	/* Literals */ 
	void visit(BoolLiteralNode x);
	void visit(IntLiteralNode x);
	void visit(StringLiteralNode x);
	void visit(NullNode x);
	
	/* Access */
	void visit(AmbiguousAccessNode x); 
	void visit(ArrayAccessNode x);
	void visit(FieldAccessNode x);
	void visit(VarAccessNode x);
	
	/* Decls */
	void visit(ClassDecl x) throws Exception;
	void visit(FieldDecl x) throws Exception;
	void visit(MethodDecl x) throws Exception;
	void visit(VarDecl x) throws Exception;
	
	/* Type Nodes */
	void visit(ArrayTypeNode x); 
	void visit(BoolTypeNode x);
	void visit(ClassIDNode x);
	void visit(ThisNode x);
	void visit(StringTypeNode x); 
	void visit(VoidTypeNode x);
	void visit(IntTypeNode x); 
	
	/* Expr nodes */
	void visit(AssignmentNode x); 
	void visit(InitNode x);
	void visit(UnExprNode x);
	void visit(BinExprNode x); 
	void visit(NewArrayNode x);
	void visit(InstantiationNode x);
	
	/* Stmt Nodes */
	void visit(BlockNode x) throws Exception; 
	void visit(BreakNode x);
	void visit(ContinueNode x);
	void visit(IfNode x) throws Exception;
	void visit(WhileNode x) throws Exception;
	void visit(ReturnNode x) throws Exception;
	void visit(CallStmtNode x) throws Exception;
	void visit(EmptyStatementNode x) throws Exception; 
	
	
	/* Call nodes */
	void visit(LibCallNode x);
	void visit(VirtualCallNode x);
	
}