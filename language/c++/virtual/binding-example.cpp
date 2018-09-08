#include <iostream>
#include <memory>

typedef int(*FuncType)(int, int);

int add(int a, int b) { return a + b; }
int substract(int a, int b) { return a - b; }

struct Function {
    virtual ~Function() {}
    virtual int doit(int, int) const = 0;
};
struct Add: Function {
    virtual int doit(int a, int b) const override { return a + b; } 
};
struct Substract: Function {
    virtual int doit(int a, int b) const override { return a - b; } 
};


int main()
{
	std::cout << "static binding: " << add(4, 5) << std::endl;
	
	char op = 0;
	std::cin >> op;
	FuncType const funcPtr = (op == '+' ? &add : &substract);
	std::cout << "dynamic binding using function pointer: " << funcPtr(4,5) << std::endl;

	std::unique_ptr<Function> func = (op == '+' ? std::unique_ptr<Function>{new Add{}}
                  : std::unique_ptr<Function>{new Substract{}});

    std::cout << "dynamic binding using virtual function: " << func->doit(4, 5) << "\n";
}

/*
static binding: 9
-
dynamic binding using function pointer: -1
dynamic binding using virtual function: -1
[What is early (static) and late (dynamic) binding in C++?](https://stackoverflow.com/questions/18035293/what-is-early-static-and-late-dynamic-binding-in-c)
*/