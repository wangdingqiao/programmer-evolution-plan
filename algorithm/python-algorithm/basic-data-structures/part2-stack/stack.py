#!/usr/bin/python
# -*- coding: UTF-8 -*-


class Stack(object):

    """
    栈
    """

    def __init__(self):
        self.data = []

    def __str__(self):
        output_str = "stack["
        for x in self.data:
            output_str += str(x)+","
        output_str += "]"
        return output_str

    def is_empty(self):
        return not self.data

    def push(self, element):
        self.data.append(element)

    def pop(self):
        return self.data.pop()

    def top(self):
        if self.data:
            return self.data[-1]
        else:
            return None

    def size(self):
        return len(self.data)


if __name__ == "__main__":
    stack = Stack()
    stack.push(5)
    stack.push(4)
    stack.push(3)
    print(stack)
    print("top:", stack.top())
    while not stack.is_empty():
        print("pop:", stack.top())
        stack.pop()
        print(stack)
    stack.push(6)
    print(stack)