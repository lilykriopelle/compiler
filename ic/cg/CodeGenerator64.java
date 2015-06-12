package ic.cg;


import ic.ast.BinOp;
import ic.ast.ClassDecl;
import ic.ast.Decl;
import ic.ast.MethodDecl;
import ic.ast.Program;
import ic.ast.UnOp;
import ic.error.CodeError;
import ic.symtab.SymbolTable;
import ic.tac.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Vector;

/**
 * A x86_64 CodeGenerator generates the assembly code for a program,
 * after each MethodDecl has been annotated with TACLists.  There is
 * some basic functionality for dealing with string constants and
 * their labels.
 * <p>
 * Feel free to change this code in any way that you find useful,
 * or to ignore it completely and roll your own code generator from 
 * scratch.
 * <p> 
 * This code assumes that your toplevel AST node is called
 * Program.  You may need to change this to match your own AST
 * hierarchy.  Usage:
 *
 * <pre>
 *   CodeGenerator cg = new CodeGenerator("file.ic", program);
 *   cg.generate();
 * </pre>
 * 
 */
public class CodeGenerator64 implements TACVisitor {
    
    /** The name of the assembly file being generated */
    protected final String asmFile;
    
    /** The writer used to print the assembly file */
    protected final PrintWriter out;
    
    /** The program being translated into assembly */
    protected final Program program;
    
   /** Whether we will use hard coded offsets or linear searching. */
    protected final boolean linearSearch; 
    
    protected final boolean cacheLookup; 
    
        
    /**
     * Given a file name "file.ic" and a program AST, construct a
     * code generator to print "file.s", the assembly code version
     * of the program.
     */
    public CodeGenerator64(String icFileName, Program program, boolean linearSearch, boolean cacheLookup) throws IOException {
    	asmFile = icFileName.substring(0, icFileName.indexOf('.')) + ".s";
        out = new PrintWriter(new FileWriter(asmFile));        
        this.program = program;
        this.linearSearch = linearSearch;
        this.cacheLookup = cacheLookup; 
    }
    
    
    /**
     * The main method to call when you wish to perform the
     * translation.
     */
    public void generate() {

        out.println("# File " + asmFile);
        out.println();
        out.println();

        generateVTables();
        generateCode();
        generateErrorHandlers();

        MethodDecl main = program.getMain(); 
        
        String classForMain = main.getClassName();
        int sizeOfMainObject = main.getEnclosingClass().getSize();
        int offsetOfMainInDV = main.getOffset();
        if(linearSearch || cacheLookup) {
        	offsetOfMainInDV = main.getNum(); 
        }
    	generateMain(classForMain, sizeOfMainObject, offsetOfMainInDV);
        generateStringConstants();

        out.close();
    }

    /**
     * Print the VTable for all the classes.  Using dispatch vector offsets calculated previously by
     * the OffsetCalculator, insert method names into the appropriate slot of the vtable array. The method name 
     * begins with the name of the class in which the method is defined.  
     * 
     *  When a method is inherited but not overridden, it appears after the name of the super class that
     *  declared it.  Otherwise, when a subclass overrides an inherited method, the method name is preceded
     *  by the name of the current class.
     *
     */
    protected void generateVTables() { 
        
        out.println("# ");                
        out.println("# VTables");                

        out.println();
        out.println(".data");
        out.println(".align 8");
        out.println();
        
        if(linearSearch || cacheLookup) {
        	/* 
             * We use the offsets calculated by the OffsetCalculator to add the method names to their 
             * proper offset within the methods in the vtable. 
            */
            for(ClassDecl c: program.getClasses()) {
            	
            	String className = c.getName();
            	out.println("_" + className + "_DV:");
            	
            	if(c.getSuperClass().equals("")) {
            		out.println("      .quad -1");
            	} else {
            		out.println("      .quad _" + c.getSuperClass() + "_DV");
            	}
            	
            	out.println("      .quad " + c.getNumNewMethods());
            	
            	Vector<MethodDecl> methodDecls = new Vector<MethodDecl>(c.getScope().getMethods().values());
            	int i = 0;
            	for(MethodDecl curMethod: methodDecls) {
            		out.println("      .quad " + (long)curMethod.getNum());
            		out.println("      .quad _" + c.getScope().getName() + "_" + curMethod.getName()); 
            		curMethod.setOffset((i + 3)*8); //two for the superclass and number of methods and one for the 
            										// number which labels it. 
            		i++;
            	}
            	
            	out.println("");
    	        out.println("");
            }
        } else {
	        /* 
	         * We use the offsets calculated by the OffsetCalculator to add the method names to their 
	         * proper offset within the methods in the vtable. 
	        */
	        for(ClassDecl c: program.getClasses()) {
	        	String[] vtable = new String[c.getNumMethods()];
	        	SymbolTable curScope = c.getScope();
	        	while(curScope != null) {
	        		Vector<MethodDecl> methodDecls = new Vector<MethodDecl>(curScope.getMethods().values());
	        		for(MethodDecl curMethod: methodDecls) {
	        			if(vtable[curMethod.getOffset()/8] == null) {
	        				vtable[curMethod.getOffset()/8] = curScope.getName() + "_" + curMethod.getName();
	        			}
	        		}
	        		curScope = curScope.getEnclosingScope();
	        	}
	        	
	        	String className = c.getName();
	        	out.println("_" + className + "_DV:");
	        	for(String methodName: vtable) {
	        		out.println("      .quad _" + methodName);
	        	}
	        	out.println("");
		        out.println("");
	        }
        }
        
    }

