import unittest
from DiGraph import DiGraph


class TestDiGraph(unittest.TestCase):

    def test_add_node(self):
        g1 = DiGraph()
        flag1 = g1.add_node(0)
        self.assertTrue(flag1, True)
        flag1 = g1.add_node(0)
        self.assertFalse(flag1, False)
        flag1 = g1.add_node(1)
        self.assertTrue(flag1, True)
        flag1 = g1.add_node(1)
        self.assertFalse(flag1, False)
        g1.remove_node(0)
        g1.remove_node(1)


    def test_add_edge(self):
        g2 = DiGraph()
        g2.add_node(0)
        g2.add_node(1)
        g2.add_edge(0, 1, 4)
        g2.add_edge(0, 1, 2)
        b1 = g2.srcOf[0][1]
        flag2 = False
        if b1 == 4:
            flag2 = True
        self.assertTrue(flag2, True)
        g2.remove_node(0)
        g2.remove_node(1)

    def test_all_in_edges_of_node(self):
        g3 = DiGraph()
        g3.add_node(0)
        g3.add_node(1)
        g3.add_edge(0, 1, 4)
        b2 = g3.all_in_edges_of_node(1)
        c1 = b2[0]
        flag3= False
        if c1 == 4:
            flag3 = True
        self.assertTrue(flag3, True)
        g3.remove_node(0)
        g3.remove_node(1)

    def test_all_out_edges_of_node(self):
        g4 = DiGraph()
        g4.add_node(0)
        g4.add_node(1)
        g4.add_edge(0, 1, 4)
        b3 = g4.all_out_edges_of_node(0)
        c2 = b3[1]
        flag4 = False
        if c2 == 4:
            flag4 = True
        self.assertTrue(flag4, True)
        g4.remove_node(0)
        g4.remove_node(1)

    def test_remove_edge(self):
        g5 = DiGraph()
        g5.add_node(0)
        g5.add_node(1)
        g5.add_node(2)
        g5.add_edge(0, 2, 3)
        g5.add_edge(1, 2, 5)
        g5.add_edge(0, 1, 2)
        g5.add_edge(1, 0, 4)
        g5.add_edge(2, 0, 7)
        g5.add_edge(2, 1, 1)
        e1 = g5.e_size()
        self.assertEqual(6, e1, "true")
        b4 = g5.all_out_edges_of_node(0)
        self.assertTrue(1 in b4, True)
        b5 = g5.all_in_edges_of_node(1)
        self.assertTrue(0 in b5, True)
        g5.remove_edge(0, 1)
        e2 = g5.e_size()
        self.assertEqual(5, e2, "true")
        b6 = g5.all_out_edges_of_node(0)
        self.assertFalse(1 in b6, False)
        b7 = g5.all_in_edges_of_node(1)
        self.assertFalse(0 in b7, False)
        g5.remove_node(0)
        g5.remove_node(1)
        g5.remove_node(2)

    def test_remove_node_and_e_size_and_MC(self):
        g = DiGraph()
        g.add_node(0)
        g.add_node(1)
        g.add_node(2)
        g.add_edge(0, 1, 2)
        g.add_edge(0, 2, 1)
        self.assertTrue(g.get_mc() == 5, True)
        e1 = g.e_size()
        self.assertEqual(2, e1, "true")
        b4 = g.all_out_edges_of_node(0)
        self.assertTrue(1 in b4, True)
        g.remove_node(0)
        self.assertTrue(g.get_mc() == 6, True)
        e1 = g.e_size()
        self.assertEqual(0, e1, "true")
        b4 = g.all_out_edges_of_node(0)
        self.assertTrue(1 not in b4, True)
        g.remove_node(1)
        g.remove_node(2)
        self.assertTrue(g.get_mc() == 8, True)


if __name__ == '__main__':
    unittest.main()




