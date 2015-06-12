package ic.tac;

import java.util.Stack;
import java.util.Vector;
import ic.ast.*;
import ic.error.TACError;

/* This visitor generates the lists of TAC instructions stores in the method declarations. */
public class TAC_Generator implements PropagatingVisitor<TACList, TACVar> {

	private Stack<TACVar> stackFrame;
	private Stack<LoopContext> loopContexts;
	
	int i;
	static int label = 0;  // steve: static so you don't reuse labels in different methods. 
	
	public TAC_Generator() {
		i = 0;
		stackFrame = new Stack<TACVar>(); 
		loopContexts = new Stack<LoopContext>();
		getNextRegister();
		getNextRegister();
	}
	
	private int getNextLabelIndex() {
		int oldLabel = label; 
		label++; 
		return oldLabel; 
	}
	
	private TempVariable getNextRegister() {
		int oldIndex = i; 
		i++; 
		TempVariable next = new TempVariable(oldIndex, false); 
		stackFrame.push(next);
		return next;
	}
	
	private int getCurIndex() {
		int oldIndex = i; 
		i++; 
		return oldIndex;
	}
	
	public TACList visit(Program x, TACVar dest) throws Exception {
		throw new TACError(0, "Calling TAC generation on a program node."); 
	}
	
	
	public TACList visit(ClassDecl x, TACVar dest) throws Exception {
		throw new TACError(0, "Calling TAC generation on a class node."); 
	}
	
	
	public TACList visit(MethodDecl x, TACVar dest) throws Exception {
		TACList bodyInstrs = x.getMethodBody().accept(this, new TempVariable("Method")); 
		bodyInstrs.setIndex(i); 
		x.setInstrs(bodyInstrs);
		x.setFrame(stackFrame); 
		return bodyInstrs; 
	}
	
	
	public TACList visit(FieldDecl x, TACVar dest) {
		 // We will never end up here, since methods do not have field declarations.  
		return null; 
	}

	
	public TACList visit(VarDecl x, TACVar dest) {
		ProgramVariable decl; 
		if(x.isParameter()) {
			decl = new ProgramVariable(x, x.getOffset()); 
		} else {
			if (x.getIndex() == 0) {
				int index = getCurIndex(); 
				x.setOffset(-index);
			}
			decl = new ProgramVariable(x, x.getOffset());
			stackFrame.add(decl);
		}
		return decl; 
	}

	
	public TACList visit(VarAccessNode x, TACVar dest) {

		ProgramVariable decl; 
		if(x.getResolved().isParameter()) {
			decl = new ProgramVariable(x.getResolved(), x.getResolved().getOffset()); 
		} else {
			decl = new ProgramVariable(x.getResolved(), x.getResolved().getOffset());
			//stackFrame.add(decl);
		}

		return decl; 

	}
	
	
	public TACList visit(ThisNode x, TACVar dest) throws Exception {
		//Since this is the last parameter passed onto the stack 	
		TempVariable t = new TempVariable(2, true);
		t.resolve(x.getResolvedClass().getResolved());
		return t;
	
	}

	
	public TACList visit(BoolLiteralNode x, TACVar dest) throws Exception {
		return new Constant(x); 	
	}

	
	public TACList visit(IntLiteralNode x, TACVar dest) throws Exception {
		return new Constant(x); 
	}

