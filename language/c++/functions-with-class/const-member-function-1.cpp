#include <iostream>

class MyClass
{
private:
    int counter;
public:
    void Foo()
    { 
        std::cout << "Foo" << std::endl;    
    }

    void Foo() const
    {
        std::cout << "Foo const" << std::endl;
    }

};

int main()
{
    MyClass cc;
    const MyClass& ccc = cc;
    cc.Foo();
    ccc.Foo();
}

/*
Foo
Foo const
*/