# File test/DataFlowTests/partition.s


# 
# VTables

.data
.align 8

_Partition_DV:
      .quad _Partition_main
      .quad _Partition_quicksort


# 
# Code.
# Partition
.text
.align 8
_Partition_main:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $64, %rsp                

     movq $15, %rax
     movq $0, %rcx
	  cmpq %rcx, %rax
     jl labelArraySizeError
     # allocate an array
andq $-16, %rsp   # stupid stack align...
     movq $15, %rdi
     call __LIB_allocateArray
     movq %rax, %rdx
     movq %rdx, -24(%rbp)
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # store a new value into a field
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $8, %rbx
     movq -24(%rbp), %rcx
     movq %rcx, 0(%rax, %rbx, 1)
     # Method call quicksort
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq $14, -56(%rbp)
     movq $0, -64(%rbp)
     # calling function Partition_quicksort
     push -56(%rbp)
     push -64(%rbp)
     movq 16(%rbp), %rcx
     push %rcx
     movq (%rcx), %rbx
     call *8(%rbx)
     add $24, %rsp


     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



.text
.align 8
_Partition_quicksort:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $128, %rsp                

     movq 24(%rbp), %rax
     movq 32(%rbp), %rbx
     movq $0, %rcx
     cmpq %rbx, %rax
     setl %cl
     movq %rcx, -24(%rbp)
     movq -24(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -48(%rbp)
     # conditional jump to label endIf0
     movq -48(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne endIf0
     # store  into mid
     movq $15, -56(%rbp)
     # Method call quicksort
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -56(%rbp), %rcx
     movq %rcx, -72(%rbp)
     movq 24(%rbp), %rcx
     movq %rcx, -80(%rbp)
     # calling function Partition_quicksort
     push -72(%rbp)
     push -80(%rbp)
     movq 16(%rbp), %rcx
     push %rcx
     movq (%rcx), %rbx
     call *8(%rbx)
     add $24, %rsp


     # Library call printi
     movq 24(%rbp), %rcx
     movq %rcx, -88(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print an integer
     movq -88(%rbp), %rdi
     call __LIB_printi

movq %r15, %rsp   # Stupid stack align

     # Method call quicksort
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq 32(%rbp), %rcx
     movq %rcx, -104(%rbp)
     movq -56(%rbp), %rcx
     addq $1,%rcx
     movq %rcx, -112(%rbp)
     # calling function Partition_quicksort
     push -104(%rbp)
     push -112(%rbp)
     movq 16(%rbp), %rcx
     push %rcx
     movq (%rcx), %rbx
     call *8(%rbx)
     add $24, %rsp


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

       movq $  16, %rdi                 # o = new Partition
     call __LIB_allocateObject   
     leaq _Partition_DV(%rip), %rdi       
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




