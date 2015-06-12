package ic;

import java.util.Vector;

import ic.ast.*;
import ic.error.ScopingError;
import ic.error.TACError;
import ic.error.TypeError;
import ic.IndentingPrintStream;

/**
 * This class traverses the tree of nodes created during the parse, printing
 * them out in order. 
 */
public class PrettyPrint implements PropagateUpVisitor<String> {
	
	IndentingPrintStream out = new IndentingPrintStream(System.out);
	
	@Override
	public String visit(VarAccessNode x) {
		out.print(x.getName());
		if (x.getResolved() != null) {
			return " " + x.getResolved().getName() + ":" + x.getResolved().getType() + " declared on line " + x.getResolved().getLine(); 
		} else return "";
	}
	
	
	public String visit(Program x) throws Exception {
		for (ClassDecl c : x.getClasses()) {
			c.accept(this);
		}
		return ""; 
	}

	@Override
	public String visit(BoolLiteralNode x) {
		out.print(x.getValue()); 
		return ""; 
	}

	@Override
	public String visit(IntLiteralNode x) {
		out.print(x.getValue()); 
		return ""; 
	}

	@Override
	public String visit(StringLiteralNode x) {
		out.print("\"" + x.getValue() + "\"");
		return ""; 
	}

	@Override
	public String visit(NullNode x) {
		out.print("null");
		return ""; 
	}

	@Override
	public String visit(AmbiguousAccessNode x) {
		out.print(x.getName());
		LocationNode resolvedLoc = x.getResolvedLocation();
		if(resolvedLoc != null) {
			Decl resolved = null; 
			if (resolvedLoc instanceof VarAccessNode) {
				resolved = ((VarAccessNode)resolvedLoc).getResolved();
			} else if (resolvedLoc instanceof FieldAccessNode) {
				resolved = ((FieldAccessNode)resolvedLoc).getResolved(); 
			}
			if(resolved != null) {
				return " " + resolved.getName() + ":" + resolved.getType() + " declared on line " + resolved.getLine();
			} else return "";
		} else return "";
	}

	@Override
	public String visit(ArrayAccessNode x) throws Exception {
		x.getArray().accept(this);
		out.print("[");
		String resolveIndex = x.getIndex().accept(this);
		out.print("]");
		return resolveIndex; 
	}

	@Override
	public String visit(FieldAccessNode x) throws Exception {
		x.getReceiver().accept(this);
		out.print("." + x.getField());
		if (x.getResolved() != null) {
			return " " + x.getResolved().getName() + ":" + x.getResolved().getType() + "-" + x.getResolved().getLine(); 
		} else return "";
	}

	
	@Override
	public String visit(ClassDecl x) throws Exception {
		out.print("\n");
		out.print("class " + x.getName());
		if (x.getSuperClass() != "") out.print(" extends " + x.getSuperClass());
		out.print(" {\n");
		out.println();
		out.indentMore();
		for (Decl d : x.getDeclarations()) {
			d.accept(this);
		}
		out.indentLess();
		out.println("");
		out.println("}");
		return ""; 
	}


	@Override
	public String visit(FieldDecl x) throws Exception {
		x.getType().accept(this);
		out.print(" " + x.getName());
		out.print("; ---	offset = " + x.getOffset() + "\n");
		return ""; 
	}

	@Override
	public String visit(MethodDecl x) throws Exception {
		out.println();
	//	out.println(x.getName() + "() --- offset = " + x.getOffset());
		out.println(x.getName() + "() --- framesize = " + x.getFrameSize());
		out.print(x.getReturnType() + " "); 
		out.print(x.getName());
		out.print("(");
		for (int i = 0; i < x.getParams().size(); ++i) {
			x.getParams().get(i).accept(this);
			if (i != x.getParams().size() - 1) {
				out.print(", ");
			}
		}
		out.print(") ");
		x.getMethodBody().accept(this);
		out.println("");
		return ""; 
	}

	@Override
	public String visit(VarDecl x) throws Exception {
		if(x.getType() != null) {
			x.getType().accept(this);
			out.print(" ");
		}
		out.print(x.getName());
		return "";
	}


	@Override
	public String visit(ArrayTypeNode x) throws Exception {
		x.getElementType().accept(this);
		out.print("[]"); 
		return ""; 
	}

	@Override
	public String visit(BoolTypeNode x) {
		out.print("boolean"); 
		return ""; 
	}

	@Override
	public String visit(ClassIDNode x) {
		out.print(x.getName()); 
		ClassDecl classDecl = ((ClassIDNode)x).getResolved();
		if(classDecl != null) return " " + classDecl.getName() + " declared on line " + classDecl.getLine();
		else return ""; 
	}

	@Override
	public String visit(ThisNode x) {
		out.print("this"); 
		return ""; 
	}

	@Override
	public String visit(AssignmentNode x) throws Exception {
		String leftResolve = x.getLeft().accept(this);
		out.print(" = ");
		String rightResolve = x.getRight().accept(this);
		if(rightResolve.length() > 0 || leftResolve.length() > 0) {
			out.println(";\t/* " + leftResolve + rightResolve + " */");
		} else out.println(";");
		return "";
	}

