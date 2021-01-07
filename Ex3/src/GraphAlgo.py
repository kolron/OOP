from DiGraph import DiGraph
from json import *
import matplotlib.pyplot as plt

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
            dir = {"Edges": [], "Nodes": []}
            for n in self.g.get_all_v:
                node = self.g.get_node(n)
                data = {}
                data["id"] = node.get_key
                data["pos"] = str(node.pos)
                dir["Nodes"].append(data)
                data.clear()
                for nkey in self.g.srcOf[node.get_key]:
                    nei = self.g.get_node(nkey)
                    data["src"] = node.get_key
                    data["dest"] = nei.get_key
                    data["w"] = self.g.srcOf[node.get_key][nei.get_key]
                    dir["Edges"].append(data)
            with open(file_name, 'w') as file:
                dump(dir, file)
            return True
        else:
            return False

    def load_from_json(self, file_name: str) -> bool:
        with open(file_name, 'r') as file:
            data = loads(file.read())
        if data:
            graph = DiGraph()
            for elem in data["Nodes"]:
                if elem is isinstance(dict):
                    id = elem.get("id")
                    pos = elem.get("pos")
                    if id and pos:
                        id = int(id)
                        x, y, z = [float(p) for p in pos.split(",")]
                        pos(x, y, z)
                        graph.add_node(id, pos)
                else:
                    continue
            for elem in data["Edges"]:
                if elem is isinstance(dict):
                    src = int(elem.get("src"))
                    dest = int(elem.get("dest"))
                    w = float(elem.get("w"))
                    graph.add_edge(src, dest, w)
                else:
                    continue
            self.g = graph
            return True
        else:
            return False
    #
    # def dfs(self, src: int):
    #     discovery = finish = parent = {}
    #     for n in self.g.get_all_v:
    #         node = self.g.get_node(n)
    #         node.set_info("white")
    #     time = 0
    #     for n in self.g.get_all_v:
    #         node = self.g.get_node(n)
    #         if node.get_info == "white":
    #             src_n, time, parent, discovery, finish = self.dfs_visit(src, time, parent, discovery, finish)
    #
    # def dfs_visit(self, src: int, time: int, parent: dict, d: dict, f: dict):
    #     src_node = self.g.get_node(src)
    #     src_node.set_info("grey")
    #     time += 1
    #     d[src] = time
    #     for n in self.g.srcOf[src_node.get_key()]:
    #         node = self.g.get_node(n)
    #         if node.get_info == "white":
    #             parent[n] = src
    #             self.dfs_visit(n)
    #     src_node.set_info("black")
    #     f[src] = time
    #     ++time
    #     return src, time, parent, d, f

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
        for n in self.g.nodes:
            node = self.g.get_node(n)
            x = (node.get_x())
            y = (node.get_y())
            plt.scatter(x,y)
            plt.annotate(node.get_key(), xy=((node.get_x())-0.5, (node.get_y())+1))

        for n in self.g.nodes:
            node = self.g.get_node(n)
            nodex = node.get_x()
            nodey = node.get_y()
            if self.g.srcOf.get(n) is None:
                continue
            for neikey in self.g.srcOf[n]:
               nei = self.g.get_node(neikey)
               neix = nei.get_x()
               neiy = nei.get_y()
               plt.arrow(nodex, nodey,(neix-nodex),(neiy-nodey), head_width = 1.5, head_length = 1.5, length_includes_head = True)

        plt.show()


graph = DiGraph({}, {}, {}, 0, 0)
graph.add_node(0)
graph.add_node(1)
graph.add_node(2)
graph.add_node(3)
graph.add_edge(0, 1, 1)
graph.add_edge(1, 2, 4)
print(graph.srcOf)
print(graph.destOf)
#print()
g_algo = GraphAlgo(graph)
#print(g_algo.shortest_path(0, 1))
#print(g_algo.shortest_path(0, 2))
#print(g_algo.shortest_path(2, 1))

g_algo.plot_graph()

