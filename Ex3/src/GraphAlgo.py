from DiGraph import DiGraph
from json import *

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
                kno = [i for i in self.g.srcOf]
            if cur[1].get_key() in kno:
                for n in self.g.srcOf[cur[1].get_key()]:
                    if cur[1].get_tag() + self.g.srcOf[cur[1].get_key()][n] < self.g.nodes.get(n).get_tag():
                        self.g.nodes.get(n).set_tag(cur[1].get_tag() + self.g.srcOf[cur[1].get_key()][n])
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

    def save_to_json(self, file_name: str) -> bool:
        if self.graph and self.graph is isinstance(DiGraph):
            dir = {"Edges":[], "Nodes":[]}
            for n in self.g.get_all_v:
                node = self.g.get_node(n)
                data = {}
                data ["id"] = node.get_key
                data["pos"] = str(node.pos)
                dir["Nodes"].append(data)
                data.clear()
                for nkey in self.g.srcOf[node.get_key]:
                    nei = self.g.get_node(nkey)
                    data["src"] = node.get_key
                    data["dest"] = nei.get_key
                    data["w"] =  self.g.srcOf[node.get_key][nei.get_key]
                    dir["Edges"].append(data)
            with open(file_name, 'w') as file:
                dump(dir,file)
            return True
        else:
            return False



    def load_from_json(self, file_name: str) -> bool:
        with open(file_name, 'r') as file:
            data = loads(file.read())
        if data:
            graph = DiGraph()
            for elem in  data["Nodes"]:
                if elem is isinstance(dict):
                    id = elem.get("id")
                    pos = elem.get("pos")
                    if id and pos:
                        id = int(id)
                        x,y,z=[float(p) for p in pos.split(",")]
                        pos(x,y,z)
                        graph.add_node(id,pos)
                else:
                    continue
            for elem in data["Edges"]:
                if elem is isinstance(dict):
                    src = int (elem.get("src"))
                    dest = int (elem.get("dest"))
                    w = float (elem.get("w"))
                    graph.add_edge(src, dest ,w)
                else:
                    continue
            self.g = graph
            return True
        else:
            return False







graph = DiGraph({}, {}, {}, 0, 0)
graph.add_node(0)
graph.add_node(1)
graph.add_node(2)
#graph.add_node(3)
graph.add_edge(0, 1, 1)
graph.add_edge(1, 2, 4)
print(graph.srcOf)
print(graph.destOf)
print()
g_algo = GraphAlgo(graph)
print(g_algo.shortest_path(0, 1))
print(g_algo.shortest_path(0, 2))
print(g_algo.shortest_path(2, 1))



