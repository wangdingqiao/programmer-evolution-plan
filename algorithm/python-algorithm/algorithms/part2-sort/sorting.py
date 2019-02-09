#!/usr/env/python
# _*_ coding: utf-8 _*_

"""
排序算法
reference-link:
[1]-https://sites.fas.harvard.edu/~cscie119/lectures/sorting.pdf
[2]-https://en.wikipedia.org/wiki/Shellsort
[3]-https://en.wikipedia.org/wiki/Quicksort
[4]-https://www.geeksforgeeks.org/hoares-vs-lomuto-partition-scheme-quicksort/
[5]-http://personal.kent.edu/%7Ermuhamma/Algorithms/MyAlgorithms/Sorting/countingSort.htm
[6]-https://stackoverflow.com/questions/9755721/how-can-building-a-heap-be-on-time-complexity
"""


class Sort(object):

    @staticmethod
    def __compare__(left, right, descend_order=False):
        if descend_order:
            return left > right
        else:
            return left < right

    @staticmethod
    def __index_of_selection__(input_array, start, end, descend_order=False):
        if not input_array:
            raise ValueError("input array is empty.")
        if start < 0 \
                or end < 0 \
                or start > end \
                or end >= len(input_array):
            raise AssertionError("invalid array index", start, end)
        select_index = start
        for i in range(start + 1, end + 1):
            if descend_order:  # 降序排列时选择次大元素
                if input_array[i] > input_array[select_index]:
                    select_index = i
            elif input_array[i] < input_array[select_index]:
                select_index = i
        return select_index

    @staticmethod
    def __swap_element__(input_array, index_left, index_right):
        if not input_array:
            raise ValueError("input array is empty.")
        if index_left < 0 \
                or index_right < 0 \
                or index_left >= len(input_array) \
                or index_right >= len(input_array):
            raise AssertionError("invalid array index", index_left, index_right)
        if index_left != index_right:
            input_array[index_left], input_array[index_right] = input_array[index_right], input_array[index_left]

    @staticmethod
    def selection_sort(input_array, descend_order=False):
        """
        选择排序
        依次从[0,len-1]位置选择应该放在这个位置上的那个最小或者最大元素
        :param input_array: 输入元素列表
        :param descend_order: 是否降序排列
        :return: None
        """
        for i in range(len(input_array) - 1):
            select_index = Sort.__index_of_selection__(input_array, i, len(input_array) - 1, descend_order)
            if i != select_index:
                Sort.__swap_element__(input_array, i, select_index)
            # print("after ", i + 1, " select: ", input_array)

    @staticmethod
    def insertion_sort(input_array, descend_order=False):
        """
        插入排序
        依次将[1,len-1]位置元素插入在适当位置
        :param input_array: 输入元素列表
        :param descend_order: 是否降序排列
        :return: None
        """
        for i in range(1, len(input_array)):
            to_insert = input_array[i]
            j = i
            if descend_order:
                while j > 0 and input_array[j - 1] < to_insert:  # 降序排列时小于插入元素的部分后移
                    input_array[j] = input_array[j - 1]
                    j -= 1
            else:
                while j > 0 and input_array[j - 1] > to_insert:  # 升序排列时大于插入元素的部分后移
                    input_array[j] = input_array[j - 1]
                    j -= 1
            if j != i:
                input_array[j] = to_insert
            print("after ", i, " insert: ", input_array)

    @staticmethod
    def shell_sort(input_array, descend_order=False):
        """
        希尔排序
        对插入排序的改进
        根据间隔序列(gap sequence)对子数组进行插入排序 间隔序列对排序效率有影响
        :param input_array: 输入元素列表
        :param descend_order: 是否降序排列
        :return: None
        """
        gap_seq = [701, 301, 132, 57, 23, 10, 4, 1]  # Using Marcin Ciura's gap sequence
        # gap_seq = [5, 3, 1]
        for gap in gap_seq:
            for i in range(gap, len(input_array)):
                to_insert = input_array[i]
                j = i
                if descend_order:
                    while j >= gap and input_array[j - gap] < to_insert:  # 降序排列时小于插入元素的部分后移
                        input_array[j] = input_array[j - gap]
                        j -= gap
                else:
                    while j >= gap and input_array[j - gap] > to_insert:  # 升序排列时大于插入元素的部分后移
                        input_array[j] = input_array[j - gap]
                        j -= gap
                if j != i:
                    input_array[j] = to_insert
            print('after gap= ', gap, " array is: ", input_array)

    @staticmethod
    def bubble_sort(input_array, descend_order=False):
        """
        冒泡排序
        进行len-1趟冒泡过程，每次将最大(或者最小元素)交换到最终位置
        :param input_array: 输入元素列表
        :param descend_order: 是否降序排列
        :return: None
        """
        for j in range(len(input_array) - 1, 0, -1):
            for i in range(j):
                if descend_order:  # 降序排列时 将最小元素交换到最终位置
                    if input_array[i] < input_array[i + 1]:
                        Sort.__swap_element__(input_array, i, i + 1)
                else:  # 升序排列时 将最大元素交换到最终位置
                    if input_array[i] > input_array[i + 1]:
                        Sort.__swap_element__(input_array, i, i + 1)

    @staticmethod
    def lomuto_partition(input_array, low, high, descend_order=False):
        """
        lomuto划分算法
        :param input_array: 输入数组
        :param low: 开始索引
        :param high: 停止索引
        :param descend_order: 是否降序排列
        :return: pivot所在索引pos 划分区间为[low, pos-1] [ pos+1, high]
        """
        if low < 0 \
                or high < 0 \
                or low >= len(input_array) \
                or high >= len(input_array):
            raise AssertionError("invalid array index", low, high)
        pivot = input_array[high]  # right most element as pivot
        print('lomuto partition choosing pivot= ', pivot)
        i = low - 1  # marker for element less than pivot
        for j in range(low, high):
            if descend_order:  # 降序排列 大的元素在左 小的元素在右
                if input_array[j] > pivot:
                    i += 1
                    Sort.__swap_element__(input_array, i, j)
            else:  # 升序排列 大的元素在右 小的元素在左
                if input_array[j] < pivot:
                    i += 1
                    Sort.__swap_element__(input_array, i, j)
        Sort.__swap_element__(input_array, i + 1, high)
        return i + 1

    @staticmethod
    def hoare_partition(input_array, low, high, descend_order=False):
        if low < 0 \
                or high < 0 \
                or low >= len(input_array) \
                or high >= len(input_array):
            raise AssertionError("invalid array index", low, high)
        pivot = input_array[(low + high) / 2]
        print('hoare partition choosing pivot= ', pivot)
        while True:
            if descend_order:  # 降序排列 大的元素在左 小的元素在右
                while low < high and input_array[low] > pivot:
                    low += 1
                while low < high and input_array[high] < pivot:
                    high -= 1
            else:  # 升序排列 大的元素在右 小的元素在左
                while low < high and input_array[low] < pivot:
                    low += 1
                while low < high and input_array[high] > pivot:
                    high -= 1
            if low < high:
                Sort.__swap_element__(input_array, low, high)
                low += 1
                high -= 1
            else:
                return high

    @staticmethod
    def quick_sort_lomuto(input_array, descend_order=False):
        """
        快速排序
        :param input_array: 输入元素列表
        :param descend_order: 是否降序排列
        :return: None
        """

        def quick_sort(in_array, low, high):
            pos = Sort.lomuto_partition(in_array, low, high, descend_order)
            if low < pos - 1:
                quick_sort(in_array, low, pos - 1)
            if pos + 1 < high:
                quick_sort(in_array, pos + 1, high)

        if input_array:
            quick_sort(input_array, 0, len(input_array) - 1)

    @staticmethod
    def quick_sort_hoare(input_array, descend_order=False):
        """
        快速排序
        :param input_array: 输入元素列表
        :param descend_order: 是否降序排列
        :return: None
        """

        def quick_sort(in_array, low, high):
            pos = Sort.hoare_partition(in_array, low, high, descend_order)
            if low < pos:
                quick_sort(in_array, low, pos)
            if pos + 1 < high:
                quick_sort(in_array, pos + 1, high)

        if input_array:
            quick_sort(input_array, 0, len(input_array) - 1)

    @staticmethod
    def merge_sort(input_array, descend_order=False):
        """
        归并排序
        需要和数组大小等长的辅助空间
        先划分数组然后合并子数组
        :param input_array: 输入元素列表
        :param descend_order: 是否降序排列
        :return: None
        """

        def merge_array(array, left_start, left_end, right_start, right_end, descend=False):
            result_array = [None for _ in range(right_end - left_start)]  # 辅助空间
            i, j, k = left_start, right_start, 0
            while i <= left_end and j <= right_end:
                if Sort.__compare__(array[i], array[j], descend):
                    result_array[k] = array[i]
                    i += 1
                else:
                    result_array[k] = array[j]
                    j += 1
                k += 1
            if i <= left_end:
                result_array[k:] = array[i:left_end + 1]
            if j <= right_end:
                result_array[k:] = array[j:right_end + 1]
            array[left_start:right_end + 1] = result_array

        def split_and_merge(array, start, end, desc_order=False):
            if end <= start:
                return
            mid = (start + end) / 2
            left_start, left_end = start, mid
            right_start, right_end = mid + 1, end
            split_and_merge(array, left_start, left_end, descend_order)
            split_and_merge(array, right_start, right_end, descend_order)
            print("merge: ", array[left_start:left_end + 1], array[right_start:right_end + 1])
            merge_array(array, left_start, left_end, right_start, right_end, desc_order)

        split_and_merge(input_array, 0, len(input_array) - 1, descend_order)

    @staticmethod
    def count_sort(input_array, descend_order=False):
        """
           计数排序 尚不支持负数排序
           根据输入数据计算出一个max_num大小的辅助计数数组 升序时计数数组中位置i存放数值<=i的元素个数
           根据元素的计数值 决定它在排序后数组中的位置
           :param input_array: 输入元素列表
           :param descend_order: 是否降序排列
           :return: None
        """
        def value_to_pos(value, max_value, desc_order=False):
            if desc_order:
                return max_value - value
            else:
                return value
        if not input_array:
            return
        max_num = max(input_array)
        count_array = [0 for _ in range(max_num+1)]
        out_array = [None for _ in range(len(input_array))]
        for x in input_array:
            pos = value_to_pos(x, max_num, desc_order=descend_order)
            count_array[pos] += 1
        for i in range(1, len(count_array)):
            count_array[i] += count_array[i-1]
        for x in input_array:
            pos = value_to_pos(x, max_num, desc_order=descend_order)
            out_array[count_array[pos] - 1] = x
            count_array[pos] -= 1
        for i in range(len(input_array)):
            input_array[i] = out_array[i]

    @staticmethod
    def radix_sort(input_array, descend_order=False):
        """
        基数排序 尚不支持负数排序
        对元素的每个位进行基本排序(计数、快排等) 最后结果即有序
        :param input_array: 输入元素列表
        :param descend_order: 是否降序排列
        :return: None
        """
        def count_sort(array, exp, desc_order=False):

            def value_to_pos(value, max_value, des_order=False):
                if des_order:
                    return max_value - (value / exp) % 10   # 利用exp计算对应位的数值 例如170 计算十位则为7
                else:
                    return (value / exp) % 10

            if not array:
                return
            max_num = 9
            count_array = [0 for _ in range(max_num + 1)]
            out_array = [None for _ in range(len(array))]
            for x in array:
                pos = value_to_pos(x, max_num, des_order=desc_order)
                count_array[pos] += 1
            for i in range(1, len(count_array)):
                count_array[i] += count_array[i - 1]
            for i in range(len(array)-1, -1, -1):    # 注意这里逆序填入辅助数组 保证原数组中先出现元素在小的索引位置
                pos = value_to_pos(array[i], max_num, des_order=descend_order)
                out_array[count_array[pos] - 1] = array[i]
                count_array[pos] -= 1
            for i in range(len(array)):
                array[i] = out_array[i]

        if not input_array:
            return
        exp_value = 1  # 用于计算数值对应位的数字 使用least significant digit(LSD)策略
        max_number = max(input_array)
        while max_number / exp_value > 0:
            count_sort(input_array, exp_value, descend_order)
            print(("after sort by %d's digit: " % exp_value), input_array)
            exp_value *= 10

    @staticmethod
    def heap_sort(input_array, descend_order=False):
        """
        堆排序
        根据升序构造大顶堆(降序则小顶堆) 依次将堆顶元素输出到数组尾部则排序完成
        :param input_array: 输入元素列表
        :param descend_order: 是否降序排列
        :return: None
        """

        class BinaryHeap(object):
            """
            二叉堆
            """
            def __init__(self, input_data, heap_type="max"):
                self.data = input_data
                if heap_type == "max":
                    self.cmp_func = lambda x, y: x > y
                else:
                    self.cmp_func = lambda x, y: x < y
                self.heapify()

            @staticmethod
            def get_parent_index(i):
                return (i - 1) / 2

            @staticmethod
            def get_left_index(i):
                return 2 * i + 1

            @staticmethod
            def get_right_index(i):
                return 2 * i + 2

            def heapify(self):
                if not self.data:
                    return
                start = (len(self.data) - 1) / 2
                for i in range(start, -1, -1):
                    self.sift_down(i, len(self.data) - 1)

            def sift_down(self, start, end):
                """
                将start开始的节点持续与[start, end]范围内孩子节点比较 如果违背堆性质则交换节点 否则算法停止
                :param start: 开始元素索引
                :param end: 停止元素索引
                :return: None
                """
                if start >= end:
                    return
                left, right = BinaryHeap.get_left_index(start), BinaryHeap.get_right_index(start)
                while left <= end or right <= end:
                    child_index = left
                    if right <= end:
                        if self.cmp_func(self.data[right], self.data[child_index]):
                            child_index = right
                    if self.cmp_func(self.data[child_index], self.data[start]):
                        self.data[start], self.data[child_index] = self.data[child_index], self.data[start]
                        start = child_index
                        left, right = BinaryHeap.get_left_index(start), BinaryHeap.get_right_index(start)
                    else:
                        break

            def sort(self):
                for i in range(len(self.data)-1, 0, -1):
                    self.data[i], self.data[0] = self.data[0], self.data[i]
                    if i - 1 > 0:
                        self.sift_down(0, i - 1)
                    print(('after choose the %dth element: ' % i), self.data)

        if not input_array:
            return
        h_type = (descend_order and "min") or "max"
        binary_heap = BinaryHeap(input_array, heap_type=h_type)
        binary_heap.sort()

    @staticmethod
    def bucket_sort(input_array, descend_order=False):
        """
        桶排序 distribution sort
        构造n个桶，将元素按照一定规则装入桶中；对每个桶里元素进行排序，最后将各个桶中元素汇总得到最终排序结果
        :param input_array: 输入元素列表
        :param descend_order: 是否降序排列
        :return: None
        """
        def value_to_bucket_index(value):
            return int(value * 10)

        # build the buckets
        buckets = [[] for _ in range(10)]
        for x in input_array:
            pos = value_to_bucket_index(x)
            buckets[pos].append(x)
        # sort the buckets
        for i in range(len(buckets)):
            Sort.selection_sort(buckets[i], descend_order)
            print(("the %dth bucket elements are: " % i), buckets[i])
        # concatenate the buckets
        start = 0
        concatenate_range = (descend_order and range(len(buckets)-1, -1, -1)) or range(len(buckets))
        for i in concatenate_range:
            if not buckets:
                continue
            input_array[start:start + len(buckets[i])] = buckets[i]
            start += len(buckets[i])


