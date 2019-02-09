#!/usr/bin/python
# -*- coding: UTF-8 -*-

from binaryTree import BinaryTree
from GraphVisual import GraphVisualization


class BinaryHeap(object):
    """
        二叉堆
        基于数组实现 堆元素索引从0开始
    """

    @staticmethod
    def compare_less(x, y):
        return x < y

    @staticmethod
    def compare_greater(x, y):
        return x > y

    MIN_COMPARE_FUNC = compare_less
    MAX_COMPARE_FUNC = compare_greater

    def __init__(self, heap_type="min", init_data=None):
        self.data = []
        self.heap_type = heap_type
        if not init_data:
            return
        if type(init_data) is not list:
            raise ValueError("Init with list of data only.")
        self.data = init_data
        for adjust_index in range(len(self.data) / 2 - 1, -1, -1):
            BinaryHeap.__move__down(self.heap_type, self.data, adjust_index)

    def find_min(self):
        """
            获取最小元素 大顶堆不支持该操作
        """
        if self.heap_type != "min":
            raise NotImplementedError("unsupported operation, not a min heap")
        return (self.data and self.data[0]) or None

    def find_max(self):
        """
            获取最大元素 小顶堆不支持该操作
        """
        if self.heap_type != "max":
            raise NotImplementedError("unsupported operation, not a max heap")
        return (self.data and self.data[0]) or None

    def add(self, val):
        self.data.append(val)
        BinaryHeap.__move__up__(self.heap_type, self.data, len(self.data) - 1)

    @staticmethod
    def __move__up__(heap_type, data_array, start_index):
        """
        自底向上调整节点
        1.Compare the added element with its parent; if they are in the correct order, stop.
        2.If not, swap the element with its parent and return to the previous step.
        :param heap_type: 堆类型
        :param data_array: 输入数组
        :param start_index: 开始索引
        :return: None
        """
        if heap_type == "min":
            compare_func = BinaryHeap.MIN_COMPARE_FUNC
        else:
            compare_func = BinaryHeap.MAX_COMPARE_FUNC
        if start_index > len(data_array):
            raise KeyError(" index = " + str(start_index) + " exceed array index, with length= " + str(len(data_array)))
        cur_index = start_index
        parent_index = BinaryHeap.get_parent_index(cur_index)
        while cur_index > 0 and not compare_func(data_array[parent_index], data_array[cur_index]):
            data_array[parent_index], data_array[cur_index] = data_array[cur_index], data_array[parent_index]
            cur_index = parent_index
            parent_index = BinaryHeap.get_parent_index(cur_index)

    def remove_min(self):
        """
        移除最小元素 大顶堆不支持该操作
        :return: 如果存在则返回最小元素
        """
        if self.heap_type != "min":
            raise NotImplementedError("unsupported operation, not a min heap.")
        return self.__remove_root__()

    def remove_max(self):
        """
        移除最大元素 小顶堆不支持该操作
        :return: 如果存在则返回最大元素
        """
        if self.heap_type != "max":
            raise NotImplementedError("unsupported operation, not a max heap.")
        return self.__remove_root__()

    def __remove_root__(self):
        if self.is_empty():
            return None
        if len(self.data) == 1:
            return self.data.pop(-1)
        min_data = self.data[0]
        self.data[0] = self.data.pop(-1)  # 堆顶和最后一个元素交换 然后开始调整
        BinaryHeap.__move__down(self.heap_type, self.data,0)
        return min_data

    @staticmethod
    def __move__down(heap_type, data_array, start_index, end_index=None):
        """
        自顶向下调整
        1.Compare the new root with its children; if they are in the correct order, stop.
        2.If not, swap the element with one of its children and return to the previous step.
        (Swap with its smaller child in a min-heap and its larger child in a max-heap.)
        :param heap_type: 堆类型
        :param data_array: 输入数组
        :param start_index: 开始索引
        :return:None
        """
        if not end_index:
            end_index = len(data_array)
        if heap_type == "min":
            compare_func = BinaryHeap.MIN_COMPARE_FUNC
        else:
            compare_func = BinaryHeap.MAX_COMPARE_FUNC
        cur_index = start_index
        left_child_index, right_child_index = \
            BinaryHeap.get_left_child_index(cur_index), BinaryHeap.get_right_child_index(cur_index)
        while left_child_index < end_index:
            the_proper_index = left_child_index
            if right_child_index < end_index and compare_func(
                    data_array[right_child_index], data_array[left_child_index]):
                the_proper_index = right_child_index
            if compare_func(data_array[cur_index], data_array[the_proper_index]):
                break
            data_array[cur_index], data_array[the_proper_index] = data_array[the_proper_index], data_array[cur_index]
            cur_index = the_proper_index
            left_child_index, right_child_index = \
                BinaryHeap.get_left_child_index(cur_index), BinaryHeap.get_right_child_index(cur_index)

    @staticmethod
    def sort(data_array, order="Desc"):
        heap_type = (order == "Desc" and "min") or "max"
        # 建立堆方式1 从最后一个节点父节点开始
        start_index = BinaryHeap.get_parent_index(len(data_array) - 1)
        for adjust_index in range(start_index, -1, -1):
            BinaryHeap.__move__down(heap_type, data_array, adjust_index)
        # 建立堆方式2 每个节点都向上调整
        # for i in range(len(input_data)):
        #     BinaryHeap.__move__up__(heap_type, input_data, i)
        bt = BinaryTree(data_array)
        node_text_map, edges = bt.get_show_info()
        GraphVisualization.show(node_text_map, edges, view_graph=True)
        # 依次与第i个元素交换 然后调整
        for i in range(len(data_array)-1, 0, -1):
            data_array[i], data_array[0] = data_array[0], data_array[i]
            BinaryHeap.__move__down(heap_type, data_array, 0, i)
        return data_array

    def is_empty(self):
        return not self.data

    def __str__(self):
        ret_str = self.heap_type + " heap["
        for x in self.data:
            ret_str += str(x) + ","
        ret_str += "]"
        return ret_str

    def __repr__(self):
        self.__str__()

    @staticmethod
    def get_left_child_index(cur_index):
        return cur_index * 2 + 1

    @staticmethod
    def get_right_child_index(cur_index):
        return cur_index * 2 + 2

    @staticmethod
    def get_parent_index(cur_index):
        return (cur_index - 1) / 2


