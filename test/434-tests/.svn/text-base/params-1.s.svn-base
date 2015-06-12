# File test/434-tests/params-1.s


# 
# VTables

.data
.align 8

_Test_DV:
      .quad _Test_f
      .quad _Test_main


# 
# Code.
# Test
.text
.align 8
_Test_f:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $120, %rsp                

     # Library call printi
     movq 24(%rbp), %rcx
     movq %rcx, -24(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print an integer
     movq -24(%rbp), %rdi
     call __LIB_printi

movq %r15, %rsp   # Stupid stack align

     # Library call printb
     movq 32(%rbp), %rcx
     movq %rcx, -32(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print a boolean t3
     movq -32(%rbp), %rdi
     call __LIB_printb

movq %r15, %rsp   # Stupid stack align

     # Library call print
     movq 40(%rbp), %rcx
     movq %rcx, -40(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print
     movq -40(%rbp), %rdi
     call __LIB_print

movq %r15, %rsp   # Stupid stack align

     # Library call print
     # Library call itos
     movq 24(%rbp), %rcx
     movq %rcx, -56(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # return a string representation of the integer array
     movq -56(%rbp), %rdi
     call __LIB_itos
     movq %rax, -48(%rbp)

movq %r15, %rsp   # Stupid stack align

movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print
     movq -48(%rbp), %rdi
     call __LIB_print

movq %r15, %rsp   # Stupid stack align

     # Library call printi
     # Library call stoi
     movq 40(%rbp), %rcx
     movq %rcx, -72(%rbp)
     movq $11, %rcx
     negq %rcx
     movq %rcx, -80(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # returns an integer representation of the string
     movq -72(%rbp), %rdi
     movq -80(%rbp), %rsi
     call __LIB_stoi
     movq %rax, -64(%rbp)

movq %r15, %rsp   # Stupid stack align

movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print an integer
     movq -64(%rbp), %rdi
     call __LIB_printi

movq %r15, %rsp   # Stupid stack align

     # Library call printi
     # Library call stoi
     movq _str0(%rip), %rdi
     movq %rdi, -104(%rbp)
     movq $11, %rcx
     negq %rcx
     movq %rcx, -112(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # returns an integer representation of the string
     movq -104(%rbp), %rdi
     movq -112(%rbp), %rsi
     call __LIB_stoi
     movq %rax, -96(%rbp)

movq %r15, %rsp   # Stupid stack align

movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print an integer
     movq -96(%rbp), %rdi
     call __LIB_printi

movq %r15, %rsp   # Stupid stack align

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
     subq $48, %rsp                

     # Method call f
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq _str1(%rip), %rdi
     movq %rdi, -32(%rbp)
     movq $0, -40(%rbp)
     movq $1, -48(%rbp)
     # calling function Test_f
     push -32(%rbp)
     push -40(%rbp)
     push -48(%rbp)
     movq 16(%rbp), %rcx
     push %rcx
     movq (%rcx), %rbx
     call *0(%rbx)
     add $32, %rsp


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

.quad 3
  _str0Chars:	.ascii "moo"
_str0:	.quad _str0Chars
.quad 2
  _str1Chars:	.ascii "33"
_str1:	.quad _str1Chars



