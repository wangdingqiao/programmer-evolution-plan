#include <iostream>
using namespace std;

class Empty {};

int main()
{
	Empty e;
	cout << sizeof(e) << endl; // 1
	return 0;
}