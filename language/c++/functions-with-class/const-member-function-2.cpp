#include <iostream>

class MyClass
{
private:
    int counter;
public:
   void Foo()
    {
        counter++; //this works
        std::cout << "Foo" << std::endl;    
    }

    void Foo() const
    {
        counter++; //this will not compile  error C3490: 由于正在通过常量对象访问“counter”，因此无法对其进行修改
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