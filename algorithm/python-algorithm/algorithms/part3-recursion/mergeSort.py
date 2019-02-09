#!/usr/env/python
# _*_ coding: utf-8 _*_

"""
使用递归的归并排序
算法步骤:
1.将输入数组分为左右两部分, 依次对左右两部分进行归并排序
2.将左右两部分归并排序结果合并后得到左右两部分的有序结果

12 8 14 4 6 33 2 27
split: 12 8 14 4 , 6 33 2 27
split: 12 8,	14 4,	6 33,	2 27
split: 12,8  14,4   6, 33	2,27
merge: 8,12  4,14  	6,33 	2,27
merge: 4,8,12,14   2,6,27,33
merge:2,4,6,8,12,14,27,33
"""


class Solution(object):

    @staticmethod
    def merge_sort(input_array):
        return Solution.__merge_sort__(input_array, 0, len(input_array) - 1)

    @staticmethod
    def __merge_sort__(array, start, end):
        if end > start:
            mid = (start + end) / 2
            print("split:", array[start:mid+1], array[mid+1:end+1])
            Solution.__merge_sort__(array, start, mid)
            Solution.__merge_sort__(array, mid + 1, end)
            Solution.merge_array(array, start, mid, mid + 1, end)

    @staticmethod
    def merge_array(input_array, low, high, start, end):
        """
        合并数组
        :param input_array: 输入数组
        :param low: 第一部分开始索引
        :param high:第一部分终止索引
        :param start: 第二部分开始索引
        :param end: 第二部分终止索引
        :return: 合并后的数组
        """
        print("merge:", input_array[low:high+1], input_array[start:end+1])
        length = end - low + 1
        temp_array = [None for _ in range(length)]
        i = 0
        left, right = low, start
        while left <= high and right <= end:
            if input_array[left] < input_array[right]:
                temp_array[i] = input_array[left]
                left += 1
            else:
                temp_array[i] = input_array[right]
                right += 1
            i += 1
        while left <= high:
            temp_array[i] = input_array[left]
            left += 1
            i += 1
        while right <= end:
            temp_array[i] = input_array[right]
            right += 1
            i += 1
        i = 0
        for j in range(low, end + 1):
            input_array[j] = temp_array[i]
            i += 1


if __name__ == "__main__":
    test_array = [12, 8, 14, 4, 6, 33, 2, 27]
    print("before sort: ", test_array)
    Solution.merge_sort(test_array)
    print("after sort:", test_array)  