#!/usr/bin/python
# -*- coding: UTF-8 -*-
from graph import Graph
from graph import Edge
from GraphVisual import GraphVisualization

"""
拓扑排序 
拓扑排序的关键是  如果u->v存在边  则u的输出顺序一定在v之前 
一种是 Kahn’s algorithm BFS 使用入度为0数组 每次总是选取入度为0元素  保证与这个顶点连接的边的顶点 总是先访问了
另一种是DFS 总是让相邻的顶点先入栈  顶点自己后入栈  这样出栈序列里保证  u->x u->y u->z 这种边里面u一定先输出
reference-link
[1]-https://www.geeksforgeeks.org/topological-sorting-indegree-based-solution/
[2]-https://www.geeksforgeeks.org/topological-sorting/
[3]-http://www.cs.cornell.edu/courses/cs2112/2012sp/lectures/lec24/lec24-12sp.html
"""


def topological_sort_by_bfs(g):
    """
    拓扑排序 BFS实现 Kahn’s algorithm
    :param g: 输入为有向无环图  directed acyclic graph (DAG)
    :return: 一个排序序列
    """
    if not g.is_directed_graph():
        raise AssertionError("only use to directed graph.")
    visited_order = []
    in_degree_zero_queue = []
    vertex_count = g.get_vertices_count()
    in_degrees = [0 for _ in range(vertex_count)]
    visited = [False for _ in range(vertex_count)]
    for vertex in range(vertex_count):
        in_degrees[vertex] = g.get_in_degree(vertex)
        if in_degrees[vertex] == 0:
            in_degree_zero_queue.append(vertex)
            visited[vertex] = True
    if not in_degree_zero_queue:
        raise AssertionError("Invalid graph for sort.")
    while in_degree_zero_queue:
        vertex_index = in_degree_zero_queue.pop(0)
        visited_order.append(vertex_index)
        for edge in g.get_adjacent_edges(vertex_index):  # BFS搜索相邻未访问过的定点
            if not visited[edge.to_vertex_index]:
                in_degrees[edge.to_vertex_index] -= 1
                if in_degrees[edge.to_vertex_index] == 0:
                    in_degree_zero_queue.append(edge.to_vertex_index)
                    visited[edge.to_vertex_index] = True
    for x in visited:
        if not x:
            raise AssertionError("invalid graph for topological sort, loop detected.")
    return visited_order


def topological_sort_by_dfs(g):
    """
    拓扑排序  DFS实现 借助tricolor algorithm
    :param g: 输入图DAG
    :return: 一个排序序列
    """
    vertex_count = g.get_vertices_count()
    if not vertex_count:
        return
    colored = ["white" for _ in range(vertex_count)]  # 未访问定点标记为白色
    visited_order = []

    def dfs(cur_vertex, clr, visited):
        if clr[cur_vertex] == "gray":
            raise AssertionError('invalid graph for topological sort, loop detected.')
        clr[cur_vertex] = "gray"       # 已发现但未完成的顶点标记为灰色
        adjacent_edges = g.get_adjacent_edges(cur_vertex)
        for edge in adjacent_edges:
            if clr[edge.to_vertex_index] != "black":
                dfs(edge.to_vertex_index, clr, visited)
        clr[cur_vertex] = "black"    # 相邻节点都已访问完毕 完成状态的顶点标记为黑色
        visited.append(cur_vertex)

    for vertex in range(vertex_count):
        if colored[vertex] == "white":
            dfs(vertex, colored, visited_order)
    return reversed(visited_order)   # DFS逆序即拓扑排序


def test_case_1():
    vertex_to_name = {x: str(x+1) for x in range(5)}
    edge_list = [Edge(0, 1), Edge(0, 2), Edge(1, 2), Edge(1, 3), Edge(2, 3), Edge(2, 4)]
    g = Graph(Graph.GRAPH_TYPE_DIRECTED, vertex_to_name, edge_list)
    node_text_map, edges, directed = g.get_show_info()
    GraphVisualization.show(node_text_map, edges, is_directed=directed, view_graph=True, rank_dir="BT")
    print('one topological sort of by BFS is:  ', [vertex_to_name[x] for x in topological_sort_by_bfs(g)])
    print('one topological sort of by DFS is:  ', [vertex_to_name[x] for x in topological_sort_by_dfs(g)])


def test_case_2():
    vertex_to_name = {x: str(x+1) for x in range(5)}
    # 存在环的有向图 (2, 3)-->(3, 4)-->(4, 2)
    edge_list = [Edge(0, 1), Edge(0, 2), Edge(1, 2), Edge(1, 3), Edge(2, 3), Edge(2, 4), Edge(3, 4), Edge(4, 2)]
    g = Graph(Graph.GRAPH_TYPE_DIRECTED, vertex_to_name, edge_list)
    node_text_map, edges, directed = g.get_show_info()
    GraphVisualization.show(node_text_map, edges, is_directed=directed, view_graph=True, rank_dir="BT")
    print('one topological sort of by BFS is:  ', [vertex_to_name[x] for x in topological_sort_by_bfs(g)])
    print('one topological sort of by DFS is:  ', [vertex_to_name[x] for x in topological_sort_by_dfs(g)])

if __name__ == "__main__":
    test_case_2()