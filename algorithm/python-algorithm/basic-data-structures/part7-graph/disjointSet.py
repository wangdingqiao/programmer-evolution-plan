#!/usr/bin/python
# -*- coding: UTF-8 -*-

"""
不相交集合
reference-link:
[1]-https://www.cs.princeton.edu/courses/archive/spring13/cos423/lectures/UnionFind.pdf
[2]-https://www.geeksforgeeks.org/union-find-algorithm-set-2-union-by-rank/
"""
from GraphVisual import GraphVisualization


class DisjointSet(object):
    def __init__(self, set_size=10):
        self.parent = [x for x in range(set_size)]  # parent装的是元素所在集合的代表元素
        self.rank = [0 for _ in range(set_size)]    # 代表元素所在集合的rank

    def make_set(self, x):
        if x >= len(self.parent):
            for i in range(len(self.parent), x+1):
                self.parent.append(i)
                self.rank.append(0)
        else:
            self.parent[x] = x
            self.rank[x] = 0

    def find(self, x):
        """
        查找元素x所在集合
        :param x: 查找元素
        :return: 所在集合的代表元素
        """
        if x > len(self.parent):
            return None
        if self.parent[x] != x:
            self.parent[x] = self.find(self.parent[x])  # path-compression 使用递归
        return self.parent[x]

    def union(self, x, y):
        x_root = self.find(x)
        y_root = self.find(y)
        if x_root == y_root:
            return
        if self.rank[x_root] < self.rank[y_root]:
            self.parent[x_root] = y_root
        elif self.rank[x_root] > self.rank[y_root]:
            self.parent[y_root] = x_root
        else:
            self.parent[x_root] = y_root
            self.rank[y_root] = self.rank[y_root] + 1

    def get_show_info(self):
        nodes_info = GraphVisualization.make_nodes({x: str(x) for x in range(len(self.parent))})
        edges = []
        for x in range(len(self.parent)):
            if self.parent[x] == x:
                edges.append((x, x, True))
            else:
                while self.parent[x] != x:
                    val = (self.parent[x], x, True)
                    if val not in edges:
                        edges.append(val)
                    x = self.parent[x]
        edges_info = GraphVisualization.make_edges(edges)
        return nodes_info, edges_info


def test_case_1():
    dis_set = DisjointSet(4)
    dis_set.union(0, 1)
    dis_set.union(1, 2)
    dis_set.union(2, 3)
    dis_set.make_set(4)
    dis_set.union(1, 4)
    print(dis_set.parent, dis_set.rank)
    node_text_map, edges = dis_set.get_show_info()
    GraphVisualization.show(node_text_map, edges, is_directed=True, view_graph=True, rank_dir="TB")


def test_case_2():
    dis_set = DisjointSet(10)
    dis_set.union(3, 4)
    dis_set.union(8, 4)
    dis_set.union(9, 4)
    dis_set.union(0, 6)
    dis_set.union(1, 2)
    dis_set.union(7, 2)
    dis_set.union(2, 6)
    dis_set.union(5, 6)
    print('before is 7 8 in same set ?', dis_set.find(7) == dis_set.find(8))
    dis_set.union(7, 3)
    print('after is 7 8 in same set ?', dis_set.find(7) == dis_set.find(8))
    print(dis_set.parent, dis_set.rank)
    node_text_map, edges = dis_set.get_show_info()
    GraphVisualization.show(node_text_map, edges, is_directed=True, view_graph=True, rank_dir="TB")

if __name__ == "__main__":
    test_case_2()