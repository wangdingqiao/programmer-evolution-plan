#!/usr/bin/python
# -*- coding: UTF-8 -*-
from graph import Graph
from graph import Edge
from disjointSet import DisjointSet
from GraphVisual import GraphVisualization
import Queue as Q

"""
最小生成树算法
reference-link:
[1]-https://www.ics.uci.edu/~eppstein/161/960206.html
[2]-https://www.hackerearth.com/zh/practice/algorithms/graphs/minimum-spanning-tree/tutorial/
"""


def kruskal_algorithm(g):
    """
    Kruskal最小生成树算法
    先对边进行升序排列；
    然后初始化一个空的子图；并依次将不会构成环的边加入这个子图
    最后得到的子图即是最小生成树
    :param g: 输入图
    :return: 最小生成树权重和，以及所选中的边集合
    """
    selected_edges = []
    min_weight_sum = 0
    all_edges = g.get_all_edges()
    vertex_count = g.get_vertices_count()
    all_edges.sort(key=lambda e: e.weight)
    dis_set = DisjointSet(vertex_count)
    for edge in all_edges:
        if dis_set.find(edge.from_vertex_index) != dis_set.find(edge.to_vertex_index):
            selected_edges.append(edge)
            min_weight_sum += edge.weight
            dis_set.union(edge.from_vertex_index, edge.to_vertex_index)
    return min_weight_sum, selected_edges


def prim_algorithm(g):
    selected_edges = []
    min_weight_sum = 0
    visited_node = {}
    q = Q.PriorityQueue()
    q.put((0, 0, 0))   # weight, from_index, to_index
    while not q.empty():
        cur_weight, from_index, to_index = q.get()
        if to_index in visited_node:
            continue
        if from_index != to_index:
            min_weight_sum += cur_weight
            selected_edges.append(Edge(from_index, to_index, cur_weight))
        visited_node[to_index] = True
        for edge in g.get_adjacent_edges(to_index):
            if edge.to_vertex_index not in visited_node:
                q.put((edge.weight, to_index, edge.to_vertex_index))
    return min_weight_sum, selected_edges


def test_case_1():
    vertex_to_name = {x: str(x) for x in range(5)}
    edge_list = [Edge(0, 1, 1), Edge(0, 2, 7), Edge(1, 2, 5), Edge(1, 3, 4), Edge(1, 4, 3), Edge(2, 4, 6),
                 Edge(3, 4, 2)]
    g = Graph(Graph.GRAPH_TYPE_UNDIRECTED_WEIGHT, vertex_to_name, edge_list)
    node_text_map, edges, directed = g.get_show_info()
    GraphVisualization.show(node_text_map, edges, is_directed=directed, view_graph=True, rank_dir="LR")
    weight_sum, edges = prim_algorithm(g)
    print('minimum span tree weight sum= ', weight_sum, ' select edges: ', edges)


def test_case_2():
    vertex_to_name = {x: str(x) for x in range(4)}
    edge_list = [Edge(0, 1, 1), Edge(0, 2, 4), Edge(0, 3, 3), Edge(1, 3, 2), Edge(2, 3, 5)]
    g = Graph(Graph.GRAPH_TYPE_UNDIRECTED_WEIGHT, vertex_to_name, edge_list)
    node_text_map, edges, directed = g.get_show_info()
    GraphVisualization.show(node_text_map, edges, is_directed=directed, view_graph=True, rank_dir="LR")
    weight_sum, edges = prim_algorithm(g)
    print('minimum span tree weight sum= ', weight_sum, ' select edges: ', edges)

if __name__ == "__main__":
    test_case_1()