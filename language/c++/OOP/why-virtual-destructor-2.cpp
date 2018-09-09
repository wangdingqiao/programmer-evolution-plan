#include <iostream>

class Base
{
public:
	Base()
	{
		std::cout << "Base::constructor called." << std::endl;
	}

	// 使用虚函数 保证运行时子类析构函数能够被执行  因为实现多态需要依赖虚函数 否则将仅执行基类析构函数
	// 虚函数性质将被继承 如果继承层次中根类析构函数是虚函数  则派生类析构函数也将是虚函数
	virtual ~Base() 
	{
		std::cout << "--Base::destructed." << std::endl;
	}
};


class Derived: public Base
{

public:
	  Derived():_buff(NULL)
	  {
	  	 _buff = new char[1024 * 1024 * 10]();
	  	 std::cout << "Derived::constructor called." << std::endl;
	  }
	  ~Derived()
	  {
	  	 std::cout << "--Derived::destructed." << std::endl;
	  	 delete[] _buff;
	  	 _buff = NULL;
	  }
private:
	char* _buff;
};


int main()
{
	Base* p = new Base;
	delete p;

	p = new Derived;
	delete p;

	std::cout << "Press any key to exit." << std::endl;
	char waitKey;
	std::cin >> waitKey;
}