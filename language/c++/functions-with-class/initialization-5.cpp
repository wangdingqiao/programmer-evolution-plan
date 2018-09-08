#include <iostream>
#include <cassert>
 
class Fraction
{
private:
    int m_numerator;
    int m_denominator;
 
public:
    // Default constructor 这个也称之为默认构造函数 可以不需要参数就调用
    Fraction(int numerator=0, int denominator=1)
    {
        assert(denominator != 0);
        m_numerator = numerator;
        m_denominator = denominator;
    }
 
    int getNumerator() { return m_numerator; }
    int getDenominator() { return m_denominator; }
    double getValue() { return static_cast<double>(m_numerator) / m_denominator; }
};


int main()
{
	Fraction zero; // will call Fraction(0, 1)
	Fraction six(6); // will call Fraction(6, 1)
	Fraction fiveThirds(5,3); // will call Fraction(5, 3)
    std::cout << six.getNumerator() << "/" << six.getDenominator() << '\n';
 
    return 0;
}