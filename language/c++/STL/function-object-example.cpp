#include <iostream>
#include <vector>
#include <iterator>
#include <algorithm>

struct AddX
{
	AddX(const int x):x(x){}
	int operator()(const int y) const { return x + y;}
private:
	int x;
};

int main()
{
	AddX add42(42);
	std::cout << add42(8) << std::endl;

	std::vector<int> foo;
    std::vector<int> bar;
    for (int i=1; i<6; i++)
    	foo.push_back (i*10);

    bar.resize(foo.size());

    std::transform(foo.begin(), foo.end(), bar.begin(), AddX(1));

    std::ostream_iterator<int> out_it (std::cout,", ");
    std::copy ( bar.begin(), bar.end(), out_it);   
}