	/** Strings will need to be handled specially during x86
	 *  generation so they constitute their own subclass of
	 *  Constant.
	 */
	public TACList visit(StringLiteralNode x, TACVar dest) throws Exception {
		return new String_Constant(x); 
	}

	
	public TACList visit(NullNode x, TACVar dest) throws Exception {
		return new Constant(x); 
	}

	
	public TACList visit(AmbiguousAccessNode x, TACVar dest) throws Exception {
		return x.getResolvedLocation().accept(this, dest); 
	}


	
	public TACList visit(ArrayTypeNode x, TACVar dest) {
		// do nothing for types
		return null;
	}

	
	public TACList visit(BoolTypeNode x, TACVar dest) {
		// do nothing for types
		return null;
	}

	
	public TACList visit(ClassIDNode x, TACVar dest) {
		// do nothing for types
		return null;
	}

	
	public TACList visit(StringTypeNode x, TACVar dest) {
		// do nothing for types
		return null;
	}

	
	public TACList visit(VoidTypeNode x, TACVar dest) {
		// do nothing for types
		return null;
	}

	
	public TACList visit(IntTypeNode x, TACVar dest) {
		// do nothing for types
		return null;
	}

	
	public TACList visit(BreakNode x, TACVar loopEnd) {
		return new TACList(new Jump(loopContexts.peek().getEnd()));
	}

	
	public TACList visit(ContinueNode x, TACVar dest) {
		return new TACList(new Jump(loopContexts.peek().getBegin()));
	}
	
	
	public TACList visit(IfNode x, TACVar dest) throws Exception {
		TACList ifInstrs = new TACList(); 
		
		// Compute the condition and take the logical not of it. This result will be the condition of cjump.
		TempVariable ifCond = getNextRegister(); 
		Operand cond = handleOperand(x.getCondition().accept(this, ifCond), ifInstrs, ifCond); 
		TempVariable notCond = getNextRegister();
		ifInstrs.add(new UnExpr(UnOp.NOT, cond, notCond));
		
		String labelName;
		boolean hasElse = ! (x.getElse() instanceof EmptyStatementNode); 
		if(hasElse) {
			labelName = "else" + getNextLabelIndex(); 
		} else {
			labelName = "endIf" + getNextLabelIndex();
		}
		
		Label endIf = new Label(labelName);
		ifInstrs.add(new Cond_Jump(notCond, endIf)); 
		
		ifInstrs.add(x.getThen().accept(this, new TempVariable("then")));
		
		
		if(hasElse) {
			Label endElse = new Label("endElse" + getNextLabelIndex()); 
			//After then, we must skip the else by jumping to its end
			ifInstrs.add(new Jump(endElse)); 
			
			ifInstrs.add(endIf); 
			ifInstrs.add(x.getElse().accept(this, null));	
			ifInstrs.add(endElse);
		} else {
			ifInstrs.add(endIf); 
		}
		
		return ifInstrs; 
		
	}

	
	public TACList visit(WhileNode x, TACVar dest) throws Exception {
		TACList whileInstrs = new TACList(); 
		TempVariable whileCond = getNextRegister(); 
		Label whileBegin = new Label("whileBegin" + getNextLabelIndex()); 
	
		whileInstrs.add(whileBegin);
		
		// Compute the condition and take the logical not of it. This result will be the condition of cjump.
		Operand cond = handleOperand(x.getCondition().accept(this, whileCond), whileInstrs, whileCond); 
		TempVariable notCond = getNextRegister();
		whileInstrs.add(new UnExpr(UnOp.NOT, cond, notCond));
		Label whileEnd = new Label("whileEnd" + getNextLabelIndex()); 
		whileInstrs.add(new Cond_Jump(notCond, whileEnd));
		
		//We pass the beginning and ending labels into the loop context so that break and continue will know where to jump to.
		loopContexts.push(new LoopContext(whileBegin, whileEnd));
		
		whileInstrs.add(x.getBlock().accept(this, new TempVariable("Error: loop body"))); 
		whileInstrs.add(new Jump(whileBegin));
		whileInstrs.add(whileEnd);
		
		loopContexts.pop();
		
		return whileInstrs; 
	}
	
	
	
	public TACList visit(AssignmentNode x, TACVar dest) throws Exception {
		TACList assignmentInstrs = new TACList(); 
		TempVariable rightReg = getNextRegister();
		Operand right = handleOperand(x.getRight().accept(this, rightReg), assignmentInstrs, rightReg); 
		
		LocationNode left = x.getLeft(); 
		if(left instanceof AmbiguousAccessNode) {
			left = ((AmbiguousAccessNode)left).getResolvedLocation();
		}

		if(left instanceof FieldAccessNode) {
			FieldAccessNode cur = (FieldAccessNode)left; 
			Field_Access field = (Field_Access)left.accept(this, new TempVariable("field access"));
			assignmentInstrs.add(field.getChecks()); 
			assignmentInstrs.add(new Field_Store(field, right)); 
		} else if(left instanceof ArrayAccessNode) {
			Array_Access array = (Array_Access)left.accept(this, new TempVariable("array access"));
			assignmentInstrs.add(array.getChecks()); 
			assignmentInstrs.add(new Array_Store(array, right));
		} else {
			Operand var = (Operand)left.accept(this, new TempVariable("variable"));
			assignmentInstrs.add(new Store((TACVar)var, right));
		}
		
		return assignmentInstrs; 
		
	}

	
	public TACList visit(InitNode x, TACVar dest) throws Exception {
		TACList assignmentInstrs = new TACList(); 
		ProgramVariable left = (ProgramVariable)x.getLeft().accept(this, null); 
		//This will put the result of the right into the destination specified by init
		if(x.getRight() != null) {
			Operand value = handleOperand(x.getRight().accept(this, left), assignmentInstrs, left);
			if(value != left) {
				assignmentInstrs.add(new Store(left, value));
			}
		}
		return assignmentInstrs; 
	}
	
	

	/** This will load the operand into the register if it is not already in one: 
	 *  useful for inits and parameters, which must be stored in registers. */
	private TACVar loadOperand(TACList operand, TACList context, TACVar dest) {
		Operand strippedOp = handleOperand(operand, context, dest); 
		if(strippedOp != dest) { 
			context.add(new Load(dest, (Operand)strippedOp));
		}
		return dest; 
	}

