# File test/DataFlowTests/sieve.s


# 
# VTables

.data
.align 8

_Sieve_DV:
      .quad -1
      .quad 5
      .quad 4
      .quad _Sieve_main
      .quad 1
      .quad _Sieve_sieveAll
      .quad 3
      .quad _Sieve_printPrimes
      .quad 2
      .quad _Sieve_sieve
      .quad 0
      .quad _Sieve_initArray


# 
# Code.
# Sieve
.text
.align 8
_Sieve_initArray:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $128, %rsp                

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
     movq %rcx, -96(%rbp)
     movq -96(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -96(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -24(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # store into an array
     movq -96(%rbp), %rax
     movq -24(%rbp), %rdi
     movq -24(%rbp), %rcx
     movq %rcx, 0(%rax, %rdi, 8)
     movq -24(%rbp), %rcx
     addq $1,%rcx
     movq %rcx, -112(%rbp)
     # store  into i
     movq -112(%rbp), %rcx
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
_Sieve_sieveAll:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $112, %rsp                

     # store  into i
     movq $2, -24(%rbp)
     whileBegin2: 
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
     # conditional jump to label whileEnd3
     movq -72(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne whileEnd3
     # Method call sieve
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -24(%rbp), %rcx
     movq %rcx, -88(%rbp)

     # calling function Sieve_sieve
     push -88(%rbp)
     movq 16(%rbp), %rcx
     push %rcx
     movq (%rcx), %rdi
     movq $0, %rsi
     movq $2, %rdx
     call __LIB_lookInCache
     call *%rax
     add $16, %rsp


     movq -24(%rbp), %rcx
     addq $1,%rcx
     movq %rcx, -96(%rbp)
     # store  into i
     movq -96(%rbp), %rcx
     movq %rcx, -24(%rbp)
     # jump to label whileBegin2
     jmp whileBegin2
     whileEnd3: 
     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



.text
.align 8
_Sieve_sieve:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $144, %rsp                

     movq $2, %rax
     movq 24(%rbp), %rbx
     mulq %rbx
     movq %rax, -24(%rbp)
     whileBegin4: 
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # load a field from an object
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $8, %rbx
     movq 0(%rax, %rbx, 1), %rcx
     movq %rcx, -72(%rbp)
     movq -72(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -72(%rbp), %rdi
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
     movq %rcx, -88(%rbp)
     # conditional jump to label whileEnd5
     movq -88(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne whileEnd5
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
     movq %rcx, -112(%rbp)
     movq -112(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -112(%rbp), %rdi
     movq -8(%rdi), %rax
     movq -24(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # store into an array
     movq -112(%rbp), %rax
     movq -24(%rbp), %rdi
     movq $0, %rcx
     movq %rcx, 0(%rax, %rdi, 8)
     movq -24(%rbp), %rcx
     addq 24(%rbp),%rcx
     movq %rcx, -128(%rbp)
     # store  into i
     movq -128(%rbp), %rcx
     movq %rcx, -24(%rbp)
     # jump to label whileBegin4
     jmp whileBegin4
     whileEnd5: 
     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



.text
.align 8
_Sieve_printPrimes:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $232, %rsp                

     # store  into i
     movq $2, -24(%rbp)
     # Library call print
     movq _str0(%rip), %rdi
     movq %rdi, -32(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print
     movq -32(%rbp), %rdi
     call __LIB_print

movq %r15, %rsp   # Stupid stack align

     # Library call printi
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # load a field from an object
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $8, %rbx
     movq 0(%rax, %rbx, 1), %rcx
     movq %rcx, -48(%rbp)
     movq -48(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -48(%rbp), %rdi
     movq -8(%rdi), %rcx
     movq %rcx, -40(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print an integer
     movq -40(%rbp), %rdi
     call __LIB_printi

movq %r15, %rsp   # Stupid stack align

     # Library call print
     movq _str1(%rip), %rdi
     movq %rdi, -64(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print
     movq -64(%rbp), %rdi
     call __LIB_print

movq %r15, %rsp   # Stupid stack align

     whileBegin6: 
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # load a field from an object
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $8, %rbx
     movq 0(%rax, %rbx, 1), %rcx
     movq %rcx, -96(%rbp)
     movq -96(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     movq -96(%rbp), %rdi
     movq -8(%rdi), %rcx
     movq %rcx, -88(%rbp)
     movq -24(%rbp), %rax
     movq -88(%rbp), %rbx
     movq $0, %rcx
     cmpq %rbx, %rax
     setl %cl
     movq %rcx, -72(%rbp)
     movq -72(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -112(%rbp)
     # conditional jump to label whileEnd7
     movq -112(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne whileEnd7
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
     movq %rcx, -128(%rbp)
     movq -128(%rbp), %rax
     movq $0, %rbx
     movq $0, %rcx
     cmpq %rax, %rbx
     setne %cl
     movq %rcx, -120(%rbp)
     movq -120(%rbp), %rbx
     movq $0, %rcx
     cmpq $0, %rbx
     sete %cl
     movq %rcx, -168(%rbp)
     # conditional jump to label endIf8
     movq -168(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne endIf8
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
     movq -24(%rbp), %rbx
     movq $0, %rcx
	  cmpq %rcx, %rbx
     jl labelArrayBoundsError
	  cmpq %rbx, %rax
     jle labelArrayBoundsError
     # load an element of an array
     movq -192(%rbp), %rax
     movq -24(%rbp), %rdi
     movq 0(%rax, %rdi, 8), %rcx
     movq %rcx, -176(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print an integer
     movq -176(%rbp), %rdi
     call __LIB_printi

movq %r15, %rsp   # Stupid stack align

     # Library call print
     movq _str2(%rip), %rdi
     movq %rdi, -208(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print
     movq -208(%rbp), %rdi
     call __LIB_print

movq %r15, %rsp   # Stupid stack align

     endIf8: 
     movq -24(%rbp), %rcx
     addq $1,%rcx
     movq %rcx, -216(%rbp)
     # store  into i
     movq -216(%rbp), %rcx
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
_Sieve_main:

     # prologue
     pushq %rbp                    
     movq %rsp, %rbp                
     subq $216, %rsp                

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
     # conditional jump to label endIf9
     movq -64(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne endIf9
     # Library call println
     movq _str3(%rip), %rdi
     movq %rdi, -72(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print t8 and a new line
     movq -72(%rbp), %rdi
     call __LIB_println

movq %r15, %rsp   # Stupid stack align



     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



     endIf9: 
     # Library call println
     movq _str4(%rip), %rdi
     movq %rdi, -80(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print t9 and a new line
     movq -80(%rbp), %rdi
     call __LIB_println

movq %r15, %rsp   # Stupid stack align

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
     # conditional jump to label endIf10
     movq -152(%rbp), %rax
     movq $0, %rcx
     cmpq %rax, %rcx
     jne endIf10
     # Library call println
     movq _str5(%rip), %rdi
     movq %rdi, -160(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print t19 and a new line
     movq -160(%rbp), %rdi
     call __LIB_println

movq %r15, %rsp   # Stupid stack align



     # epilogue
     movq %rbp, %rsp                
     popq %rbp                      
     ret                           



     endIf10: 
     movq -24(%rbp), %rax
     movq $0, %rcx
	  cmpq %rcx, %rax
     jl labelArraySizeError
     # allocate an array
andq $-16, %rsp   # stupid stack align...
     movq -24(%rbp), %rdi
     call __LIB_allocateArray
     movq %rax, %rdx
     movq %rdx, -168(%rbp)
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError
     # store a new value into a field
     movq 16(%rbp), %r8
     movq %r8, %rax
     movq $8, %rbx
     movq -168(%rbp), %rcx
     movq %rcx, 0(%rax, %rbx, 1)
     # Method call initArray
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError

     # calling function Sieve_initArray
     movq 16(%rbp), %rcx
     push %rcx
     movq (%rcx), %rdi
     movq $1, %rsi
     movq $0, %rdx
     call __LIB_lookInCache
     call *%rax
     add $8, %rsp


     # Method call sieveAll
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError

     # calling function Sieve_sieveAll
     movq 16(%rbp), %rcx
     push %rcx
     movq (%rcx), %rdi
     movq $2, %rsi
     movq $1, %rdx
     call __LIB_lookInCache
     call *%rax
     add $8, %rsp


     # Method call printPrimes
     movq 16(%rbp), %rbx
     movq $0, %r8
     cmpq %rbx, %r8
     je labelNullPtrError

     # calling function Sieve_printPrimes
     movq 16(%rbp), %rcx
     push %rcx
     movq (%rcx), %rdi
     movq $3, %rsi
     movq $3, %rdx
     call __LIB_lookInCache
     call *%rax
     add $8, %rsp


     # Library call println
     movq _str4(%rip), %rdi
     movq %rdi, -216(%rbp)
movq %rsp, %r15
andq $-16, %rsp   # stupid stack align...
     # print t26 and a new line
     movq -216(%rbp), %rdi
     call __LIB_println

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

       movq $  16, %rdi                 # o = new Sieve
     call __LIB_allocateObject   
     leaq _Sieve_DV(%rip), %rdi       
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

.quad 0
  _str4Chars:	.ascii ""
_str4:	.quad _str4Chars
.quad 1
  _str2Chars:	.ascii " "
_str2:	.quad _str2Chars
.quad 2
  _str1Chars:	.ascii ": "
_str1:	.quad _str1Chars
.quad 19
  _str3Chars:	.ascii "Unspecified number."
_str3:	.quad _str3Chars
.quad 17
  _str0Chars:	.ascii "Primes less than "
_str0:	.quad _str0Chars
.quad 20
  _str5Chars:	.ascii "Invalid array length"
_str5:	.quad _str5Chars



