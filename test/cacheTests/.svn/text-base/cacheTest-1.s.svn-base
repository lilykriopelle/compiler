# File test/cacheTests/cacheTest-1.s


# 
# VTables

.data
.align 8

_SuperCacheTest_DV:
      .quad -1
      .quad 11
      .quad 10
      .quad _SuperCacheTest_k
      .quad 9
      .quad _SuperCacheTest_j
      .quad 8
      .quad _SuperCacheTest_i
      .quad 7
      .quad _SuperCacheTest_h
      .quad 6
      .quad _SuperCacheTest_g
      .quad 5
      .quad _SuperCacheTest_f
      .quad 4
      .quad _SuperCacheTest_e
      .quad 3
      .quad _SuperCacheTest_d
      .quad 2
      .quad _SuperCacheTest_c
      .quad 1
      .quad _SuperCacheTest_b
      .quad 0
      .quad _SuperCacheTest_a


_CacheTest_DV:
      .quad _SuperCacheTest_DV
      .quad 6
      .quad 16
      .quad _CacheTest_main
      .quad 15
      .quad _CacheTest_k
      .quad 14
      .quad _CacheTest_j
      .quad 13
      .quad _CacheTest_h
      .quad 12
      .quad _CacheTest_g
      .quad 11
      .quad _CacheTest_f


# 
# Code.
# SuperCacheTest
.text
.align 8
_SuperCacheTest_a:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $16, %rsp                

     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



.text
.align 8
_SuperCacheTest_b:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $16, %rsp                

     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



.text
.align 8
_SuperCacheTest_c:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $16, %rsp                

     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



.text
.align 8
_SuperCacheTest_d:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $16, %rsp                

     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



.text
.align 8
_SuperCacheTest_e:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $16, %rsp                

     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



.text
.align 8
_SuperCacheTest_f:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $16, %rsp                

     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



.text
.align 8
_SuperCacheTest_g:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $16, %rsp                

     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



.text
.align 8
_SuperCacheTest_h:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $16, %rsp                

     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



.text
.align 8
_SuperCacheTest_i:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $16, %rsp                

     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



.text
.align 8
_SuperCacheTest_j:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $16, %rsp                

     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



.text
.align 8
_SuperCacheTest_k:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $16, %rsp                

     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



# CacheTest
.text
.align 8
_CacheTest_f:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $16, %rsp                

     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



.text
.align 8
_CacheTest_g:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $16, %rsp                

     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



.text
.align 8
_CacheTest_h:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $16, %rsp                

     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



.text
.align 8
_CacheTest_j:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $16, %rsp                

     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



.text
.align 8
_CacheTest_k:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $16, %rsp                

     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



.text
.align 8
_CacheTest_main:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $192, %rsp                

     # store  into i
     movq $0, -32(%rbp)
     whileBegin0: 
     movq -32(%rbp), %rax
     movq $10000000, %rbx
     movq $0, %rcx
     cmpq %rbx, %rax
     setl %cl
     movq %rcx, -40(%rbp)
     movq -40(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -64(%rbp)
     # conditional jump to label whileEnd1
     movq -64(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne whileEnd1
     movq $0, %rdx
     movq -32(%rbp), %rax
     movq $2, %rcx
     divq %rcx
     movq %rdx, -80(%rbp)
     movq -80(%rbp), %rax
     movq $0, %rbx
     movq $0, %rcx
     cmpq %rax, %rbx
     setz %cl
     movq %rcx, -72(%rbp)
     movq -72(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -112(%rbp)
     # conditional jump to label else2
     movq -112(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne else2
     # allocate an object
andq $-16, %rsp   # stupid stack align...
     movq $8, %rdi
     call __LIB_allocateObject
     leaq _SuperCacheTest_DV (%rip), %rdi
     movq %rdi, (%rax)
     movq %rax, -120(%rbp)


     # store  into c
     movq -120(%rbp), %rcx
     movq %rcx, -24(%rbp)
     # jump to label endElse3
     jmp endElse3
     else2: 
     # allocate an object
andq $-16, %rsp   # stupid stack align...
     movq $8, %rdi
     call __LIB_allocateObject
     leaq _CacheTest_DV (%rip), %rdi
     movq %rdi, (%rax)
     movq %rax, -128(%rbp)


     # store  into c
     movq -128(%rbp), %rcx
     movq %rcx, -24(%rbp)
     endElse3: 
     # Method call f
     movq -24(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError

     # calling function SuperCacheTest_f
     movq -24(%rbp), %rcx
     push %rcx
     movq (%rcx), %rdi
     movq $0, %rsi
     movq $5, %rdx
     call __LIB_lookInCache
     call *%rax
     add $8, %rsp


     # Method call g
     movq -24(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError

     # calling function SuperCacheTest_g
     movq -24(%rbp), %rcx
     push %rcx
     movq (%rcx), %rdi
     movq $1, %rsi
     movq $6, %rdx
     call __LIB_lookInCache
     call *%rax
     add $8, %rsp


     # Method call h
     movq -24(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError

     # calling function SuperCacheTest_h
     movq -24(%rbp), %rcx
     push %rcx
     movq (%rcx), %rdi
     movq $2, %rsi
     movq $7, %rdx
     call __LIB_lookInCache
     call *%rax
     add $8, %rsp


     # Method call j
     movq -24(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError

     # calling function SuperCacheTest_j
     movq -24(%rbp), %rcx
     push %rcx
     movq (%rcx), %rdi
     movq $3, %rsi
     movq $9, %rdx
     call __LIB_lookInCache
     call *%rax
     add $8, %rsp


     # Method call k
     movq -24(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError

     # calling function SuperCacheTest_k
     movq -24(%rbp), %rcx
     push %rcx
     movq (%rcx), %rdi
     movq $4, %rsi
     movq $10, %rdx
     call __LIB_lookInCache
     call *%rax
     add $8, %rsp


     movq -32(%rbp), %rcx
     addq $1,%rcx
     movq %rcx, -176(%rbp)
     # store  into i
     movq -176(%rbp), %rcx
     movq %rcx, -32(%rbp)
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

       movq $   8, %rdi                 # o = new CacheTest
     call __LIB_allocateObject   
     leaq _CacheTest_DV(%rip), %rdi       
     movq %rdi, (%rax)
     pushq %rax                        # o.main(args) > push o
     movq (%rax), %rdi
     movq $16, %rsi
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




