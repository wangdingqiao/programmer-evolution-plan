#include <iostream>
#include <ctime>
class X 
{
public:
	inline int aplusb_pow2(int a, int b) 
	{
		return (a + b)*(a + b) ;
	}
};

int main()
{
	clock_t begin = std::clock();
	X x;
	const int MAX_NUM = 100000;
	for(int a = 0; a < MAX_NUM; ++a)
		for(int b = 0; b < MAX_NUM; ++b)
			x.aplusb_pow2(a, b);

	clock_t end = std::clock();
    double elapsed_secs = double(end - begin) / CLOCKS_PER_SEC;
	std::cout << " with inline, eplapsed seconds: " << elapsed_secs << std::endl;
	return 0;
}
