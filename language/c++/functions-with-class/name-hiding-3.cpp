#include<iostream>

using namespace std;

class ParentClass {
public: 
     virtual void someFunc(int a){
        std::cout << "ParentClass::someFunc(int)" << std::endl;
    };

    virtual void someFunc(char a){
        std::cout << "ParentClass::someFunc(char)" << std::endl;
    };
};

class ChildClass : public ParentClass {
public:
    virtual void someFunc(char a){
         std::cout << "ChildClass::someFunc(char)" << std::endl;
    };
};

int main(){
    ChildClass obj;
    obj.someFunc(7);
	ParentClass* p = &obj;
	p->someFunc(7);
	p->someFunc('7');
}

/*
ChildClass::someFunc(char)
ParentClass::someFunc(int)
ChildClass::someFunc(char)
*/