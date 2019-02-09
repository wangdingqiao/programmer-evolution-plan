#!/usr/env/python
# _*_ coding: utf-8 _*_

"""
It consists of three rods and a number of disks of different sizes,
which can slide onto any rod. The puzzle starts with
the disks in a neat stack in ascending order of size on one rod,
the smallest at the top, thus making a conical shape.
The objective of the puzzle is to move the entire stack to another rod,
obeying the following simple rules:
    1.Only one disk can be moved at a time.
    2.Each move consists of taking the upper disk from one of the stacks and placing it on top of another stack.
    3.No disk may be placed on top of a smaller disk.
"""


class Solution(object):
    @staticmethod
    def move_tower(n, src, dst, tmp):
        if n > 0:
            Solution.move_tower(n-1, src, tmp, dst)
            Solution.move_one_tower(n, src, dst)
            Solution.move_tower(n-1, tmp, dst, src)

    @staticmethod
    def move_one_tower(n, src, dst):
        print("move disk-%i ,from %i to %i" % (n, src, dst))


if __name__ == "__main__":
    Solution.move_tower(3, 1, 3, 2)