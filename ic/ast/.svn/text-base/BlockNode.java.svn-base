package ic.ast;

import ic.error.ScopingError;
import ic.error.TACError;
import ic.error.TypeError;
import ic.symtab.SymbolTable;

import java.util.Vector;

public class BlockNode extends StmtNode {
	Vector<InitNode> variableDecls; 
	Vector<StmtNode> statements;
	SymbolTable scope = null;
	
	public BlockNode(int line,
			Vector<InitNode> variableDecls, Vector<StmtNode> statements) {
		super(line);
		this.variableDecls = variableDecls;
		this.statements = statements;
	}
	
	
	public Vector<InitNode> getDecls() {
		return variableDecls;
	}
	
	public Vector<StmtNode> getStatements() {
		return statements;
	}
	
	public void setScope(SymbolTable scope) {
		this.scope = scope;
	}
	
	
	@Override
	public String toString() {
		String toString = "{"; 
		if (variableDecls != null) {
			for (InitNode assignment: variableDecls) {
				toString += "\n" + assignment; 
			}
		}
		if (statements != null) {
			for (StmtNode stmtNode: statements) {
				toString += "\n" + stmtNode; 
			}
		}
		return toString + "\n}"; 
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

	public SymbolTable getScope() {
		return scope;
	}
	
	public <UpType> UpType accept(PropagateUpVisitor<UpType> v) throws Exception {
		return v.visit(this); 
	}
}

