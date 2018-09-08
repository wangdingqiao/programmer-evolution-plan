#include <iostream>

namespace outer
{
  void foo(char c) { std::cout << "outer\n"; }

  namespace inner
  {
	//using outer::foo;
    void foo(int i) { std::cout << "inner\n"; }

    void bar() { foo('c'); }

  }
}

int main()
{
  outer::inner::bar();
}