def test_case_1():
    input_array = [15, 6, 2, 12, 4]
    array_copy = [x for x in input_array]
    print("input array is: ", input_array)

    Sort.selection_sort(input_array)
    print(" selection sort by ascending order: ", input_array)
    Sort.selection_sort(array_copy, descend_order=True)
    print(" selection sort by descending order: ", array_copy)


def test_case_2():
    input_array = [12, 5, 2, 13, 18, 4]
    array_copy = [x for x in input_array]
    print("input array is: ", input_array)

    Sort.insertion_sort(input_array)
    print(" insertion sort by ascending order: ", input_array)
    Sort.insertion_sort(array_copy, descend_order=True)
    print(" insertion sort by descending order: ", array_copy)


def test_case_3():
    input_array = [62, 83, 18, 53, 07, 17, 95, 86, 47, 69, 25, 28]
    array_copy = [x for x in input_array]
    print("input array is: ", input_array)

    Sort.shell_sort(input_array)
    print(" shell sort by ascending order: ", input_array)
    Sort.shell_sort(array_copy, descend_order=True)
    print(" shell sort by descending order: ", array_copy)


def test_case_4():
    input_array = [28, 24, 27, 18]
    array_copy = [x for x in input_array]
    print("input array is: ", input_array)

    Sort.bubble_sort(input_array)
    print(" bubble sort by ascending order: ", input_array)
    Sort.bubble_sort(array_copy, descend_order=True)
    print(" bubble sort by descending order: ", array_copy)


