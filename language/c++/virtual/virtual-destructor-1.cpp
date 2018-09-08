#include <iostream>

class Base
{
public:
    ~Base() // note: not virtual
    {
        std::cout << "Calling ~Base()" << std::endl;
    }
};
 
class Derived: public Base
{
private:
    int* m_array;
 
public:
    Derived(int length)
    {
        m_array = new int[length];
    }
 
    ~Derived() // note: not virtual
    {
        std::cout << "Calling ~Derived()" << std::endl;
        delete[] m_array;
    }
};
 
int main()
{
    Derived *derived = new Derived(5);
    Base *base = derived ;
    delete base;   // Êä³ö Calling ~Base()
 
    return 0;
}