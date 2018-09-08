#include <iostream>

class Foo
{
public:
    int m_x;
    int m_y;
};
 
int main()
{
    Foo foo = { 4, 5 }; // initialization list
	
	std::cout << foo.m_x << "," << foo.m_y << std::endl;
    return 0;
}