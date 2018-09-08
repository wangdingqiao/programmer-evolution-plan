#include <iostream>
#include <stdint.h>
#include <iomanip>
#include <string>

class Base {
public:
	virtual void f() { std::cout << "Base::f called." << std::endl; }
	virtual void g() { std::cout << "Base::g called." << std::endl; }
	virtual void h() { std::cout << "Base::h called." << std::endl; }
};

class Derive : public Base
{
public:
	virtual void f() { std::cout << "Derive::f called." << std::endl; } // 覆盖虚函数
};

void printMemAddress(const std::string& what, const intptr_t& address)
{
	std::cout << what << ": 0x" << std::hex << std::noshowbase << std::setw(8) << std::setfill('0') << address << std::endl;
}

int main()
{
	typedef void(*pFunc)();
	Derive d;
	intptr_t  * vptr = *(intptr_t**)&d; // 虚函数表首地址
	printMemAddress("memory address of virtual table", (intptr_t)vptr);
	pFunc pf = (pFunc)*vptr; // 第一个虚函数地址
	printMemAddress("memory address of virtual function f", (intptr_t)pf);
	pf();
	pFunc pg = (pFunc)*++vptr;
	printMemAddress("memory address of virtual function g", (intptr_t)pg);
	pg();
	pFunc ph = (pFunc)*++vptr;
	printMemAddress("memory address of virtual function h", (intptr_t)ph);
	ph();
	return 0;
}

/*
VS2013 window 7 64 bit
监视变量输出:
-		d	{...}	Derive
-		Base	{...}	Base
-		__vfptr	0x009adcd4 {Project.exe!const Derive::`vftable'} {0x009a10c3 {Project.exe!Derive::f(void)}, 0x009a10e6 {Project.exe!Base::g(void)}, ...}	void * *
		[0]	0x009a10c3 {Project.exe!Derive::f(void)}	void *
		[1]	0x009a10e6 {Project.exe!Base::g(void)}	void *
		[2]	0x009a14d3 {Project.exe!Base::h(void)}	void *
程序输出:
memory address of virtual table: 0x009adcd4
memory address of virtual function f: 0x009a10c3
Derive::f called.
memory address of virtual function g: 0x009a10e6
Base::g called.
memory address of virtual function h: 0x009a14d3
Base::h called.
*/