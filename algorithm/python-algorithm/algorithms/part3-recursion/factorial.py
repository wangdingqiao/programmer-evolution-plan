#!/usr/env/python
# _*_ coding: utf-8 _*_

"""
阶乘调用堆栈:
factorial(5)
   factorial(4)
      factorial(3)
         factorial(2)
            factorial(1)
               return 1
            return 2*1 = 2
         return 3*2 = 6
      return 4*6 = 24
   return 5*24 = 120

"""


class Solution(object):
    @staticmethod
    def factorial(n):
        if n <= 1:
            return 1
        else:
            return n * Solution.factorial(n-1)


if __name__ == "__main__":
    print(Solution.factorial(5))