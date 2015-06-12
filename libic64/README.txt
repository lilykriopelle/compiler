
Files:

 - libic64/src/libic.c  --- the source code for the library
 - Makefile             --- the Makefile to build it
 - gc-7.2alpha6/        --- The Boehm GC, a mark-sweep GC for C programs
 - test/Makefile        --- Run a simple test

To build in lab:

   make linux

That creates two libraries:

  - gc-linux/lib/libgc.a   --- the Boehm garbage collector
  - lib-linux/libic64.a    --- the IC run-time library

To run your code with that version, simply pass it to gcc:

 gcc -g -m64 matrix.s libic64/lib-linux/libic64.a

If you wish to modify the library, you can also directly include the
source when running gcc on .s files.  You must include the gc lib as
well, and also define the LINUX flag:

 gcc -DLINUX -g -m64 matrix.s libic64/src/libic.c libic64/gc-linux/lib/libgc.a

