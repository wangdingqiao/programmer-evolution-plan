#include <iostream>
#include <algorithm>
#include <string>

template<typename T, typename U>
struct Pair
{
	T first;
	U second;
	Pair(const T& t, const U& u):first(t), second(u){}
	Pair(const Pair& p): first(p.first), second(p.second) {}
	Pair& operator=(Pair p)
	{
		swap(*this, p);
		return *this;
	}
	void swap(Pair<T,U>& p1, Pair<T,U>& p2)
	{
		using std::swap;
		std::swap(p1.first, p2.first);
		std::swap(p1.second, p2.second);
	}
	friend std::ostream& operator << (std::ostream& stream, const Pair<T,U>& p)
	{
		return stream << "(" << p.first << "," << p.second << ")";
	}
};

int main()
{
	Pair<int,int> p1(3,4);
	std::cout << "p1:" << p1 << std::endl;

	{
		Pair<int,int> p2(p1);
		std::cout << "p2:" << p2 << std::endl;
	}
	std::cout << "p1:" << p1 << std::endl;

	{
		Pair<int,int> p3(0,0);
		p3 = p1;
		std::cout << "p3:" << p3 << std::endl;
	}
	std::cout << "p1:" << p1 << std::endl;

	Pair<int ,std::string> p4(3,"Important");
	std::cout << "p4:" << p4 << std::endl;
}
