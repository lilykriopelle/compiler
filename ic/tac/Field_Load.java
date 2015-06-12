package ic.tac;

import ic.error.OptimizationError;
import ic.error.TACError;


public class Field_Load extends TACInstr implements AvailableExpr {

    Field_Access field; 
    TACVar dest; 
	
    public Field_Load(TACVar dest, Field_Access field) {
    	this.field = field; 
    	this.dest = dest; 
    }
    
    public boolean accessesField(String f) {
	return field.getField().equals(f);
    } 
    
    @Override 
    public String toString() {
    	return (String)field.accept(new TAC_StringGenerator()); 
    }

    @Override
    public boolean equals (Object other) {
    	return hashCode() == other.hashCode(); 
    } 
    
    // Since toString only prints the field access of the instruction, this is
  	// what we want to be hashing on in available expressions.
    @Override 
     public int hashCode() {
    	return toString().hashCode();
     }
    
    public boolean involves(TACVar var) {
	// We will need to invalidate this expression if *.field
	// is redefined but we will deal with this specially.
	return false; 
	}

	
    public TACVar getDest() {
	return dest; 
    }
	
    public Field_Access getField() {
	return field; 
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
    
    @Override
	public void setDest(TACVar dest) {
		this.dest = dest; 
	}

}
