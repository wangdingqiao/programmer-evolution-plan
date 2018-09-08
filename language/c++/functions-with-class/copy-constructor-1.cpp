#include<iostream>
using namespace std;
 
class Point
{
private:
    int x, y;
public:
    Point(int x1, int y1) { x = x1; y = y1; }
 
    int getX()            {  return x; }
    int getY()            {  return y; }
	void setX(const int x) { this->x = x; }
	void setY(const int y) { this->y = y; }
};
 
int main()
{
    Point p1(10, 15); // Normal constructor is called here
    Point p2 = p1; // using implicit Copy constructor
	p1.setX(20);
    // Let us access values assigned by constructors
    cout << "p1.x = " << p1.getX() << ", p1.y = " << p1.getY();
    cout << "\np2.x = " << p2.getX() << ", p2.y = " << p2.getY();
 
    return 0;
}