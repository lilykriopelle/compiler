# File test/DataFlowTests/optimizationTest-3.s


# 
# VTables

.data
.align 8

_Loop_DV:
      .quad _Loop_main


# 
# Code.
# Loop
.text
.align 8
_Loop_main:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $144, %rsp                

     # store  into x
     movq $1, -40(%rbp)
     whileBegin0: 
     movq -40(%rbp), %rax
     movq $100, %rbx
     movq $0, %rcx
     cmpq %rbx, %rax
     setl %cl
     movq %rcx, -48(%rbp)
     movq -48(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -72(%rbp)
     # conditional jump to label whileEnd1
     movq -72(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne whileEnd1
     # store  into y
     movq $6, -32(%rbp)
     movq -40(%rbp), %rcx
     addq $1,%rcx
     movq %rcx, -88(%rbp)
     # store  into x
     movq -88(%rbp), %rcx
     movq %rcx, -40(%rbp)
     # Library call printi
     movq -40(%rbp), %rcx
     movq %rcx, -136(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print an integer
     movq -136(%rbp), %rdi
     call __LIB_printi

movq %r15, %rsp   # Stupid stack align

     # jump to label whileBegin0
     jmp whileBegin0
     whileEnd1: 
     # Library call printi
     movq -32(%rbp), %rcx
     movq %rcx, -144(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print an integer
     movq -144(%rbp), %rdi
     call __LIB_printi

movq %r15, %rsp   # Stupid stack align

     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



# 
# Error handling.  Jump to these procedures when a runtime check fails.

.data
.align 8

.quad 23
  strNullPtrErrorChars:     .ascii "Null pointer violation."
strNullPtrError: .quad strNullPtrErrorChars

.quad 23
  strArrayBoundsErrorChars: .ascii "Array bounds violation."
strArrayBoundsError: .quad strArrayBoundsErrorChars

.quad 21
  strArraySizeErrorChars:   .ascii "Array size violation."
strArraySizeError: .quad strArraySizeErrorChars

.text
.align 8
labelNullPtrError:
    movq strNullPtrError(%rip), %rdi
andq $-16, %rsp   # stupid stack align...
    call __LIB_println
    movq $1, %rdi
    call __LIB_exit

.align 8
labelArrayBoundsError:
    movq strArrayBoundsError(%rip), %rdi
andq $-16, %rsp   # stupid stack align...
    call __LIB_println
    movq $1, %rdi
    call __LIB_exit

.align 8
labelArraySizeError:
    movq strArraySizeError(%rip), %rdi
andq $-16, %rsp   # stupid stack align...
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
     pushq %rdi                        # o.main(args) > push args

       movq $   8, %rdi                 # o = new Loop
     call __LIB_allocateObject   
     leaq _Loop_DV(%rip), %rdi       
     movq %rdi, (%rax)
     pushq %rax                        # o.main(args) > push o
     movq (%rax), %rax            
     call *0(%rax)                   # main is at offset 0 in vtable
     addq $8, %rsp                
     movq $0, %rax                     # __ic_main always returns 0

     movq %rbp,%rsp                    # epilogue
     popq %rbp                    
     ret                         



# 
# String Constants

.data
.align 8




