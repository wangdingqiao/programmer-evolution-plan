#include <iostream>
#include <stdint.h>
#include <iomanip>
#include <string>

class B1 {
public:
	virtual ~B1() {}
	void f0() {}
	virtual void f1() { std::cout << "B1::f1 called." << std::endl; }
	int int_in_b1;
};

class B2 {
public:
	virtual ~B2() {}
	virtual void f2() { std::cout << "B2::f2 called." << std::endl; }
	int int_in_b2;
};

class D : public B1, public B2 {
public:
	void d() {}
	virtual void f1() { std::cout << "D::f1 called." << std::endl; }  // override B1::f1()
	int int_in_d;
};

void printMemAddress(const std::string& what, const intptr_t& address)
{
	std::cout << what << ": 0x" << std::hex << std::noshowbase << std::setw(8) << std::setfill('0') << address << std::endl;
}

int main()
{
	typedef void(*pFunc)();
	D d;
	std::cout << "size of object d: " << sizeof(d) << std::endl;
	intptr_t  * vptr_B1 = *(intptr_t**)&d; // B1 虚函数表首地址
	printMemAddress("memory address of virtual table of B1", (intptr_t)vptr_B1);
	pFunc pfunc = (pFunc)*++vptr_B1; // 第二个虚函数地址 第一个为虚析构函数
	printMemAddress("memory address of virtual function f1", (intptr_t)pfunc);
	pfunc();

	return 0;
}