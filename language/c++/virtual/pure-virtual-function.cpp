#include <iostream>
class Shape
{
public:
    virtual  double calcArea(){} //虚函数
    virtual  double calcPerimeter()=0;//纯虚函数
};

class IShape
{
public:
    virtual double calcArea()=0//计算面积
    virtual double calcPerimeter()=0//计算周长
};

/*
如果在抽象类当中仅含有纯虚函数而不含其他任何东西，我们称之为接口类。

    1.没有任何数据成员
    2.仅有成员函数
    3.成员函数都是纯虚函数
*/

class Flyable//会飞
{
public:
    virtual void takeoff()=0;//起飞
    virtual void land()=0;//降落
}；
class Bird:public Flyable
{
public:
    virtual void takeoff(){}
    virtual void land(){}
};
void flyMatch(Flyable *a,Flyable *b)//飞行比赛
//要求传入一个会飞对象的指针，此时鸟类的对象指针可以传入进来
{
    a->takeoff();
    b->takeoff();
    a->land();
    b->land();
}