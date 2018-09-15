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
    : B(a, b)
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
