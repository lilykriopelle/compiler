# File test/DataFlowTests/arrayTempIntro.s


# 
# VTables

.data
.align 8

_Array_DV:
      .quad _Array_main


# 
# Code.
# Array
.text
.align 8
_Array_main:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $192, %rsp                

     # store  into i
     movq $0, -24(%rbp)
     movq $10, %rax
     movq $0, %rcx
	  cmpq %rcx, %rax
     jl labelArraySizeError
     # allocate an array
andq $-16, %rsp   # stupid stack align...
     movq $10, %rdi
     call __LIB_allocateArray
     movq %rax, %rdx
     movq %rdx, -32(%rbp)
     # store a new value into a field
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $16, %rbx
     movq -208(%rbp), %rcx
     movq %rcx, 0(%rax, %rbx, 1)
     movq -208(%rbp), %rcx
     movq %rcx, -200(%rbp)
     movq -200(%rbp), %rcx
     movq %rcx, -32(%rbp)
     whileBegin0: 
     movq -24(%rbp), %rax
     movq $10, %rbx
     movq $0, %rcx
     cmpq %rbx, %rax
     setl %cl
     movq %rcx, -56(%rbp)
     movq -56(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -80(%rbp)
     # conditional jump to label whileEnd1
     movq -80(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne whileEnd1
     # Array Access
     movq -200(%rbp), %rcx
     movq %rcx, -104(%rbp)
     movq -104(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -104(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -24(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # store into an array
     movq -104(%rbp), %rax
     movq -24(%rbp), %rdi
     movq -24(%rbp), %rcx
     movq %rcx, 0(%rax, %rdi, 8)
     movq -24(%rbp), %rcx
     addq $1,%rcx
     movq %rcx, -120(%rbp)
     # store  into i
     movq -120(%rbp), %rcx
     movq %rcx, -24(%rbp)
     # jump to label whileBegin0
     jmp whileBegin0
     whileEnd1: 
     # store a new value into a field
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $8, %rbx
     movq $10, %rcx
     movq %rcx, 0(%rax, %rbx, 1)
     # Library call printi
     # Array Access
     movq -208(%rbp), %rcx
     movq %rcx, -176(%rbp)
     movq -176(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # load a field from an object
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $8, %rbx
     movq 0(%rax, %rbx, 1), %rcx
     movq %rcx, -168(%rbp)
     movq -176(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -168(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq -176(%rbp), %rax
     movq -168(%rbp), %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -160(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print an integer
     movq -160(%rbp), %rdi
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

       movq $  24, %rdi                 # o = new Array
     call __LIB_allocateObject   
     leaq _Array_DV(%rip), %rdi       
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