    private void generatePrologue(String className, String methodName, int frameSize) {
    	out.println(".text");
        out.println(".align 8");
        out.println("_" + className + "_" + methodName + ":");
        out.println("");
        out.println("     # prologue");
        out.println("     pushq %rbp                    ");
        out.println("     movq %rsp, %rbp                ");
        out.println("     subq $" + frameSize + ", %rsp                ");
        out.println("");
    }
    
    private void generateEpilogue() {
    	 out.println("     # epilogue");
         out.println("     movq %rbp, %rsp                ");
         out.println("     popq %rbp                      ");
         out.println("     ret                           ");
         out.println("");
         out.println("");
         out.println("");
    }

    /**
     * Generate Simple.main method.  You'll need to change this.
     */
    protected void generateCode() { 
                
        out.println("# ");
        out.println("# Code.");
        for(ClassDecl c: program.getClasses()) {
        	out.println("# " + c.getName());
        	for(Decl d: c.getDeclarations()) {
        		if(d instanceof MethodDecl) {
        			MethodDecl curMethod = (MethodDecl)d;
        			TACList methodInstrs = curMethod.getTAC();
        			generatePrologue(c.getName(), curMethod.getName(), curMethod.getFrameSize());
        			for(TACInstr instr : methodInstrs.getInstrs()) {
        				instr.accept(this);
        			}
        			generateEpilogue(); 
        		}
        	}
        	
        }
        

       
    }

    /**
     * Print out the assembly code to print runtime errors and
     * exit gracefully.  You should jump to these labels on
     * runtime check failure.  You should not need to change this
     * method.
     */
    protected void generateErrorHandlers() {
        out.println("# ");
        out.println("# Error handling.  Jump to these procedures when a runtime check fails.");
        out.println("");
        out.println(".data");
        out.println(".align 8");
        out.println("");
        out.println(".quad 23");
        out.println("  strNullPtrErrorChars:     .ascii \"Null pointer violation.\"");
        out.println("strNullPtrError: .quad strNullPtrErrorChars");
        out.println("");
        out.println(".quad 23");
        out.println("  strArrayBoundsErrorChars: .ascii \"Array bounds violation.\"");
        out.println("strArrayBoundsError: .quad strArrayBoundsErrorChars");
        out.println("");
        out.println(".quad 21");
        out.println("  strArraySizeErrorChars:   .ascii \"Array size violation.\"");
        out.println("strArraySizeError: .quad strArraySizeErrorChars");
        out.println("");

	out.println(".text");
        out.println(".align 8");
        out.println("labelNullPtrError:");
        out.println("    movq strNullPtrError(%rip), %rdi");
		out.println("andq $-16, %rsp   # stupid stack align...");
        out.println("    call __LIB_println");
        out.println("    movq $1, %rdi");
        out.println("    call __LIB_exit");
        out.println("");
        out.println(".align 8");
        out.println("labelArrayBoundsError:");
        out.println("    movq strArrayBoundsError(%rip), %rdi");
		out.println("andq $-16, %rsp   # stupid stack align...");
        out.println("    call __LIB_println");
        out.println("    movq $1, %rdi");
        out.println("    call __LIB_exit");
        out.println("");
        out.println(".align 8");
        out.println("labelArraySizeError:");
        out.println("    movq strArraySizeError(%rip), %rdi");
		out.println("andq $-16, %rsp   # stupid stack align...");
        out.println("    call __LIB_println");
        out.println("    movq $1, %rdi");
        out.println("    call __LIB_exit");
        out.println("");
        out.println("");
        out.println("");
    }

