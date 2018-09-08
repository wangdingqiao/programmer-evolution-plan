#include <iostream>
using namespace std;

class A
{

public:

A(int m,int n)
    :m(m),
    n(n)
{
    cout<<"constructor A"<<m<<n;
};

~A()
{

};

private:

int m;
int n;

};


class B : public A

{

public:

B()
    :b(5) //error : default constructor to initialize A is not found
{
    cout<<"constructor B"<<b;
}

~B()
{};

private:

int b;

};


int main()
{

B x;
return 0;

};

/*
 error C2512: “A”: 没有合适的默认构造函数可用
*/