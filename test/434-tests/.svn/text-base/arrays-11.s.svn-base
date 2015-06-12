# File test/434-tests/arrays-11.s


# 
# VTables

.data
.align 8

_A_DV:
      .quad _A_f
      .quad _A_main


# 
# Code.
# A
.text
.align 8
_A_f:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $48, %rsp                

     # Array Access
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # load a field from an object
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $8, %rbx
     movq 0(%rax, %rbx, 1), %rcx
     movq %rcx, -40(%rbp)
     movq -40(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -40(%rbp), %rdi
     movq -8(%rdi), %rax
     movq $1, %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # store into an array
     movq -40(%rbp), %rax
     movq $1, %rdi
     movq $2, %rcx
     movq %rcx, 0(%rax, %rdi, 8)
     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



.text
.align 8
_A_main:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $80, %rsp                

     movq $10, %rax
     movq $0, %rcx
	  cmpq %rcx, %rax
     jl labelArraySizeError
     # allocate an array
andq $-16, %rsp   # stupid stack align...
     movq $10, %rdi
     call __LIB_allocateArray
     movq %rax, %rdx
     movq %rdx, -24(%rbp)
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # store a new value into a field
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $8, %rbx
     movq -24(%rbp), %rcx
     movq %rcx, 0(%rax, %rbx, 1)
     # Method call f
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # calling function A_f
     movq 16(%rbp), %rcx
     push %rcx
     movq (%rcx), %rbx
     call *0(%rbx)
     add $8, %rsp


     # Library call printi
     # Array Access
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # load a field from an object
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $8, %rbx
     movq 0(%rax, %rbx, 1), %rcx
     movq %rcx, -72(%rbp)
     movq -72(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -72(%rbp), %rdi
     movq -8(%rdi), %rax
     movq $1, %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq -72(%rbp), %rax
     movq $1, %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -56(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print an integer
     movq -56(%rbp), %rdi
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

       movq $  16, %rdi                 # o = new A
     call __LIB_allocateObject   
     leaq _A_DV(%rip), %rdi       
     movq %rdi, (%rax)
     pushq %rax                        # o.main(args) > push o
     movq (%rax), %rax            
     call *8(%rax)                   # main is at offset 8 in vtable
     addq $8, %rsp                
     movq $0, %rax                     # __ic_main always returns 0

     movq %rbp,%rsp                    # epilogue
     popq %rbp                    
     ret                         



# 
# String Constants

.data
.align 8




