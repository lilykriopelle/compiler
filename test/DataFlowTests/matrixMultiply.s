# File test/DataFlowTests/matrixMultiply.s


# 
# VTables

.data
.align 8

_MatrixMult_DV:
      .quad -1
      .quad 4
      .quad 3
      .quad _MatrixMult_main
      .quad 0
      .quad _MatrixMult_multiply
      .quad 1
      .quad _MatrixMult_initMatrix
      .quad 2
      .quad _MatrixMult_printMatrix


# 
# Code.
# MatrixMult
.text
.align 8
_MatrixMult_multiply:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $560, %rsp                

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
     movq %rcx, -80(%rbp)
     movq -80(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -80(%rbp), %rdi
     movq -8(%rdi), %rcx
     movq %rcx, -72(%rbp)
     movq -24(%rbp), %rax
     movq -72(%rbp), %rbx
     movq $0, %rcx
     cmpq %rbx, %rax
     setl %cl
     movq %rcx, -56(%rbp)
     movq -56(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -96(%rbp)
     # conditional jump to label whileEnd1
     movq -96(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne whileEnd1
     # store  into j
     movq $0, -32(%rbp)
     whileBegin2: 
     # Array Access
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # load a field from an object
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $24, %rbx
     movq 0(%rax, %rbx, 1), %rcx
     movq %rcx, -152(%rbp)
     movq -152(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -152(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -24(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq -152(%rbp), %rax
     movq -24(%rbp), %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -136(%rbp)
     movq -136(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -136(%rbp), %rdi
     movq -8(%rdi), %rcx
     movq %rcx, -128(%rbp)
     movq -32(%rbp), %rax
     movq -128(%rbp), %rbx
     movq $0, %rcx
     cmpq %rbx, %rax
     setl %cl
     movq %rcx, -112(%rbp)
     movq -112(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -168(%rbp)
     # conditional jump to label whileEnd3
     movq -168(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne whileEnd3
     # Array Access
     # Array Access
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # load a field from an object
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $24, %rbx
     movq 0(%rax, %rbx, 1), %rcx
     movq %rcx, -208(%rbp)
     movq -208(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -208(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -24(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq -208(%rbp), %rax
     movq -24(%rbp), %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -192(%rbp)
     movq -192(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -192(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -32(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # store into an array
     movq -192(%rbp), %rax
     movq -32(%rbp), %rdi
     movq $0, %rcx
     movq %rcx, 0(%rax, %rdi, 8)
     # store  into k
     movq $0, -40(%rbp)
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
     movq %rcx, -272(%rbp)
     movq -272(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -272(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -24(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq -272(%rbp), %rax
     movq -24(%rbp), %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -256(%rbp)
     movq -256(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -256(%rbp), %rdi
     movq -8(%rdi), %rcx
     movq %rcx, -248(%rbp)
     movq -40(%rbp), %rax
     movq -248(%rbp), %rbx
     movq $0, %rcx
     cmpq %rbx, %rax
     setl %cl
     movq %rcx, -232(%rbp)
     movq -232(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -288(%rbp)
     # conditional jump to label whileEnd5
     movq -288(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne whileEnd5
     # Array Access
     # Array Access
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # load a field from an object
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $24, %rbx
     movq 0(%rax, %rbx, 1), %rcx
     movq %rcx, -344(%rbp)
     movq -344(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -344(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -24(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq -344(%rbp), %rax
     movq -24(%rbp), %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -328(%rbp)
     movq -328(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -328(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -32(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq -328(%rbp), %rax
     movq -32(%rbp), %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -304(%rbp)
     # Array Access
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
     movq %rcx, -400(%rbp)
     movq -400(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -400(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -24(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq -400(%rbp), %rax
     movq -24(%rbp), %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -384(%rbp)
     movq -384(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -384(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -40(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq -384(%rbp), %rax
     movq -40(%rbp), %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -360(%rbp)
     # Array Access
     # Array Access
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # load a field from an object
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $16, %rbx
     movq 0(%rax, %rbx, 1), %rcx
     movq %rcx, -440(%rbp)
     movq -440(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -440(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -40(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq -440(%rbp), %rax
     movq -40(%rbp), %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -424(%rbp)
     movq -424(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -424(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -32(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq -424(%rbp), %rax
     movq -32(%rbp), %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -368(%rbp)
     movq -360(%rbp), %rax
     movq -368(%rbp), %rbx
     mulq %rbx
     movq %rax, -312(%rbp)
     movq -304(%rbp), %rcx
     addq -312(%rbp),%rcx
     movq %rcx, -296(%rbp)
     # Array Access
     # Array Access
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # load a field from an object
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $24, %rbx
     movq 0(%rax, %rbx, 1), %rcx
     movq %rcx, -480(%rbp)
     movq -480(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -480(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -24(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq -480(%rbp), %rax
     movq -24(%rbp), %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -464(%rbp)
     movq -464(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -464(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -32(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # store into an array
     movq -464(%rbp), %rax
     movq -32(%rbp), %rdi
     movq -296(%rbp), %rcx
     movq %rcx, 0(%rax, %rdi, 8)
     movq -40(%rbp), %rcx
     addq $1,%rcx
     movq %rcx, -496(%rbp)
     # store  into k
     movq -496(%rbp), %rcx
     movq %rcx, -40(%rbp)
     # jump to label whileBegin4
     jmp whileBegin4
     whileEnd5: 
     movq -32(%rbp), %rcx
     addq $1,%rcx
     movq %rcx, -520(%rbp)
     # store  into j
     movq -520(%rbp), %rcx
     movq %rcx, -32(%rbp)
     # jump to label whileBegin2
     jmp whileBegin2
     whileEnd3: 
     movq -24(%rbp), %rcx
     addq $1,%rcx
     movq %rcx, -544(%rbp)
     # store  into i
     movq -544(%rbp), %rcx
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
_MatrixMult_initMatrix:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $264, %rsp                

     # store  into i
     movq $0, -24(%rbp)
     whileBegin6: 
     movq 24(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq 24(%rbp), %rdi
     movq -8(%rdi), %rcx
     movq %rcx, -64(%rbp)
     movq -24(%rbp), %rax
     movq -64(%rbp), %rbx
     movq $0, %rcx
     cmpq %rbx, %rax
     setl %cl
     movq %rcx, -48(%rbp)
     movq -48(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -80(%rbp)
     # conditional jump to label whileEnd7
     movq -80(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne whileEnd7
     # store  into j
     movq $0, -32(%rbp)
     whileBegin8: 
     # Array Access
     movq 24(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq 24(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -24(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq 24(%rbp), %rax
     movq -24(%rbp), %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -120(%rbp)
     movq -120(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -120(%rbp), %rdi
     movq -8(%rdi), %rcx
     movq %rcx, -112(%rbp)
     movq -32(%rbp), %rax
     movq -112(%rbp), %rbx
     movq $0, %rcx
     cmpq %rbx, %rax
     setl %cl
     movq %rcx, -96(%rbp)
     movq -96(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -144(%rbp)
     # conditional jump to label whileEnd9
     movq -144(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne whileEnd9
     # Library call random
     movq 24(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq 24(%rbp), %rdi
     movq -8(%rdi), %rcx
     movq %rcx, -168(%rbp)
     movq -168(%rbp), %rax
     movq $2, %rbx
     mulq %rbx
     movq %rax, -160(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # generate a random variable
     movq -160(%rbp), %rdi
     call __LIB_random
     movq %rax, -152(%rbp)

movq %r15, %rsp   # Stupid stack align

     # Array Access
     # Array Access
     movq 24(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq 24(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -24(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq 24(%rbp), %rax
     movq -24(%rbp), %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -200(%rbp)
     movq -200(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -200(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -32(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # store into an array
     movq -200(%rbp), %rax
     movq -32(%rbp), %rdi
     movq -152(%rbp), %rcx
     movq %rcx, 0(%rax, %rdi, 8)
     movq -32(%rbp), %rcx
     addq $1,%rcx
     movq %rcx, -224(%rbp)
     # store  into j
     movq -224(%rbp), %rcx
     movq %rcx, -32(%rbp)
     # jump to label whileBegin8
     jmp whileBegin8
     whileEnd9: 
     movq -24(%rbp), %rcx
     addq $1,%rcx
     movq %rcx, -248(%rbp)
     # store  into i
     movq -248(%rbp), %rcx
     movq %rcx, -24(%rbp)
     # jump to label whileBegin6
     jmp whileBegin6
     whileEnd7: 
     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



.text
.align 8
_MatrixMult_printMatrix:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $256, %rsp                

     # store  into i
     movq $0, -24(%rbp)
     whileBegin10: 
     movq 24(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq 24(%rbp), %rdi
     movq -8(%rdi), %rcx
     movq %rcx, -64(%rbp)
     movq -24(%rbp), %rax
     movq -64(%rbp), %rbx
     movq $0, %rcx
     cmpq %rbx, %rax
     setl %cl
     movq %rcx, -48(%rbp)
     movq -48(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -80(%rbp)
     # conditional jump to label whileEnd11
     movq -80(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne whileEnd11
     # store  into j
     movq $0, -32(%rbp)
     whileBegin12: 
     # Array Access
     movq 24(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq 24(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -24(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq 24(%rbp), %rax
     movq -24(%rbp), %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -120(%rbp)
     movq -120(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -120(%rbp), %rdi
     movq -8(%rdi), %rcx
     movq %rcx, -112(%rbp)
     movq -32(%rbp), %rax
     movq -112(%rbp), %rbx
     movq $0, %rcx
     cmpq %rbx, %rax
     setl %cl
     movq %rcx, -96(%rbp)
     movq -96(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -144(%rbp)
     # conditional jump to label whileEnd13
     movq -144(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne whileEnd13
     # Library call printi
     # Array Access
     # Array Access
     movq 24(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq 24(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -24(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq 24(%rbp), %rax
     movq -24(%rbp), %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -168(%rbp)
     movq -168(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -168(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -32(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq -168(%rbp), %rax
     movq -32(%rbp), %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -152(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print an integer
     movq -152(%rbp), %rdi
     call __LIB_printi

movq %r15, %rsp   # Stupid stack align

     # Library call print
     movq _str0(%rip), %rdi
     movq %rdi, -192(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print
     movq -192(%rbp), %rdi
     call __LIB_print

movq %r15, %rsp   # Stupid stack align

     movq -32(%rbp), %rcx
     addq $1,%rcx
     movq %rcx, -200(%rbp)
     # store  into j
     movq -200(%rbp), %rcx
     movq %rcx, -32(%rbp)
     # jump to label whileBegin12
     jmp whileBegin12
     whileEnd13: 
     # Library call print
     movq _str1(%rip), %rdi
     movq %rdi, -224(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print
     movq -224(%rbp), %rdi
     call __LIB_print

movq %r15, %rsp   # Stupid stack align

     movq -24(%rbp), %rcx
     addq $1,%rcx
     movq %rcx, -232(%rbp)
     # store  into i
     movq -232(%rbp), %rcx
     movq %rcx, -24(%rbp)
     # jump to label whileBegin10
     jmp whileBegin10
     whileEnd11: 
     # Library call print
     movq _str1(%rip), %rdi
     movq %rdi, -256(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print
     movq -256(%rbp), %rdi
     call __LIB_print

movq %r15, %rsp   # Stupid stack align

     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



.text
.align 8
_MatrixMult_main:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $904, %rsp                

     movq 24(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq 24(%rbp), %rdi
     movq -8(%rdi), %rcx
     movq %rcx, -64(%rbp)
     movq -64(%rbp), %rax
     movq $3, %rbx
     movq $0, %rcx
     cmpq %rax, %rbx
     setne %cl
     movq %rcx, -56(%rbp)
     movq -56(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -88(%rbp)
     # conditional jump to label endIf14
     movq -88(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne endIf14
     # Library call println
     movq _str2(%rip), %rdi
     movq %rdi, -96(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print t11 and a new line
     movq -96(%rbp), %rdi
     call __LIB_println

movq %r15, %rsp   # Stupid stack align

     # Library call exit
     movq $1, -104(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # exit with exit code
     movq -104(%rbp), %rdi
     call __LIB_exit

movq %r15, %rsp   # Stupid stack align

     endIf14: 
     # Library call stoi
     # Array Access
     movq 24(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq 24(%rbp), %rdi
     movq -8(%rdi), %rax
     movq $0, %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq 24(%rbp), %rax
     movq $0, %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -120(%rbp)
     movq $0, -144(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # returns an integer representation of the string
     movq -120(%rbp), %rdi
     movq -144(%rbp), %rsi
     call __LIB_stoi
     movq %rax, -112(%rbp)

movq %r15, %rsp   # Stupid stack align

     # store  into m
     movq -112(%rbp), %rcx
     movq %rcx, -24(%rbp)
     # Library call stoi
     # Array Access
     movq 24(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq 24(%rbp), %rdi
     movq -8(%rdi), %rax
     movq $1, %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq 24(%rbp), %rax
     movq $1, %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -160(%rbp)
     movq $0, -184(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # returns an integer representation of the string
     movq -160(%rbp), %rdi
     movq -184(%rbp), %rsi
     call __LIB_stoi
     movq %rax, -152(%rbp)

movq %r15, %rsp   # Stupid stack align

     # store  into n
     movq -152(%rbp), %rcx
     movq %rcx, -32(%rbp)
     # Library call stoi
     # Array Access
     movq 24(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq 24(%rbp), %rdi
     movq -8(%rdi), %rax
     movq $2, %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq 24(%rbp), %rax
     movq $2, %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -200(%rbp)
     movq $0, -224(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # returns an integer representation of the string
     movq -200(%rbp), %rdi
     movq -224(%rbp), %rsi
     call __LIB_stoi
     movq %rax, -192(%rbp)

movq %r15, %rsp   # Stupid stack align

     # store  into p
     movq -192(%rbp), %rcx
     movq %rcx, -40(%rbp)
     movq -24(%rbp), %rax
     movq $1, %rbx
     movq $0, %rcx
     cmpq %rbx, %rax
     setl %cl
     movq %rcx, -232(%rbp)
     movq -232(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -256(%rbp)
     # conditional jump to label endIf15
     movq -256(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne endIf15
     # Library call println
     movq _str3(%rip), %rdi
     movq %rdi, -264(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print t32 and a new line
     movq -264(%rbp), %rdi
     call __LIB_println

movq %r15, %rsp   # Stupid stack align

     # Library call exit
     movq $1, -272(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # exit with exit code
     movq -272(%rbp), %rdi
     call __LIB_exit

movq %r15, %rsp   # Stupid stack align

     endIf15: 
     movq -32(%rbp), %rax
     movq $1, %rbx
     movq $0, %rcx
     cmpq %rbx, %rax
     setl %cl
     movq %rcx, -280(%rbp)
     movq -280(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -304(%rbp)
     # conditional jump to label endIf16
     movq -304(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne endIf16
     # Library call println
     movq _str4(%rip), %rdi
     movq %rdi, -312(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print t38 and a new line
     movq -312(%rbp), %rdi
     call __LIB_println

movq %r15, %rsp   # Stupid stack align

     # Library call exit
     movq $1, -320(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # exit with exit code
     movq -320(%rbp), %rdi
     call __LIB_exit

movq %r15, %rsp   # Stupid stack align

     endIf16: 
     movq -40(%rbp), %rax
     movq $1, %rbx
     movq $0, %rcx
     cmpq %rbx, %rax
     setl %cl
     movq %rcx, -328(%rbp)
     movq -328(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -352(%rbp)
     # conditional jump to label endIf17
     movq -352(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne endIf17
     # Library call println
     movq _str5(%rip), %rdi
     movq %rdi, -360(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print t44 and a new line
     movq -360(%rbp), %rdi
     call __LIB_println

movq %r15, %rsp   # Stupid stack align

     # Library call exit
     movq $1, -368(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # exit with exit code
     movq -368(%rbp), %rdi
     call __LIB_exit

movq %r15, %rsp   # Stupid stack align

     endIf17: 
     movq -24(%rbp), %rax
     movq $0, %rcx
	  cmpq %rcx, %rax
     jl labelArraySizeError
     # allocate an array
andq $-16, %rsp   # stupid stack align...
     movq -24(%rbp), %rdi
     call __LIB_allocateArray
     movq %rax, %rdx
     movq %rdx, -376(%rbp)
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # store a new value into a field
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $8, %rbx
     movq -376(%rbp), %rcx
     movq %rcx, 0(%rax, %rbx, 1)
     # store  into i
     movq $0, -48(%rbp)
     whileBegin18: 
     movq -48(%rbp), %rax
     movq -24(%rbp), %rbx
     movq $0, %rcx
     cmpq %rbx, %rax
     setl %cl
     movq %rcx, -408(%rbp)
     movq -408(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -432(%rbp)
     # conditional jump to label whileEnd19
     movq -432(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne whileEnd19
     movq -32(%rbp), %rax
     movq $0, %rcx
	  cmpq %rcx, %rax
     jl labelArraySizeError
     # allocate an array
andq $-16, %rsp   # stupid stack align...
     movq -32(%rbp), %rdi
     call __LIB_allocateArray
     movq %rax, %rdx
     movq %rdx, -440(%rbp)
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
     movq %rcx, -464(%rbp)
     movq -464(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -464(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -48(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # store into an array
     movq -464(%rbp), %rax
     movq -48(%rbp), %rdi
     movq -440(%rbp), %rcx
     movq %rcx, 0(%rax, %rdi, 8)
     movq -48(%rbp), %rcx
     addq $1,%rcx
     movq %rcx, -480(%rbp)
     # store  into i
     movq -480(%rbp), %rcx
     movq %rcx, -48(%rbp)
     # jump to label whileBegin18
     jmp whileBegin18
     whileEnd19: 
     movq -32(%rbp), %rax
     movq $0, %rcx
	  cmpq %rcx, %rax
     jl labelArraySizeError
     # allocate an array
andq $-16, %rsp   # stupid stack align...
     movq -32(%rbp), %rdi
     call __LIB_allocateArray
     movq %rax, %rdx
     movq %rdx, -504(%rbp)
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # store a new value into a field
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $16, %rbx
     movq -504(%rbp), %rcx
     movq %rcx, 0(%rax, %rbx, 1)
     # store  into i
     movq $0, -48(%rbp)
     whileBegin20: 
     movq -48(%rbp), %rax
     movq -32(%rbp), %rbx
     movq $0, %rcx
     cmpq %rbx, %rax
     setl %cl
     movq %rcx, -536(%rbp)
     movq -536(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -560(%rbp)
     # conditional jump to label whileEnd21
     movq -560(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne whileEnd21
     movq -40(%rbp), %rax
     movq $0, %rcx
	  cmpq %rcx, %rax
     jl labelArraySizeError
     # allocate an array
andq $-16, %rsp   # stupid stack align...
     movq -40(%rbp), %rdi
     call __LIB_allocateArray
     movq %rax, %rdx
     movq %rdx, -568(%rbp)
     # Array Access
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # load a field from an object
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $16, %rbx
     movq 0(%rax, %rbx, 1), %rcx
     movq %rcx, -592(%rbp)
     movq -592(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -592(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -48(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # store into an array
     movq -592(%rbp), %rax
     movq -48(%rbp), %rdi
     movq -568(%rbp), %rcx
     movq %rcx, 0(%rax, %rdi, 8)
     movq -48(%rbp), %rcx
     addq $1,%rcx
     movq %rcx, -608(%rbp)
     # store  into i
     movq -608(%rbp), %rcx
     movq %rcx, -48(%rbp)
     # jump to label whileBegin20
     jmp whileBegin20
     whileEnd21: 
     movq -24(%rbp), %rax
     movq $0, %rcx
	  cmpq %rcx, %rax
     jl labelArraySizeError
     # allocate an array
andq $-16, %rsp   # stupid stack align...
     movq -24(%rbp), %rdi
     call __LIB_allocateArray
     movq %rax, %rdx
     movq %rdx, -632(%rbp)
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # store a new value into a field
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $24, %rbx
     movq -632(%rbp), %rcx
     movq %rcx, 0(%rax, %rbx, 1)
     # store  into i
     movq $0, -48(%rbp)
     whileBegin22: 
     movq -48(%rbp), %rax
     movq -24(%rbp), %rbx
     movq $0, %rcx
     cmpq %rbx, %rax
     setl %cl
     movq %rcx, -664(%rbp)
     movq -664(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -688(%rbp)
     # conditional jump to label whileEnd23
     movq -688(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne whileEnd23
     movq -40(%rbp), %rax
     movq $0, %rcx
	  cmpq %rcx, %rax
     jl labelArraySizeError
     # allocate an array
andq $-16, %rsp   # stupid stack align...
     movq -40(%rbp), %rdi
     call __LIB_allocateArray
     movq %rax, %rdx
     movq %rdx, -696(%rbp)
     # Array Access
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # load a field from an object
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $24, %rbx
     movq 0(%rax, %rbx, 1), %rcx
     movq %rcx, -720(%rbp)
     movq -720(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -720(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -48(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # store into an array
     movq -720(%rbp), %rax
     movq -48(%rbp), %rdi
     movq -696(%rbp), %rcx
     movq %rcx, 0(%rax, %rdi, 8)
     movq -48(%rbp), %rcx
     addq $1,%rcx
     movq %rcx, -736(%rbp)
     # store  into i
     movq -736(%rbp), %rcx
     movq %rcx, -48(%rbp)
     # jump to label whileBegin22
     jmp whileBegin22
     whileEnd23: 
     # Method call initMatrix
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # load a field from an object
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $8, %rbx
     movq 0(%rax, %rbx, 1), %rcx
     movq %rcx, -768(%rbp)

     # calling function MatrixMult_initMatrix
     push -768(%rbp)
     movq 16(%rbp), %rcx
     push %rcx
     movq (%rcx), %rdi
     movq $0, %rsi
     movq $1, %rdx
     call __LIB_lookInCache
     call *%rax
     add $16, %rsp


     # Library call println
     movq _str6(%rip), %rdi
     movq %rdi, -784(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print t97 and a new line
     movq -784(%rbp), %rdi
     call __LIB_println

movq %r15, %rsp   # Stupid stack align

     # Method call printMatrix
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # load a field from an object
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $8, %rbx
     movq 0(%rax, %rbx, 1), %rcx
     movq %rcx, -800(%rbp)

     # calling function MatrixMult_printMatrix
     push -800(%rbp)
     movq 16(%rbp), %rcx
     push %rcx
     movq (%rcx), %rdi
     movq $1, %rsi
     movq $2, %rdx
     call __LIB_lookInCache
     call *%rax
     add $16, %rsp


     # Method call initMatrix
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # load a field from an object
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $16, %rbx
     movq 0(%rax, %rbx, 1), %rcx
     movq %rcx, -824(%rbp)

     # calling function MatrixMult_initMatrix
     push -824(%rbp)
     movq 16(%rbp), %rcx
     push %rcx
     movq (%rcx), %rdi
     movq $2, %rsi
     movq $1, %rdx
     call __LIB_lookInCache
     call *%rax
     add $16, %rsp


     # Library call println
     movq _str7(%rip), %rdi
     movq %rdi, -840(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print t104 and a new line
     movq -840(%rbp), %rdi
     call __LIB_println

movq %r15, %rsp   # Stupid stack align

     # Method call printMatrix
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # load a field from an object
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $16, %rbx
     movq 0(%rax, %rbx, 1), %rcx
     movq %rcx, -856(%rbp)

     # calling function MatrixMult_printMatrix
     push -856(%rbp)
     movq 16(%rbp), %rcx
     push %rcx
     movq (%rcx), %rdi
     movq $3, %rsi
     movq $2, %rdx
     call __LIB_lookInCache
     call *%rax
     add $16, %rsp


     # Method call multiply
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError

     # calling function MatrixMult_multiply
     movq 16(%rbp), %rcx
     push %rcx
     movq (%rcx), %rdi
     movq $4, %rsi
     movq $0, %rdx
     call __LIB_lookInCache
     call *%rax
     add $8, %rsp


     # Library call println
     movq _str8(%rip), %rdi
     movq %rdi, -880(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print t109 and a new line
     movq -880(%rbp), %rdi
     call __LIB_println

movq %r15, %rsp   # Stupid stack align

     # Method call printMatrix
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # load a field from an object
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $24, %rbx
     movq 0(%rax, %rbx, 1), %rcx
     movq %rcx, -896(%rbp)

     # calling function MatrixMult_printMatrix
     push -896(%rbp)
     movq 16(%rbp), %rcx
     push %rcx
     movq (%rcx), %rdi
     movq $5, %rsi
     movq $2, %rdx
     call __LIB_lookInCache
     call *%rax
     add $16, %rsp


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

       movq $  32, %rdi                 # o = new MatrixMult
     call __LIB_allocateObject   
     leaq _MatrixMult_DV(%rip), %rdi       
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

.quad 31
  _str5Chars:	.ascii "Invalid value for parameter `p'"
_str5:	.quad _str5Chars
.quad 1
  _str0Chars:	.ascii " "
_str0:	.quad _str0Chars
.quad 9
  _str7Chars:	.ascii "Matrix B:"
_str7:	.quad _str7Chars
.quad 17
  _str8Chars:	.ascii "Matrix C = A x B:"
_str8:	.quad _str8Chars
.quad 29
  _str2Chars:	.ascii "Invalid number of parameters."
_str2:	.quad _str2Chars
.quad 31
  _str4Chars:	.ascii "Invalid value for parameter `n'"
_str4:	.quad _str4Chars
.quad 9
  _str6Chars:	.ascii "Matrix A:"
_str6:	.quad _str6Chars
.quad 1
  _str1Chars:	.ascii "\n"
_str1:	.quad _str1Chars
.quad 31
  _str3Chars:	.ascii "Invalid value for parameter `m'"
_str3:	.quad _str3Chars



