#include <iostream>
class Date
{
private:
    int m_year = 1900;
    int m_month = 1;
    int m_day = 1;
 
public:
    Date(int year, int month, int day) // normal non-default constructor
    {
        m_year = year;
        m_month = month;
        m_day = day;
    }
 
    // No implicit constructor provided because we already defined our own constructor
};
 
int main()
{
    Date date; // error: Can't instantiate object because default constructor doesn't exist and the compiler won't generate one
    Date today(2020, 10, 14); // today is initialized to Oct 14th, 2020
 
    return 0;
}

/*
 error C2512: “Date”: 没有合适的默认构造函数可用
*/