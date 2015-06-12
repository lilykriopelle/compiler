package ic.tc;

import java.util.Vector;
import ic.ast.TypeNode;

/* Used to deal with the various possible types of return statements in branches within methods. */ 
public class PossibleTypes extends TypeNode {
	Vector<TypeNode> possibilities;
	public PossibleTypes(int line) {
		super(line);
		possibilities = new Vector<TypeNode>();
	}
	
	public void add(TypeNode t) {
		possibilities.add(t);
	}
	
	public Vector<TypeNode> getPossibilities() {
		return possibilities; 
	}
}
