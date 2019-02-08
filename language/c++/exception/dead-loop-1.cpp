#include <vector>
#include <iostream>

int main(void)
{
    std::vector<int> x(10);

    for (unsigned int i = 9; i >= 0; i--)
    {
        std::cout << "i= " << i << std::endl;
        x[i] = 2 * i;
    }
    std::cout << "x0= " << x[0] << std::endl;
    return 0;
}