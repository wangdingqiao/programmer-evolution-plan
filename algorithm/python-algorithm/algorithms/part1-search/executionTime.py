#!/usr/env/python
# _*_ coding: utf-8 _*_

import time


def time_usage(func):
    def wrapper(*args, **kwargs):
        beg_ts = time.time()
        ret_val = func(*args, **kwargs)
        end_ts = time.time()
        print("elapsed time: %f ms" % (end_ts - beg_ts))
        return ret_val
    return wrapper


@time_usage
def test():
    total_sum = 0
    for i in xrange(0, 1000000):
        total_sum += i * i


if __name__ == "__main__":
    test()