package ic.tc;

import java.util.Vector;

import ic.ast.*;
import ic.error.ScopingError;
import ic.error.TACError;
import ic.error.TypeError;
import ic.symtab.SymbolTable;

/* Subtyping: 
 * In the interesting case when subtypes may be nontrivial, 
 * we will start with a ClassIDNode representing the type of the object.
 * We will then look in the symbol tables for other class declarations.
 * Every reachable class declaration is a superclass of the current class 
 * and hence will be a supertype for the instantiated object. If we don't 
 * see any other class declarations, the only supertype will be Null. 
 * 
 * For Strings and Arrays the only supertype will be Null. Integers and Booleans
 * have no supertypes. 
 * 
 */
public class TypeChecker implements PropagateUpVisitor<TypeNode> {
	
	MethodDecl mainMethod = null; 
	
	
	@Override
	public TypeNode visit(Program x) throws Exception {
		for(ClassDecl c: x.getClasses()) {
			c.accept(this);
		}
		
		if( mainMethod == null ) {
			throw new ScopingError(x.getLine(), "No main method defined in program.");
		} else {
			x.setMain(mainMethod); 
		}
		
		return null;
	}


	/** We return the type of the resolved location in the ambiguous access node. */
	@Override
	public TypeNode visit(AmbiguousAccessNode x) {
		return x.getType();
	}

	/** For an array access, we check to make sure that the index expr resolves to an integer 
	 * and the array expr resolves to an element of ArrayType. If so, we return the element
	 * type from inside the ArrayType. 
	 * @throws ScopingError 
	 * @throws TACError 
	 */
	@Override
	public TypeNode visit(ArrayAccessNode x) throws Exception {
		//Check to see that the type checked index expression resolves to an integer
		ExprNode index = x.getIndex();  
		if(! (index.accept(this) instanceof IntTypeNode)) {
			throw new TypeError(x.getLine(), "Array indices must be integers.");
		}
		
		//Similarly check to see that the array expression resolves to an array type.
		ExprNode array = x.getArray();
		TypeNode arrayType = array.accept(this);
		if (arrayType instanceof ArrayTypeNode) {
			TypeNode elementType = ((ArrayTypeNode)arrayType).getElementType();
			x.setType(elementType);
			return elementType; 
		} else {
			throw new TypeError(x.getLine(), "Trying to perform array access on an expr which does not resolve to an array.");
		}
	}
	
	@Override
	public TypeNode visit(VarAccessNode x) {
		return x.getType();
	}

	@Override
	public TypeNode visit(ClassDecl x) throws Exception {
		for (Decl d: x.getDeclarations()) {
			d.accept(this);
		}
		return null;
	}

	/* We return the declared type of the field. */
	@Override
	public TypeNode visit(FieldDecl x) {
		return x.getType();
	}

	/* Checks that the overriding method's signature matches the overriden one. */
	private void checkOverriding(MethodDecl method, MethodDecl overriden) throws TypeError, ScopingError {
		//The current method's return type must be the same as the overriden type
		if( ! method.getReturnType().isEqualTo(overriden.getReturnType())) {
			throw new ScopingError(method.getLine(), method.getName() + "'s return type does not match that of the overriden method. ");
		}
		Vector<VarDecl> overridenParams = overriden.getParams();
		Vector<VarDecl> curParams = method.getParams();
		if(curParams.size() != overridenParams.size()) {
			throw new ScopingError(method.getLine(), method.getName() + "'s number of parameters (" + curParams.size() 
									+ ")  does not match the number (" + overridenParams.size() +  ") in the overriden method .");
		}
		//The parameters of the current method must be supertypes of the overriden parameters
		for(int i = 0; i < curParams.size(); ++i) {
			if( ! curParams.get(i).getType().isEqualTo(overridenParams.get(i).getType())) {
				throw new ScopingError(method.getLine(), method.getName() + "'s parameter (" + (i + 1) + ") does not match the parameter in the overriden method. " );
			}
		}
	}
	
//	/* Checks that the overriding method is a subtype of the overriden one. */
//	private void checkOverriding(MethodDeclNode method, MethodDeclNode overriden) throws TypeError, ScopingError {
//		//The current method's return type must be a subclass of the overriden type
//		checkSubtyping(overriden.getReturnType(), method.getReturnType(), method.getName() + "'s return type does not properly subtype that of the overriden method. ");
//		Vector<VarDeclNode> overridenParams = overriden.getParams();
//		Vector<VarDeclNode> curParams = method.getParams();
//		if(curParams.size() != overridenParams.size()) {
//			throw new ScopingError(method.getLine(), method.getName() + "'s number of parameters (" + curParams.size() 
//									+ ")  does not match the number (" + overridenParams.size() +  ") in the overriden method .");
//		}
//		//The parameters of the current method must be supertypes of the overriden parameters
//		for(int i = 0; i < curParams.size(); ++i) {
//			checkSubtyping(curParams.get(i).getType(), overridenParams.get(i).getType(), method.getName() + "'s parameter (" + (i + 1) + ") is not a supertype of the parameter in the overriden method. " );
//		}
//	}
	
