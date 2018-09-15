#include <iostream>

class Base
{
};


class Derived: private Base
{
};


int main()
{
	Derived d;
	Base b = d;

	std::cout << "Press any key to exit." << std::endl;
	char waitKey;
	std::cin >> waitKey;
}

/*
 error C2243: “类型转换”: 从“Derived *”到“const Base &”的转换存在，但无法访问
 https://stackoverflow.com/questions/3674876/why-would-the-conversion-between-derived-to-base-fails-with-private-inheritanc
 */