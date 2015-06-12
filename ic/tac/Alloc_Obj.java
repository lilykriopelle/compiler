package ic.tac;

import ic.ast.ClassDecl;
import ic.error.TACError;
import ic.error.OptimizationError; 

public class Alloc_Obj extends Allocation {

    ClassDecl classDecl; 
    TACVar dest; 
	
    public Alloc_Obj(ClassDecl classDecl, TACVar dest) {
	this.classDecl = classDecl; 
	this.dest = dest; 
    }

    public boolean involves(TACVar other) {
	throw new OptimizationError("Allocating an object should not be an available expr."); 
    }

    public <UpType> UpType accept(TACPropagatingUpVisitor<UpType> v) {
	return v.visit(this);
    }
	
    public void accept(TACVisitor v) throws TACError {
	v.visit(this);
    }

    public TACVar getDest() {
	return dest; 
    }
	
    public String getClassName() {
	return classDecl.getName();
    }
	
    public Integer getSize() {
	return classDecl.getSize();
    }
	
    public <UpType, DownType> UpType accept(TACPropagatingVisitor<UpType, DownType> v,
					    DownType t) {
	return v.visit(this, t);
    }
	
    public <UpType, DownType1, DownType2> UpType accept(TACPropagateThreeVisitor<UpType, DownType1, DownType2> v,
							DownType1 t1, DownType2 t2) {
	return v.visit(this, t1, t2);
    }
}
