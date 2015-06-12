# File test/434-tests/objects-5.s


# 
# VTables

.data
.align 8

_Super_DV:
      .quad _Super_f


_Test_DV:
      .quad _Super_f
      .quad _Test_g
      .quad _Test_main


# 
# Code.
# Super
.text
.align 8
_Super_f:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $32, %rsp                

     movq $99, %rcx
     negq %rcx
     movq %rcx, -24(%rbp)
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

     movq $11, %rax


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
     subq $64, %rsp                

     # Library call printi
     # Method call g
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError

     # calling function Test_g
     movq 16(%rbp), %rcx
     push %rcx
     movq (%rcx), %rdi
     movq $0, %rsi
     movq $1, %rdx
     call __LIB_lookInCache
     call *%rax
     movq %rax, -24(%rbp)


movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print an integer
     movq -24(%rbp), %rdi
     call __LIB_printi

movq %r15, %rsp   # Stupid stack align

     # Library call printi
     # Method call f
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError

     # calling function Super_f
     movq 16(%rbp), %rcx
     push %rcx
     movq (%rcx), %rdi
     movq $1, %rsi
     movq $0, %rdx
     call __LIB_lookInCache
     call *%rax
     movq %rax, -40(%rbp)


movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print an integer
     movq -40(%rbp), %rdi
     call __LIB_printi

movq %r15, %rsp   # Stupid stack align

     # Library call printi
     # Method call f
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError

     # calling function Super_f
     movq 16(%rbp), %rcx
     push %rcx
     movq (%rcx), %rdi
     movq $2, %rsi
     movq $0, %rdx
     call __LIB_lookInCache
     call *%rax
     movq %rax, -56(%rbp)


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

       movq $   8, %rdi                 # o = new Test
     call __LIB_allocateObject   
     leaq _Test_DV(%rip), %rdi       
     movq %rdi, (%rax)
     pushq %rax                        # o.main(args) > push o
     movq (%rax), %rax            
     call *16(%rax)                   # main is at offset 16 in vtable
     addq $8, %rsp                
     movq $0, %rax                     # __ic_main always returns 0

     movq %rbp,%rsp                    # epilogue
     popq %rbp                    
     ret                         



# 
# String Constants

.data
.align 8




