#include <iostream>
 
class Base
{
public:
    int m_id;
 
    Base(int id=0)
        : m_id(id)
    {
        std::cout << "call Base constructor.\n";
    }
 
    int getId() const { return m_id; }
};
 
class Derived: public Base
{
public:
    double m_cost;
 
    Derived(double cost=0.0)
        : m_cost(cost)
    {
        std::cout << "call Derived constructor.\n";
    }
 
    double getCost() const { return m_cost; }
};
 
int main()
{
    std::cout << "Instantiating Base\n";
    Base cBase;
    
    std::cout << "\nInstantiating Derived\n";
    Derived cDerived;
 
    return 0;
}