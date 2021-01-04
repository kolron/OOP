from DiGraph import DiGraph


class GraphAlgo:

    def __init__(self, g=None):
        self.g = g

    def addNode(self, id):
        self.g.add_node(id)

    def addEdge(self, id1, id2, w):
        self.g.add_edge(id1, id2, w)

    def get_graph(self):
        return self.g

    def shortest_path(self, id1: int, id2: int):
        kn = [i for i in self.g.nodes]
        if id1 not in kn or id2 not in kn or len(self.g.nodes) == 0:
            return float('inf'), []
        if id1 == id2:
            return 0, [id1]
        for n in self.g.nodes:
            self.g.nodes.get(n).set_tag(float('inf'))
        parent = {}
        pq = []
        vis = []
        src = self.g.nodes.get(id1)
        src.set_tag(0)
        dest = self.g.nodes.get(id2)
        pq.append((0, src))
        while len(pq) != 0:
            pq.sort(reverse=True, key=lambda x: x[0])
            cur = pq.pop()
            if cur[1].get_key() not in vis:
                vis.append(cur[1].get_key())
                if cur[1] == dest:
                    break
                kno = [i for i in self.g.neiOut]
            if cur[1].get_key() in kno:
                for n in self.g.neiOut[cur[1].get_key()]:
                    if cur[1].get_tag() + self.g.neiOut[cur[1].get_key()][n] < self.g.nodes.get(n).get_tag():
                        self.g.nodes.get(n).set_tag(cur[1].get_tag() + self.g.neiOut[cur[1].get_key()][n])
                        parent[n] = cur[1].get_key()
                        pq.append((self.g.nodes.get(n).get_tag(), self.g.nodes.get(n)))
        kp = [i for i in parent]
        if dest.get_key() not in kp:
            return float('inf'), []
        s = []
        s.append(dest.get_key())
        key = dest.get_key()
        while key != src.get_key():
            key = parent[key]
            s.append(key)
        s.reverse()
        return dest.get_tag(), s


graph = DiGraph({}, {}, {}, 0, 0)
graph.add_node(0)
graph.add_node(1)
graph.add_node(2)
#graph.add_node(3)
graph.add_edge(0, 1, 1)
graph.add_edge(1, 2, 4)
print(graph.neiOut)
print(graph.neiIn)
print()
g_algo = GraphAlgo(graph)
print(g_algo.shortest_path(0, 1))
print(g_algo.shortest_path(0, 2))
print(g_algo.shortest_path(2, 1))



