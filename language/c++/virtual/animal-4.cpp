#include <iostream>

class Animal
{
    public:
        virtual void eat()  { std::cout << "I'm eating generic food." << std::endl; }  // 使用关键字virtual声明为虚函数
};

class Cat : public Animal
{
    public:
        virtual void eat() { std::cout << "I'm eating a rat." << std::endl; }
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
	func(cat);    // Outputs: "I'm eating a rat."   // OK

	delete animal;
	delete cat;
	return 0;
}