def test_case_5():
    input_arrays = [[10, 80, 30, 90, 40, 50, 70], [7, 15, 4, 9, 6, 18, 9, 12]]
    for input_array in input_arrays:
        array_copy = [x for x in input_array]
        print("input array is: ", input_array)

        pos = Sort.lomuto_partition(input_array, 0, len(input_array) - 1)
        print(" lomuto partition by ascending order: ", input_array, "split= ", pos)

        pos = Sort.lomuto_partition(array_copy, 0, len(array_copy) - 1, descend_order=True)
        print(" lomuto partition by descending order: ", array_copy, "split= ", pos)
        print("\n")


def test_case_6():
    input_arrays = [[10, 80, 30, 90, 40, 50, 70], [7, 15, 4, 9, 6, 18, 9, 12]]
    for input_array in input_arrays:
        array_copy = [x for x in input_array]
        print("input array is: ", input_array)

        pos = Sort.hoare_partition(input_array, 0, len(input_array) - 1)
        print(" hoare partition by ascending order: ", input_array, "split= ", pos)

        pos = Sort.hoare_partition(array_copy, 0, len(array_copy) - 1, descend_order=True)
        print(" hoare partition by descending order: ", array_copy, "split= ", pos)
        print("\n")


def test_case_7():
    input_arrays = [[10, 80, 30, 90, 40, 50, 70], [7, 15, 4, 9, 6, 18, 9, 12]]
    for input_array in input_arrays:
        array_copy = [x for x in input_array]
        print("input array is: ", input_array)

        Sort.quick_sort_lomuto(input_array)
        print(" quick sort lomuto partition by ascending order: ", input_array)

        Sort.quick_sort_lomuto(array_copy, descend_order=True)
        print(" quick sort lomuto partition by descending order: ", array_copy)
        print("\n")


