#include <iostream>
#include <cstdlib>
#include <iterator>
#include <algorithm>


// 第三个版本 支持范围输入
template<typename T>
T* binary_search(T* start, T* end , T value)
{
	if(!start || start == end)
		return end;
	T* low = start, *high = end -1;
	while(*low <= *high)		// 这里执行解引用 会消耗额外代价 需要改进
	{
		T* mid = low + (high - low) / 2;
		if(*mid == value)
			return mid;
		else if(*mid < value)
			low = mid + 1;
		else
			high = mid - 1;
	}
	return end;
}



int main()
{
	int inputs[] = {1, 3, 4, 7, 9, 12, 14, 17};
	int toFinds[] = {6, 8, 12, 15, 17};

	std::cout << "search array is: ";
    std::ostream_iterator<int> out_it (std::cout,", ");
    std::copy (inputs, inputs + sizeof(inputs) / sizeof(*inputs), out_it);
    std::cout << std::endl;

    int * start = inputs, *end = inputs + sizeof(inputs) / sizeof(*inputs);
	for(int i = 0; i < sizeof(toFinds)/ sizeof(*toFinds); ++i)
	{
		int * pos = binary_search(start, end, toFinds[i]);
		std::cout << "search value=" << toFinds[i] << ", result pos= " 
				  << (pos != end ? std::distance(inputs,pos) : -1) << std::endl;
	}
	char waitKey;
	std::cout << "Enter any key to exit.";
	std::cin >> waitKey;
	return 0;
}