#!/usr/bin/python
# -*- coding: UTF-8 -*-

"""
图 邻接矩阵表示
"""
from GraphVisual import GraphVisualization


class Edge(object):
    def __init__(self, from_vertex_index, to_vertex_index,  weight=None):
        self.from_vertex_index = from_vertex_index
        self.to_vertex_index = to_vertex_index
        self.weight = weight

    def set_weight(self, weight):
        self.weight = weight

    def __str__(self):
        value_str = (self.weight and str(self.weight)) or 'None'
        return "Edge(" + str(self.from_vertex_index) + "-" + str(self.to_vertex_index) + ", " + value_str + " ]"

    def __repr__(self):
        return self.__str__()


class Graph(object):
    GRAPH_TYPE_UNDIRECTED = "un_directed"
    GRAPH_TYPE_DIRECTED = "directed"
    GRAPH_TYPE_UNDIRECTED_WEIGHT = "un_directed_weight"
    GRAPH_TYPE_DIRECTED_WEIGHT = "directed_weight"

    def __init__(self, graph_type, vertex_index_to_name, init_edges=None):
        self.graph_type = graph_type
        self.vertex_index_to_name = vertex_index_to_name
        self.matrix = []
        self.__extend_matrix__(len(vertex_index_to_name))
        if not init_edges:
            return
        if type(init_edges) is not list:
            raise ValueError("init with edge list only.")
        for edge in init_edges:
            self.add_edge(edge)

    def __get_matrix_default_value__(self):
        default_value = 0
        if self.graph_type == Graph.GRAPH_TYPE_UNDIRECTED_WEIGHT or self.graph_type == Graph.GRAPH_TYPE_DIRECTED_WEIGHT:
            default_value = float("inf")
        return default_value

    def get_vertices_count(self):
        return (self.matrix and len(self.matrix[0])) or 0

    def get_edges_count(self):
        return len(self.get_all_edges())

    def is_vertex_connected(self, from_vertex_index, to_vertex_index):
        cur_vertex_count = self.get_vertices_count()
        if from_vertex_index > cur_vertex_count or to_vertex_index > cur_vertex_count:
            return False
        else:
            return self.matrix[from_vertex_index][to_vertex_index] != self.__get_matrix_default_value__()

    def get_weight(self, from_vertex_index, to_vertex_index):
        return self.matrix[from_vertex_index][to_vertex_index]

    def is_directed_graph(self):
        return self.graph_type == Graph.GRAPH_TYPE_DIRECTED \
               or self.graph_type == Graph.GRAPH_TYPE_DIRECTED_WEIGHT

    def is_weighted_graph(self):
        return self.graph_type == Graph.GRAPH_TYPE_UNDIRECTED_WEIGHT \
               or self.graph_type == Graph.GRAPH_TYPE_DIRECTED_WEIGHT

    def get_all_edges(self):
        all_edges = []
        if not self.matrix:
            return all_edges
        cur_vertex_count = self.get_vertices_count()
        for row in range(cur_vertex_count):
            for col in range(cur_vertex_count):
                if not self.is_vertex_connected(row, col):
                    continue
                if self.is_directed_graph() or row <= col:
                    all_edges.append(Edge(row, col, self.matrix[row][col]))
        return all_edges

    def get_adjacent_edges(self, vertex_index):
        adjacent_edges = []
        if self.matrix and vertex_index < self.get_vertices_count():
            for vertex, edge_weight in enumerate(self.matrix[vertex_index]):
                if edge_weight != self.__get_matrix_default_value__():
                    adjacent_edges.append(Edge(vertex_index, vertex, edge_weight))
        return adjacent_edges

    def get_vertices(self):
        cur_vertex_count = self.get_vertices_count()
        return [i for i in range(cur_vertex_count)]

    def get_in_degree(self, vertex_index):
        in_degree_num = 0
        cur_vertex_count = self.get_vertices_count()
        for row in range(cur_vertex_count):
            if self.is_vertex_connected(row, vertex_index):
                in_degree_num += 1
        return in_degree_num

    def get_out_degree(self, vertex_index):
        out_degree_num = 0
        cur_vertex_count = self.get_vertices_count()
        for col in range(cur_vertex_count):
            if self.is_vertex_connected(vertex_index, col):
                out_degree_num += 1
        return out_degree_num

    def add_edges(self, _edges):
        for _edge in _edges:
            self.add_edge(_edge)

    def add_edge(self, edge):
        cur_vertex_count = self.get_vertices_count()
        expected_size = max(edge.from_vertex_index, edge.to_vertex_index) + 1
        if cur_vertex_count < expected_size:
            self.__extend_matrix__(expected_size)
        if not self.is_weighted_graph():
            edge_value = edge.weight or 1
        else:
            edge_value = edge.weight or self.__get_matrix_default_value__()
        self.matrix[edge.from_vertex_index][edge.to_vertex_index] = edge_value
        if not self.is_directed_graph():
            self.matrix[edge.to_vertex_index][edge.from_vertex_index] = edge_value

    def __extend_matrix__(self, larger_size):
        cur_size = self.get_vertices_count()
        if cur_size < larger_size:
            default_value = self.__get_matrix_default_value__()
            for i in range(cur_size):
                for j in range(cur_size, larger_size):
                    self.matrix[i].append(default_value)
            for i in range(cur_size, larger_size):
                self.matrix.append([default_value for _ in range(larger_size)])

    def add_vertex_name(self, vertex_index, vertex_name):
        self.vertex_index_to_name[vertex_index] = vertex_name

    def get_vertex_name(self, vertex_index):
        if vertex_index in self.vertex_index_to_name:
            return self.vertex_index_to_name[vertex_index]
        else:
            return None

    def get_show_info(self):
        def get_edge_label(edge):
            if self.graph_type == Graph.GRAPH_TYPE_DIRECTED_WEIGHT or \
                            self.graph_type == Graph.GRAPH_TYPE_UNDIRECTED_WEIGHT:
                return str(self.matrix[edge.from_vertex_index][edge.to_vertex_index])
            else:
                return None
        nodes_info = GraphVisualization.make_nodes({str(x): y for x, y in self.vertex_index_to_name.items()})
        edges_info = GraphVisualization.make_edges([(_edge.from_vertex_index, _edge.to_vertex_index,
                                                     True, get_edge_label(_edge)) for _edge in self.get_all_edges()])
        is_directed = self.graph_type == Graph.GRAPH_TYPE_DIRECTED \
                        or self.graph_type == Graph.GRAPH_TYPE_DIRECTED_WEIGHT
        return nodes_info, edges_info, is_directed

    def get_next_adjacent_vertex(self, vertex_index, visited_nodes=None):
        """
        寻找下一个相邻节点
        :param vertex_index:  顶点索引
        :param visited_nodes: 需要排除的已访问过顶点
        :return: 能找到时则返回下一个相邻节点，否则返回-1
        """
        for i, x in enumerate(self.matrix[vertex_index]):
            if visited_nodes and i in visited_nodes:
                continue
            if x != self.__get_matrix_default_value__():
                return i
        return -1


