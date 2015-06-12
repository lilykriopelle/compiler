# File test/DataFlowTests/arrayLoop.s


# 
# VTables

.data
.align 8

_ArrayLoop_DV:
      .quad _ArrayLoop_main


# 
# Code.
# ArrayLoop
.text
.align 8
_ArrayLoop_main:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $232, %rsp                

     movq $100, %rax
     movq $0, %rcx
	  cmpq %rcx, %rax
     jl labelArraySizeError
     # allocate an array
andq $-16, %rsp   # stupid stack align...
     movq $100, %rdi
     call __LIB_allocateArray
     movq %rax, %rdx
     movq %rdx, -24(%rbp)
     # store  into j
     movq $0, -40(%rbp)
     # store  into i
     movq $0, -48(%rbp)
     whileBegin0: 
     movq -24(%rbp), %rdi
     movq -8(%rdi), %rcx
     movq %rcx, -240(%rbp)
     movq -240(%rbp), %rcx
     movq %rcx, -72(%rbp)
     movq -48(%rbp), %rax
     movq -72(%rbp), %rbx
     movq $0, %rcx
     cmpq %rbx, %rax
     setl %cl
     movq %rcx, -56(%rbp)
     movq -56(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -88(%rbp)
     # conditional jump to label whileEnd1
     movq -88(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne whileEnd1
     # Array Access
     movq -24(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -48(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # store into an array
     movq -24(%rbp), %rax
     movq -48(%rbp), %rdi
     movq -48(%rbp), %rcx
     movq %rcx, 0(%rax, %rdi, 8)
     movq -48(%rbp), %rcx
     addq $1,%rcx
     movq %rcx, -120(%rbp)
     # store  into i
     movq -120(%rbp), %rcx
     movq %rcx, -48(%rbp)
     # jump to label whileBegin0
     jmp whileBegin0
     whileEnd1: 
     whileBegin2: 
     movq -240(%rbp), %rcx
     movq %rcx, -160(%rbp)
     movq -40(%rbp), %rax
     movq -160(%rbp), %rbx
     movq $0, %rcx
     cmpq %rbx, %rax
     setl %cl
     movq %rcx, -144(%rbp)
     movq -144(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -176(%rbp)
     # conditional jump to label whileEnd3
     movq -176(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne whileEnd3
     # Library call printi
     # Array Access
     movq -24(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -24(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -40(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq -24(%rbp), %rax
     movq -40(%rbp), %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -184(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print an integer
     movq -184(%rbp), %rdi
     call __LIB_printi

movq %r15, %rsp   # Stupid stack align

     # Library call println
     movq _str0(%rip), %rdi
     movq %rdi, -208(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print t25 and a new line
     movq -208(%rbp), %rdi
     call __LIB_println

movq %r15, %rsp   # Stupid stack align

     movq -40(%rbp), %rcx
     addq $1,%rcx
     movq %rcx, -216(%rbp)
     # store  into j
     movq -216(%rbp), %rcx
     movq %rcx, -40(%rbp)
     # jump to label whileBegin2
     jmp whileBegin2
     whileEnd3: 
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

       movq $   8, %rdi                 # o = new ArrayLoop
     call __LIB_allocateObject   
     leaq _ArrayLoop_DV(%rip), %rdi       
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

.quad 0
  _str0Chars:	.ascii ""
_str0:	.quad _str0Chars