    /**
     * Generate the __ic_main stub that creates and calls main on
     * the right object.  You should not need to change this
     * method.
     * @param className                name of the class containing main
     * @param objectSize               size of objects of that class
     * @param indexOfMainInVTable      index on that class's vtable for main
     */
    protected void generateMain(String className, int objectSize, int indexOfMainInVTable) {
        out.println("# The main entry point.  Allocate object and invoke main on it.");
        out.println("");
	out.println(".text");
        out.println(".align 8");
        out.println(".globl __ic_main");
        out.println("__ic_main:");
        out.println("     pushq %rbp                        # prologue");
        out.println("     movq %rsp,%rbp                ");
        out.println("     pushq %rdi                        # o.main(args) > push args");
        out.println("");
        out.printf ("       movq $%4d, %%rdi                 # o = new %s\n", 
                    objectSize, className);
        out.println("     call __LIB_allocateObject   ");
        out.printf ("     leaq _%s_DV(%%rip), %%rdi       \n", 
                    className);
        out.println("     movq %rdi, (%rax)");
        out.println("     pushq %rax                        # o.main(args) > push o");
        
        if(linearSearch || cacheLookup) {
        	out.println("     movq (%rax), %rdi");
			// pass the num representing the method as the second param.
			out.println("     movq $" + indexOfMainInVTable + ", %rsi");
			out.println("     call __LIB_findMethod");
			// call the method we have found and returned
			out.println("     call *%rax");
        } else {
	        out.println("     movq (%rax), %rax            ");
	        out.printf ("     call *%d(%%rax)                   # main is at offset %d in vtable\n", 
	                    indexOfMainInVTable, indexOfMainInVTable);
        }
        
        out.println("     addq $8, %rsp                ");
        out.println("     movq $0, %rax                     # __ic_main always returns 0");
        out.println("");
        out.println("     movq %rbp,%rsp                    # epilogue");
        out.println("     popq %rbp                    ");

        
        out.println("     ret                         ");
        out.println("");
        out.println("");
        out.println("");
    }

   
    /********************** String Constants *********************/
        
        
    /** A map from string constant to the assembly code label in
     * the data segment where that constant is stored.  See the
     * labelForStringConstant method.
     */
    protected final HashMap<String,String> stringConstantsToLabel = new HashMap<String,String>();

    /**
     * Return a unique label for a string constant.  After
     * translating all code and getting labels for all string
     * constants, the code generator will print out a data segment
     * containing the labels and string constants.  The string may
     * contain only the following escape characters: \n, \r, \t.
     */
    protected String labelForStringConstant(String stringConstant) {
        String label = stringConstantsToLabel.get(stringConstant);
        if (label == null) {
            label = "_str" + stringConstantsToLabel.size();
            stringConstantsToLabel.put(stringConstant, label);
        }
        return label;
    }

        
    /**
     * Iterate over all used string constants and print them into
     * a data segment.  You should not need to change this method.
     */
    protected void generateStringConstants() {
        out.println("# ");                
        out.println("# String Constants");                

        out.println();
        out.println(".data");
        out.println(".align 8");
        out.println();

        for (String s : stringConstantsToLabel.keySet()) {
            String label = stringConstantsToLabel.get(s);
            int len = s.length();
            out.printf(".quad %s\n", len);
            String escapedString = 
                s.replace("\n", "\\n").replace("\t", "\\t").replace("\"", "\\\"").replace("\r", "\\r");
            out.printf("  %sChars:\t.ascii \"%s\"\n", label, escapedString);
            out.printf("%s:\t.quad %sChars\n", label, label);
        }
        out.println("");
        out.println("");
        out.println("");
    }


   @Override
	public void visit(Alloc_Array x) {
		out.println("     # allocate an array");
		out.println("andq $-16, %rsp   # stupid stack align...");

		loadOpIntoReg(x.getLength(), "%rdi");
		out.println("     call __LIB_allocateArray");
		out.println("     movq %rax, %rdx");
		out.println("     movq %rdx, " + x.getDest().getOffset() + "(%rbp)");
		
	}


	@Override
	public void visit(Alloc_Obj x) {
		out.println("     # allocate an object");
		out.println("andq $-16, %rsp   # stupid stack align...");

		out.println("     movq $" + x.getSize() + ", %rdi");
		out.println("     call __LIB_allocateObject");
		out.println("     leaq _" + x.getClassName() + "_DV (%rip), %rdi");
		out.println("     movq %rdi, (%rax)");
		out.println("     movq %rax, " + x.getDest().getOffset() + "(%rbp)"); 
		out.println("");
		out.println("");
	}


