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


int main(int argc, char *argv[])
{
	Animal *animal = new Animal;
	Cat *cat = new Cat;

	animal->eat(); // Outputs: "I'm eating generic food."
	cat->eat();    // Outputs: "I'm eating a rat."

	delete animal;
	delete cat;
	return 0;
}

// Windows visual studio Брвы
// H:\cpp-summary\virtual>cl -EHsc animal-5.cpp