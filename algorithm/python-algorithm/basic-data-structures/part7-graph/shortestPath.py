#!/usr/bin/python
# -*- coding: UTF-8 -*-
from graph import Graph
from graph import Edge
from GraphVisual import GraphVisualization
import heapq as hp
import sys

"""
最短路径算法
[1]-https://www.geeksforgeeks.org/greedy-algorithms-set-6-dijkstras-shortest-path-algorithm/
[2]-https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
[3]-https://www.hackerearth.com/zh/practice/algorithms/graphs/shortest-path-algorithms/tutorial/
[4]-https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm
"""


def shortest_path_dijkstra(g, source_vertex):
    """
    Dijkstra's algorithm
    单源最短路径算法
    :param g:输入图
    :param source_vertex:源点
    :return:源点到其他点的最短距离，最短路径
    """
    vertex_count = g.get_vertices_count()
    if not vertex_count:
        return [], []
    dist = [float("inf") for _ in range(vertex_count)]
    predecessors = [-1 for _ in range(vertex_count)]
    dist[source_vertex] = 0
    explored_set = []
    priority_queue = []
    hp.heappush(priority_queue, (0, source_vertex))
    while priority_queue:
        distance, vertex = hp.heappop(priority_queue)
        if vertex in explored_set:
            continue
        explored_set.append(vertex)
        for edge in g.get_adjacent_edges(vertex):
            if dist[vertex] + edge.weight < dist[edge.to_vertex_index]:
                dist[edge.to_vertex_index] = dist[vertex] + edge.weight
                predecessors[edge.to_vertex_index] = vertex
                # 更新队列数值 重复的话使用 根据 explored_set跳过
                hp.heappush(priority_queue, (dist[edge.to_vertex_index], edge.to_vertex_index))
    # 根据每个顶点最短路径的前驱 找出完整最短路径
    paths = []
    for x in range(vertex_count):
        path = [x]
        vertex = x
        while predecessors[vertex] != -1:
            vertex = predecessors[vertex]
            path.append(vertex)
        paths.append(list(reversed(path)))
    return dist, paths


def print_array(array_data):
    for i in range(len(array_data)):
        for j in range(len(array_data)):
            sys.stdout.write(str(array_data[i][j]))
            sys.stdout.write("\t\t")
        sys.stdout.write("\n")


def shortest_path_floyd_warshall(g):
    """
    all-pair 最短路径算法 Floyd–Warshall algorithm
    :param g:输入带权图
    :return:所有顶点之间的最短距离 路径
    """
    vertex_count = g.get_vertices_count()
    if not vertex_count:
        return [], []
    distance = [[0 for _row in range(vertex_count)] for _col in range(vertex_count)]
    paths = [[[] for _row in range(vertex_count)] for _col in range(vertex_count)]
    for i in range(vertex_count):
        for j in range(vertex_count):
            if i == j:
                distance[i][j] = 0    # 如果设置为inf 会找到 1-3-4-2-1 这样的路径
            else:
                distance[i][j] = g.get_weight(i, j)
                if g.is_vertex_connected(i, j):
                    paths[i][j] = [i, j]

    def is_weight_shorter(old_weight, this_weight):  # 这里视为了-1 与inf比较补充的函数
        if old_weight == float("inf"):
            return this_weight != float("inf")
        else:
            return this_weight < old_weight

    for k in range(vertex_count):  # 中间节点为k
        for i in range(vertex_count):
            for j in range(vertex_count):
                if is_weight_shorter(old_weight=distance[i][j], this_weight=distance[i][k] + distance[k][j]):
                    distance[i][j] = distance[i][k] + distance[k][j]
                    # 更新最短路径
                    paths[i][j] = [x for x in paths[i][k]]
                    paths[i][j].extend(paths[k][j][1:])
    return distance, paths


def test_case_1():
    vertex_to_name = {x: str(x) for x in range(9)}
    edge_list = [Edge(0, 1, 4), Edge(0, 7, 8), Edge(1, 2, 8), Edge(1, 7, 11), Edge(2, 3, 7),
                 Edge(2, 5, 4), Edge(2, 8, 2), Edge(3, 4, 9), Edge(3, 5, 14), Edge(4, 5, 10),
                 Edge(5, 6, 2), Edge(6, 7, 1), Edge(6, 8, 6), Edge(7, 8, 7)]
    g = Graph(Graph.GRAPH_TYPE_UNDIRECTED_WEIGHT, vertex_to_name, edge_list)
    node_text_map, edges, directed = g.get_show_info()
    GraphVisualization.show(node_text_map, edges, is_directed=directed, view_graph=True, rank_dir="LR")
    distances, paths = shortest_path_dijkstra(g, 1)
    print("shortest path from source and weight sum as:")
    for x in range(g.get_vertices_count()):
        path_str = "-" . join([g.get_vertex_name(vertex) for vertex in paths[x]])
        print("%s%s %i" % (path_str, '*' * (15 - len(path_str)),  distances[x]))


def test_case_2():
    vertex_to_name = {x: str(x+1) for x in range(4)}
    edge_list = [Edge(0, 2, -2), Edge(1, 0, 4), Edge(1, 2, 3), Edge(2, 3, 2), Edge(3, 1, -1)]
    g = Graph(Graph.GRAPH_TYPE_DIRECTED_WEIGHT, vertex_to_name, edge_list)
    node_text_map, edges, directed = g.get_show_info()
    GraphVisualization.show(node_text_map, edges, is_directed=directed, view_graph=True, rank_dir="LR")
    distances, paths = shortest_path_floyd_warshall(g)
    print("shortest path from and weight sum as:")
    for i in range(g.get_vertices_count()):
        for j in range(g.get_vertices_count()):
            path_str = "-" . join([g.get_vertex_name(vertex) for vertex in paths[i][j]])
            print("%s to %s: %s%s %d" % (g.get_vertex_name(i), g.get_vertex_name(j), path_str, '*' * (15 - len(path_str)),  distances[i][j]))

if __name__ == "__main__":
    test_case_2()