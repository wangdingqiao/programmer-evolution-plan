#include <iostream>

template <typename T>
int compare(const T& v1, const T& v2)
{
	if(v1 < v2) return -1;
	if(v2 < v1) return 1;
	return 0;
}

int main(int argc, char *argv[])
{
	std::cout << compare(1,3) << std::endl;
	std::string s1("abcdef"), s2("ABCDEF");
	std::cout << compare(s1, s2) << std::endl;
	std::cout << compare("abcdef", "ABCDEF") << std::endl;
    std::cout << compare("abcdefgh", "ABCDEFGH") << std::endl;

	return 0;
}
