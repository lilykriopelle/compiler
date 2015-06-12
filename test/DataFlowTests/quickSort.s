# File test/DataFlowTests/quickSort.s


# 
# VTables

.data
.align 8

_Quicksort_DV:
      .quad -1
      .quad 5
      .quad 4
      .quad _Quicksort_main
      .quad 3
      .quad _Quicksort_printArray
      .quad 0
      .quad _Quicksort_partition
      .quad 2
      .quad _Quicksort_initArray
      .quad 1
      .quad _Quicksort_quicksort


# 
# Code.
# Quicksort
.text
.align 8
_Quicksort_partition:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $456, %rsp                

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
     whileBegin0: 
     movq $1, %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -88(%rbp)
     # conditional jump to label whileEnd1
     movq -88(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne whileEnd1
     whileBegin2: 
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
     # conditional jump to label whileEnd3
     movq -144(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne whileEnd3
     movq -56(%rbp), %rcx
     addq $1,%rcx
     movq %rcx, -152(%rbp)
     # store  into i
     movq -152(%rbp), %rcx
     movq %rcx, -56(%rbp)
     # jump to label whileBegin2
     jmp whileBegin2
     whileEnd3: 
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
     movq %rcx, -208(%rbp)
     movq -208(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -208(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -64(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq -208(%rbp), %rax
     movq -64(%rbp), %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -184(%rbp)
     movq -184(%rbp), %rax
     movq -24(%rbp), %rbx
     movq $0, %rcx
     cmpq %rbx, %rax
     setg %cl
     movq %rcx, -176(%rbp)
     movq -176(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -224(%rbp)
     # conditional jump to label whileEnd5
     movq -224(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne whileEnd5
     movq -64(%rbp), %rcx
     subq $1,%rcx
     movq %rcx, -232(%rbp)
     # store  into j
     movq -232(%rbp), %rcx
     movq %rcx, -64(%rbp)
     # jump to label whileBegin4
     jmp whileBegin4
     whileEnd5: 
     movq -56(%rbp), %rax
     movq -64(%rbp), %rbx
     movq $0, %rcx
     cmpq %rbx, %rax
     setge %cl
     movq %rcx, -256(%rbp)
     movq -256(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -280(%rbp)
     # conditional jump to label endIf6
     movq -280(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne endIf6
     # jump to label whileEnd1
     jmp whileEnd1
     endIf6: 
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
     movq %rcx, -304(%rbp)
     movq -304(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -304(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -56(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq -304(%rbp), %rax
     movq -56(%rbp), %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -288(%rbp)
     # store  into tmp
     movq -288(%rbp), %rcx
     movq %rcx, -72(%rbp)
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
     movq %rcx, -336(%rbp)
     movq -336(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -336(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -64(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq -336(%rbp), %rax
     movq -64(%rbp), %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -320(%rbp)
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
     movq %rcx, -360(%rbp)
     movq -360(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -360(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -56(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # store into an array
     movq -360(%rbp), %rax
     movq -56(%rbp), %rdi
     movq -320(%rbp), %rcx
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
     movq %rcx, -392(%rbp)
     movq -392(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -392(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -64(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # store into an array
     movq -392(%rbp), %rax
     movq -64(%rbp), %rdi
     movq -72(%rbp), %rcx
     movq %rcx, 0(%rax, %rdi, 8)
     movq -56(%rbp), %rcx
     addq $1,%rcx
     movq %rcx, -408(%rbp)
     # store  into i
     movq -408(%rbp), %rcx
     movq %rcx, -56(%rbp)
     movq -64(%rbp), %rcx
     subq $1,%rcx
     movq %rcx, -432(%rbp)
     # store  into j
     movq -432(%rbp), %rcx
     movq %rcx, -64(%rbp)
     # jump to label whileBegin0
     jmp whileBegin0
     whileEnd1: 
     movq -64(%rbp), %rax


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
_Quicksort_quicksort:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $144, %rsp                

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
     # conditional jump to label endIf7
     movq -48(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne endIf7
     # Method call partition
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq 32(%rbp), %rcx
     movq %rcx, -72(%rbp)
     movq 24(%rbp), %rcx
     movq %rcx, -80(%rbp)

     # calling function Quicksort_partition
     push -72(%rbp)
     push -80(%rbp)
     movq 16(%rbp), %rcx
     push %rcx
     movq (%rcx), %rdi
     movq $0, %rsi
     movq $0, %rdx
     call __LIB_lookInCache
     call *%rax
     add $24, %rsp
     movq %rax, -56(%rbp)


     # Method call quicksort
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -56(%rbp), %rcx
     movq %rcx, -96(%rbp)
     movq 24(%rbp), %rcx
     movq %rcx, -104(%rbp)

     # calling function Quicksort_quicksort
     push -96(%rbp)
     push -104(%rbp)
     movq 16(%rbp), %rcx
     push %rcx
     movq (%rcx), %rdi
     movq $1, %rsi
     movq $1, %rdx
     call __LIB_lookInCache
     call *%rax
     add $24, %rsp


     # Method call quicksort
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq 32(%rbp), %rcx
     movq %rcx, -120(%rbp)
     movq -56(%rbp), %rcx
     addq $1,%rcx
     movq %rcx, -128(%rbp)

     # calling function Quicksort_quicksort
     push -120(%rbp)
     push -128(%rbp)
     movq 16(%rbp), %rcx
     push %rcx
     movq (%rcx), %rdi
     movq $2, %rsi
     movq $1, %rdx
     call __LIB_lookInCache
     call *%rax
     add $24, %rsp


     endIf7: 
     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



.text
.align 8
_Quicksort_initArray:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $168, %rsp                

     # store  into i
     movq $0, -24(%rbp)
     whileBegin8: 
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
     # conditional jump to label whileEnd9
     movq -72(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne whileEnd9
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
     # jump to label whileBegin8
     jmp whileBegin8
     whileEnd9: 
     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



.text
.align 8
_Quicksort_printArray:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $152, %rsp                

     # store  into i
     movq $0, -24(%rbp)
     # Library call print
     movq _str0(%rip), %rdi
     movq %rdi, -32(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print
     movq -32(%rbp), %rdi
     call __LIB_print

movq %r15, %rsp   # Stupid stack align

     whileBegin10: 
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # load a field from an object
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $8, %rbx
     movq 0(%rax, %rbx, 1), %rcx
     movq %rcx, -64(%rbp)
     movq -64(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -64(%rbp), %rdi
     movq -8(%rdi), %rcx
     movq %rcx, -56(%rbp)
     movq -24(%rbp), %rax
     movq -56(%rbp), %rbx
     movq $0, %rcx
     cmpq %rbx, %rax
     setl %cl
     movq %rcx, -40(%rbp)
     movq -40(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -80(%rbp)
     # conditional jump to label whileEnd11
     movq -80(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne whileEnd11
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
     movq %rcx, -104(%rbp)
     movq -104(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -104(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -24(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq -104(%rbp), %rax
     movq -24(%rbp), %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -88(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print an integer
     movq -88(%rbp), %rdi
     call __LIB_printi

movq %r15, %rsp   # Stupid stack align

     # Library call print
     movq _str1(%rip), %rdi
     movq %rdi, -120(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print
     movq -120(%rbp), %rdi
     call __LIB_print

movq %r15, %rsp   # Stupid stack align

     movq -24(%rbp), %rcx
     addq $1,%rcx
     movq %rcx, -128(%rbp)
     # store  into i
     movq -128(%rbp), %rcx
     movq %rcx, -24(%rbp)
     # jump to label whileBegin10
     jmp whileBegin10
     whileEnd11: 
     # Library call print
     movq _str2(%rip), %rdi
     movq %rdi, -152(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print
     movq -152(%rbp), %rdi
     call __LIB_print

movq %r15, %rsp   # Stupid stack align

     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



.text
.align 8
_Quicksort_main:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $256, %rsp                

     movq 24(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq 24(%rbp), %rdi
     movq -8(%rdi), %rcx
     movq %rcx, -40(%rbp)
     movq -40(%rbp), %rax
     movq $1, %rbx
     movq $0, %rcx
     cmpq %rax, %rbx
     setne %cl
     movq %rcx, -32(%rbp)
     movq -32(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -64(%rbp)
     # conditional jump to label endIf12
     movq -64(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne endIf12
     # Library call println
     movq _str3(%rip), %rdi
     movq %rdi, -72(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print t8 and a new line
     movq -72(%rbp), %rdi
     call __LIB_println

movq %r15, %rsp   # Stupid stack align

     # Library call exit
     movq $1, -80(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # exit with exit code
     movq -80(%rbp), %rdi
     call __LIB_exit

movq %r15, %rsp   # Stupid stack align

     endIf12: 
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
     movq %rcx, -96(%rbp)
     movq $0, -120(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # returns an integer representation of the string
     movq -96(%rbp), %rdi
     movq -120(%rbp), %rsi
     call __LIB_stoi
     movq %rax, -88(%rbp)

movq %r15, %rsp   # Stupid stack align

     # store  into n
     movq -88(%rbp), %rcx
     movq %rcx, -24(%rbp)
     movq -24(%rbp), %rax
     movq $0, %rbx
     movq $0, %rcx
     cmpq %rbx, %rax
     setle %cl
     movq %rcx, -128(%rbp)
     movq -128(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -152(%rbp)
     # conditional jump to label endIf13
     movq -152(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne endIf13
     # Library call println
     movq _str4(%rip), %rdi
     movq %rdi, -160(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print t19 and a new line
     movq -160(%rbp), %rdi
     call __LIB_println

movq %r15, %rsp   # Stupid stack align

     # Library call exit
     movq $1, -168(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # exit with exit code
     movq -168(%rbp), %rdi
     call __LIB_exit

movq %r15, %rsp   # Stupid stack align

     endIf13: 
     movq -24(%rbp), %rax
     movq $0, %rcx
	  cmpq %rcx, %rax
     jl labelArraySizeError
     # allocate an array
andq $-16, %rsp   # stupid stack align...
     movq -24(%rbp), %rdi
     call __LIB_allocateArray
     movq %rax, %rdx
     movq %rdx, -176(%rbp)
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # store a new value into a field
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $8, %rbx
     movq -176(%rbp), %rcx
     movq %rcx, 0(%rax, %rbx, 1)
     # Method call initArray
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError

     # calling function Quicksort_initArray
     movq 16(%rbp), %rcx
     push %rcx
     movq (%rcx), %rdi
     movq $3, %rsi
     movq $2, %rdx
     call __LIB_lookInCache
     call *%rax
     add $8, %rsp


     # Method call printArray
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError

     # calling function Quicksort_printArray
     movq 16(%rbp), %rcx
     push %rcx
     movq (%rcx), %rdi
     movq $4, %rsi
     movq $3, %rdx
     call __LIB_lookInCache
     call *%rax
     add $8, %rsp


     # Method call quicksort
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -24(%rbp), %rcx
     subq $1,%rcx
     movq %rcx, -224(%rbp)
     movq $0, -248(%rbp)

     # calling function Quicksort_quicksort
     push -224(%rbp)
     push -248(%rbp)
     movq 16(%rbp), %rcx
     push %rcx
     movq (%rcx), %rdi
     movq $5, %rsi
     movq $1, %rdx
     call __LIB_lookInCache
     call *%rax
     add $24, %rsp


     # Method call printArray
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError

     # calling function Quicksort_printArray
     movq 16(%rbp), %rcx
     push %rcx
     movq (%rcx), %rdi
     movq $6, %rsi
     movq $3, %rdx
     call __LIB_lookInCache
     call *%rax
     add $8, %rsp


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

       movq $  16, %rdi                 # o = new Quicksort
     call __LIB_allocateObject   
     leaq _Quicksort_DV(%rip), %rdi       
     movq %rdi, (%rax)
     pushq %rax                        # o.main(args) > push o
     movq (%rax), %rdi
     movq $4, %rsi
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

.quad 1
  _str1Chars:	.ascii " "
_str1:	.quad _str1Chars
.quad 16
  _str0Chars:	.ascii "Array elements: "
_str0:	.quad _str0Chars
.quad 1
  _str2Chars:	.ascii "\n"
_str2:	.quad _str2Chars
.quad 20
  _str4Chars:	.ascii "Invalid array length"
_str4:	.quad _str4Chars
.quad 24
  _str3Chars:	.ascii "Unspecified array length"
_str3:	.quad _str3Chars



