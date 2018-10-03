#include <iostream>
#include <cstdlib>
#include <iterator>
#include <algorithm>


// 第二个版本 模板支持泛型
template<typename T>
int binary_search(T* array, int size, T value)
{
	if(!array || size <= 0)
		return -1;
	int low = 0, high = size -1;
	while(low <= high)
	{
		int mid = (low + high) / 2;
		if(*(array + mid) == value)
			return mid;
		else if(*(array + mid) < value)
			low = mid + 1;
		else
			high = mid - 1;
	}
	return -1;
}



int main()
{
	int inputs[] = {1, 3, 4, 7, 9, 12, 14, 17};
	int toFinds[] = {6, 8, 12, 15, 17};

	std::cout << "search array is: ";
    std::ostream_iterator<int> out_it (std::cout,", ");
    std::copy (inputs, inputs + sizeof(inputs) / sizeof(*inputs), out_it);
    std::cout << std::endl;

	for(int i = 0; i < sizeof(toFinds)/ sizeof(*toFinds); ++i)
	{
		std::cout << "search value=" << toFinds[i] << ", result pos= " 
		<< binary_search(inputs, sizeof(inputs) / sizeof(*inputs), toFinds[i]) << std::endl;
	}
	char waitKey;
	std::cout << "Enter any key to exit.";
	std::cin >> waitKey;
	return 0;
}