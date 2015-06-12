#
# Makefile for build IC Compiler.  
#
# To build the source files, simply type "make" on the command line.
#
# It supports the following modes, via commands like "make javadoc".
#
#   - java:    run JavaCup on ic.cup and JFlex on ic.flex and then build java files
#   - dump:    same as "java" but dump out JavaCup info
#   - javadoc: build javadoc files in doc sub-directory
#   - clean:   removes all class files, javadoc files and ~ files
#   - all:     all of the above.
#

java:   flexcup
	mkdir -p bin
	javac -d bin -classpath .:tools/java-cup-11a.jar ic/Compiler.java

flexcup:
	java -jar tools/java-cup-11a.jar -destdir ic/parser ic/parser/ic.cup
	java -jar tools/JFlex.jar ic/lex/ic.flex

dump:
	mkdir -p bin
	java -jar tools/java-cup-11a.jar -dump -destdir ic/parser ic/parser/ic.cup
	java -jar tools/JFlex.jar ic/lex/ic.flex
	javac -d bin -classpath .:tools/java-cup-11a.jar ic/Compiler.java

javadoc:
	javadoc -classpath .:bin:tools/java-cup-11a.jar \
		-link  http://java.sun.com/products/j2se/1.5.0/docs/api \
	        -d doc  `find ic -name "*.java"`

clean:
	rm -f ic/lex/Lexer.java
	rm -f ic/parser/parser.java
	rm -f ic/parser/sym.java
	rm -rf bin/*
	rm -rf doc/*

all:	clean java javadoc
