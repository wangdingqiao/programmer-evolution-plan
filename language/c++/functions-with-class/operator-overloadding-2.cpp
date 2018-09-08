#include<iostream>
using namespace std;
 
class Complex {
private:
    int real, imag;
public:
    Complex(int r = 0, int i =0)  {real = r;   imag = i;}
     
    // This is automatically called when '+' is used with
    // between two Complex objects
    Complex operator + (Complex const &obj) {
         Complex res;
         res.real = real + obj.real;
         res.imag = imag + obj.imag;
         return res;
    }
	friend std::ostream& operator << (std::ostream& stream, const Complex& x);
};

std::ostream& operator << (std::ostream& stream, const Complex& x)
{
	stream << x.real << " + " << x.imag << "i" << endl;
	return stream;
} 

int main()
{
    Complex c1(10, 5), c2(2, 4);
    Complex c3 = c1 + c2; // An example call to "operator+"
    std::cout << c3; // call operator <<
}