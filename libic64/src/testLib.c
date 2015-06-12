

/***************************************************************
 * Linear search for methods
 */
long *LIB(_LIB_findMethod)(long *vpointer, long methodNum) 
{
  long *superVTablePointer = *(vpointer); 
  long numMethodsInClass = *(vpointer + 8); 
  long firstMethodNum = *(vpointer + 16);
  if(firstMethodNum + numMethodsInClass < methodNum) {
    return LIB(_LIB_findMethod)(*superVTablePointer, methodNum);  
  } else { 
    long i; 
    for(i = 2; i <= numMethodsInClass + 2; i++) {
      long curMethod = *(vpointer + i*8);
      if(curMethod == methodNum) {
	return vpointer + i*8; 
      }
    } 
  }
}
