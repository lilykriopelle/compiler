# File test/TACtests/offsetCalculatorTest.s


# ----------------------------
# VTables

.data
.align 8

_C_DV:
      .quad _C_g
      .quad _C_f


_B_DV:
      .quad _B_g
      .quad _B_f
      .quad _B_println


_A_DV:
      .quad _A_g
      .quad _A_f
      .quad _A_println
      .quad _A_bAndC
      .quad _A_main


_D_DV:


# ----------------------------
# Code.  Just the Simple main method for now...

.text
.align 8
_Simple_main:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $8, %rsp                

     # store 1234 into a local variable.
     movq $1234, %rcx                     
     movq %rcx, -8(%rbp)

     # print that local var...
     movq -8(%rbp), %rdi
     call __LIB_printi

     # ... and a new line
     movq _str0(%rip), %rdi
     call __LIB_println
        
     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



# ----------------------------
# Error handling.  Jump to these procedures when a run-time check fails.

.data
.align 8

.long 23
  strNullPtrErrorChars:     .ascii "Null pointer violation."
strNullPtrError: .quad strNullPtrErrorChars

.long 23
  strArrayBoundsErrorChars: .ascii "Array bounds violation."
strArrayBoundsError: .quad strArrayBoundsErrorChars

.long 21
  strArraySizeErrorChars:   .ascii "Array size violation."
strArraySizeError: .quad strArraySizeErrorChars

.text
.align 8
labelNullPtrError:
    movq strNullPtrError(%rip), %rdi
    call __LIB_println
    movq $1, %rdi
    call __LIB_exit

.align 8
labelArrayBoundsError:
    movq strArrayBoundsError(%rip), %rdi
    call __LIB_println
    movq $1, %rdi
    call __LIB_exit

.align 8
labelArraySizeError:
    movq strArraySizeError(%rip), %rdi
    call __LIB_println
    movq $1, %rdi
    call __LIB_exit



# The main entry point.  Allocate object and invoke main on it.

.text
.align 8
.globl __ic_main
__ic_main:
       pushq %rbp                        # prologue
       movq %rsp,%rbp                
       pushq %rdi                        # o.main(args) -> push args

       movq $8   , %rdi                 # o = new A
       call __LIB_allocateObject   
       leaq _A_DV(%rip), %rdi       
       movq %rdi, (%rax)
       pushq %rax                        # o.main(args) -> push o
       movq (%rax), %rax            
       call *256(%rax)                   # main is at offset 32 in vtable
       addq $16, %rsp                
       movq $0, %rax                     # __ic_main always returns 0

       movq %rbp,%rsp                    # epilogue
       popq %rbp                    
       ret                         



# ----------------------------
# String Constants

.data
.align 8

.quad 62
  _str0Chars:	.ascii "\na string \n containing \t\t quotes and escape characters: \"moo\"\n"
_str0:	.quad _str0Chars



