#include <stdio.h>

extern char *__LIB_makeString();
extern char *__LIB_stringCat();

extern void __LIB_println();
extern void __LIB_print();
extern void __LIB_printi();
extern void __LIB_printb();

extern long  __LIB_readi();
extern char *__LIB_readln();
extern long  __LIB_eof();

extern long  __LIB_stoi();
extern char *__LIB_itos();
extern long  *__LIB_stoa();
extern char *__LIB_atos();

extern void *__LIB_allocateArray();

extern long  __LIB_random();
extern long  __LIB_time();
extern void __LIB_exit(long i);


#define LENGTHOF(x) (*( ((long *) (x)) - 1 ))

char b[100];

long __ic_main(char **args) {
  char *s, *s1, *s2, *s3;
  long i, n, *v, len;

  printf("\n *** sizeof(int) = %lX\n", sizeof(int));
  printf("\n *** sizeof(long) = %lX\n", sizeof(long));

  printf("\n  *** testing __LIB_makestring, __LIB_stringcat");
  printf("\n  *** testing print, printi, printb\n"); 
  s1 = __LIB_makeString("Print string: \"");
  s2 = __LIB_makeString("testing");
  s3 = __LIB_stringCat(s1,s2);
  __LIB_print(s3);
  s = __LIB_makeString("\" [len=");
  __LIB_print(s); 
  __LIB_printi(LENGTHOF(s3));
  printf("]\n");

  s = __LIB_makeString("Print number: ");
  __LIB_print(s);  
  __LIB_printi(100); 
  printf("\n");

  s = __LIB_makeString("Print boolean: '");
  __LIB_print(s);  
  __LIB_printb(1); 
  printf("'\n");

  printf("\n  *** testing readi, readln, eof\n");
  printf("enter char : ");
  printf("read  char : '%c'\n\n", (int)__LIB_readi());
  __LIB_readln();

  printf("enter line : ");
  s = __LIB_readln();
  printf("read  line : \"%s\", [len=%ld]\n", s, LENGTHOF(s));

  printf("\n  *** testing itos, stoi\n"); 
  __LIB_println(__LIB_itos(125));
  __LIB_printi(__LIB_stoi(__LIB_makeString("126"), 0)); 
  printf("\n"); 

  printf("\n  *** testing atos, stoa\n"); 
  v = (unsigned long *)__LIB_allocateArray(3, 8);
  for(i=0;i<LENGTHOF(v);i++) v[i] = 65+i;
  __LIB_println(__LIB_atos(v));

  s = __LIB_makeString("DEF");
  __LIB_print(s);
  printf(" -(stoa)-> "); 
  v = __LIB_stoa(s);
  s = __LIB_makeString(" ");
  for(i=0; i<LENGTHOF(v); i++) { 
      __LIB_printi(v[i]); 
      __LIB_print(s); 
  };  
  printf(" -(atos)-> ");
  s = __LIB_atos(v); 
  __LIB_println(s);

  printf("\n  *** testing arguments\n"); 
  len = LENGTHOF(args);
  printf("main has %ld arguments\n", len);
  for(i=0; i<len; i++) {
    printf("  arg%ld = \"", i); 
    __LIB_print(args[i]);
    printf("\" [len = %ld]\n", LENGTHOF(args[i])); 
  }

  printf("\n  *** testing random\n");
  n = 1000;
  printf("random[0-%ld]:", n);
  for (i=0; i<10; i++)
    printf("%ld, ", __LIB_random(n));
  printf("\n");
    
  printf("\n  *** testing time\n");
  for(i=0;i<.3E8;i++) n = n+i*i;
  printf("time = %ld \n", __LIB_time());
  for(i=0;i<.3E8;i++) n = n+i*i;
  printf("time = %ld \n", __LIB_time());
  for(i=0;i<.3E8;i++) n = n+i*i;
  printf("time = %ld \n", __LIB_time());

  printf("\n  *** tests succeeded.\n"); 
  return 0;
}