	@Override
	public void visit(Array_Load x) {
		out.println("     # load an element of an array");
		Array_Access access = x.getArray();
		String array = loadOpIntoReg(access.getArray(), "%rax");
		String index = loadOpIntoReg(access.getIndex(), "%rdi");
		out.println("     movq 0(" + array + ", " + index + ", 8), %rcx");
		out.println("     movq %rcx, " + x.getDest().getOffset() + "(%rbp)");
	}
	
	@Override
	public void visit(Array_Store x) {
		out.println("     # store into an array");
		Array_Access access = x.getArray();
		String array = loadOpIntoReg(access.getArray(), "%rax");
		String index = loadOpIntoReg(access.getIndex(), "%rdi");
		out.println("     movq " + handleSource(x.getValue()) + ", %rcx");
		out.println("     movq " + "%rcx" + ", 0(" + array + ", " + index + ", 8)");
	}


	private String getStringRepOfConstant(Constant con) {
		String rep; 
		Object val = con.getValue();
		if (val == null) {
			return "$0";
		}
		if(val instanceof Boolean) {
			if((Boolean)val) {
				rep = "$1";
			} else {
				rep = "$0"; 
			}
		} else {
			rep = "$" + val.toString(); //for integers
		} 
		return rep;
	}
	
	
	private String handleSource(Operand variable) {
		String src; 
		if(variable instanceof String_Constant) {
			out.printf ("   movq %s(%%rip), %%r\n", labelForStringConstant(((String_Constant)variable).toString()));
			src = "%r9";
		} else if(variable instanceof Constant) {
			src = getStringRepOfConstant((Constant)variable);
		} else {
			src = "" + variable.getOffset() + "(%rbp)";
		}
		return src;
	}
	
	private String loadOpIntoReg(Operand op, String reg) {
		if(op instanceof String_Constant) {
			out.printf ("     movq %s(%%rip), %%r9\n", labelForStringConstant(((String_Constant)op).toString()));
		} else if (op instanceof Constant) {
			out.println("     movq " + getStringRepOfConstant((Constant)op) + ", " + reg);
		} else {
			out.println("     movq " + op.getOffset() + "(%rbp), " + reg);
		}
		return reg;
	}
	
	@Override
	public void visit(UnExpr x) {
		UnOp op = x.getOp();
		
		if(op == UnOp.NOT) {
			String dest = loadOpIntoReg(x.getExpr(), "%rbx"); 
			out.println("     movq $0, %rcx"); // clear all of rcx
			out.println("     cmpq $0, %rbx");  // steve: was sub 1, should be logical not
			out.println("     sete %cl");  // 
			out.println("     movq " + "%rcx" + ", " + x.getDest().getOffset() + "(%rbp)");
		} else if (op == UnOp.UMINUS) {
			String dest = loadOpIntoReg(x.getExpr(), "%rcx"); 
			out.println("     negq " + dest);
			out.println("     movq " + dest + ", " + x.getDest().getOffset() + "(%rbp)");
		} else if (op == UnOp.LENGTH) {
			out.println("     movq " + x.getExpr().getOffset() + "(%rbp), %rdi");
			out.println("     movq -8(%rdi), %rcx");
			out.println("     movq %rcx, " + x.getDest().getOffset() + "(%rbp)");
		} else {
			throw new CodeError("Trying to generate a nonexistent binary expression.");
		}
	}
	
