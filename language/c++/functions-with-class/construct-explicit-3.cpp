#include <iostream>

class String {
public:
    String(int n){ std::cout<< "allocate " << n << " bytes." << std::endl;} // allocate n bytes to the String object
    String(const char *p){std::cout<< "initializes using char pointer." << std::endl;} // initializes object with char *p
};

int main()
{
   String mystring = 'x'; // 愿意为初始化为字符'x' 却错误地初始化为120大小的字符缓冲区了
   return 0;
}

/*
输出:
allocate 120 bytes.

声明构造函数为explicit
class String {
public:
    explicit String (int n); //allocate n bytes
    String(const char *p); // initialize sobject with string p
};
*/