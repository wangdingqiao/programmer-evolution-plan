#include <iostream>

class Foo
{
public:
    Foo( int x ) { _val = x; }
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
   doSomething( 10 ); // Acts like doSomething( Foo( 10 ) ); 进行了隐式转换 构造了Foo对象 这个可能违背了愿意 无意中导致错误
   return 0;
}
