#!/usr/env/python
# _*_ coding: utf-8 _*_

import csv
import random
import timeit
import matplotlib.pyplot as plt


"""
参考
[1]-https://cs.brynmawr.edu/Courses/cs110/fall2011/section01/slides/09_Searching.pdf
[2]-https://github.com/dwyl/english-words
"""


def linear_search(input_array, x):
    """
    线性查找
    :param input_array: 输入元素数组
    :param x: 待查找元素
    :return: 查找成功时返回元素所在索引，失败时返回-1
    """
    for i, element in enumerate(input_array):
        if element == x:
            return i
    return -1


def binary_search_by_iteration(input_sort_array, x):
    """
    二分查找 迭代版本
    :param input_sort_array: 输入有序数组
    :param x: 待查找元素
    :return: 查找成功时返回元素所在索引，失败时返回-1
    """
    if not input_sort_array:
        return -1
    low, high = 0, len(input_sort_array) - 1
    while low <= high:
        mid = (low + high) / 2
        if input_sort_array[mid] == x:
            return mid
        elif input_sort_array[mid] > x:
            high = mid - 1
        else:
            low = mid + 1
    return -1


def binary_search_by_recursion(input_sort_array, x):
    """
    二分查找  递归版本
    :param input_sort_array: 输入有序数组
    :param x: 待查找元素
    :return: 查找成功时返回元素所在索引，失败时返回-1
    """

    def __binary_search__(input_array, x, start, end):
        if not input_array or start > end:
            return -1
        mid = (start + end)
        if input_array[mid] == x:
            return mid
        elif input_array[mid] > x:
            return __binary_search__(input_array, x, start, mid - 1)
        else:
            return __binary_search__(input_array, x, mid + 1, end)
    return __binary_search__(input_sort_array, x, 0, len(input_sort_array) - 1)


def test_case_1():
    input_array = [chr(x) for x in range(65, 65 + 25)]
    print('input array is: ', input_array)
    search_items = ['j', 'J', '#']
    print('------------using linear search--------------')
    for x in search_items:
        print("index of: ", x, " is: ", linear_search(input_array, x))
    print('------------using binary search iteration--------------')
    for x in search_items:
        print("index of: ", x, " is: ", binary_search_by_iteration(input_array, x))
    print('------------using binary search recursion--------------')
    for x in search_items:
        print("index of: ", x, " is: ", binary_search_by_recursion(input_array, x))


def test_case_2():
    input_array = [0, 1, 2, 8, 13, 17, 19, 32, 42]
    print('input array is: ', input_array)
    search_items = [3, 13]
    print('------------using linear search--------------')
    for x in search_items:
        print("index of: ", x, " is: ", linear_search(input_array, x))
    print('------------using binary search iteration--------------')
    for x in search_items:
        print("index of: ", x, " is: ", binary_search_by_iteration(input_array, x))
    print('------------using binary search recursion--------------')
    for x in search_items:
        print("index of: ", x, " is: ", binary_search_by_recursion(input_array, x))


def test_case_3():
    words = []
    with open("words.csv", "rU") as f:
        reader = csv.reader(f)
        for row in reader:
            words.append(row[0])
    words.sort()
    search_type_2_func = {"linear": linear_search,
                          "binary-iteration": binary_search_by_iteration}
    search_type_2_used_times = {k: [] for k, v in search_type_2_func.items()}
    array_size = [1000, 5000, 10000, 20000, 50000, 100000, 150000, 200000, 250000, 300000, 350000]
    for search_type, func in search_type_2_func.items():
        for test_size in array_size:
            input_array = words[:test_size]
            search_items = [input_array[i] for i in random.sample(xrange(len(input_array)), 1000)]
            start_time = timeit.default_timer()
            for x in search_items:
                func(input_array, x)
            elapsed = (timeit.default_timer() - start_time) / len(search_items) * 1000000
            print("finished search array size: ", len(input_array), " with method: ",
                  search_type, " used time: ", elapsed, " us")
            search_type_2_used_times[search_type].append(elapsed)

    # show graph
    plt.title("Execution time of search element")
    plt.xlabel('Array size', fontsize=12)
    plt.ylabel('Search time(us)', fontsize=12)
    # Plot the data
    for search_type, times in search_type_2_used_times.items():
        plt.plot(array_size, times, label=search_type)

    # Add a legend
    plt.legend()

    # Show the plot
    plt.show()


if __name__ == "__main__":
    test_case_1()