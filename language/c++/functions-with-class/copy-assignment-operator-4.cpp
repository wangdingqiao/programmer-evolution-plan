#include <iostream>
#include <string>
#include <algorithm>

class String
{
public:
	// 构造函数
	explicit String(const char * p = NULL) :_data(NULL)
	{
		std::cout << "Default constructor called." << std::endl;
		if (p)
		{
			std::size_t len = strlen(p);
			_data = new char[len + 1];
			strcpy_s(_data, len + 1, p); // strcpy_s(char *restrict dest, rsize_t destsz, const char *restrict src);
		}
	}
	// 析构函数
	virtual ~String()
	{
		std::cout << "Destructor called." << std::endl;
		if (_data)
		{
			delete[] _data;
			_data = NULL;
		}
	}
public:
	// 拷贝构造函数
	String(const String& other) :_data(NULL)
	{
		std::cout << "Copy constructor called." << std::endl;
		std::size_t len = other.size();
		if (len > 0)
		{
			_data = new char[len + 1];
			strcpy_s(_data, len + 1, other.c_str());
		}
	}
public:
	// 复制赋值操作符
	String& operator= (String other)
	{
		std::cout << "Copy assignment operator called." << std::endl;
		swap(*this, other);
		return *this;
	}
	friend void swap(String& one, String& other)
	{
		using std::swap;
		swap(one._data, other._data);
	}
public:
	std::size_t size() const { return _data ? strlen(_data) : 0; }
	const char* c_str() const { return _data; }
private:
	char* _data;
};

int main(int argc, char *argv[])
{
	String first("Hello"); // 默认构造函数
	{
		String copy;
		copy = first; // 赋值操作符
		std::cout << first.c_str() << " " << copy.c_str() << std::endl;
	} // 两次析构函数

	String copy(first); // 拷贝构造函数
	std::cout << first.c_str() << " " << copy.c_str() << std::endl;
	return 0;
}

/*
Default constructor called.
Default constructor called.
Copy constructor called.
Copy assignment operator called.
Destructor called.
Hello Hello
Destructor called.
Copy constructor called.
Hello Hello
Destructor called.
Destructor called.
*/