	@Override
	public TypeNode visit(MethodDecl x) throws Exception {
		
		Vector<TypeNode> paramTypes = new Vector<TypeNode>();
		for (VarDecl p: x.getParams()) {
			paramTypes.add(p.accept(this));
		}
		
		if(x.getName().equals("main")) {
			if(mainMethod != null) {
				throw new ScopingError(x.getLine(), "Multiple main methods defined in program.");
			} else {
				//Check return type
				if(x.getReturnType() instanceof VoidTypeNode) {
					//Check parameter 
					if(paramTypes.size() == 0) {
						throw new TypeError(x.getLine(), "Main method missing parameter.");
					}
					
					TypeNode paramType = paramTypes.get(0);
					if (x.getParams().size() == 1 && paramType instanceof ArrayTypeNode 
							&& (((ArrayTypeNode)paramType).getElementType() instanceof StringTypeNode)) {
						//parameters and return type match
						mainMethod = x;
					} else {
						throw new TypeError(x.getLine(), "The formal parameter of the main method must be of type string[].");
					}
				} else {
					throw new TypeError(x.getLine(), "The main method must have return type void.");
				}
			}
		}
		
		if(x.getOverriden() != null) {
			checkOverriding(x, x.getOverriden());
		}
		
		TypeNode methodReturn = x.getMethodBody().accept(this);
		if((x.getReturnType() instanceof VoidTypeNode)) {
			if( methodReturn == null ) return null;
			else throw new TypeError(x.getLine(), "Return type error: void methods cannot return a value.");
		} else if (methodReturn == null) {
			throw new TypeError(x.getLine(), "Return type error: missing return statement.");
		} else {
			return checkSubtyping(x.getReturnType(), methodReturn, "Return type error: ");
		}
		
	}

	/* We return the declared type of the variable. */
	@Override
	public TypeNode visit(VarDecl x) {
		return x.getType();
	}

	@Override
	public TypeNode visit(ThisNode x) {
		return x.getResolvedClass();
	}

	/* Checks that typeRight is a subtype of typeLeft. 
	 * We throw errors within this helper method so we can provide more detailed information.
	 * This does pose the issue that we can not use this method when we don't want to immediately throw errors. 
	 */
	private TypeNode checkSubtyping(TypeNode typeLeft, TypeNode typeRight, String message) throws TypeError {
		/* Used for multiple possible return statements in a method which must all be 
		 * subtypes of the return type of the method. 
		 */
		if(typeRight instanceof PossibleTypes) {
			TypeNode resolvedType = null; 
			for(TypeNode curType: ((PossibleTypes)typeRight).getPossibilities()) {
				resolvedType = checkSubtyping(typeLeft, curType, message);
			}
			return resolvedType; 
		}
		
		if (typeLeft instanceof ArrayTypeNode) {
			TypeNode leftArrayType = ((ArrayTypeNode)typeLeft).getElementType();

			if( typeRight instanceof VoidTypeNode ) {
				return typeLeft; 
			} else if (! (typeRight instanceof ArrayTypeNode) ) {
				throw new TypeError (typeRight.getLine(), 
									message + "Expected " + leftArrayType + " array. Found " + typeRight);
			} else {
				if (! ofSameType(typeLeft, typeRight)) {
					throw new TypeError(typeRight.getLine(), message + "Array element type mismatch.");
				} else {
					return typeLeft;
				}
			}
		} 
		
		if(typeLeft instanceof StringTypeNode) {
			if(!(typeRight instanceof StringTypeNode || typeRight instanceof VoidTypeNode)) {
				throw new TypeError(typeRight.getLine(), message + "Expected String or null. Found " + typeRight);
			} else return typeLeft;
		} else if (typeLeft instanceof IntTypeNode) {
			if(!(typeRight instanceof IntTypeNode)) {
				throw new TypeError(typeRight.getLine(), message + "Expected int. Found " + typeRight + ".");
			} else return typeLeft;
		} else if (typeLeft instanceof BoolTypeNode) {
			if(!(typeRight instanceof BoolTypeNode)) {
				throw new TypeError(typeRight.getLine(), message + "Expected boolean. Found " + typeRight + ".");
			} else return typeLeft;
		} else {
			if(typeRight instanceof VoidTypeNode || 
					(typeRight instanceof ClassIDNode && ((ClassIDNode)typeRight).isSubTypeOf((ClassIDNode)typeLeft))) {
				return typeLeft;
			} else {
				throw new TypeError(typeRight.getLine(), message + "Type mismatch. Expected subclass of " + typeLeft + ". Found " + typeRight + ".");
			}
		}
		
	}
	
