# File test/DataFlowTests/binOp.s


# 
# VTables

.data
.align 8

_BinOP_DV:
      .quad _BinOP_main


# 
# Code.
# BinOP
.text
.align 8
_BinOP_main:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $112, %rsp                

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
     # Array Access
     # store into an array
     movq -24(%rbp), %rax
     movq $5, %rdi
     movq $10, %rcx
     movq %rcx, 0(%rax, %rdi, 8)
     # Array Access
     movq -24(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -24(%rbp), %rdi
     movq -8(%rdi), %rax
     movq $5, %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq -24(%rbp), %rax
     movq $5, %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -72(%rbp)
     movq -72(%rbp), %rax
     movq $10, %rbx
     movq $0, %rcx
     cmpq %rax, %rbx
     setz %cl
     movq %rcx, -64(%rbp)
     movq -64(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -104(%rbp)
     # conditional jump to label endIf0
     movq -104(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne endIf0
     # Library call println
     movq _str0(%rip), %rdi
     movq %rdi, -112(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print t13 and a new line
     movq -112(%rbp), %rdi
     call __LIB_println

movq %r15, %rsp   # Stupid stack align

     endIf0: 
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

       movq $   8, %rdi                 # o = new BinOP
     call __LIB_allocateObject   
     leaq _BinOP_DV(%rip), %rdi       
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

.quad 3
  _str0Chars:	.ascii "YAY"
_str0:	.quad _str0Chars



