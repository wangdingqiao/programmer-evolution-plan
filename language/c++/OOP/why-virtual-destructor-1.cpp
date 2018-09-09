#include <iostream>

class Base
{
public:
	Base()
	{
		std::cout << "Base::constructor called." << std::endl;
	}
	~Base()
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