	@Override
	public TypeNode visit(AssignmentNode x) throws Exception {
		TypeNode typeLeft = x.getLeft().accept(this);
		TypeNode typeRight = x.getRight().accept(this);
		checkSubtyping(typeLeft, typeRight, "Assignment Error. "); 
		return null; 
	}
	
	/* Init node should basically duplicate the work in Assignment node. */
	@Override
	public TypeNode visit(InitNode x) throws Exception {
		TypeNode typeLeft = x.getLeft().accept(this);
		if (x.getRight() != null) {
			TypeNode typeRight = x.getRight().accept(this);
			checkSubtyping(typeLeft, typeRight, "Initialization Error. "); 
		}
		return null;
	}

	@Override
	public TypeNode visit(UnExprNode x) throws Exception {
		UnOp op = x.getOp();
		TypeNode t = x.getExpr().accept(this); 
		if(op == UnOp.UMINUS && (t instanceof IntTypeNode)) {
			x.setType(t);
			return t;
		} else if (op == UnOp.NOT && (t instanceof BoolTypeNode)) {
			x.setType(t);
			return t; 
		} else if (op == UnOp.LENGTH && (t instanceof ArrayTypeNode)) {
			TypeNode intType = new IntTypeNode(x.getLine());
			x.setType(intType);
			return intType; 
		} else {
			throw new TypeError(x.getLine(), "Type Mismatch cannot apply operator " + op + " to " + t);
		}
	}
	
	private boolean voidMatch(TypeNode other) {
		return other instanceof VoidTypeNode || other instanceof StringTypeNode || other instanceof ClassIDNode;
	}
	
	private boolean ofSameType(TypeNode left, TypeNode right) {
		// Since there is no array subtyping, we simply return whether or not the arrays 
		// are of the precisely same type. 
		if (left instanceof ArrayTypeNode) {
			return left.isEqualTo(right);
		}
		
		/* Deals with the special case of one of the two operands being null
		 * which should be allowed when the other is of String, Void, or ClassID type.  
		 */
		if(left instanceof VoidTypeNode && voidMatch(right) 
				|| right instanceof VoidTypeNode && voidMatch(left)) {
			return true;
		}
		
		// Check if they are the exact same type
		if(left.isEqualTo(right)) {
			return true; 
		} 
		
		// Check if one ClassIDNode is a subtype of the other
		if (left instanceof ClassIDNode && right instanceof ClassIDNode) {
				return ((ClassIDNode)left).isSubTypeOf(((ClassIDNode)right)) || ((ClassIDNode)right).isSubTypeOf(((ClassIDNode)left));
		} else {
			return false;
		}				
	}

