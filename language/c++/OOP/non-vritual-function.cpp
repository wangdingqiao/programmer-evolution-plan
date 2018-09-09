#include <iostream>

class Shape
{
public:
	void toString() const
	{
		std::cout << "Shape::toString called." << std::endl;
	}
	virtual void calcArea() const
	{
		std::cout << "Shape::calcArea called." << std::endl;
	}
};


class Circle: public Shape
{

public:
	 void toString() const
	{
		std::cout << "Circle::toString called." << std::endl;
	}
	void calcArea() const
	{
		std::cout << "Circle::calcArea called." << std::endl;
	}
};

void foo(const Shape& shape)
{
	shape.toString();
	shape.calcArea();
}

int main()
{
	Shape shape;
	Circle circle;
	foo(shape);
	std::cout << "---------" << std::endl;
	foo(circle);

	std::cout << "Press any key to exit." << std::endl;
	char waitKey;
	std::cin >> waitKey;
}

/*
Shape::toString called.
Shape::calcArea called.
---------
Shape::toString called.
Circle::calcArea called.
Press any key to exit.

 */