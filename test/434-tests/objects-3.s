# File test/434-tests/objects-3.s


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
     call __LIB_lookInCache
     movq $0, %r8
     cmpq %rax, %r8
     jne cacheHit0
     movq (%rcx), %rdi
     movq $0, %rsi
     call __LIB_findMethod
     movq %rax, %r8
     movq (%rcx), %rdi
     movq %rax, %rsi
     movq $0, %rdx
     call __LIB_updateCache
     movq %r8, %rax
     cacheHit0:
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
     subq $72, %rsp                

     # allocate an object
andq $-16, %rsp   # stupid stack align...
     movq $8, %rdi
     call __LIB_allocateObject
     leaq _Super_DV (%rip), %rdi
     movq %rdi, (%rax)
     movq %rax, -24(%rbp)


     # Library call printi
     # Method call f
     movq -24(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError

     # calling function Super_f
     movq -24(%rbp), %rcx
     push %rcx
     movq (%rcx), %rdi
     movq $1, %rsi
     call __LIB_lookInCache
     movq $0, %r8
     cmpq %rax, %r8
     jne cacheHit1
     movq (%rcx), %rdi
     movq $0, %rsi
     call __LIB_findMethod
     movq %rax, %r8
     movq (%rcx), %rdi
     movq %rax, %rsi
     movq $1, %rdx
     call __LIB_updateCache
     movq %r8, %rax
     cacheHit1:
     call *%rax
     add $8, %rsp
     movq %rax, -40(%rbp)


movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print an integer
     movq -40(%rbp), %rdi
     call __LIB_printi

movq %r15, %rsp   # Stupid stack align

     # allocate an object
andq $-16, %rsp   # stupid stack align...
     movq $8, %rdi
     call __LIB_allocateObject
     leaq _Test_DV (%rip), %rdi
     movq %rdi, (%rax)
     movq %rax, -56(%rbp)


     # store  into y
     movq -56(%rbp), %rcx
     movq %rcx, -32(%rbp)
     # Library call printi
     # Method call f
     movq -32(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError

     # calling function Super_f
     movq -32(%rbp), %rcx
     push %rcx
     movq (%rcx), %rdi
     movq $2, %rsi
     call __LIB_lookInCache
     movq $0, %r8
     cmpq %rax, %r8
     jne cacheHit2
     movq (%rcx), %rdi
     movq $0, %rsi
     call __LIB_findMethod
     movq %rax, %r8
     movq (%rcx), %rdi
     movq %rax, %rsi
     movq $2, %rdx
     call __LIB_updateCache
     movq %r8, %rax
     cacheHit2:
     call *%rax
     add $8, %rsp
     movq %rax, -64(%rbp)


movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print an integer
     movq -64(%rbp), %rdi
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




