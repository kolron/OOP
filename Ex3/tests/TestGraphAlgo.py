import unittest
from DiGraph import DiGraph
from GraphAlgo import GraphAlgo


class TestDiGraph(unittest.TestCase):

    def test_get_graph(self):
        g = DiGraph()
        g.add_node(0)
        g.add_node(1)
        g.add_edge(0, 1, 1)
        algo = GraphAlgo(g)
        gc = algo.get_graph()
        flag = g.get_node(0) == gc.get_node(0)
        self.assertTrue(flag, True)
        flag = g.get_node(1) == gc.get_node(1)
        self.assertTrue(flag, True)
        flag = g.all_in_edges_of_node(1) == gc.all_in_edges_of_node(1)
        self.assertTrue(flag, True)
        flag = g.all_out_edges_of_node(0) == gc.all_out_edges_of_node(0)
        self.assertTrue(flag, True)
        g.remove_node(0)
        g.remove_node(1)
        gc.remove_node(0)
        gc.remove_node(1)

    def test_shortest_path(self):
        g = DiGraph()
        g.add_node(0)
        g.add_node(1)
        g.add_node(2)
        g.add_edge(0, 2, 5)
        g.add_edge(0, 1, 3)
        g.add_edge(1, 2, 1)
        g.add_edge(2, 1, 1)
        g.add_edge(2, 0, 2)
        g.add_edge(1, 0, 5)
        algo = GraphAlgo(g)
        s1 = algo.shortest_path(0, 2)
        s2 = algo.shortest_path(1, 0)
        flag = False
        if 1 in s1[1] and 2 in s2[1]:
            flag = True
        self.assertTrue(flag, True)

    def test_connected_component_and_connected_component(self):
        g = DiGraph()
        x = 0
        while x < 7:
            g.add_node(x)
            x += 1
        while x > 1:
            g.add_edge(0, x-1, x-1)
            x -= 1
        g.add_edge(1, 2, 3)
        g.add_edge(1, 3, 4)
        g.add_edge(2, 0, 2)
        g.add_edge(2, 5, 7)
        g.add_edge(3, 4, 7)
        g.add_edge(4, 5, 9)
        g.add_edge(5, 3, 8)
        g.add_edge(3, 6, 9)
        algo = GraphAlgo(g)
        s = algo.connected_components()
        s1 = algo.connected_component(1)
        s2 = algo.connected_component(2)
        s3 = algo.connected_component(3)
        s4 = algo.connected_component(5)
        flag = False
        if s1 == s2 and s3 == s4:
            flag = True
        self.assertTrue(flag, True)
        if s1 not in s or s4 not in s:
            flag = False
        self.assertTrue(flag, True)


if __name__ == '__main__':
    unittest.main()
