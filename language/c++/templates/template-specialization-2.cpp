#include <iostream>
#include <cstring>

template <typename T>
int compare(const T& v1, const T& v2)
{
	if(v1 < v2) return -1;
	if(v2 < v1) return 1;
	return 0;
}

// 为compare模板 编写字符串类型特化的版本 因为使用通用版本产生错误
template<> 
int compare<const char*>(const char* const &v1, const char* const &v2)
{
	std::cout << "using specialization version." << std::endl;
	return strcmp(v1, v2); // 使用C风格字符串版本
}

int main(int argc, char *argv[])
{
	const char * ch1 = "abcdef";
	const char * ch2 = "ABCDEF";
	
	std::cout << compare(ch1, ch2) << std::endl;
	return 0;
}