	@Override
	public void visit(BinExpr x) {
		BinOp op = x.getOp();
		
		if (op == BinOp.PLUS) {
			String src = handleSource(x.getRight());
			String dest = loadOpIntoReg(x.getLeft(), "%rcx"); 
			out.println("     addq " + src + "," + dest);
			out.println("     movq " + dest + ", " + x.getDest().getOffset() + "(%rbp)");
		} else if (op == BinOp.MINUS) {
			String src = handleSource(x.getRight());
			String dest = loadOpIntoReg(x.getLeft(), "%rcx"); 
			out.println("     subq " + src + "," + dest);
			out.println("     movq " + dest + ", " + x.getDest().getOffset() + "(%rbp)");
		} else if (op == BinOp.MULT) {
			loadOpIntoReg(x.getLeft(), "%rax");
			String src = loadOpIntoReg(x.getRight(), "%rbx"); 
			out.println("     mulq " + src);
			out.println("     movq %rax, " + x.getDest().getOffset() + "(%rbp)");
		} else if (op == BinOp.MOD) {
			out.println("     movq $0, %rdx"); // clear all of rdx

			loadOpIntoReg(x.getLeft(), "%rax");
			String src = loadOpIntoReg(x.getRight(), "%rcx"); 
			out.println("     divq " + src);
			out.println("     movq %rdx, " + x.getDest().getOffset() + "(%rbp)");
		} else if (op == BinOp.DIV) {
			loadOpIntoReg(x.getLeft(), "%rax");
			String src = loadOpIntoReg(x.getRight(), "%rcx");
			out.println("     divq " + src);
			out.println("     movq %rax, " + x.getDest().getOffset() + "(%rbp)");
		} else if(op == BinOp.GT) {
			String rax = loadOpIntoReg(x.getLeft(), "%rax");
			String rbx = loadOpIntoReg(x.getRight(), "%rbx");
			out.println("     movq $0, %rcx");  // steve: clear reg
			out.println("     cmpq " + rbx + ", " + rax);
			out.println("     setg %cl");
			out.println("     movq %rcx, " + x.getDest().getOffset() + "(%rbp)");
		} else if (op == BinOp.GE) {
			String rax = loadOpIntoReg(x.getLeft(), "%rax");
			String rbx = loadOpIntoReg(x.getRight(), "%rbx");
			out.println("     movq $0, %rcx");  // steve: clear reg
			out.println("     cmpq " + rbx + ", " + rax);
			out.println("     setge %cl");
			out.println("     movq %rcx, " + x.getDest().getOffset() + "(%rbp)");
		} else if (op == BinOp.LT) {
			String rax = loadOpIntoReg(x.getLeft(), "%rax");
			String rbx = loadOpIntoReg(x.getRight(), "%rbx");
			out.println("     movq $0, %rcx");  // steve: clear reg
			out.println("     cmpq " + rbx + ", " + rax);
			out.println("     setl %cl");
			out.println("     movq %rcx, " + x.getDest().getOffset() + "(%rbp)");
		} else if (op == BinOp.LE) {
			String rax = loadOpIntoReg(x.getLeft(), "%rax");
			String rbx = loadOpIntoReg(x.getRight(), "%rbx");
			out.println("     movq $0, %rcx");  // steve: clear reg
			out.println("     cmpq " + rbx + ", " + rax);
			out.println("     setle %cl");
			out.println("     movq %rcx, " + x.getDest().getOffset() + "(%rbp)");
		} else if (op == BinOp.NE) {
			String rax = loadOpIntoReg(x.getLeft(), "%rax");
			String rbx = loadOpIntoReg(x.getRight(), "%rbx");
			out.println("     movq $0, %rcx");  // steve: clear reg
			out.println("     cmpq " + rax + ", " + rbx);
			out.println("     setne %cl");
			out.println("     movq %rcx, " + x.getDest().getOffset() + "(%rbp)");
		} else if (op == BinOp.EQEQ) {
			String rax = loadOpIntoReg(x.getLeft(), "%rax");
			String rbx = loadOpIntoReg(x.getRight(), "%rbx");
			out.println("     movq $0, %rcx");  // steve: clear reg
			out.println("     cmpq " + rax + ", " + rbx);
			out.println("     setz %cl");
			out.println("     movq %rcx, " + x.getDest().getOffset() + "(%rbp)");
		} else if (op == BinOp.AND){
			String src = loadOpIntoReg(x.getRight(), "%rbx");
			
			String dest = loadOpIntoReg(x.getLeft(), "%rcx"); 
			out.println("     andq " + src + "," + dest);
			out.println("     movq " + dest + ", " + x.getDest().getOffset() + "(%rbp)");
		} else if (op == BinOp.OR) {
			String src = loadOpIntoReg(x.getRight(), "%rbx");
			String dest = loadOpIntoReg(x.getLeft(), "%rcx"); 
			out.println("     orq " + src + "," + dest);
			out.println("     movq " + dest + ", " + x.getDest().getOffset() + "(%rbp)");
		} else if (op == BinOp.CONCAT) { 
			out.println("   # string concat");
			Operand left = x.getLeft();
			if(left instanceof String_Constant) {
				out.printf ("     movq %s(%%rip), %%rdi\n", labelForStringConstant(left.toString()));
			} else {
				out.println("     movq " + left.getOffset() + "(%rbp), %rdi");
			}
			
			Operand right = x.getRight();
			if(right instanceof String_Constant) {
				out.printf ("     movq %s(%%rip), %%rsi\n", labelForStringConstant(right.toString()));
			} else {
				out.println("     movq " + right.getOffset() + "(%rbp), %rsi");
			}
			out.println("andq $-16, %rsp   # stupid stack align...");

			out.println("     call __LIB_stringCat");
			out.println("     movq %rax, " + x.getDest().getOffset() + "(%rbp)");
		} else {
			throw new CodeError("Trying to generate a nonexistent binary expression.");
		}
	}

	
	@Override
	public void visit(Check_Positive x) {
		if(x.isNegative()) {
			out.println("     jmp labelArraySizeError");
		} else {
			String rax = loadOpIntoReg(x.getLength(), "%rax");
			out.println("     movq $0, %rcx");
			out.println("	  cmpq %rcx, " + rax);
			out.println("     jl labelArraySizeError");
		}
	}

