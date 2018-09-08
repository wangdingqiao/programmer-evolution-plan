#include <iostream>
using namespace std;
 
class A
{

public:
A(int m,int n):m(m),n(n)
{
    cout<<"constructor A "<<m << "," <<n << endl;
};

~A(){};

private:

int m;
int n;

};


class B : public A
{

public:

B():A(3,4),b(5)
{
    cout<<"constructor B "<<b<< endl;
}

~B(){};

private:

int b;

};


int main()
{
    B b;
    return 0;
}

/*
Êä³ö:
constructor A 3,4
constructor B 5
*/