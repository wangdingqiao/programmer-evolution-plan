#include <iostream>

class String
{
public:
	// 构造函数
	explicit String(const char * p=NULL):data(NULL)
	{
		if (p)
		{
			std::size_t len = strlen(p);
			data = new char[len + 1];
			strcpy_s(data, len + 1, p);
		}
	}
	// 析构函数
	~String()
	{
		std::cout << "destructor called." << std::endl;
		if(data)
		{
			delete[] data;
			data = NULL;
		}
	}
	// 复制构造函数和赋值操作符 此处省略
public:
	const char* c_str() const {return data;}
private:
    char* data;
};

int main(int argc, char *argv[])
{
	String foo("foo");
	std::cout << foo.c_str() << std::endl;
	return 0;
}

/*
When is destructor called?
A destructor function is called automatically when the object goes out of scope:
(1) the function ends
(2) the program ends
(3) a block containing local variables ends
(4) a delete operator is called 
*/