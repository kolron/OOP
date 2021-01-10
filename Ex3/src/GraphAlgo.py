import json

from DiGraph import DiGraph
import matplotlib.pyplot as plt
from random import uniform

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
        edges = []
        nodes = []
        dir = {"Edges": "", "Nodes": "" }
        for n in self.g.get_all_v:
            for neikey in self.g.srcOf[n]:
                edges.append(({"src" : n, "dest" : neikey, "w" : self.g.srcOf[n][neikey]}))
            if self.g.positions == {}:
                nodes.append({"id" : n})
            else:
                nodes.append({"id" : n, "pos" : self.g.positions[n]})
            dir["Edges"] = edges
            dir ["Nodes"] = nodes
        with open(file_name, 'w') as file:
            json.dump(dir, file)
        return True

    def load_from_json(self, file_name: str) -> bool:
        self.g = DiGraph()
        with open(file_name, 'r') as file:
            data = json.loads(file.read())
            for elem in data["Nodes"]:
                self.g.add_node(elem["id"])
                if "pos" in elem:
                     self.g.positions[elem["id"]] = elem["pos"]
                else:
                     continue
            for elem in data["Edges"]:
                self.g.add_edge(elem["src"], elem["dest"], elem['w'])
            return True

    def connected_component(self, id1: int) -> list:
        kn = [i for i in self.g.nodes]
        if id1 not in kn:
            return []
        result = self.connected_components()
        l = len(result)
        for list in result:
            if id1 in list:
                return list

    def connected_components(self):  # -> List[list]:
        graph = {}
        for key in self.g.nodes:
            graph[key] = []
            for n in self.g.all_out_edges_of_node(key):
                graph[key].append(n)
        result = []
        stack = []
        low = {}
        call_stack = []
        for v in graph:
            call_stack.append((v, 0, len(low)))
            while call_stack:
                v, pi, num = call_stack.pop()
                if pi == 0:
                    if v in low:
                        continue
                    low[v] = num
                    stack.append(v)
                if pi > 0:
                    low[v] = min(low[v], low[graph[v][pi - 1]])
                if pi < len(graph[v]):
                    call_stack.append((v, pi + 1, num))
                    call_stack.append((graph[v][pi], 0, len(low)))
                    continue
                if num == low[v]:
                    comp = []
                    while True:
                        comp.append(stack.pop())
                        low[comp[-1]] = len(graph)
                        if comp[-1] == v:
                            break
                    result.append(comp)
        return result

    def plot_graph(self) -> None:
        if self.g.positions == {}:
            for n in self.g.positions:
                x = uniform(0,1000000)
                y = uniform(0,1000000)
                pos = str(x) + "," + str(y)
                self.g.positions = pos
                plt.scatter(x,y)
                plt.annotate(n, xy = (x-0.5, y+1))
            else:
                for n in self.g.positions:
                    len_x = self.g.positions[n].find(",")
                    len_y = self.g.positions[n].find("," , len_x + 1)
                    x = self.g.positions[n][0 : len_x]
                    x = float(x)
                    y = self.g.positions[n][len_x + 1 : len_y]
                    y= float(y)
                    plt.plot(x,y)
        for n in self.g.nodes:
            n_len_x = self.g.positions[n].find(",")
            n_len_y = self.g.positions[n].find(",", n_len_x + 1)
            node_x = self.g.positions[n][0: n_len_x]
            node_x = float(node_x)
            node_y = self.g.positions[n][n_len_x + 1: n_len_y]
            node_y = float(node_y)
            if self.g.srcOf.get(n) is None:
                continue
            for neikey in self.g.srcOf[n]:
                nei_len_x = self.g.positions[neikey].find(",")
                nei_len_y = self.g.positions[neikey].find(",", nei_len_x + 1)
                nei_x = self.g.positions[n][0: nei_len_x]
                nei_x = float(nei_x)
                nei_y = self.g.positions[n][nei_len_x + 1: nei_len_y]
                nei_y = float(nei_y)
                plt.arrow(node_x, node_y,(nei_x-node_x),(nei_y-node_y), head_width = 1.5, head_length = 1.5, length_includes_head = True)

        plt.show()


