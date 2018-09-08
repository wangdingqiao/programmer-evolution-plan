#include <iostream>
#include <algorithm> // for std::copy

class Array {
public:
    int size;
    int* data;

    explicit Array(int sz) 
        : size(sz), data(new int[size])
    {
    }
	// 自定义的拷贝构造函数
	Array(const Array& other):size(other.size), data(new int[other.size])
	{
		std::copy(other.data, other.data + other.size, data);
	}

    ~Array() 
    {
        if (data != NULL) 
		{
			delete[] this->data;
			data = NULL;
			std::cout << "Array desctructor called." << std::endl;
		}
    }
};
 
int main() 
{
    Array first(20);
    first.data[0] = 25;

    {
        Array copy = first;
        std::cout << first.data[0] << " " << copy.data[0] << std::endl;
    }    // (1) 这里执行了析构 因为使用了自定义的拷贝构造函数  执行的是深拷贝  两个对象数据空间互不干扰

    first.data[0] = 10;    // OK
}