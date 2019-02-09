#!/usr/bin/python
# -*- coding: UTF-8 -*-


def factorial(x):
    if x <= 1:
        return 1
    else:
        return x * factorial(x-1)


if __name__ == "__main__":
    ret = factorial(5)
    print(ret)
