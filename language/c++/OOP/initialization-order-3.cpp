#include <iostream>
 
class A
{
public:
    A(int a)
    {
        std::cout << "A: " << a << '\n';
    }
};
 
class B: public A
{
public:
    B(int a, double b)
    : A(a)
    {
        std::cout << "B: " << b << '\n';
    }
};
 
class C: public B
{
public:
    C(int a , double b , char c)
    : B(a, b), A(a)               // 派生类只能初始化自己的直接基类 error C2614: “C”: 非法的成员初始化:“A”不是基或成员
    {
        std::cout << "C: " << c << '\n';
    }
};
 
int main()
{
    C c(5, 4.3, 'R');
 
    return 0;
}

/*
A: 5
B: 4.3
C: R
*/

// https://www.learncpp.com/cpp-tutorial/114-constructors-and-initialization-of-derived-classes/