	/** This helper method loads operands into registers so they can be used in larger expressions.
	 */
	private Operand handleOperand(TACList operand, TACList context, TACVar dest) {
		// We will always want to load arrays into registers when they are on the right side of an expression
		if (operand instanceof Array_Access) {
			context.add(((Array_Access)operand).getChecks());
			context.add(new Array_Load(dest, (Array_Access) operand));
			return (Operand)dest; 
		} else if (operand instanceof Field_Access) {
			context.add(((Field_Access)operand).getChecks());
			context.add(new Field_Load(dest, (Field_Access) operand));
			return (Operand)dest; 
		} else if (operand instanceof Operand){
			return (Operand)operand;
		} else {
			context.add(operand); 
			return (Operand)dest; 
		} 
	}
	
	/** Binary expression deals with the special cases of
	 *  short-circuit AND and OR and string concatenation.  It
	 *  makes heavy use of handleOperand which will
	 */
	public TACList visit(BinExprNode x, TACVar dest) throws Exception {
		
		TACList binInstrs = new TACList(); 
		TempVariable leftRegister = getNextRegister();
		TempVariable rightRegister = getNextRegister(); 
		
		Operand left = handleOperand(x.getLeft().accept(this, leftRegister), binInstrs, leftRegister); 
		
		/** We need to deal specially with short-circuit AND
		 * and OR, which involve jumps.  */
		if(x.getOp() == BinOp.AND) {
			binInstrs.add(new TAC_Comment("Short-circuit AND."));
			TempVariable condReg = getNextRegister(); 
			binInstrs.add(new UnExpr(UnOp.NOT, (Operand)left, condReg));
			
			Label setToFalse = new Label("setToFalse" + getNextLabelIndex()); 
			Label endAnd = new Label("endAnd" + getNextLabelIndex()); 
			
			binInstrs.add(new Cond_Jump(condReg, setToFalse));
			
			Operand right = handleOperand(x.getRight().accept(this, rightRegister), binInstrs, rightRegister); 
			binInstrs.add(new Store(dest, right)); // because her we know that the left is true
			
			binInstrs.add(new Jump(endAnd));
			
			binInstrs.add(setToFalse);
			binInstrs.add(new Store(dest, new Constant(new BoolLiteralNode(x.getLine(), false))));
			binInstrs.add(endAnd); 
			
		} else if(x.getOp() == BinOp.OR) {
			binInstrs.add(new TAC_Comment("Short-circuit OR."));
			
			if(left instanceof Operand) {
				TempVariable condReg = getNextRegister(); 
				binInstrs.add(new Store(condReg, left));
				left = condReg;
			}
			
			Label setToTrue = new Label("setToTrue" + getNextLabelIndex()); 
			Label endOR = new Label("endOR" + getNextLabelIndex()); 
			
			binInstrs.add(new Cond_Jump(left, setToTrue));
			
			Operand right = handleOperand(x.getRight().accept(this, rightRegister), binInstrs, rightRegister); 
			binInstrs.add(new Store(dest, right)); 
			
			binInstrs.add(new Jump(endOR));
			
			binInstrs.add(setToTrue);
			binInstrs.add(new Store(dest, new Constant(new BoolLiteralNode(x.getLine(), true))));
			binInstrs.add(endOR); 
		} else {
		
			Operand right = handleOperand(x.getRight().accept(this, rightRegister), binInstrs, rightRegister); 
			BinOp op = x.getOp();
			if(x.getRight().getType() instanceof StringTypeNode) {
			    //System.out.println("String concatenation"); 
				op = BinOp.CONCAT;
			}
			BinExpr expr = new BinExpr(left, op, right, dest);
			binInstrs.add(expr); 
		}
		
		return binInstrs; 
	}
	
	public TACList visit(UnExprNode x, TACVar dest) throws Exception {
		TACList unInstrs = new TACList(); 
		
		TempVariable exprReg = getNextRegister(); 
		Operand operand = handleOperand(x.getExpr().accept(this, exprReg), unInstrs, exprReg); 
		
		if(x.getOp() == UnOp.LENGTH) {
			unInstrs.add(new Check_Null(operand)); 
		}
		
		unInstrs.add(new UnExpr(x.getOp(), operand, dest));
		return unInstrs; 
	}
	
	
	public TACList visit(ArrayAccessNode x, TACVar register) throws Exception {
		TACList arrayAccess = new TACList();
		arrayAccess.add(new TAC_Comment("Array Access"));
		TempVariable indexReg = getNextRegister(); 
		TempVariable arrayReg = getNextRegister(); 
		
		Operand array = handleOperand(x.getArray().accept(this, arrayReg), arrayAccess, arrayReg);
		arrayAccess.add(new Check_Null(array));
		
		Operand index = handleOperand(x.getIndex().accept(this, indexReg), arrayAccess, indexReg);
		arrayAccess.add(new Check_Bounds(index, array));
		return new Array_Access(array, index, arrayAccess);
	}

	
	public TACList visit(FieldAccessNode x, TACVar dest) throws Exception {
		TACList fieldAccess = new TACList();
		TempVariable receiverReg = getNextRegister();
		Operand receiver = handleOperand(x.getReceiver().accept(this, receiverReg), fieldAccess, receiverReg);
		fieldAccess.add(new Check_Null(receiver));
		return new Field_Access(receiver, x.getResolved(), fieldAccess); 
	}
	
