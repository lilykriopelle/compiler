# File test/434-tests/stmts-1.s


# 
# VTables

.data
.align 8

_Test_DV:
      .quad _Test_main


# 
# Code.
# Test
.text
.align 8
_Test_main:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $88, %rsp                

     # store  into i
     movq $0, -24(%rbp)
     whileBegin0: 
     movq -24(%rbp), %rax
     movq $5, %rbx
     movq $0, %rcx
     cmpq %rbx, %rax
     setl %cl
     movq %rcx, -32(%rbp)
     movq -32(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -56(%rbp)
     # conditional jump to label whileEnd1
     movq -56(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne whileEnd1
     movq -24(%rbp), %rcx
     addq $1,%rcx
     movq %rcx, -64(%rbp)
     # store  into i
     movq -64(%rbp), %rcx
     movq %rcx, -24(%rbp)
     # Library call printi
     movq -24(%rbp), %rcx
     movq %rcx, -88(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print an integer
     movq -88(%rbp), %rdi
     call __LIB_printi

movq %r15, %rsp   # Stupid stack align

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




