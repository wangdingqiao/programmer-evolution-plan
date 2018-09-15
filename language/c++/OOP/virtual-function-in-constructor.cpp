#include <iostream>
using namespace std; 
  
class dog 
{ 
public: 
    dog()  
    { 
        cout<< "Constructor called" <<endl; 
        bark() ; 
    } 
  
    ~dog() 
    {  
        bark();  
    } 
  
    virtual void bark() 
    {  
        cout<< "Virtual method called" <<endl;  
    } 
  
    void seeCat()  
    {  
        bark();  
    } 
}; 
  
class Yellowdog : public dog 
{ 
public: 
        Yellowdog()  
        { 
            cout<< "Derived class Constructor called" <<endl;  
        } 
        void bark()  
        { 
            cout<< "Derived class Virtual method called" <<endl;  
        } 
}; 
  
int main() 
{ 
    Yellowdog d; 
    d.seeCat(); 
} 

/*
Constructor called     // 先调用dog基类构造函数
Virtual method called  // 构造函数中调用的虚函数是继承树中当前层的虚函数版本 就是dog类的版本
Derived class Constructor called    // 子类构造函数调用
Derived class Virtual method called // 调用父类的seeCat函数 其中的虚函数实际上未发生多态调用  调用的就是d类型的版本
Virtual method called  // 析构函数中调用虚函数  就是继承树中当前层的虚函数版本 也就是dog类的版本。
 */