	@Override
	public void visit(Check_Bounds x) {
		
		out.println("     movq " + x.getArray().getOffset() + "(%rbp), %rdi");
		
		out.println("     movq -8(%rdi), %rax"); 
		
		String rbx = loadOpIntoReg(x.getIndex(), "%rbx");
		
		out.println("     movq $0, %rcx");
		out.println("	  cmpq %rcx, %rbx");
		out.println("     jl labelArrayBoundsError");
		
		out.println("	  cmpq " + rbx + ", %rax");
		
		out.println("     jle labelArrayBoundsError");
	}


	@Override
	public void visit(Check_Null x) {
		if(x.getToBeChecked() instanceof Constant) {
			out.println("     jmp labelNullPtrError");
		} else {
			out.println("     movq " + x.getToBeChecked().getOffset() + "(%rbp), %rbx");
			out.println("     movq $0, %r8");
			out.println("     cmpq " + "%rbx" + ", %r8");
			out.println("     je labelNullPtrError");
		}
	}



	@Override
	public void visit(Field_Load x) {
		out.println("     # load a field from an object");
		Field_Access field = x.getField(); 
		String receiver = loadOpIntoReg(field.getReceiver(), "%r8");
		out.println("     movq " + receiver + ", %rax");
		out.println("     movq $" + field.getFieldOffset() + ", %rbx");
		out.println("     movq 0(%rax, %rbx, 1), %rcx");
		out.println("     movq %rcx, " + x.getDest().getOffset() + "(%rbp)");
	}


	@Override
	public void visit(Field_Store x) {
		out.println("     # store a new value into a field");
		Field_Access field = x.getField(); 
		String newVal = handleSource(x.getValue());
		String receiver = loadOpIntoReg(field.getReceiver(), "%r8");
		out.println("     movq " + receiver + ", %rax");
		out.println("     movq $" + field.getFieldOffset() + ", %rbx");
		out.println("     movq " + newVal + ", %rcx");
		out.println("     movq %rcx, 0(%rax, %rbx, 1)");
	}


	private void pushParameters(Vector<Operand> vector) {
		for(int p = vector.size() - 1; p >= 0; --p) {
			String parameter = handleSource((Operand)vector.get(p));
			out.println("     push " + parameter); 
		}
	}
		
	@Override
	public void visit(Fun_Call x) {
		out.println(""); 
		out.println("     # calling function " + x.getMethodName());
		pushParameters(x.getParams());
		loadOpIntoReg(x.getReceiver(), "%rcx");
		out.println("     push %rcx");
		
		if(cacheLookup) {
		
			// dereference %rcx to get the receiver class vpointer
			// storing the vpointer in rdi to be used as a parameter
			// to the library call			
			out.println("     movq (%rcx), %rdi");
			out.println("     movq $" + x.getFunCallID() + ", %rsi");
			out.println("     movq $" + x.getMethodNum() + ", %rdx");
			out.println("     call __LIB_lookInCache");

//			out.println("     movq $0, %r8");
//			out.println("     cmpq " + "%rax" + ", %r8");
//			out.println("     jne cacheHit" + x.getFunCallID());
//			
//			out.println("     movq $1, %rdi");
//			out.println("     call __LIB_printi");
//			
//			//If we don't find the method in the cache, we do a linear search.
//			out.println("     movq (%rcx), %rdi");
//			// pass the num representing the method as the second param.
//			out.println("     movq $" + x.getMethodNum() + ", %rsi");
//			out.println("     call __LIB_findMethod");
//			
//			//Store the method pointer into r8
//			out.println("     movq %rax, %r8");
//			
//			out.println("     movq $2, %rdi");
//			out.println("     call __LIB_printi");
//			
//			// We put the vtable pointer and method pointer into the param
//			// regs for update cache.
//			out.println("     movq (%rcx), %rdi");
//			out.println("     movq %rax, %rsi");
//			out.println("     movq $" + x.getFunCallID() + ", %rdx");
//			out.println("     call __LIB_updateCache");
//			
//			//Move the method pointer back into rax
//			out.println("     movq %r8, %rax");
//			
//			out.println("     cacheHit" + x.getFunCallID() + ":");
//			// call the method we have found and returned
//			out.println("     movq $3, %rdi");
//			out.println("     call __LIB_printi");
			out.println("     call *%rax");
			out.println("     add $" + (1 + x.getParams().size()) * 8 + ", %rsp");
		} else if(linearSearch) {
			// dereference %rcx to get the receiver class vpointer
			// storing the vpointer in rdi to be used as a parameter
			// to the library call			
			out.println("     movq (%rcx), %rdi");
			// pass the num representing the method as the second param.
			out.println("     movq $" + x.getMethodNum() + ", %rsi");
			out.println("     call __LIB_findMethod");
			
			
			// call the method we have found and returned
			out.println("     call *%rax");
			
			// move stackpointer
			out.println("     add $" + (1 + x.getParams().size()) * 8 + ", %rsp");
		} else {
			// dereference %rcx to get the receiver class vpointer
			// storing the vpointer in rbx
			out.println("     movq (%rcx), %rbx");

			// call the method at the specified offset from the vpointer
			out.println("     call *" + x.getMethodOffset() + "(%rbx)");

			out.println("     add $" + (1 + x.getParams().size()) * 8 + ", %rsp");
		}
		
		// store value returned by function into the dest reg
		if(x.getDest().getOffset() != 0) {
			out.println("     movq %rax, " + x.getDest().getOffset() + "(%rbp)");
		}
		out.println("");
		out.println("");
		
	}
	
