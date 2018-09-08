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

// 引用计数类
class RC
{
public:
	RC():referenceCount(0) {}
	void incRef() {++referenceCount;}
	int decRef() {return --referenceCount;}
	int getRefCount() const {return referenceCount;}
private:
	int referenceCount;
};

// 使用模板 支持泛型
template <typename T>
class SP
{
public:
	// 默认构造函数
	SP() :pData(NULL), ref(NULL)
	{
		ref = new RC();
		ref->incRef();
	}

    // 带参数构造函数
	SP(T* pValue): pData(pValue), ref(NULL)
	{
		ref = new RC();
		ref->incRef();
	}
	~SP()
	{
		if(ref->decRef() == 0)
		{
			delete pData;
			pData = NULL;
			delete ref;
			ref = NULL;
		}
	}
public:
	// 拷贝构造函数 注意参数书写方式
	SP(const SP<T>& other):pData(other.pData), ref(other.ref)
	{
		ref->incRef(); // 共享同一份引用计数 在此基础上加1
	}
	// 拷贝赋值操作符
	SP<T>& operator = (const SP<T>& other)
	{
		if(this != &other)
		{
			if(ref->decRef() == 0)  // 赋值时 先对原对象引用计数减1 如果减为0了则应该释放原对象
			{
				delete pData;
				pData = NULL;
				delete ref;
				ref = NULL;
			}
			pData = other.pData;
			ref = other.ref;
			ref->incRef();
		}
		return *this;
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
	RC* ref; // 这个引用计数必须是动态分配的 同一个对象的多个智能指针之间共享同一份计数
};

int main(int argc, char *argv[])
{
	SP<Person> p(new Person("Jack", 20));
	p->display();
	{
		SP<Person> q = p;
		q->display();

		SP<Person> r;
		r = p;
		r->display();
	}
	p->display(); // OK
	p = NULL; // OK
	return 0;
}
