#include <iostream>
#include <string>

int compare(const int& v1, const int& v2)
{
	if(v1 < v2) return -1;
	if(v2 < v1) return 1;
	return 0;
}

int compare(const std::string& v1, const std::string& v2)
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
	
	return 0;
}