	@Override
	public void visit(Return x) {
		if(x.getExpr() != null) {
			loadOpIntoReg(x.getExpr(), "%rax");
		}
		out.println(""); 
		out.println("");
		generateEpilogue();
	}

	@Override
	public void visit(Cond_Jump x) {
		out.println("     # conditional jump to label " + x.getLabel());
		String rax = loadOpIntoReg(x.getCond(), "%rax");
		out.println("     movq $0, %rcx");
		out.println("     cmpq " + rax + ", %rcx");
		out.println("     jne " + x.getLabel());
	}

	@Override
	public void visit(Jump x) {
		out.println("     # jump to label " + x.getLabel());
		out.println("     jmp " + x.getLabel());
	}


	@Override
	public void visit(Label x) {
		out.println("     " + x.getLabelName() + ": ");
	}


	@Override
	public void visit(Lib_Call x) {
		Vector<Operand> args = x.getArgs();
		String methodName = x.getMethodName(); 
		TACVar dest = x.getDest(); 
		
		out.println("movq %rsp, %r15");
		out.println("andq $-16, %rsp   # stupid stack align...");
		
		if(methodName.equals("printi")) {
			out.println("     # print an integer");
			out.println("     movq " + args.get(0).getOffset() + "(%rbp), %rdi");
			out.println("     call __LIB_printi");
		} else if (methodName.equals("println")) {
			out.println("     # print " + args.get(0).toString() + " and a new line");
			out.println("     movq " + args.get(0).getOffset() + "(%rbp), %rdi");
			out.println("     call __LIB_println");
		} else if (methodName.equals("print")) {
			out.println("     # print");
			out.println("     movq " + args.get(0).getOffset() + "(%rbp), %rdi");
			out.println("     call __LIB_print");
		} else if (methodName.equals("printb")) {
			out.println("     # print a boolean " + args.get(0).toString());
			out.println("     movq " + args.get(0).getOffset() + "(%rbp), %rdi");
			out.println("     call __LIB_printb");
		} else if (methodName.equals("readi")) {
			out.println("     # read in a character from input");
			out.println("     call __LIB_readi");
			out.println("     movq %rax, " + dest.getOffset() + "(%rbp)");
			// This translates the input from an ascii to an integer
			out.println("     subq $48, " + dest.getOffset() + "(%rbp)" );
		} else if (methodName.equals("readln")) {
			out.println("     # read in one line input");
			out.println("     call __LIB_readln");
			out.println("     movq %rax, " + dest.getOffset() + "(%rbp)");
		} else if (methodName.equals("eof")) {
			out.println("     # test whether its the end of file");
			out.println("     call __LIB_eof");
			out.println("     movq %rax, " + dest.getOffset() + "(%rbp)");
		} else if (methodName.equals("stoi")) {
			out.println("     # returns an integer representation of the string"); 
			out.println("     movq " + args.get(0).getOffset() + "(%rbp), %rdi");
			out.println("     movq " + args.get(1).getOffset() + "(%rbp), %rsi");
			out.println("     call __LIB_stoi");
			out.println("     movq %rax, " + dest.getOffset() + "(%rbp)");
		} else if (methodName.equals("itos")) {
			out.println("     # return a string representation of the integer array");
			out.println("     movq " + args.get(0).getOffset() + "(%rbp), %rdi");
			out.println("     call __LIB_itos");
			out.println("     movq %rax, " + dest.getOffset() + "(%rbp)");
		} else if (methodName.equals("stoa")) {
			out.println("     movq " + args.get(0).getOffset() + "(%rbp), %rdi");
			out.println("     call __LIB_stoa");
			out.println("     movq %rax, " + dest.getOffset() + "(%rbp)" );
		} else if (methodName.equals("atos")) {
			out.println("     movq " + args.get(0).getOffset() + "(%rbp), %rdi");
			out.println("     call __LIB_atos");
			out.println("     movq %rax, " + dest.getOffset() + "(%rbp)");
		} else if (methodName.equals("random")) {
			out.println("     # generate a random variable");
			out.println("     movq " + args.get(0).getOffset() + "(%rbp), %rdi");
			out.println("     call __LIB_random");
			out.println("     movq %rax, " + dest.getOffset() + "(%rbp)");
		} else if (methodName.equals("exit")) {
			out.println("     # exit with exit code");
			out.println("     movq " + args.get(0).getOffset() + "(%rbp), %rdi");
			out.println("     call __LIB_exit");
		} else if (methodName.equals("time")) {
			out.println("     # get number of milliseconds since program start");
			out.println("     call __LIB_time");
			out.println("     movq %rax, " + dest.getOffset() + "(%rbp)");
		} else {
			throw new CodeError("Library Error: Method name does not exist.");
		}
		out.println("");
		out.println("movq %r15, %rsp   # Stupid stack align");
		out.println("");
	}

