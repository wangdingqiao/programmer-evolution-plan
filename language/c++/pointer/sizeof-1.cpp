#include <iostream>  
  
size_t getPtrSize( char ptr[] )  
{  
   return sizeof( ptr );  
}  
  
int main()  
{  
   char szHello[] = "Hello, world!";  
  
   std::cout  << "The size of a char is: "  << sizeof( char )  << std::endl;
   std::cout  << "The length of `" << szHello << "` is: "  <<  strlen(szHello)  << std::endl;
   std::cout  << "The size of `" << szHello << "`  is: "  << sizeof szHello  << std::endl;
   std::cout  << "The size of the pointer is "  << getPtrSize( szHello ) << std::endl;
}  