	/** For virtual calls we must have a run time check to ensure that the receiver is not null. We must also 
	 * pass the arguments backwards onto the stack frame but forwards into Fun_Call, hence the gymnastics in the loop.
	 */
	public TACList visit(VirtualCallNode x, TACVar dest) throws Exception {
		TACList methodInstrs = new TACList();
		methodInstrs.add(new TAC_Comment("Method call " + x.getMethodName()));
		
		TempVariable receiverReg = getNextRegister(); 
		Operand receiver = handleOperand(x.getReceiver().accept(this, receiverReg), methodInstrs, receiverReg); 
		methodInstrs.add(new Check_Null(receiver));
		
		Vector<Operand> paramRegs = new Vector<Operand>(); 
		Vector<ExprNode> args = x.getArguments();
		// We iterate backwards so that the parameters are added to the stack frame in the
		// reverse order
		if(args.size() > 0) {
			for(int i = args.size() - 1; i >= 0; --i) {
				TempVariable curReg = getNextRegister(); 
				TACVar reg = loadOperand(args.get(i).accept(this, curReg), methodInstrs, curReg);
				paramRegs.add(0, reg); //so that the parameters print forward
			}
		}
		methodInstrs.add(new Fun_Call(receiver, x.getResolvedMethod(), paramRegs, dest));
		return methodInstrs; 
	}

	

	/** In this method we allocate a new array. First we must do a runtime check that the length expression 
	 *  exceeds 0. Then we must compute the size of the array by multiplying the length by 8 and adding 8 for
	 *  the length field. We finally add the allocate array TAC instruction. 
	 */
	public TACList visit(NewArrayNode x, TACVar dest) throws Exception {
		TACList newArrayList = new TACList(); 
		TempVariable lengthReg = getNextRegister();
		Operand length = handleOperand(x.getSize().accept(this, lengthReg), newArrayList, lengthReg);
		/** Checks that the length is greater than 0. */
		newArrayList.add(new Check_Positive(length)); 
		newArrayList.add(new Alloc_Array(length, dest));
		return newArrayList; 

	}

	/** The resolved class declaration should be able to compute the needed space for the object so in instantiation
	 *  we simply pass the resolved class to Alloc_Obj. 
	 */
	public TACList visit(InstantiationNode x, TACVar dest) {
		return new TACList(new Alloc_Obj(x.getInstantiatedClass().getResolved(), dest));
	}

	
	public TACList visit(BlockNode x, TACVar dest) throws Exception {
		TACList blockInstrs = new TACList();
		for(InitNode cur: x.getDecls()) {
			blockInstrs.add(cur.accept(this, dest));
		}
		for(StmtNode cur: x.getStatements()) {
			blockInstrs.add(cur.accept(this, dest));
		}
		return blockInstrs; 
	}


	
	public TACList visit(ReturnNode x, TACVar label) throws Exception {
		TACList returnList = new TACList(); 
		if(x.getExpr() == null) {
			returnList.add(new Return(null, i));
		} else {
			TempVariable returnReg = getNextRegister();
			Operand temp = handleOperand(x.getExpr().accept(this, returnReg), returnList, returnReg);
			returnList.add(new Return(temp, i)); 
		}
		return returnList; 
	}

	
	public TACList visit(CallStmtNode x, TACVar dest) throws Exception {
		return x.getCall().accept(this, dest); 
	}

	
	public TACList visit(EmptyStatementNode x, TACVar dest) {
		// do nothing
		return new TACList(); 
	}
	
	
	public TACList visit(LibCallNode x, TACVar dest) throws Exception {
		TACList libInstrs = new TACList();
		libInstrs.add(new TAC_Comment("Library call " + x.getMethod()));
		Vector<Operand> paramRegs = new Vector<Operand>(); 
		for(ExprNode param: x.getArguments()) {
			TempVariable curReg = getNextRegister();
			TACVar paramOp = loadOperand(param.accept(this, curReg), libInstrs, curReg);
			paramRegs.add(paramOp);
		}
		libInstrs.add(new Lib_Call(x.getMethod(), paramRegs, dest));
		return libInstrs; 
	}


}