def test_min_heap():
    print('\ntest min heap' + '*'*20)
    input_data = [35, 33, 42, 10, 14, 19, 27, 44, 26, 31]
    print('input data:', input_data)
    bh = BinaryHeap("min", input_data)
    print('heap data', bh.data)
    print('heap min', bh.find_min())
    bh.remove_min()
    print('after remove min', bh.data)
    bh.add(23)
    print('after insert element 23', bh.data)


def test_max_heap():
    print('\ntest max heap' + '*'*20)
    input_data = [35, 33, 42, 10, 14, 19, 27, 44, 26, 31]
    print('input data:', input_data)
    bh = BinaryHeap("max", input_data)
    print('heap data', bh.data)
    print('heap max', bh.find_max())
    bh.remove_max()
    print('after remove max', bh.data)
    bh.add(23)
    print('after insert element 23', bh.data)


def test_heap_sort():
    print('\ntest heap sort' + '*'*20)
    input_data = [2, 8, 6, 1, 10, 15, 3, 12, 11, 49, 38, 65, 97, 76, 13, 27]
    print('before sort:', input_data)
    BinaryHeap.sort(input_data, order="Desc")
    print('after sort by Descend order: ', input_data)
    BinaryHeap.sort(input_data, order="Asce")
    print('after sort by Ascending order: ', input_data)


if __name__ == "__main__":
    test_min_heap()
    test_max_heap()
    test_heap_sort()