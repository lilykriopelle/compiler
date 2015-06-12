package ic.tac;

import ic.error.TACError;
import ic.error.OptimizationError; 

public class Cond_Jump extends TACInstr implements Jumper {

    Operand cond;
    Label label;
	
    public Cond_Jump(Operand tempWithBool, Label label) {
	this.cond = tempWithBool;
	this.label = label; 
    }

    

    public Operand getCond() {
	return cond; 
    }
	
    public Label getLabel() {
	return label; 
    }
	
    public <UpType> UpType accept(TACPropagatingUpVisitor<UpType> v) {
	return v.visit(this);
    }
	
    public void accept(TACVisitor v) throws TACError {
	v.visit(this);
    }
	
    public <UpType, DownType> UpType accept(TACPropagatingVisitor<UpType, DownType> v,
					    DownType t) {
	return v.visit(this, t);
    }
	
    public <UpType, DownType1, DownType2> UpType accept(TACPropagateThreeVisitor<UpType, DownType1, DownType2> v,
							DownType1 t1, DownType2 t2) {
	return v.visit(this, t1, t2);
    }

    public void setCond(Operand cond) {
	this.cond = cond; 
    }

}