#include<iostream>
#include<stdio.h>
 
using namespace std;
 
class Test
{
public:
   Test() {}
   Test(const Test &t)
   {
      cout<<"Copy constructor called "<<endl;
   }
   Test& operator = (const Test &t)
   {
      cout<<"Assignment operator called "<<endl;
	  return *this;
   }
};
 
int main()
{
  Test t1, t2;
  t2 = t1; // 赋值操作符
  Test t3 = t1; // 拷贝构造函数
  getchar();
  return 0;
}

/*
Assignment operator called
Copy constructor called
*/