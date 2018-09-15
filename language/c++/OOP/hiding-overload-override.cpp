#include <iostream>
struct B {
    virtual void f(int) { std::cout << "B::f\n"; }
    void g(char)        { std::cout << "B::g\n"; }
    void h(int)         { std::cout << "B::h\n"; }
 protected:
    int m; // B::m is protected
    typedef int value_type;
};
 
struct D : B {
    using B::m; // D::m is public
    using B::value_type; // D::value_type is public
 
    using B::f;
    void f(int) { std::cout << "D::f\n"; } // D::f(int) overrides B::f(int)  发生了虚函数的覆盖


    using B::g;
    void g(int) { std::cout << "D::g\n"; } // both g(int) and g(char) are visible  发生了非虚函数的重载
                                           // as members of D
    using B::h;
    void h(int) { std::cout << "D::h\n"; } // D::h(int) hides B::h(int)  发生了非虚函数的隐藏
};
 
int main()
{
    D d;
    B& b = d;
 
//    b.m = 2; // error, B::m is protected
    d.m = 1; // protected B::m is accessible as public D::m
    b.f(1); // calls derived f()   发生了虚函数调用 D::f
    d.f(1); // calls derived f()   没有动态调用  D::f
    d.g(1); // calls derived g(int)  调用g的int重载版本 D::g
    d.g('a'); // calls base g(char)   调用g的char重载版本 这个版本由基类提供 B::g
    b.h(1); // calls base h()   未发生多态调用 B::h
    d.h(1); // calls derived h() 调用子类的隐藏版本 D::h
}

/*
D::f
D::f
D::g
B::g
B::h
D::h
 */