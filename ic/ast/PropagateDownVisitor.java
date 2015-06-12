package ic.ast;

import ic.error.ScopingError;
import ic.error.TACError;
import ic.error.TypeError;

public interface PropagateDownVisitor<DownType> {

		void visit(Program x, DownType context) throws Exception; 
		
		/* Literals */ 
		void visit(BoolLiteralNode x, DownType y);
		void visit(IntLiteralNode x, DownType y);
		void visit(StringLiteralNode x, DownType y);
		void visit(NullNode x, DownType y);
		
		/* Access */
		void visit(AmbiguousAccessNode x, DownType y) throws Exception; 
		void visit(ArrayAccessNode x, DownType y) throws Exception;
		void visit(FieldAccessNode x, DownType y) throws Exception;
		void visit(VarAccessNode x, DownType y) throws Exception;
		
		/* Decls */
		void visit(ClassDecl x, DownType y) throws Exception;
		void visit(FieldDecl x, DownType y) throws Exception;
		void visit(MethodDecl x, DownType y) throws Exception;
		void visit(VarDecl x, DownType y) throws Exception;
		
		/* Type Nodes */
		void visit(ArrayTypeNode x, DownType y) throws Exception; 
		void visit(BoolTypeNode x, DownType y);
		void visit(ClassIDNode x, DownType y) throws Exception;
		void visit(ThisNode x, DownType y);
		void visit(StringTypeNode x, DownType y);
		void visit(VoidTypeNode x, DownType y); 
		void visit(IntTypeNode x, DownType y); 
		
		/* Expr nodes */
		void visit(AssignmentNode x, DownType y) throws Exception;
		void visit(InitNode x, DownType y) throws Exception;
		void visit(UnExprNode x, DownType y) throws Exception;
		void visit(BinExprNode x, DownType y) throws Exception; 
		void visit(NewArrayNode x, DownType y) throws Exception;
		void visit(InstantiationNode x, DownType y) throws Exception; 
		
		/* Stmt Nodes */
		void visit(BlockNode x, DownType y) throws Exception; 
		void visit(BreakNode x, DownType y) throws Exception;
		void visit(ContinueNode x, DownType y) throws Exception;
		void visit(IfNode x, DownType y) throws Exception;
		void visit(WhileNode x, DownType y) throws Exception;
		void visit(ReturnNode x, DownType y) throws Exception;
		void visit(CallStmtNode x, DownType y) throws Exception; 
		void visit(EmptyStatementNode x, DownType y) throws TACError; 
		
		/* Call nodes */
		void visit(LibCallNode x, DownType y) throws Exception;
		void visit(VirtualCallNode x, DownType y) throws Exception;
		
	}

