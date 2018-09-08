#include <iostream>

class Animal
{
    public:
        void eat() { std::cout << "I'm eating generic food." << std::endl; }
};

class Cat : public Animal
{
    public:
        void eat() { std::cout << "I'm eating a rat." << std::endl; }
};

// 为每种类型定义一个函数 用于操作这种类型对象

void func_a(Animal *xyz) 
{ 
	xyz->eat(); 
}

void func_c(Cat *xyz) 
{ 
	xyz->eat(); 
}

int main(int argc, char *argv[])
{
	Animal *animal = new Animal;
	Cat *cat = new Cat;

	func_a(animal); // Outputs: "I'm eating generic food."
	func_c(cat);    // Outputs: "I'm eating a rat."   // OK 但是当有多种类型时  你就需要定义多个func_xxx 十分麻烦

	delete animal;
	delete cat;
	return 0;
}
