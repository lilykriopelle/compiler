package ic.tac;

import ic.ast.StringLiteralNode;
import ic.error.CodeError;
import ic.error.TACError;

public class String_Constant extends Constant {
	
    public String_Constant(StringLiteralNode x) {
	super(x); 
    }

    public boolean involves(TACVar other) {
	return false; 
    }

    public <UpType> UpType accept(TACPropagatingUpVisitor<UpType> v) {
	return v.visit(this);
    }
    
    public void accept(TACVisitor v) throws TACError {
	v.visit(this);
    }
      
    
    public String getValue() {
	return (String)super.getValue(); 
    }
	
    @Override
    public int getOffset() throws CodeError {
	throw new CodeError("Trying to get offset on a string.");
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
