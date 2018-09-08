#include <iostream>
#include <string>
#include "template-compile-separate-1.h"

int main()
{
	Pair<int,int> p1(3,4);
	
	std::cout << "p1:" << p1 << std::endl;

	{
		Pair<int,int> p2(p1);
		std::cout << "p2:" << p2 << std::endl;
	}
	std::cout << "p1:" << p1 << std::endl;

	{
		Pair<int,int> p3(0,0);
		p3 = p1;
		std::cout << "p3:" << p3 << std::endl;
	}
	std::cout << "p1:" << p1 << std::endl;

	Pair<int ,std::string> p4(3,"Important");
	std::cout << "p4:" << p4 << std::endl;
}
