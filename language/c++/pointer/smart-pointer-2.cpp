#include <iostream>

class Person
{
public:
	Person(char* name, int age):age(age)
	{
		if(name)
		{
			std::size_t len = strlen(name);
			pName = new char[len+1];
			strcpy(pName, name);
			pName[len] = '\0';
		}
	}
	Person():pName(NULL), age(0) {}
	~Person() 
	{
		if(pName)
		{
			delete[] pName;
			pName = NULL;
		}
	}
public:
	void display()
	{
		std::cout << "Name= " << pName << " Age= " << age << std::endl;
	}
private:
	int age;
	char *pName;
};


// 使用模板 支持泛型
template <typename T>
class SP
{
public:
	SP(T* pValue): pData(pValue){}
	~SP()
	{
		delete pData;
		pData = NULL;
	}
public:
	// 重载解引用操作符
	T& operator *()
	{
		return *pData;
	}
	// 重载箭头运算符
	T* operator->()
	{
		return pData;
	}
private:
	T* pData;
};

int main(int argc, char *argv[])
{
	SP<Person> p(new Person("Jack", 20));
	p->display();
	{
		SP<Person> q = p;
		q->display();
	}
	p->display(); // 非法访问  因为q已经释放了 指向同一个对象的内存
	return 0;
}