def make_graph_1():
    vertex_to_name = {x: str(x) for x in range(4)}
    edge_list = [Edge(0, 0), Edge(1, 0), Edge(3, 0), Edge(0, 2), Edge(3, 2), Edge(2, 3)]
    return Graph(Graph.GRAPH_TYPE_DIRECTED, vertex_to_name, edge_list)


def make_graph_2():
    vertex_to_name = {x: chr(65+x) for x in range(7)}
    edge_list = [Edge(0, 1), Edge(1, 2), Edge(2, 3), Edge(0, 4), Edge(0, 5), Edge(5, 6)]
    return Graph(Graph.GRAPH_TYPE_UNDIRECTED, vertex_to_name, edge_list)


def make_graph_3():
    vertex_to_name = {0: "San Francisco", 1: "San Diego", 2: "Phoenix", 3: "Albuquerque", 4: "Denver"}
    edge_list = [Edge(0, 1, 504), Edge(0, 2, 763), Edge(0, 4, 752),
                 Edge(1, 2, 355), Edge(2, 3, 432), Edge(3, 4, 604), Edge(2, 4, 648)]
    return Graph(Graph.GRAPH_TYPE_UNDIRECTED_WEIGHT, vertex_to_name, edge_list)


def make_graph_4():
    vertex_to_name = {0: 'S', 1: 'A', 2: 'B', 3: 'C', 4: 'D', 5: 'E', 6: 'F', 7: 'G'}
    g = Graph(Graph.GRAPH_TYPE_UNDIRECTED, vertex_to_name, None)
    g.add_edge(Edge(0, 1))
    g.add_edge(Edge(0, 2))
    g.add_edge(Edge(0, 3))
    g.add_edge(Edge(1, 4))
    g.add_edge(Edge(2, 5))
    g.add_edge(Edge(3, 6))
    g.add_edge(Edge(4, 7))
    g.add_edge(Edge(5, 7))
    g.add_edge(Edge(6, 7))
    return g

if __name__ == "__main__":
    g = make_graph_3()
    node_text_map, edges, directed = g.get_show_info()
    GraphVisualization.show(node_text_map, edges, is_directed=directed, view_graph=True, rank_dir="LR")