def test_case_8():
    input_arrays = [[10, 80, 30, 90, 40, 50, 70], [7, 15, 4, 9, 6, 18, 9, 12]]
    for input_array in input_arrays:
        # array_copy = [x for x in input_array]
        print("input array is: ", input_array)

        Sort.quick_sort_hoare(input_array)
        print(" quick sort hoare partition by ascending order: ", input_array)

        # Sort.quick_sort_hoare(array_copy, descend_order=True)
        # print(" quick sort hoare partition by descending order: ", array_copy)
        print("\n")


def test_case_9():
    input_array = [12, 8, 14, 4, 6, 33, 2, 27]
    array_copy = [x for x in input_array]
    print("input array is: ", input_array)

    Sort.merge_sort(input_array)
    print(" merge sort by ascending order: ", input_array)
    Sort.merge_sort(array_copy, descend_order=True)
    print(" merge sort by descending order: ", array_copy)


def test_case_10():
    input_array = [3, 6, 4, 1, 3, 4, 1, 4]
    array_copy = [x for x in input_array]
    print("input array is: ", input_array)

    Sort.count_sort(input_array)
    print(" count sort by ascending order: ", input_array)
    Sort.count_sort(array_copy, descend_order=True)
    print(" count sort by descending order: ", array_copy)


