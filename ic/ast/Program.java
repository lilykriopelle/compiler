package ic.ast;

import ic.symtab.SymbolTable;

import java.util.Vector;


public class Program extends ASTNode {
	Vector<ClassDecl> classes = new Vector<ClassDecl>();
	SymbolTable scope = null;
	MethodDecl main = null; 

	public Program() {
		super(0);
	}
	
	public Vector<ClassDecl> getClasses() {
		return classes;
	}
	
	public void setScope(SymbolTable scope) {
		this.scope = scope;
	}
	
	public void setMain(MethodDecl main) {
		this.main = main; 
	}
	
	public MethodDecl getMain() {
		return main;
	}
	
	public void add(ClassDecl c) {
		classes.add(c); 
	}

	public SymbolTable getGlobalSymbolTable() {
		return scope;
	}
	
	 
	public String toString() {
		String toString = ""; 
		for (ClassDecl cur: classes) {
			toString += "\n" + cur; 
		}
		return toString; 
	} 	
	
	 
	public void accept(Visitor v) throws Exception {
		v.visit(this); 
	}
	
	 
	public <UpType,DownType> UpType accept(PropagatingVisitor<UpType,DownType> v, DownType t) throws Exception {
		return v.visit(this, t);
	}
	
	 
	public <DownType> void accept(PropagateDownVisitor<DownType> v, DownType t) throws Exception {
		v.visit(this, t); 
	}
	
	 
	public <UpType> UpType accept(PropagateUpVisitor<UpType> v) throws Exception {
		return v.visit(this); 
	}
	
	
}
