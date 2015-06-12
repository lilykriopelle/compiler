# File test/DataFlowTests/arrayMan.s


# 
# VTables

.data
.align 8

_ArrayManipulation_DV:
      .quad _ArrayManipulation_main
      .quad _ArrayManipulation_initArray
      .quad _ArrayManipulation_partition


# 
# Code.
# ArrayManipulation
.text
.align 8
_ArrayManipulation_main:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $112, %rsp                

     movq $10, %rax
     movq $0, %rcx
	  cmpq %rcx, %rax
     jl labelArraySizeError
     # allocate an array
andq $-16, %rsp   # stupid stack align...
     movq $10, %rdi
     call __LIB_allocateArray
     movq %rax, %rdx
     movq %rdx, -32(%rbp)
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # store a new value into a field
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $8, %rbx
     movq -32(%rbp), %rcx
     movq %rcx, 0(%rax, %rbx, 1)
     # Method call initArray
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # calling function ArrayManipulation_initArray
     movq 16(%rbp), %rcx
     push %rcx
     movq (%rcx), %rbx
     call *8(%rbx)
     add $8, %rsp


     # Method call partition
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq $9, -80(%rbp)
     movq $0, -88(%rbp)
     # calling function ArrayManipulation_partition
     push -80(%rbp)
     push -88(%rbp)
     movq 16(%rbp), %rcx
     push %rcx
     movq (%rcx), %rbx
     call *16(%rbx)
     add $24, %rsp
     movq %rax, -64(%rbp)


     # store  into pivot
     movq -64(%rbp), %rcx
     movq %rcx, -24(%rbp)
     # Method call partition
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq $9, -104(%rbp)
     movq -24(%rbp), %rcx
     movq %rcx, -112(%rbp)
     # calling function ArrayManipulation_partition
     push -104(%rbp)
     push -112(%rbp)
     movq 16(%rbp), %rcx
     push %rcx
     movq (%rcx), %rbx
     call *16(%rbx)
     add $24, %rsp


     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



