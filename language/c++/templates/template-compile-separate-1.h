#ifndef _TEMPLATE_EXAMPLE_PAIR_H_
#define _TEMPLATE_EXAMPLE_PAIR_H_

#include <iostream>

template<typename T, typename U> struct Pair;
template<typename T, typename U>
std::ostream& operator << (std::ostream& stream, const Pair<T, U>& p);

template<typename T, typename U>
struct Pair
{
	T first;
	U second;
	Pair(const T& t, const U& u);
	Pair(const Pair& p);
	Pair& operator=(Pair p);
	void swap(Pair<T,U>& p1, Pair<T,U>& p2);
	friend std::ostream& operator << <T, U>(std::ostream& stream, const Pair<T, U>& p);
};

#include "template-compile-separate-1.hpp"
#endif