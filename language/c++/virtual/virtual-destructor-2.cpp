#include <iostream>
#include <vector>

class Base
{
public:
    ~Base() // note: not virtual
    {
       
    }
};
 
class Derived: public Base
{
private:
    int* m_array;
 
public:
    Derived(int length)
    {
        m_array = new int[length];
    }
 
    ~Derived() // note: not virtual
    {
        delete[] m_array;
    }
}; 

class ObjManager
{
public:
	void makeObj(const int objCount)
	{
		for(int i = 1; i <= objCount ; ++i)
		{
			seqBasePtr.push_back(new Derived(i));
		}
	}
	void releaseObj()
	{
		for(SeqBasePtr::const_iterator it = seqBasePtr.begin(); it != seqBasePtr.end();++it)
		{
			Base * base = *it;
			if(base)
			{
				delete base; // µ¼ÖÂÄÚ´æÐ¹Â¶
			}
		}
		seqBasePtr.clear();
	}
	~ObjManager()
	{
		releaseObj();
	}
private:
	typedef std::vector<Base*> SeqBasePtr;
	SeqBasePtr seqBasePtr;
};
int main()
{
	const int objCount = 10000;
	ObjManager objManager;
	objManager.makeObj(objCount);
	std::cout << "allocate " << objCount << " objects finished, press key to continue:" << std::endl;
	char waitkey;
	std::cin >> waitkey;
	objManager.releaseObj();
	std::cout << "deallocate object finished, press key to continue:" << std::endl;
	std::cin >> waitkey;
    return 0;
}