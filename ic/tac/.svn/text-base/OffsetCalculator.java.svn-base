package ic.tac;
import ic.ast.*;
import java.util.*;
import ic.ast.AmbiguousAccessNode;
import ic.ast.ArrayAccessNode;
import ic.ast.ArrayTypeNode;
import ic.ast.AssignmentNode;
import ic.ast.BinExprNode;
import ic.ast.BlockNode;
import ic.ast.BoolLiteralNode;
import ic.ast.BoolTypeNode;
import ic.ast.BreakNode;
import ic.ast.CallStmtNode;
import ic.ast.ClassDecl;
import ic.ast.ClassIDNode;
import ic.ast.ContinueNode;
import ic.ast.EmptyStatementNode;
import ic.ast.FieldAccessNode;
import ic.ast.FieldDecl;
import ic.ast.IfNode;
import ic.ast.InitNode;
import ic.ast.InstantiationNode;
import ic.ast.IntLiteralNode;
import ic.ast.IntTypeNode;
import ic.ast.LibCallNode;
import ic.ast.MethodDecl;
import ic.ast.NewArrayNode;
import ic.ast.NullNode;
import ic.ast.Program;
import ic.ast.PropagateDownVisitor;
import ic.ast.ReturnNode;
import ic.ast.StringLiteralNode;
import ic.ast.StringTypeNode;
import ic.ast.ThisNode;
import ic.ast.TypeNode;
import ic.ast.UnExprNode;
import ic.ast.VarAccessNode;
import ic.ast.VarDecl;
import ic.ast.VirtualCallNode;
import ic.ast.VoidTypeNode;
import ic.ast.WhileNode;
import ic.error.TACError;
import ic.*;
import ic.symtab.SymbolTable;

/** This class calculates offsets for fields and methods by checking for inherited
 * fields and methods first, in order to determine a) the lowest offset for new 
 * fields and methods and b) the offsets for overriden methods. It uses the setOffset()
 * method in the DeclNode class to set the integer offset field associated with every 
 * Declaration Node in the AST. */

public class OffsetCalculator {

	private final static int OFF = 8;
	
	public void calculateOffsets(Program p) {
		calculateFieldOffsets(p);
		calculateMethodOffsets(p);
		calculateParameterOffsets(p);
	}
	
	
	
	private void calculateParameterOffsets(Program p) {
		for (ClassDecl c : p.getClasses()) {
			for (Decl d: c.getDeclarations()) {
				if (d instanceof MethodDecl) {
					int numArgs = ((MethodDecl) d).getParams().size();
					//System.out.println("NUM ARGS of " + d.getName() + ":" + numArgs);
					for (int i = numArgs-1; i >= 0; --i) {
						
						// subtract 1 from i-numArgs in order to leave space in the stack frame for
						// "this", which is an implicit parameter for every method.
						((MethodDecl) d).getParams().elementAt(i).setOffset(i + 2);
						
						// debugging printing
						//System.out.println("param offset = " + ((MethodDecl)d).getParams().elementAt(i).getOffset()
							//	+ " for param " + ((MethodDecl)d).getParams().elementAt(i).getName());
					
					}
				}
			}
		}
	}
	
	
	
	private void calculateMethodOffsets(Program p) {
		
		for (ClassDecl c : p.getClasses()) {
			
			int index = 0;
			int numNewMethods = 0; 
			
			String superClass = c.getSuperClass();
			if (superClass.equals("")) {				
				for (Decl d : c.getDeclarations()) {
					if (d instanceof MethodDecl) {
						d.setOffset(index * OFF);
						// With no superclasses we can set the number representing the method, 
						// simply to the current index. 
						((MethodDecl)d).setNum(index); 
						++index;
						++numNewMethods; 
					}
				}
			} else {
				ClassDecl supClass = p.getGlobalSymbolTable().findClass(superClass);
				Vector<String> supClassMethods = new Vector<String>(getMethods(supClass, p.getGlobalSymbolTable()));
				index = supClassMethods.size(); 
				int methodNum = index; 
				
				for (Decl d : c.getDeclarations()) {
					if (d instanceof MethodDecl) {
						String method = d.getName();
						if (supClassMethods.contains(method)) {
							//Overriden method
							d.setOffset(OFF * supClassMethods.indexOf(method));
						} else {
							d.setOffset(OFF * index);
							++index;
						}
						((MethodDecl)d).setNum(methodNum); 
						++methodNum;
						++numNewMethods; 
					}
				}
			}
			
			c.setNumNewMethdos(numNewMethods); 
			c.setNumMethods(index); // This allows us to create an array of Strings with the method names 
									// for when we construct the vtables in code generation. 
		}	
	}

	private void calculateFieldOffsets(Program p) {
		for (ClassDecl c : p.getClasses()) {
			//System.out.println("class: " + c.getName());
			
			// Leave room for the class' vpointer
			
			int i = 1;
			
			String superClass = c.getSuperClass();
			
			if (superClass.equals("")) {				
				for (Decl d : c.getDeclarations()) {
					if (d instanceof FieldDecl) {
						d.setOffset(i * OFF);
						++i;
					}
				}
			
			} else {
				
				ClassDecl supClass = p.getGlobalSymbolTable().findClass(superClass);
				Vector<String> supClassFields = new Vector<String>(getFields(supClass, p.getGlobalSymbolTable()));
				i = i + supClassFields.size();
				//System.out.println("supClassFields : " + supClassFields.size());
				for (Decl d : c.getDeclarations()) {		
					if (d instanceof FieldDecl) {
						d.setOffset(i * OFF);
						++i;
					}
				}
				
			}

			c.setSize(i*OFF);
		
		}
	}
	
	
	private Vector<String> getMethods(ClassDecl supClass, SymbolTable global) {
		Vector<String> supClassMethods = new Vector<String>();
		while (supClass != null) {

			for (int i = supClass.getDeclarations().size() - 1; i >= 0; --i) {
				Decl d = supClass.getDeclarations().elementAt(i);
				if (d instanceof MethodDecl && ! supClassMethods.contains(d.getName())) {
					supClassMethods.add(0, d.getName());
				}
			}
			supClass = global.findClass(supClass.getSuperClass());
		}	
		return supClassMethods;
	}
	
	private Vector<String> getFields(ClassDecl supClass, SymbolTable global) {
		Vector<String> supClassFields = new Vector<String>();
		while (supClass != null) {
			for (int i = supClass.getDeclarations().size() - 1; i >= 0; --i) {
				Decl d = supClass.getDeclarations().elementAt(i);
				if (d instanceof FieldDecl) {
					supClassFields.add(0, d.getName());
				}
			}
			supClass = global.findClass(supClass.getSuperClass());
		}	
		return supClassFields;
	}
}
