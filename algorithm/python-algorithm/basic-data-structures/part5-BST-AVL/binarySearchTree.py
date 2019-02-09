#!/usr/bin/python
# -*- coding: UTF-8 -*-
import GraphVisual
from binaryTree import BinaryTree
from binaryTree import TreeNode


class BinarySearchTree(BinaryTree):

    """
        二叉搜索树
    """

    def __init__(self):
        self.root = None

    def __init__(self, data_array):
        if type(data_array) is not list:
            raise ValueError('init with data array only')
        self.root = None
        for x in data_array:
            self.add(x)

    def add(self, value):
        if not self.root:
            self.root = TreeNode(value)
            return True
        else:
            return BinarySearchTree.__add_value(self.root, value)

    @staticmethod
    def __add_value(node, value):
        if not node:
            return False
        if node.data == value:
            return False       # duplicate
        elif value < node.data:
            if node.left:
                return BinarySearchTree.__add_value(node.left, value)
            else:
                node.left = TreeNode(value)
        else:
            if node.right:
                return BinarySearchTree.__add_value(node.right, value)
            else:
                node.right = TreeNode(value)
        return True

    def find(self, value):
        return BinarySearchTree.__find__(self.root, value)

    @staticmethod
    def __find__(node, value):
        if not node:
            return False
        if value == node.data:
            return True
        elif value < node.data:
            return BinarySearchTree.__find__(node.left, value)
        else:
            return BinarySearchTree.__find__(node.right, value)

    def remove_by_recursion(self, value):
        if not self.root:
            return False
        elif value == self.root.data:
            temp_root = TreeNode()      # 创建一个临时节点 方便处理
            temp_root.left = self.root
            ret = BinarySearchTree.__remove__(temp_root.left, temp_root, value)
            self.root = temp_root.left
            return ret
        elif value < self.root.data and self.root.left:
            return BinarySearchTree.__remove__(self.root.left, self.root, value)
        elif value > self.root.data and self.root.right:
            return BinarySearchTree.__remove__(self.root.right, self.root, value)
        return False

    @staticmethod
    def __remove__(node, parent_node, value):
        if not node or not parent_node:
            return False
        if value == node.data:
            if node.left and node.right: # case 被删除节点左右孩子都存在
                min_node = node.right
                while min_node.left:
                    min_node = min_node.left
                node.data = min_node.data
                BinarySearchTree.__remove__(node.right, node, node.data)
            else:                        # case 最多只有一个孩子存在
                if node == parent_node.left:
                    parent_node.left = node.left or node.right
                else:
                    parent_node.right = node.left or node.right
        elif value < node.data and node.left:
            return BinarySearchTree.__remove__(node.left, node, value)
        elif value > node.data and node.right:
            return BinarySearchTree.__remove__(node.right, node, value)
        return False

    def remove_by_iterate(self, remove_key):
        if not self.root:
            return False
        node_to_remove, parent_node = self.root, None
        has_finished = False
        while not has_finished:
            while node_to_remove.data != remove_key:
                if remove_key < node_to_remove.data and node_to_remove.left:
                    parent_node = node_to_remove
                    node_to_remove = node_to_remove.left
                elif remove_key > node_to_remove.data and node_to_remove.right:
                    parent_node = node_to_remove
                    node_to_remove = node_to_remove.right
                else:
                    return False
            if node_to_remove.left and node_to_remove.right:  # case 被删除节点左右孩子都存在
                min_node = node_to_remove.right
                while min_node.left:
                    min_node = min_node.left
                node_to_remove.data = min_node.data
                remove_key = node_to_remove.data
                parent_node = node_to_remove
                node_to_remove = node_to_remove.right
                has_finished = False
            else:  # case 最多只有一个孩子存在
                if parent_node:
                    if node_to_remove == parent_node.left:
                        parent_node.left = node_to_remove.left or node_to_remove.right
                    else:
                        parent_node.right = node_to_remove.left or node_to_remove.right
                else:
                    self.root = node_to_remove.left or node_to_remove.right
                has_finished = True
        return True


if __name__ == "__main__":
    value_list = [5, 2, -4, 3, 12, 9, 21, 19, 25]
    bst = BinarySearchTree(value_list)
    print(bst.to_string("InOrder"))
    node_text_map, edges = bst.get_show_info()
    GraphVisual.GraphVisualization.show(node_text_map, edges, view_graph=True)
    print('has value 29 ?', bst.find(29))
    print('has value 12 ?', bst.find(12))
    print('has value 27 ?', bst.find(27))
    print('remove 12 success ?', bst.remove_by_iterate(12))
    print(bst.to_string("InOrder"))
    print('remove 21 success ?', bst.remove_by_iterate(21))
    print(bst.to_string("InOrder"))
    print('remove -4 success ?', bst.remove_by_iterate(-4))
    print(bst.to_string("InOrder"))