#include <iostream>

class Base
{
public:
	virtual void SomeFunction() { std::cout << "test base" << std::endl; }
private:
	int m_j;
};

class Derived : public Base {
public:
	void SomeFunction() { std::cout << "test derive" << std::endl; }
private:
	int m_i;
};

void MyWonderfulCode(Base baseArray[], std::size_t arraySize)
{
	for (std::size_t i = 0; i < arraySize; ++i)
	{
		baseArray[i].SomeFunction();  // 只输出第一个 后面开始崩溃
	}
}
int main()
{
	std::cout << " size of Base: " << sizeof(Base) << " size of Derived: " << sizeof(Derived) << std::endl; // 输出 size of Base: 8 size of Derived: 12
	Derived derivedArray[10];
	MyWonderfulCode(derivedArray, 10);
	return 0;
}

/*
Since in general the size of the derived class is different to the size of the base class, 
polymorphism and pointer arithmetic don't play together nicely. 
Since array access involves pointer arithmetic, expressions such as baseArray[i] don't work as expected.
One possibility is to have an array of pointers to your objects, 
perhaps even smart pointers to simplify memory management. But don't forget to define a virtual destructor in Base.
*/