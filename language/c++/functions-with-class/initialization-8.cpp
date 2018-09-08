#include <iostream>
 
class Fraction
{
private:
    int m_numerator;
    int m_denominator;
 
public:
    Fraction(int numerator=0, int denominator=1):m_numerator(numerator), m_denominator(denominator) // directly initialize our member variables using member initializer lists
    {
         // No need for assignment here
    }
 
    int getNumerator() { return m_numerator; }
    int getDenominator() { return m_denominator; }
    double getValue() { return static_cast<double>(m_numerator) / m_denominator; }
};
 
int main()
{
    Fraction frac; // Since no arguments, calls Fraction() default constructor
    std::cout << frac.getNumerator() << "/" << frac.getDenominator() << '\n';
 
    return 0;
}