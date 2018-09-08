#include <iostream>
using namespace std;

void swap_1(int *r, int *s) // not work
{
    int *pSwap = r;
    r = s;
    s = pSwap;
}

void swap_2(int *r, int *s) // work
{
    int pSwap = *r;
    *r = *s;
    *s = pSwap;
}

void swap_3(int& r, int& s) // work
{
    int pSwap = r;
    r = s;
    s = pSwap;
}

void swap_string(char**p, char** q)
{
	char * tmp = *p;
	*p = *q;
	*q = tmp;
}

int main()
{
    int p = 7;
    int q = 9;
	cout << "brfore p = " << p << " q= " << q << endl;
    swap_1(&p, &q);
    cout << "after p = " << p << " q= " << q << endl;
	cout << endl;

	p = 7;
    q = 9;
	cout << "brfore p = " << p << " q= " << q << endl;
    swap_2(&p, &q);
    cout << "after p = " << p << " q= " << q << endl;
    cout << endl;

	p = 7;
    q = 9;
	cout << "brfore p = " << p << " q= " << q << endl;
    swap_3(p, q);
    cout << "after p = " << p << " q= " << q << endl;
	cout << endl;

	char* x = "Hello";
    char* y = "world";
	cout << "brfore x = " << x << " y= " << y << endl;
    swap_string(&x, &y);
    cout << "after x = " << x << " y= " << y << endl;

    return 0;
}