.text
.align 8
_ArrayManipulation_initArray:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $168, %rsp                

     # store  into i
     movq $0, -24(%rbp)
     whileBegin0: 
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # load a field from an object
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $8, %rbx
     movq 0(%rax, %rbx, 1), %rcx
     movq %rcx, -56(%rbp)
     movq -56(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -56(%rbp), %rdi
     movq -8(%rdi), %rcx
     movq %rcx, -48(%rbp)
     movq -24(%rbp), %rax
     movq -48(%rbp), %rbx
     movq $0, %rcx
     cmpq %rbx, %rax
     setl %cl
     movq %rcx, -32(%rbp)
     movq -32(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -72(%rbp)
     # conditional jump to label whileEnd1
     movq -72(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne whileEnd1
     # Library call random
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # load a field from an object
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $8, %rbx
     movq 0(%rax, %rbx, 1), %rcx
     movq %rcx, -112(%rbp)
     movq -112(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -112(%rbp), %rdi
     movq -8(%rdi), %rcx
     movq %rcx, -96(%rbp)
     movq -96(%rbp), %rax
     movq $2, %rbx
     mulq %rbx
     movq %rax, -88(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # generate a random variable
     movq -88(%rbp), %rdi
     call __LIB_random
     movq %rax, -80(%rbp)

movq %r15, %rsp   # Stupid stack align

     # Array Access
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # load a field from an object
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $8, %rbx
     movq 0(%rax, %rbx, 1), %rcx
     movq %rcx, -136(%rbp)
     movq -136(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -136(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -24(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # store into an array
     movq -136(%rbp), %rax
     movq -24(%rbp), %rdi
     movq -80(%rbp), %rcx
     movq %rcx, 0(%rax, %rdi, 8)
     movq -24(%rbp), %rcx
     addq $1,%rcx
     movq %rcx, -152(%rbp)
     # store  into i
     movq -152(%rbp), %rcx
     movq %rcx, -24(%rbp)
     # jump to label whileBegin0
     jmp whileBegin0
     whileEnd1: 
     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



.text
.align 8
_ArrayManipulation_partition:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $536, %rsp                

     # Array Access
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # load a field from an object
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $8, %rbx
     movq 0(%rax, %rbx, 1), %rcx
     movq %rcx, -40(%rbp)
     movq -40(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -40(%rbp), %rdi
     movq -8(%rdi), %rax
     movq 24(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq -40(%rbp), %rax
     movq 24(%rbp), %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -24(%rbp)
     # store  into i
     movq 24(%rbp), %rcx
     movq %rcx, -56(%rbp)
     # store  into j
     movq 32(%rbp), %rcx
     movq %rcx, -64(%rbp)
     whileBegin2: 
     movq $1, %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -88(%rbp)
     # conditional jump to label whileEnd3
     movq -88(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne whileEnd3
     whileBegin4: 
     # Array Access
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # load a field from an object
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $8, %rbx
     movq 0(%rax, %rbx, 1), %rcx
     movq %rcx, -128(%rbp)
     movq -128(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -128(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -56(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq -128(%rbp), %rax
     movq -56(%rbp), %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -104(%rbp)
     movq -104(%rbp), %rax
     movq -24(%rbp), %rbx
     movq $0, %rcx
     cmpq %rbx, %rax
     setl %cl
     movq %rcx, -96(%rbp)
     movq -96(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -144(%rbp)
     # conditional jump to label whileEnd5
     movq -144(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne whileEnd5
     movq -56(%rbp), %rcx
     addq $1,%rcx
     movq %rcx, -152(%rbp)
     # store  into i
     movq -152(%rbp), %rcx
     movq %rcx, -56(%rbp)
     # Library call printi
     # Array Access
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # load a field from an object
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $8, %rbx
     movq 0(%rax, %rbx, 1), %rcx
     movq %rcx, -192(%rbp)
     movq -192(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -192(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -56(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq -192(%rbp), %rax
     movq -56(%rbp), %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -176(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print an integer
     movq -176(%rbp), %rdi
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

     # jump to label whileBegin4
     jmp whileBegin4
     whileEnd5: 
     whileBegin6: 
     # Array Access
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # load a field from an object
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $8, %rbx
     movq 0(%rax, %rbx, 1), %rcx
     movq %rcx, -248(%rbp)
     movq -248(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -248(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -64(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq -248(%rbp), %rax
     movq -64(%rbp), %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -224(%rbp)
     movq -224(%rbp), %rax
     movq -24(%rbp), %rbx
     movq $0, %rcx
     cmpq %rbx, %rax
     setg %cl
     movq %rcx, -216(%rbp)
     movq -216(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -264(%rbp)
     # conditional jump to label whileEnd7
     movq -264(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne whileEnd7
     movq -64(%rbp), %rcx
     subq $1,%rcx
     movq %rcx, -272(%rbp)
     # store  into j
     movq -272(%rbp), %rcx
     movq %rcx, -64(%rbp)
     # Library call printi
     # Array Access
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # load a field from an object
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $8, %rbx
     movq 0(%rax, %rbx, 1), %rcx
     movq %rcx, -312(%rbp)
     movq -312(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -312(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -56(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq -312(%rbp), %rax
     movq -56(%rbp), %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -296(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print an integer
     movq -296(%rbp), %rdi
     call __LIB_printi

movq %r15, %rsp   # Stupid stack align

     # Library call println
     movq _str0(%rip), %rdi
     movq %rdi, -328(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print t40 and a new line
     movq -328(%rbp), %rdi
     call __LIB_println

movq %r15, %rsp   # Stupid stack align

     # jump to label whileBegin6
     jmp whileBegin6
     whileEnd7: 
     movq -56(%rbp), %rax
     movq -64(%rbp), %rbx
     movq $0, %rcx
     cmpq %rbx, %rax
     setge %cl
     movq %rcx, -336(%rbp)
     movq -336(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -360(%rbp)
     # conditional jump to label endIf8
     movq -360(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne endIf8
     # jump to label whileEnd3
     jmp whileEnd3
     endIf8: 
     # Array Access
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # load a field from an object
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $8, %rbx
     movq 0(%rax, %rbx, 1), %rcx
     movq %rcx, -384(%rbp)
     movq -384(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -384(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -56(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq -384(%rbp), %rax
     movq -56(%rbp), %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -368(%rbp)
     # Array Access
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # load a field from an object
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $8, %rbx
     movq 0(%rax, %rbx, 1), %rcx
     movq %rcx, -416(%rbp)
     movq -416(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -416(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -64(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # Array Access
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # load a field from an object
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $8, %rbx
     movq 0(%rax, %rbx, 1), %rcx
     movq %rcx, -440(%rbp)
     movq -440(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -440(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -56(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # store into an array
     movq -440(%rbp), %rax
     movq -56(%rbp), %rdi
     movq -400(%rbp), %rcx
     movq %rcx, 0(%rax, %rdi, 8)
     # Array Access
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # load a field from an object
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $8, %rbx
     movq 0(%rax, %rbx, 1), %rcx
     movq %rcx, -472(%rbp)
     movq -472(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -472(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -64(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # store into an array
     movq -472(%rbp), %rax
     movq -64(%rbp), %rdi
     movq -72(%rbp), %rcx
     movq %rcx, 0(%rax, %rdi, 8)
     movq -56(%rbp), %rcx
     addq $1,%rcx
     movq %rcx, -488(%rbp)
     # store  into i
     movq -488(%rbp), %rcx
     movq %rcx, -56(%rbp)
     movq -64(%rbp), %rcx
     subq $1,%rcx
     movq %rcx, -512(%rbp)
     # store  into j
     movq -512(%rbp), %rcx
     movq %rcx, -64(%rbp)
     # jump to label whileBegin2
     jmp whileBegin2
     whileEnd3: 
     movq -64(%rbp), %rax


     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



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

       movq $  16, %rdi                 # o = new ArrayManipulation
     call __LIB_allocateObject   
     leaq _ArrayManipulation_DV(%rip), %rdi       
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