	@Override
	public void visit(Load x) {
		Operand val = x.getValue();
		//out.println("    # load " + val + val.getOffset() + " into " + x.getDest() + x.getDest().getOffset());
		 if(x.getValue() instanceof String_Constant) {
		     out.printf ("     movq %s(%%rip), %%rdi\n", labelForStringConstant(val.toString()));
		     out.println("     movq %rdi, " + x.getDest().getOffset() + "(%rbp)");
		 } else if (val instanceof Constant) {
		     out.println("     movq " + getStringRepOfConstant((Constant)val) + ", " + x.getDest().getOffset() + "(%rbp)");
		 } else {
		     out.println("     movq " + val.getOffset() + "(%rbp), %rcx");
		     out.println("     movq %rcx, " + x.getDest().getOffset() + "(%rbp)");
		 }
	}


	@Override
	public void visit(Store x) {
		Operand val = x.getValue();
//		System.out.println(((Constant)val).getValue());
		out.println("     # store " +  " into " + x.getDest());
		if(x.getValue() instanceof String_Constant) {
			out.println("     # store " + x.getValue() + " into " + x.getDest());
			out.printf ("     movq %s(%%rip), %%rdi\n", labelForStringConstant(val.toString()));
			out.println("     movq %rdi, " + x.getDest().getOffset() + "(%rbp)");
		} else if(x.getValue() instanceof Constant) {
			out.println("     movq " + getStringRepOfConstant((Constant)val) + ", " + x.getDest().getOffset() + "(%rbp)");
		} else {
			out.println("     movq " + val.getOffset() + "(%rbp), %rcx");
			out.println("     movq %rcx, " + x.getDest().getOffset() + "(%rbp)");
		}
	}


	@Override
	public void visit(TAC_Comment x) {
		out.println("     # " + x.getComment());
	}

	
	@Override
	public void visit(Field_Access x) {
		throw new CodeError("Trying to write code for an operand.");	
	}
	
	@Override
	public void visit(Array_Access x) {
		throw new CodeError("Trying to write code for an operand.");	
	}
	
	@Override
	public void visit(TempVariable x) {
		throw new CodeError("Trying to write code for an operand.");	
	}
	
	@Override
	public void visit(String_Constant x) {
		throw new CodeError("Trying to write code for an operand.");	
	}
	
	@Override
	public void visit(ProgramVariable x) {
		throw new CodeError("Trying to write code for an operand.");	
	}
	
	@Override
	public void visit(Operand x) {
		throw new CodeError("Trying to write code for an abstract operand.");
	}
	
	@Override
	public void visit(Call x) {
		throw new CodeError("Trying to write code for an abstract call.");	
		
	}
	
	@Override
	public void visit(Constant x) {
		throw new CodeError("Trying to write code for an operand.");		
	}
	
	@Override
	public void visit(TACInstr x) {
		throw new CodeError("Trying to generate code for an abstract TACInstr."); 
	}


	@Override
	public void visit(NoOp x) {
	}
	
}

