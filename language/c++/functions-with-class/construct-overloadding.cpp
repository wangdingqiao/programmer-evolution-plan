#include <iostream>
#include <cassert>
 
class Fraction
{
private:
    int m_numerator;
    int m_denominator;
 
public:
    Fraction() // default constructor
    {
         m_numerator = 0;
         m_denominator = 1;
    }
 
    // Constructor with two parameters, one parameter having a default value
    Fraction(int numerator, int denominator=1)
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
	// 构造函数重载 一个默认 一个带参数的
    Fraction frac; // Since no arguments, calls Fraction() default constructor
	Fraction fiveThirds(5, 3); // Direct initialize a Fraction, calls Fraction(int, int) constructor
    std::cout << frac.getNumerator() << "/" << frac.getDenominator() << '\n';
	std::cout << fiveThirds.getNumerator() << "/" << fiveThirds.getDenominator() << '\n';

    return 0;
}