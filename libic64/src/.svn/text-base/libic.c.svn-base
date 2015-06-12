#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "gc.h"

#define DEBUG 

#define BUFSIZE 1024
static char buf[BUFSIZE];

/* Length of array */
#define LENGTHOF(x) (*( ((long *) (x)) - 1 ))

#define ASSERT(x,msg) if (!(x)) { printf("*** libic *** "); printf msg; exit(1); }

/* Handle various _ prefixing rules for different architectures */
#ifdef LINUX
  #define LIB(X) _ ## X
#else
  #define LIB(X) X
#endif

/**************************************************************
 * Internal routines
 */


char *LIB(_LIB_allocateArray)(long len)
{
  ASSERT((len >= 0), ("__allocate: negative array length: %ld\n", len));

  long size = (len + 1) * sizeof(long);
  char *p = (char*) GC_malloc (size);
  ASSERT((p != NULL), ("GC_malloc: memory allocation failed\n"));
  memset (p, 0, size); 

  *((long*)p) = len;
  return (p + sizeof(long));
}

char *LIB(_LIB_allocateString)(long len)
{
  ASSERT((len >= 0), ("__allocate: negative string length: %ld\n", len));

  long size = len + sizeof(long);
  char *p = (char*) GC_malloc (size);
  ASSERT((p != NULL), ("GC_malloc: memory allocation failed\n"));
  memset (p, 0, size); 

  *((long*)p) = len;
  return (p + sizeof(long));
}

char *LIB(_LIB_allocateObject)(long size)
{
  ASSERT((size >= 0), ("__allocate: negative size: %ld\n", size));

  char *p = (char*) GC_malloc (size);
  ASSERT((p != NULL), ("GC_malloc: memory allocation failed\n"));
  memset (p, 0, size);

  return p;
}

char *LIB(_LIB_stringCat)(char *s, char *d)
{
  long lens, lend, len;
  char *r;

  ASSERT((s != NULL), ("__stringcat: first source string is null\n"));
  ASSERT((d != NULL), ("__stringcat: second source string is null\n"));

  lens = LENGTHOF(s);
  lend = LENGTHOF(d);
  len = lens + lend;
  r = LIB(_LIB_allocateString)(len);
  memcpy(r, s, lens);
  memcpy(r + lens, d, lend);
  return r;
}

char *LIB(_LIB_makeString)(char *s)
{
  long len;
  char *d;

  ASSERT(s != NULL, ("__makestring: source string is null\n"));

  len = strlen(s);
  d = LIB(_LIB_allocateString)(len);
  memcpy(d, s, len);
  return d;
}

/**************************************************************
 * io module
 */

void LIB(_LIB_print)(char *s) 
{ 
  ASSERT(s != NULL, ("print: source string is null\n"));
  fwrite (s, sizeof(char), LENGTHOF(s), stdout); 
  fflush(stdout); 
}

void LIB(_LIB_println)(char *s) 
{ 
  ASSERT(s != NULL, ("print: source string is null\n"));
  fwrite (s, sizeof(char), LENGTHOF(s), stdout);
  fwrite ("\n", sizeof(char), 1, stdout);
  fflush(stdout); 
}

void LIB(_LIB_printi)(long i)  {   fprintf(stdout,"%ld", i); }
void LIB(_LIB_printb)(char b)  {   fprintf(stdout,"%s", b?"true":"false"); }

long LIB(_LIB_eof)()   {   return feof(stdin) ? 1 : 0; } 
long LIB(_LIB_readi)() {   return getc(stdin); }

char *LIB(_LIB_readln)() 
{ 
  fgets(buf,BUFSIZE-1,stdin); 
  buf[strlen(buf)-1] = 0;
  return LIB(_LIB_makeString)(buf);  
}

/***************************************************************
 * conv module
 */

long LIB(_LIB_stoi)(char *string, long error)
{
  
  int len = LENGTHOF(string);
  char z[len+1];  // make null-terminated string
  memcpy(z,string,len);
  z[len]=0;
  long i = atoi (z);
  return (i == 0 && z[0] != '0') ? error : i;
}

char *LIB(_LIB_itos)(long i)
{
  sprintf (buf, "%ld", i);
  return LIB(_LIB_makeString)(buf);
}

