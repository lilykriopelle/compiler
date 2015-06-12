# File test/DataFlowTests/optimizationTest-2.s


# 
# VTables

.data
.align 8

_A_DV:
      .quad _A_me


_Checks_DV:
      .quad _Checks_main


# 
# Code.
# A
.text
.align 8
_A_me:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $64, %rsp                

     # store a new value into a field
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $8, %rbx
     movq $5, %rcx
     movq %rcx, 0(%rax, %rbx, 1)
     movq $7, -40(%rbp)
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # store a new value into a field
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $16, %rbx
     movq -40(%rbp), %rcx
     movq %rcx, 0(%rax, %rbx, 1)
     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



# Checks
.text
.align 8
_Checks_main:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $48, %rsp                

     # allocate an object
andq $-16, %rsp   # stupid stack align...
     movq $24, %rdi
     call __LIB_allocateObject
     leaq _A_DV (%rip), %rdi
     movq %rdi, (%rax)
     movq %rax, -24(%rbp)


     # Method call me
     # calling function A_me
     movq -24(%rbp), %rcx
     push %rcx
     movq (%rcx), %rbx
     call *0(%rbx)
     add $8, %rsp


     # store  into a
     movq $0, -24(%rbp)
     # Method call me
     jmp labelNullPtrError
     # calling function A_me
     movq -24(%rbp), %rcx
     push %rcx
     movq (%rcx), %rbx
     call *0(%rbx)
     add $8, %rsp


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

       movq $   8, %rdi                 # o = new Checks
     call __LIB_allocateObject   
     leaq _Checks_DV(%rip), %rdi       
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




