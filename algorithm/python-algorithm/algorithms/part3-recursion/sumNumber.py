#!/usr/env/python
# _*_ coding: utf-8 _*_

"""
iterate and recursion of sum method
"""


class Solution(object):

    @staticmethod
    def sum_by_iteration(n):
        total = 0
        for x in range(1, n+1):
            total += x
        return total

    @staticmethod
    def sum_by_recursion(n):
        if n <= 1:
            return n
        else:
            return n + Solution.sum_by_recursion(n - 1)


if __name__ == "__main__":
    sum_to = [0, 10, 100]
    for x in sum_to:
        print("sum to %i by iterate, result= %i" % (x, Solution.sum_by_iteration(x)))
        print("sum to %i by recursion, result= %i" % (x, Solution.sum_by_recursion(x)))