def test_case_11():
    input_array = [2, 24, 45, 66, 75, 90, 170, 802]
    array_copy = [x for x in input_array]
    print("input array is: ", input_array)

    Sort.radix_sort(input_array)
    print(" radix sort by ascending order: ", input_array)
    Sort.radix_sort(array_copy, descend_order=True)
    print(" radix sort by descending order: ", array_copy)


def test_case_12():
    input_array = [15, 19, 10, 7, 17, 16, 7, 10]
    array_copy = [x for x in input_array]
    print("input array is: ", input_array)

    Sort.heap_sort(input_array)
    print(" heap sort by ascending order: ", input_array)
    Sort.heap_sort(array_copy, descend_order=True)
    print(" heap sort by descending order: ", array_copy)


def test_case_13():
    input_arrays = [[0.897, 0.565, 0.656, 0.1234, 0.665, 0.3434],
                    [0.78, 0.17, 0.39, 0.26, 0.72, 0.94, 0.21, 0.12, 0.23, 0.68]]
    for input_array in input_arrays:
        array_copy = [x for x in input_array]
        print("input array is: ", input_array)

        Sort.bucket_sort(input_array)
        print(" bucket sort by ascending order: ", input_array)
        Sort.bucket_sort(array_copy, descend_order=True)
        print(" bucket sort by descending order: ", array_copy)
        print("\n")


if __name__ == "__main__":
    test_case_13()