	@Override
	public TypeNode visit(BinExprNode x) throws Exception {
		/* Treating... 
		 * Arithmetic operators: PLUS, MINUS, MULT, MOD, DIV that's operands must be integers and return integers
		 * Relational operators: LT, LE, GT, GE that's operands must be integers and return booleans
		 * Equality Comparison operators: NE, EQEQ that's operands must have the same type and return booleans
		 * Conditional operators: AND, OR that's operands must be booleans and return booleans
		 */
		TypeNode typeLeft = x.getLeft().accept(this);
		TypeNode typeRight = x.getRight().accept(this);
		
		//Arithmetic and relational operators
		if(x.isArithmetic()) {
			//Allow plus to be applied to strings
			if(x.getOp() == BinOp.PLUS && typeLeft instanceof StringTypeNode && typeRight instanceof StringTypeNode) {
					return new StringTypeNode(x.getLine()); 
			} else if(typeLeft instanceof IntTypeNode && typeRight instanceof IntTypeNode) {
				return new IntTypeNode(x.getLine());
			} else {
				throw new TypeError(x.getLine(), "Arithmetic operator applied to noninteger expressions.");
			}
		} else if(x.isRelational()) {
			if(typeLeft instanceof IntTypeNode && typeRight instanceof IntTypeNode) {
				return new BoolTypeNode(x.getLine());
			} else {
				throw new TypeError(x.getLine(), "Relational operator applied to noninteger expressions.");
			}
		} else if(x.isEqualityComp()) {
			if( ofSameType(typeLeft, typeRight) ) {
				return new BoolTypeNode(x.getLine());
			} else {
				throw new TypeError(x.getLine(), "Equality operator applied to operands of different types.");
			}
		} else if(x.isCondOp()) {
			if(typeLeft instanceof BoolTypeNode && typeRight instanceof BoolTypeNode) {
				return new BoolTypeNode(x.getLine());
			} else {
				throw new TypeError(x.getLine(), "Conditional operator applied to nonboolean expressions.");
			}
		} else {
			throw new TypeError(x.getLine(), "Binary Expression not exhaustive -- uncaught operator.");
		}
	}

	@Override
	public TypeNode visit(BlockNode x) throws Exception {
		for (InitNode assign : x.getDecls()) {
			assign.accept(this);
		}
		TypeNode type = null; 
		for (StmtNode stmt : x.getStatements()) {
			TypeNode newType = stmt.accept(this);
			if(type == null) type = newType;
			else if (type != null && newType != null) { /* indicating multiple return statements */
				throw new TypeError(x.getLine(), "Multiple return statements in block.");
			}
		}	
		return type;
	}

	@Override
	public TypeNode visit(BreakNode x) {
		//do nothing
		return null;
	}

	@Override
	public TypeNode visit(ContinueNode x) {
		// do nothing
		return null;
	}

	@Override
	public TypeNode visit(IfNode x) throws Exception {
		TypeNode typeCond = x.getCondition().accept(this);
		if( typeCond instanceof BoolTypeNode ) {
			TypeNode typeThen = x.getThen().accept(this);
			if(x.getElse() instanceof EmptyStatementNode) {
				return typeThen; 
			} else {
				TypeNode typeElse = x.getElse().accept(this);
				if(typeThen != null && typeElse != null) {
					PossibleTypes types = new PossibleTypes(x.getLine());
					types.add(x.getThen().accept(this));
					types.add(x.getElse().accept(this));
					return types;
				} else if (typeThen == null && typeThen == null) {
					return null;
				} else {
					throw new TypeError(x.getLine(), "One branch of the if returns a value while the other doesn't.");
				}
			} 
		} else {
			throw new TypeError(x.getLine(), "The condition of an if should be of type boolean.");
		}
	}

	@Override
	public TypeNode visit(InstantiationNode x) {
		return x.getType();
	}

	@Override
	public TypeNode visit(WhileNode x) throws Exception {
		TypeNode typeCond = x.getCondition().accept(this);
		if( typeCond instanceof BoolTypeNode ) {
			return x.getBlock().accept(this);
		} else {
			throw new TypeError(x.getLine(), "The condition of a while should be of type boolean.");
		}
	}

	@Override
	public TypeNode visit(ReturnNode x) throws Exception {
		if(x.getExpr() != null) {
			return (x.getExpr()).accept(this);
		} else {
			return null;
		}
	}

	@Override
	public TypeNode visit(CallStmtNode x) throws Exception {
		x.getCall().accept(this); 
		return null;
	}

	@Override
	public TypeNode visit(EmptyStatementNode x) {
		//do nothing
		return null;
	}

	public boolean parametersMatch(int line, Vector<VarDecl> formals, Vector<TypeNode> argTypes) throws TypeError {
		if(formals.size() != argTypes.size()) {
			throw new TypeError(line, "The number of arguments (" + argTypes.size() + ") "
									+ "does not match the number of formal parameters (" + formals.size() + ")");
		} else {
			int index = 0; 
			while(index < formals.size()) {
				TypeNode formalType = formals.get(index).getType();
				TypeNode argType = argTypes.get(index);
				if(checkSubtyping(formalType, argType, "Parameter type error. ") != null) {
					index++;
				} else {
					throw new TypeError(line, "Type mismatch on parameter (" + (index + 1) + "). "
											+ "Expected subclass of " + formalType + "." 
											+ "Found " +  argType + ".");
				}
			}
			return true;
		}
	}
	
