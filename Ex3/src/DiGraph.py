from NodeData import NodeData


class DiGraph:
    #MC = 0  # number of action in the graph
    #Edges = 0  # number of edges

    # constructor

    def __init__(self, nodes={}, neiOut={}, neiIn={}, Edges=0, MC=0):
        self.nodes = nodes
        self.neiOut = neiOut  # edges that come out fron the node
        self.neiIn = neiIn  # edges that come from another edge
        self.Edges = Edges
        self.MC = MC

    def v_size(self):
        return len(self.nodes)

    def e_size(self):
        return self.Edges

    def get_all_v(self):
        return self.nodes

    def all_in_edges_of_node(self, id1: int):
        kni = [i for i in self.neiIn]
        if id1 in kni:
            return self.neiIn[id1]
        return {}

    def all_out_edges_of_node(self, id1: int):
        kno = [i for i in self.neiOut]
        if id1 in kno:
            return self.neiOut[id1]
        return {}

    def get_mc(self):
        return self.MC

    def add_edge(self, id1: int, id2: int, weight: float):
        kn = [i for i in self.nodes]
        if id1 not in kn or id2 not in kn:
            return False
        ko = [i for i in self.neiOut]
        if id1 not in ko:
            self.neiOut[id1] = {}
        ki = [i for i in self.neiIn]
        if id2 not in ki:
            self.neiIn[id2] = {}
        kno = [i for i in self.neiOut[id1]]
        if id2 in kno:
            return False
        self.neiOut[id1][id2] = weight
        self.neiIn[id2][id1] = weight
        self.Edges += 1
        self.MC += 1
        return True

    def add_node(self, node_id: int):
        kn = [i for i in self.nodes]
        if node_id not in kn:
            n = NodeData(node_id, 0, 0, "")
            self.nodes[node_id] = n
            self.MC += 1
            return True
        return False

    def remove_node(self, node_id: int):
        kn = [i for i in self.nodes]
        if node_id not in kn:
            return False
        kni = [i for i in self.neiIn]
        if node_id in kni:
            for key in self.neiIn[node_id]:
                del self.neiOut[key][node_id]
                self.Edges -= 1
                self.MC += 1
                if len(self.neiOut[key]) == 0:
                    del self.neiOut[key]
            del self.neiIn[node_id]
        kno = [i for i in self.neiOut]
        if node_id in kno:
            for key in self.neiOut[node_id]:
                del self.neiIn[key][node_id]
                self.Edges -= 1
                self.MC += 1
                if len(self.neiIn[key]) == 0:
                    del self.neiIn[key]
            del self.neiOut[node_id]
        del self.nodes[node_id]
        self.MC += 1
        return True

    def remove_edge(self, node_id1: int, node_id2: int):
        kn = [i for i in self.nodes]
        if node_id1 not in kn or node_id2 not in kn:
            return False
        kni = [i for i in self.neiIn]
        if node_id2 in kni:
            del self.neiIn[node_id2][node_id1]
            del self.neiOut[node_id1][node_id2]
            if len(self.neiIn[node_id2]) == 0:
                del self.neiIn[node_id2]
            if len(self.neiOut[node_id1]) == 0:
                del self.neiOut[node_id1]
            self.MC += 1
            self.Edges -= 1
            return True
        return False

"""
graph = DiGraph()
print(graph)
graph.add_node(0)
graph.add_node(1)
print(graph.add_edge(0, 1, 1.85765))
print(graph.add_edge(1, 0, 1.8595))
print(graph.Edges)
print(graph.neiOut)
print(graph.neiIn)
print(graph.nodes)
print(graph.MC)
graph.remove_node(0)
graph.remove_node(1)
print(graph.Edges)
print(graph.neiOut)
print(graph.neiIn)
print(graph.nodes)
print(graph.MC)
graph.add_node(0)
graph.add_node(1)
print(graph.add_edge(0, 1, 1.85765))
print(graph.add_edge(1, 0, 1.8595))
print(graph.Edges)
print(graph.neiOut)
print(graph.neiIn)
print(graph.nodes)
print(graph.MC)
print(graph.remove_edge(0, 1))
print(graph.Edges)
print(graph.neiOut)
print(graph.neiIn)
print(graph.nodes)
print(graph.MC)
"""