char *LIB(_LIB_atos)(long *a)
{
  long len, i;
  char *p;
  ASSERT(a != NULL, ("atos: null source array\n"));
  len = LENGTHOF(a);
  p = LIB(_LIB_allocateString)(len);
  for(i=0; i<len; i++) p[i] = (char) a[i];
  return p;
}

long *LIB(_LIB_stoa)(char *s)
{
  long len, i, *p;
  ASSERT(s != NULL, ("stoa: null source string\n"));
  len = LENGTHOF(s);
  p = (long*)LIB(_LIB_allocateArray)(len);
  for(i=0; i<len; i++) p[i] = (long) s[i];
  return p;
}

/*************************************************************
 * random, exit, timing
 */

void LIB(_LIB_exit)(long n) 
{ 
  exit(n); 
}

long LIB(_LIB_random)(long n) 
{ 
  ASSERT((n > 0), ("random: argument is not positive: %ld\n", n));

  return (long) 
    ((double)n *
     ((double)rand()/(double)RAND_MAX)); 
}

long LIB(_LIB_time)() 
{ 
  return (long) 
    (1000.0 * 
     ((double)clock())/(double)CLOCKS_PER_SEC);
}

/***************************************************************/

struct method {
  long methodNum; 
  long *method; 
};

typedef struct method Method; 

struct vTable;

struct vTable {
  struct vTable *superTable;
  long numMethods; 
  Method methods[1];
};

typedef struct vTable VTable; 

/*
 * Linear search for methods
 */
long *LIB(_LIB_findMethod)(VTable *vTable, long methodNum) 
{
  VTable *superVTablePointer = vTable->superTable;
  long numMethodsInClass = vTable->numMethods; 
  long i; 
  for(i = 0; i < numMethodsInClass; i++) {
    if(vTable->methods[i].methodNum == methodNum) {
      return vTable->methods[i].method;  
    }
  } 
  return LIB(_LIB_findMethod)(superVTablePointer, methodNum); 
}

typedef struct {
  VTable* receiver;
  long* methodPointer; 
} Call;


typedef struct { 
  Call calls[4]; 
} Cache;

Cache cacheList[100]; 

void LIB(_LIB_updateCache)(VTable *vTable, long* method, long funId);

/*
 * Update the cache of the function call 
 */

			  int hit = 0; 
			  int miss = 0;
 
long *LIB(_LIB_lookInCache)(VTable *vTable, long funId, long methodId) {
  int i = 0;  
  for (; i < 4; i = i + 1) {
    if(&(cacheList[funId]) && &(cacheList[funId].calls[i])) {
      if(cacheList[funId].calls[i].receiver == vTable) {
	#ifdef DEBUG
	//	printf("Found in cache\n");
	hit++; 
	#endif
	return cacheList[funId].calls[i].methodPointer; 
      }  
    }
  }
  miss++; 
  
  long *method = LIB(_LIB_findMethod)(vTable, methodId); 
  LIB(_LIB_updateCache)(vTable, method, funId); 
  return method; 

}


int indexOfFuns[100]; // default number of function calls
int curIndex; 

void LIB(_LIB_updateCache)(VTable *vTable, long* method, long funId) {
  #ifdef RANDOMIZE 
  int i = indexOfFuns[funId] % 4; 
  cacheList[funId].calls[i].receiver = vTable; 
  cacheList[funId].calls[i].methodPointer = method; 
  indexOfFuns[funId] = indexOfFuns[funId] + 1; 
#endif
 
  #ifdef SHIFT
  // Shift everything in the cache down and then add the new call
  cacheList[funId].calls[3] = cacheList[funId].calls[2]; 
  cacheList[funId].calls[2] = cacheList[funId].calls[1];
  cacheList[funId].calls[1] = cacheList[funId].calls[0];
  cacheList[funId].calls[0].receiver = vTable;
  cacheList[funId].calls[0].methodPointer = method; 
  #endif
}

/*************************************************************
 * main
 */

int main (int argc, char **argv)
{
  char **icarg;
  long i;
  extern long ( LIB(_ic_main) )(char**); 

  argc--;
  argv++;
  
  icarg = (char **) LIB(_LIB_allocateArray)(argc);
  for (i = 0; i < argc; i++) 
    icarg[i] = LIB(_LIB_makeString)(argv[i]);
  
  LIB(_ic_main)(icarg); 

  printf("HITS: %d \n", hit);
  printf("MISSES: %d \n", miss); 
}
