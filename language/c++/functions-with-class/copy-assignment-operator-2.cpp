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
    // 注意这里是值传递 不是引用形式
	Array& operator=(Array other)
	{
		std::cout << "Array operator= called." << std::endl;
		swap(*this, other);
		return *this;
	}
	
	friend void swap(Array& one, Array& other)
	{
		using std::swap;
		swap(one.size, other.size);
		swap(one.data, other.data);
	}
};
 
int main() 
{
    Array first(20);
    first.data[0] = 25;

    {
        Array copy(0);
		copy = first;
        std::cout << first.data[0] << " " << copy.data[0] << std::endl;
    }    // (1) 这里执行了析构 使用了自定义的赋值操作符  执行的是深拷贝  两个对象的存储空间独立

    first.data[0] = 10;    // OK
}

/*
输出:
Array operator= called.
Array desctructor called.
25 25
Array desctructor called.
Array desctructor called.
*/