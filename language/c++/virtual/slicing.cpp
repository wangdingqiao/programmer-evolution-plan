#include <iostream>

// Base class
class A 
{
public:
    A() {}
    A(const A& a) {std::cout << "'A' copy constructor" << std::endl;}
    virtual void run() const { std::cout << "I am an 'A'" << std::endl; }
};

// Derived class
class B: public A 
{
public:
    B():A() {}
    B(const B& a):A(a) {std::cout << "'B' copy constructor" << std::endl;}
    virtual void run() const { std::cout << "I am a 'B'" << std::endl; }
};

void g(const A & a) 
{
    a.run();
}

void h(const A * a) 
{
    a->run();
}

void k(const A a) 
{
    a.run();
}

int main() 
{
	B b;
    std::cout << "Call by reference:" << std::endl;
    g(b);   // I am a 'B'
	std::cout << std::endl << "Call by pointer:" << std::endl;
    h(&b); // I am a 'B'
    std::cout << std::endl << "Call by copy:" << std::endl;
    k(b); // 'A' copy constructor I am an 'A' ·¢ÉúÁËslicing
	return 0;
}