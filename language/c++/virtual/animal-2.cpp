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

// 定义一个函数 使用父类指针 调用对象的方法
void func(Animal *xyz) 
{ 
	xyz->eat(); 
}

int main(int argc, char *argv[])
{
	Animal *animal = new Animal;
	Cat *cat = new Cat;

	func(animal); // Outputs: "I'm eating generic food."
	func(cat);    // Outputs: "I'm eating generic food."  这里出错了

	delete animal;
	delete cat;
	return 0;
}
