#!/usr/bin/env python
# encoding: utf-8

import sys
from DebugHelper import *


def factorial(x):
    if x <= 1:
        return 1
    else:
        return x * factorial(x-1)


def main():
    print(factorial(5))
    return 1


if __name__ == "__main__":
    DebugHelper.init()
    sys.settrace(DebugHelper.trace_calls)
    main()