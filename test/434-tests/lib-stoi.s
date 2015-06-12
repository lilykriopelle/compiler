# File test/434-tests/lib-stoi.s


# 
# VTables

.data
.align 8

_Main_DV:
      .quad _Main_main


# 
# Code.
# Main
.text
.align 8
_Main_main:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $40, %rsp                

     # Library call printi
     # Library call stoi
     movq _str0(%rip), %rdi
     movq %rdi, -32(%rbp)
     movq $3, -40(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # returns an integer representation of the string
     movq -32(%rbp), %rdi
     movq -40(%rbp), %rsi
     call __LIB_stoi
     movq %rax, -24(%rbp)

movq %r15, %rsp   # Stupid stack align

movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print an integer
     movq -24(%rbp), %rdi
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

       movq $   8, %rdi                 # o = new Main
     call __LIB_allocateObject   
     leaq _Main_DV(%rip), %rdi       
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

.quad 5
  _str0Chars:	.ascii "three"
_str0:	.quad _str0Chars



