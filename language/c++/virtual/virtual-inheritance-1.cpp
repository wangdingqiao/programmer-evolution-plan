#include <iostream>

class A { public: void Foo() {} };
class B : public A {};
class C : public A {};
class D : public B, public C {};

int main()
{
	D d;
	d.Foo(); // is this B's Foo() or C's Foo() ??
}

/*
virtual-inherirance-1.cpp(11) : error C2385: 对“Foo”的访问不明确
        可能是“Foo”(位于基“A”中)
        也可能是“Foo”(位于基“A”中)
virtual-inherirance-1.cpp(11) : error C3861: “Foo”:  找不到标识符
*/