#include <iostream>

class Array {
public:
    int size;
    int* data;

    explicit Array(int sz) 
        : size(sz), data(new int[size])
    {
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
    }    // (1) 这里执行了析构 因为使用了编译器合成的拷贝构造函数  执行的是浅拷贝  data指向的空间将被释放

    first.data[0] = 10;    // (2) 空间以被释放 这里非法内存访问
}