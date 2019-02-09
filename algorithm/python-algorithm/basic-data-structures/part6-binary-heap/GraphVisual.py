#!/usr/bin/python
# -*- coding: UTF-8 -*-

import graphviz as gv
import datetime

"""
可视化数据结构树和图
Author: wangdingqiao    http://blog.csdn.net/wangdingqiaoit
"""


class GraphVisualization(object):
    class VisualNode(object):
        """
            可视化结点类
        """
        def __init__(self, node_id, node_label=None, is_visible=True):
            self.node_id = node_id
            self.is_visible = is_visible
            self.node_label = node_label or str(node_id)

        def add_to_graph(self, g):
            node_width = 0.8
            if self.node_label and len(self.node_label) > 5:
                node_width *= len(self.node_label) / 5
            node_width = str(node_width)
            if self.node_label == 'NULL':
                g.node(str(self.node_id), self.node_label, style='invis', width=node_width)
            else:
                g.node(str(self.node_id), self.node_label, width=node_width)

    class VisualEdge(object):
        """
            可视化边类
        """
        def __init__(self, from_node, to_node, is_visible=True, edge_label=None):
            self.from_node = from_node
            self.to_node = to_node
            self.is_visible = is_visible
            self.edge_label = edge_label

        def add_to_graph(self, g):
            if not self.is_visible:
                g.edge(str(self.from_node), str(self.to_node), style='invis', weight='100')
            elif self.edge_label:
                g.edge(str(self.from_node), str(self.to_node), weight='50', label=self.edge_label)
            else:
                g.edge(str(self.from_node), str(self.to_node), weight='50')

    styles_config = {
        'graph': {
            'fontsize': '12',
            'fontcolor': 'blue',
            'bgcolor': '#FFFFFF',
            'dpi': '800',
            'labeljust': 'center',
            'labelloc': 'b',
            'nodesep': '0.35'
        },
        'nodes': {
            'shape': 'circle',
            'fontcolor': 'black',
            'fontsize': '12',
            'color': 'white',
            'style': 'filled',
            'fillcolor': '#006699',
            'fixedsize': 'true',
            'width': '0.8',
            'height': '0.4',
            'ordering': 'out'
        },
        'edges': {
            'style': 'bold',
            'color': 'blue',
            'arrowhead': 'normal',
            'fontsize': '6',
            'fontcolor': 'blue',
            'arrowsize': "0.6",
            'penwidth': "1"
        }
    }

    @staticmethod
    def apply_styles(graph, styles):
        graph.graph_attr.update(
            ('graph' in styles and styles['graph']) or {}
        )
        graph.node_attr.update(
            ('nodes' in styles and styles['nodes']) or {}
        )
        graph.edge_attr.update(
            ('edges' in styles and styles['edges']) or {}
        )
        return graph

    @staticmethod
    def show(visual_nodes, visual_edges, is_directed=True, file_name=None,
             view_graph=False, show_source=False, rank_dir="TB", description=None):
        if not file_name:
            file_name = datetime.datetime.now().strftime("%Y%m%d%H%M%S%f")
        if is_directed:
            g = gv.Digraph(format="jpg")
        else:
            g = gv.Graph(format="jpg")
        g.graph_attr['rankdir'] = rank_dir
        g.graph_attr['label'] = description or 'visualized by Wangdingqiao(Based on Graphviz)'
        for node in visual_nodes:
            node.add_to_graph(g)
        for edge in visual_edges:
            edge.add_to_graph(g)
        GraphVisualization.apply_styles(g, GraphVisualization.styles_config)
        if show_source:
            print(g.source)
        g.render(filename=file_name, view=view_graph)

    @staticmethod
    def make_nodes(node_id_to_name):
        _nodes = []
        for node_id, node_name in node_id_to_name.items():
            _nodes.append(GraphVisualization.VisualNode(node_id, node_name))
        return _nodes

    @staticmethod
    def make_edges(edge_list):
        _edges = []
        for edge in edge_list:
            _edges.append(GraphVisualization.VisualEdge(*edge))
        return _edges

if __name__ == "__main__":
    nodes = GraphVisualization.make_nodes({'A': 'A', 'B': 'B', 'C': 'C', 'D': 'D', 'E': 'E', 'F': 'NULL', 'G': 'NULL'})
    edges = GraphVisualization.make_edges([('A', 'B', True), ('A', 'C', True), ('B', 'D', True),
                                           ('B', 'F', False), ('C', 'E', True), ('C', 'G', False)])
    GraphVisualization.show(nodes, edges, True, None, True, show_source=True)