	@Override
	public String visit(UnExprNode x) throws Exception {
		String exprResolve;
		if (x.getOp() == UnOp.LENGTH) {
			exprResolve = x.getExpr().accept(this);
			out.print(".length");
		} else if (x.getOp() == UnOp.UMINUS){
			out.print("-(");
			exprResolve = x.getExpr().accept(this);
			out.print(")"); 
		} else {
			out.print("!(");
			exprResolve = x.getExpr().accept(this);
			out.print(")");
		}
		return exprResolve;
	}

	@Override
	public String visit(BinExprNode x) throws Exception {
		out.print("(");
		String left = x.getLeft().accept(this);
		out.print(" " + x.opString() + " ");
		String right = x.getRight().accept(this);
		out.print(")");
		return right + left;
	}

	@Override
	public String visit(BlockNode x) throws Exception {
		
		
		out.print("{");
		out.indentMore();
		out.println("\n");
		

		for (InitNode v : x.getDecls()) {
			v.accept(this);
		}
		for (StmtNode s : x.getStatements()) {
			s.accept(this);
		}
		
		out.indentLess();
		out.print("\n");
		out.print("}\n");
		
		return "";
	}

	@Override
	public String visit(BreakNode x) {
		out.println("break;"); 
		return "";
	}

	@Override
	public String visit(ContinueNode x) {
		out.println("continue;"); 
		return "";
	}

	@Override
	public String visit(IfNode x) throws Exception {
		out.print("\nif (");
		x.getCondition().accept(this);
		out.print(") ");
		x.getThen().accept(this);
		if (! (x.getElse() instanceof EmptyStatementNode)) {
			out.print(" else ");
			x.getElse().accept(this);
		}
		out.println();
		return "";
	}

	@Override
	public String visit(InstantiationNode x) throws Exception {
		out.print("new ");
		String resolved = x.getType().accept(this);
		out.print("()");
		return resolved;
	}
	
	@Override
	public String visit(WhileNode x) throws Exception {
		out.print("while (");
		x.getCondition().accept(this);
		out.print(")");
		x.getBlock().accept(this);
		out.println();
		return "";
	}
	
	@Override
	public String visit(ReturnNode x) throws Exception {
		out.print("return ");
		String exprResolve = "";
		if(x.getExpr() != null) {
			exprResolve = x.getExpr().accept(this);
		} 
		if(exprResolve.length() > 0) out.println(";\t/*" + exprResolve + " */");
		else out.print(";\n");
		return ""; 
		
	}
	
	@Override
	public String visit(LibCallNode x) throws Exception {
		out.print("Library." + x.getMethod() + "(");
		Vector<ExprNode> args = x.getArguments(); 
		String argsResolve = "";
		if(args.size() > 0) {
			argsResolve = args.get(0).accept(this); 
			for (int i = 1; i < args.size(); i++) {
				if(i == 1) argsResolve += "\n";
				out.print(", ");
				String curResolve = args.get(i).accept(this);
				if(curResolve.length() > 0) {
					argsResolve += ", " + curResolve;
				}
			}
		}
		out.print(")");
		return argsResolve;
	}

	@Override
	public String visit(VirtualCallNode x) throws Exception {
		x.getReceiver().accept(this);
		out.print(".");
		out.print(x.getMethodName()); 
		out.print("(");
		Vector<ExprNode> args = x.getArguments();
		String argsResolve = "";
		if(args.size() > 0) {
			argsResolve = args.get(0).accept(this); 
			for (int i = 1; i < args.size(); i++) {
				if(i == 1) argsResolve += "\n";
				out.print(", ");
				String curResolve = args.get(i).accept(this);
				if(curResolve.length() > 0) {
					argsResolve += ", " + curResolve;
				}
			}
		}
		out.print(")");
		return " " + x.getResolvedMethod().getResolveName() + " declared on line " + x.getResolvedMethod().getLine() + argsResolve;
	}

	@Override
	public String visit(StringTypeNode x) {
		out.print("string"); 
		return "";
	}

	@Override
	public String visit(CallStmtNode x) throws Exception {
		String resolveCall = x.getCall().accept(this);
		if(resolveCall.length() > 0) {
			out.println("; \t/*" + resolveCall + " */");
		} else out.println(";");
		return "";
	}

	@Override
	public String visit(VoidTypeNode x) {
		out.print("void");
		return "";
	}

	@Override
	public String visit(EmptyStatementNode x) {
		out.print(""); 
		return "";
	}

	@Override
	public String visit(IntTypeNode x) {
		out.print("int");
		return "";
	}

	@Override
	public String visit(NewArrayNode x) throws Exception {
		out.print("new ");
		String elementResolve = x.getElementType().accept(this);
		out.print("[");
		String sizeResolve = x.getSize().accept(this);
		out.print("]");
		return elementResolve + sizeResolve;
	}

	@Override
	public String visit(InitNode x) throws Exception {
		String left = x.getLeft().accept(this);
		String type = "";
		if(x.getLeft().getType() instanceof ClassIDNode) {
			ClassDecl classDecl = ((ClassIDNode)x.getLeft().getType()).getResolved();
			if(classDecl != null) type = classDecl.getName() + " declared on line " + classDecl.getLine();
		}
		String right = "";
		if(x.getRight() != null) {
			out.print(" = ");
			right = x.getRight().accept(this);
		}
		if(type.length() > 0 || right.length() > 0 || left.length() > 0) out.println(";\t/* " + type + left + right + " */");
		else out.println(";");
		return "";
	}

}
