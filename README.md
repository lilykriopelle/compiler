# Java-Lite Compiler
This is a compiler for a simple object oriented language whose features mirror those of Java.  
It was built by Lily Riopelle and Emma Harrington for CS434 at Williams College (for more information about the course, 
go [here](http://cs.williams.edu/~freund/cs434/)). It compiles to x86 assembly.

## Type System
Like Java, Java-Lite is statically type checked, and allows single inheritance and interfaces.  

## Optimizations
The compiler performs several optimizations, including Dead Code Elimination, Constant Folding, Copy Propagation, 
Common Subexpression Elimination, and Polymorphic Inline Caching.

## External Technologies
We use [CUP](http://www2.cs.tum.edu/projects/cup/) to generate a LALR parser.
