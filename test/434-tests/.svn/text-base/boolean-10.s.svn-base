# File test/434-tests/boolean-10.s


# 
# VTables

.data
.align 8

_Main_DV:
      .quad _Main_main


# 
# Code.
# Main
.text
.align 8
_Main_main:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $48, %rsp                

     # Library call printb
     # Short-circuit AND.
     movq $1, %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -48(%rbp)
     # conditional jump to label setToFalse0
     movq -48(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne setToFalse0
     # store  into t2
     movq $0, -24(%rbp)
     # jump to label endAnd1
     jmp endAnd1
     setToFalse0: 
     # store  into t2
     movq $0, -24(%rbp)
     endAnd1: 
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print a boolean t2
     movq -24(%rbp), %rdi
     call __LIB_printb

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

       movq $   8, %rdi                 # o = new Main
     call __LIB_allocateObject   
     leaq _Main_DV(%rip), %rdi       
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




