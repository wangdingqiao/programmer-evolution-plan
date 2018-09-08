#include <algorithm>

template<typename T, typename U> Pair<T,U>::Pair(const T& t, const U& u)
:first(t), second(u)
{

}

template<typename T, typename U>  Pair<T,U>::Pair(const Pair& p)
: first(p.first), second(p.second) 
{

}

template<typename T, typename U> Pair<T,U>&  Pair<T,U>::operator=(Pair p)
{
	swap(*this, p);
	return *this;
}

template<typename T, typename U> void  Pair<T,U>::swap(Pair<T,U>& p1, Pair<T,U>& p2)
{
	using std::swap;
	std::swap(p1.first, p2.first);
	std::swap(p1.second, p2.second);
}

template<typename T, typename U>
std::ostream& operator << (std::ostream& stream, const Pair<T, U>& p)
{
	return stream << "(" << p.first << "," << p.second << ")";
}