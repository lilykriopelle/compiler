OSXLIBS = ../lib-osx/libic64.a 
LINUXLIBS = ../lib-linux/libic64.a 


linux:
	gcc -m64 -S t.c
	gcc -m64 -o t.exe t.s $(LINUXLIBS)
	./t.exe
	echo "Test succeeded."

osx:
	gcc -m64 -S  t.c
	gcc -m64 -o t.exe t.s $(OSXLIBS)
	./t.exe
	echo "Test succeeded."

clean:
	rm -f t.exe t.s