	@Override
	public TypeNode visit(LibCallNode x) throws Exception {
		/* Even though we are not type checking the arguments against the definition of the Library call, we should 
		 * make sure the arguments in and of themselves are well typed. 
		 */
		for (ExprNode arg: x.getArguments()) {
			arg.accept(this);
		}	
		
		String method = x.getMethod();
		if(method.equals("println") || method.equals("print") || method.equals("printi") 
				|| method.equals("printb") || method.equals("exit")) {
			return new VoidTypeNode(x.getLine());
		} else if (method.equals("readi") || method.equals("stoi") || method.equals("random") || method.equals("time")) {
			return new IntTypeNode(x.getLine());
		} else if (method.equals("readln") || method.equals("itos") || method.equals("atos")) {
			return new StringTypeNode(x.getLine());
		} else if (method.equals("eof")) {
			return new BoolTypeNode(x.getLine()); 
		} else if (method.equals("stoa")) {
			return new ArrayTypeNode(x.getLine(), new IntTypeNode(x.getLine())); 
		} else {
			throw new TypeError(x.getLine(), "Calling the undefined library function " + method);
		}
	}
	
	@Override
	public TypeNode visit(FieldAccessNode x) throws Exception {
		//This must resolve to a ClassIDNode
		TypeNode receiverType = x.getReceiver().accept(this);
		
		if(receiverType instanceof ClassIDNode) {
			ClassIDNode classType = (ClassIDNode)receiverType; 
			SymbolTable classScope = classType.getResolved().getScope();
			
			/** Search for the field in the class scope */
			FieldDecl field = classScope.findField(x.getField()); 
			if(field == null) {
				throw new ScopingError(x.getLine(), x.getField() + " undefined in " + classType.getName()); 
			} else {
				x.resolve(field);
				return field.getType();
			}
		} else {
			throw new TypeError(x.getLine(), "Field access on a " + receiverType);
		}
	}
	
	
	@Override
	public TypeNode visit(VirtualCallNode x) throws Exception {
		
		/** The receiver must resolve to a ClassIDNode type. */
		TypeNode receiverType = x.getReceiver().accept(this);
		
		if( ! (receiverType instanceof ClassIDNode)) {
			throw new TypeError(x.getLine(), "Virtual call on a " + receiverType); 
		}
		
		ClassIDNode classType = (ClassIDNode)receiverType; 
		SymbolTable classScope = classType.getResolved().getScope();
		
		/* Checks to see that the method is defined within the specified class */
		MethodDecl method = classScope.findMethod(x.getMethodName()); 
		if (method== null) {
			throw new ScopingError(x.getLine(), "The method " + x.getMethodName() 
					+ " is not defined in " + classType.getName() + ".");
		} else {
			x.resolve(method);
		} 
		
		// Resolve the arguments to types 
		Vector<TypeNode> argTypes = new Vector<TypeNode>();
		for(ExprNode curArg : x.getArguments()) {
			TypeNode curType = curArg.accept(this);
			argTypes.add(curType);
		}
		
		// Check that those arguments match the formal parameters 
		if(parametersMatch(x.getLine(), method.getParams(), argTypes)) { 
			return method.getReturnType();
		} else {
			throw new TypeError(x.getLine(), "Arguments do not match formal parameters.");
		}
	}
	
	/** For literals we simply return the type of the literal. */
	
	@Override
	public TypeNode visit(BoolLiteralNode x) {
		return new BoolTypeNode(x.getLine());
	}

	@Override
	public TypeNode visit(IntLiteralNode x) {
		return new IntTypeNode(x.getLine());
	}

	@Override
	public TypeNode visit(StringLiteralNode x) {
		return new StringTypeNode(x.getLine());
	}

	@Override
	public TypeNode visit(NullNode x) {
		return new VoidTypeNode(x.getLine());
	}
	
	/** For type nodes we simply return the type node right back. */
	
	@Override
	public TypeNode visit(ArrayTypeNode type) {
		return type;
	}

	@Override
	public TypeNode visit(BoolTypeNode type) {
		return type;
	}

	@Override
	public TypeNode visit(ClassIDNode type) {
		return type;
	}
	
	@Override
	public TypeNode visit(StringTypeNode type) {
		return type;
	}

	@Override
	public TypeNode visit(VoidTypeNode type) {
		return type;
	}

	
	@Override
	public TypeNode visit(IntTypeNode type) {
		return type;
	}

	@Override
	public TypeNode visit(NewArrayNode x) {
		return x.getType();
	}
	
}