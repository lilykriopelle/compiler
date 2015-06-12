# File test/434-tests/objects-6.s


# 
# VTables

.data
.align 8

_Super_DV:
      .quad -1
      .quad 2
      .quad 1
      .quad _Super_g
      .quad 0
      .quad _Super_f


_Test_DV:
      .quad _Super_DV
      .quad 2
      .quad 3
      .quad _Test_main
      .quad 2
      .quad _Test_g


# 
# Code.
# Super
.text
.align 8
_Super_f:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $24, %rsp                

     movq $1, %rax


     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



.text
.align 8
_Super_g:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $32, %rsp                

     # Method call f
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError

     # calling function Super_f
     movq 16(%rbp), %rcx
     push %rcx
     movq (%rcx), %rdi
     movq $0, %rsi
     movq $0, %rdx
     call __LIB_lookInCache
     call *%rax
     add $8, %rsp
     movq %rax, -24(%rbp)


     movq -24(%rbp), %rax


     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



# Test
.text
.align 8
_Test_g:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $24, %rsp                

     movq $6, %rax


     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



.text
.align 8
_Test_main:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $112, %rsp                

     # allocate an object
andq $-16, %rsp   # stupid stack align...
     movq $8, %rdi
     call __LIB_allocateObject
     leaq _Test_DV (%rip), %rdi
     movq %rdi, (%rax)
     movq %rax, -24(%rbp)


     # allocate an object
andq $-16, %rsp   # stupid stack align...
     movq $8, %rdi
     call __LIB_allocateObject
     leaq _Test_DV (%rip), %rdi
     movq %rdi, (%rax)
     movq %rax, -32(%rbp)


     # store  into i
     movq $15, -40(%rbp)
     whileBegin0: 
     movq -40(%rbp), %rax
     movq $20, %rbx
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
     # Method call g
     movq -24(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -40(%rbp), %rcx
     movq %rcx, -88(%rbp)

     # calling function Test_g
     push -88(%rbp)
     movq -24(%rbp), %rcx
     push %rcx
     movq (%rcx), %rdi
     movq $1, %rsi
     movq $2, %rdx
     call __LIB_lookInCache
     call *%rax
     add $16, %rsp


     movq -40(%rbp), %rcx
     addq $1,%rcx
     movq %rcx, -96(%rbp)
     # store  into i
     movq -96(%rbp), %rcx
     movq %rcx, -40(%rbp)
     # jump to label whileBegin0
     jmp whileBegin0
     whileEnd1: 
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

       movq $   8, %rdi                 # o = new Test
     call __LIB_allocateObject   
     leaq _Test_DV(%rip), %rdi       
     movq %rdi, (%rax)
     pushq %rax                        # o.main(args) > push o
     movq (%rax), %rdi
     movq $3, %rsi
     call __LIB_findMethod
     call *%rax
     addq $8, %rsp                
     movq $0, %rax                     # __ic_main always returns 0

     movq %rbp,%rsp                    # epilogue
     popq %rbp                    
     ret                         



# 
# String Constants

.data
.align 8




