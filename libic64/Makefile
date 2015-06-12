#
# To build on lab machines:
#
#    make linux
#
# Places built library into lib-linux/
#
# Same idea for building on osx...
#

linux:	gclinux liblinux

gclinux:
	cd gc-7.2alpha6/; ./configure --prefix=`pwd`/../gc-linux --enable-threads=no --build=x86_64 --enable-gc-debug
	cd gc-7.2alpha6/; make install

liblinux: 	
	gcc -g -m64 -DLINUX -c src/libic.c -o src/libic.o
	mkdir -p lib-linux
	cp gc-linux/lib/libgc.a lib-linux/libic64.a
	ar r lib-linux/libic64.a src/libic.o

libosx:	
	gcc -g -m64 -DOSX  -c src/libic.c -o src/libic.o
	mkdir -p lib-osx
	cp gc-osx/lib/libgc.a lib-osx/libic64.a
	ar r lib-osx/libic.a src/libic64.o

gcosx:
	cd gc-7.2alpha6/; ./configure --prefix=`pwd`/../gc-osx --enable-threads=no --build=x86_64 --enable-gc-debug
	cd gc-7.2alpha6/; make install

osx:	gcosx libosx

clean:
	rm -rf lib-osx
	rm -rf lib-linux
	rm -rf gc-osx
	rm -rf gc-linux
	rm -f src/libic.o
	cd test; make clean
