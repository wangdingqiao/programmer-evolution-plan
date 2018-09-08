#include <iostream>
#include <cassert>
 
class C { 
public:
  int x;
};

int main()
{
   C c; // Compiler-provided default constructor is used here `c.x` contains garbage
   std::cout << c.x << std::endl;

   C c2 = C(); // Does not use default constructor for `C()` part Uses value-initialization feature instead
   assert(c2.x == 0);

   C *pc = new C(); // Does not use default constructor for `C()` part Uses value-initialization feature instead
   assert(pc->x == 0);
   	
   return 0;
}