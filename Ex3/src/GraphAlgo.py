import json
from DiGraph import DiGraph
import matplotlib.pyplot as plt
from random import uniform
from GraphAlgoInterface import GraphAlgoInterface


class GraphAlgo(GraphAlgoInterface):

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
        # I'm setting up data structures here to know if I've visited this node and how I can build the list.
        parent = {}
        pq = []
        vis = []
        src = self.g.nodes.get(id1)
        src.set_tag(0)
        dest = self.g.nodes.get(id2)
        pq.append((0, src))
        while len(pq) != 0:
            pq.sort(reverse=True, key=lambda x: x[0])
            # To poll a node by priority of weight
            cur = pq.pop()
            if cur[1].get_key() not in vis:
                vis.append(cur[1].get_key())
                # After we found the destination node we do not need to continue
                if cur[1] == dest:
                    break
                kno = [i for i in self.g.srcOf]
            if cur[1].get_key() in kno:
                # Need to chek with all the nodes
                for n in self.g.srcOf[cur[1].get_key()]:
                    if cur[1].get_tag() + self.g.srcOf[cur[1].get_key()][n] < self.g.nodes.get(n).get_tag():
                        self.g.nodes.get(n).set_tag(cur[1].get_tag() + self.g.srcOf[cur[1].get_key()][n])
                        parent[n] = cur[1].get_key()
                        pq.append((self.g.nodes.get(n).get_tag(), self.g.nodes.get(n)))
        # build the list of nodes
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

    """
           Returns the shortest path from node id1 to node id2 using Dijkstra's Algorithm
           @param id1: The start node id
           @param id2: The end node id
           @return: The distance of the path, the path as a list
           Example:
   #      >>> from GraphAlgo import GraphAlgo
   #       >>> g_algo = GraphAlgo()
   #        >>> g_algo.addNode(0)
   #        >>> g_algo.addNode(1)
   #        >>> g_algo.addNode(2)
   #        >>> g_algo.addEdge(0,1,1)
   #        >>> g_algo.addEdge(1,2,4)
   #        >>> g_algo.shortestPath(0,1)
   #        (1, [0, 1])
   #        >>> g_algo.shortestPath(0,2)
   #        (5, [0, 1, 2])
           """

    def connected_component(self, id1: int) -> list:
        kn = [i for i in self.g.nodes]
        if id1 not in kn:
            return []
        result = self.connected_components()
        for list in result:
            if id1 in list:
                return list
    """"
    Finds the Strongly Connected Component(SCC) that node id1 is a part of.
    @param id1: The node id
    @return: The list of nodes in the SCC
        """

    def connected_components(self) -> list[list]:
        graph = {}  # Representation of the graph in another form
        for key in self.g.nodes:
            graph[key] = []
            for n in self.g.all_out_edges_of_node(key):
                graph[key].append(n)
        result = []
        stack = []
        low = {}
        call_stack = []
        for v in graph:
            # Insert the node we reached in the stack according to the loop
            call_stack.append((v, 0, len(low)))
            while call_stack:
                # Pulling the last node that came in and what went in with it
                v, pi, num = call_stack.pop()
                # If we're entering that condition,
                # then that probably means we haven't entered any conditions until now and haven't updated the node.
                if pi == 0:
                    # If the node is there, you no longer need to update it here
                    if v in low:
                        # We're done with that, and because we've entered that
                        # condition, we have to skip to the next iteration.
                        continue
                    # If the node isn't there, then you need to know what minimum node it's connected to.
                    low[v] = num
                    # This stack has all the components we've reached so far.
                    stack.append(v)
                # If you enter this condition, it means we've already updated the node and
                # at least one of its neighbors needs to check what's more minimal.
                if pi > 0:
                    low[v] = min(low[v], low[graph[v][pi - 1]])
                # You only enter this condition if there are other
                # nodes that we haven't completely checked on the graph.
                if pi < len(graph[v]):
                    # Add the current node to the stack to mark that it has already passed and that it can be
                    # inserted into more conditions.
                    call_stack.append((v, pi + 1, num))
                    # Also insert another node into the cartridge that is neighboring the current node
                    call_stack.append((graph[v][pi], 0, len(low)))
                    # We're done with that, and because we've entered that
                    # condition, we have to skip to the next iteration.
                    continue
                # Only if the binge component is fully formed then the minimum of the current node will equal that.
                # Because we also didn't get any continue from the other conditions.
                if num == low[v]:
                    # Build the final list.
                    comp = []
                    while True:
                        comp.append(stack.pop())
                        low[comp[-1]] = len(graph)
                        # In the stack created if we reached the current
                        # node then we are finished with its bondage component
                        if comp[-1] == v:
                            break
                    result.append(comp)
        return result

    """
           Finds all the Strongly Connected Component(SCC) in the graph.
           @return: The list all SCC
         """

    def save_to_json(self, file_name: str) -> bool:
        edges = []
        nodes = []
        dir = {"Edges": "", "Nodes": "" }
        for n in self.g.nodes:
            if n in self.g.srcOf and self.g.srcOf[n] != {}:
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
        print("Successfully saved file "  + file_name)
        return True
    """
      @param: file_name - the file name (may include a relative path).
      @return: true if we successfully saved the file
      method to save a graph to a file using JSON formatting
     """

    def load_from_json(self, file_name: str) -> bool:
        self.g = DiGraph()
        with open(file_name, 'r') as file:
            data = json.loads(file.read())
            for elem in data["Nodes"]:
                self.g.add_node(elem["id"])
                if "pos" in elem:
                     self.g.positions[elem["id"]] = elem["pos"]
                     split_pos = self.g.positions[elem["id"]].split(",")
                     pos = ""
                     x = float(split_pos[0])*100000
                     y = float(split_pos[1])*100000
                     strx = str(x)
                     stry = str(y)

                     pos = strx + "," + stry
                     self.g.positions[elem["id"]] = pos

                else:
                     x = uniform(1,2500)
                     y = uniform(1,2500)
                     z = 0.0
                     pos = ""
                     strx = str(x)
                     stry = str(y)
                     strz = str(z)
                     pos =strx+","+stry+" ,"+strz

                     self.g.positions[elem["id"]] = pos

            for elem in data["Edges"]:
                self.g.add_edge(elem["src"], elem["dest"], elem['w'])
            print("Successfully loaded file "+ file_name)
            return True
    """
    Loads graph from a file in JSON formatting to GraphAlgo.
    traverses the string inside the file and builds the graph accordingly.
    if graph is passed with positions, a random position will be generated, between (1,2500).
    @param: file_name - the file we want to load form
    @return: true if we successfully loaded the file
    """

    def plot_graph(self) -> None:
        for n in self.g.positions:
            split_pos = self.g.positions[n].split(",")
            x = float(split_pos[0])
            y = float(split_pos[1])
            plt.scatter(x,y)
            plt.annotate(n, xy = (x-0.5, y+10))

        for n in self.g.nodes:
            split_pos = self.g.positions[n].split(",")
            node_x = float(split_pos[0])
            node_y = float(split_pos[1])
            if self.g.srcOf.get(n) is None:
                continue
            for neikey in self.g.srcOf[n]:
                split_pos_nei = self.g.positions[neikey].split(",")
                nei_x = float(split_pos_nei[0])
                nei_y = float(split_pos_nei[1])
                plt.arrow(node_x, node_y,(nei_x-node_x),(nei_y-node_y), head_width = 25, head_length =44, length_includes_head = True)

        plt.show()
    """
    plots the graph using matplotlib.pyplot. 
    tested and trialed several graphs with different sizes and made adjustments to x,y position scaling
    and head_with and head_length accordingly so arrow heads will be visible to a degree, with each graph you load. 
    """

