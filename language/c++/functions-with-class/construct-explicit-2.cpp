#include <iostream>

class Foo
{
public:
    explicit Foo( int x ) { _val = x; }  // 声明为显式构造函数
public:
	int getVal() const {return _val;}
private:
    int _val;
};
 
void doSomething( const Foo &foo )
{
	std::cout << "foo dosomething with val= " << foo.getVal() << std::endl;
}

int main()
{
   doSomething( 10 ); // Error!
   doSomething( Foo( 10 ) ); // OK
   return 0;
}

/*
construct-explicit-2.cpp(20) : error C2664: “void doSomething(const Foo &)”: 无法将参数 1 从“int”转换为“const Foo &”
        原因如下:  无法从“int”转换为“const Foo”
        class“Foo”的构造函数声明为“explicit”
*/