package ic.opt;

import ic.ast.*;
import ic.dfa.DataFlowAnalysis;
import ic.dfa.LiveVariableAnalysis;
import ic.tac.TACList;
import ic.tac.TAC_Printer;

/**
 * To create an optimization, simply subclass this class and define
 * optimize(md) to compute any dataflow information necessary to
 * perform the optimization on md and then modify the method's TAC
 * list. You can either provide methods in your TACList class to
 * modify an existing list, or you can construct a new list to replace
 * the old one.
 */
public abstract class Optimization {
	
	/**
	 * Apply the optimization to each Method in p.
	 */
	public void optimize(Program p) {

		/* 
		 * We do this work in the main of the compiler. 
		 * Could change that by passing the results of live variable into
		 * the TACInstrs.
		 */
		int instrs = 0; 
		int optimizedInstrs = 0; 
		for (ClassDecl c : p.getClasses()) {
			for (Decl elem : c.getDeclarations()) {
				if (elem instanceof MethodDecl) {
					instrs += ((MethodDecl)elem).getTAC().getInstrs().size(); 
				    optimize((MethodDecl) elem);
				    optimizedInstrs += ((MethodDecl)elem).getTAC().getInstrs().size(); 
				}
			}
		}
		System.out.println("###Unoptimized num of instructions: " + instrs);
		System.out.println("###Optimized num of instructions after " + getName() + ": " + optimizedInstrs);
	}


	/**
	 * Apply the optimization to the method md.
	 */
	public abstract void optimize(MethodDecl md);
	
	public abstract String getName(); 
}
