from NodeData import NodeData
from GraphInterface import GraphInterface
from random import uniform

class DiGraph(GraphInterface):

    # constructor

    def __init__(self, nodes={}, srcOf={}, destOf={}, positions={}, Edges=0, MC=0):
        self.nodes = nodes
        self.srcOf = srcOf
        self.destOf = destOf
        self.positions = positions
        self.Edges = Edges
        self.MC = MC


    def v_size(self):
        return len(self.nodes)

    """
             Returns the number of vertices in this graph.
             @return: The number of vertices in this graph.
             """

    def e_size(self):
        return self.Edges

    """
             Returns the number of edges in this graph.
             @return: The number of edges in this graph.
             """

    def get_all_v(self):
        return self.nodes

    """return a dictionary of all the nodes in the Graph, each node is represented using a pair
             (node_id, node_data).
            """
    def all_in_edges_of_node(self, id1: int):
        kni = [i for i in self.destOf]
        if id1 in kni:
            return self.destOf[id1]
        return {}

    """return a dictionary of all the nodes connected to (into) node_id ,
            each node is represented using a pair (other_node_id, weight).
             """

    def all_out_edges_of_node(self, id1: int):
        kno = [i for i in self.srcOf]
        if id1 in kno:
            return self.srcOf[id1]
        return {}

    """return a dictionary of all the nodes connected from node_id , each node is represented using a pair
            (other_node_id, weight).
            """

    def get_mc(self):
        return self.MC

    """
           Returns the current version of this graph,
           on every change in the graph state - the MC should be increased.
           @return: The current version of this graph.
           """

    def add_edge(self, id1: int, id2: int, weight: float):
        kn = [i for i in self.nodes]
        if id1 not in kn or id2 not in kn:
            return False
        ko = [i for i in self.srcOf]
        if id1 not in ko:
            self.srcOf[id1] = {}
        ki = [i for i in self.destOf]
        if id2 not in ki:
            self.destOf[id2] = {}
        kno = [i for i in self.srcOf[id1]]
        if id2 in kno:
            return False
        self.srcOf[id1][id2] = weight
        self.destOf[id2][id1] = weight
        self.Edges += 1
        self.MC += 1
        return True

    """
            Adds an edge to the graph.
            @param id1: The start node of the edge.
            @param id2: The end node of the edge.
            @param weight: The weight of the edge.
            @return: True if the edge was added successfully, False o.w.
            Note: If the edge already exists or one of the nodes dose not exists the functions will do nothing.
            """

    def add_node(self, node_id: int, pos1: tuple = None):
        kn = [i for i in self.nodes]
        if node_id not in kn:
            n = NodeData(node_id, 0, 0, "")
            self.nodes[node_id] = n
            self.MC += 1
            if pos1 is not None:
                pos = ""
                strx = str(pos1[0]*100)
                stry = str(pos1[1]*100)
                strz = str(pos1[2])
                pos = strx + "," + stry + " ," + strz
                self.positions[node_id] = pos
            else:
                x = uniform(1, 100000)
                y = uniform(1, 100000)
                z = 0.0
                pos = ""
                strx = str(x)
                stry = str(y)
                strz = str(z)
                pos = strx + "," + stry + " ," + strz
                self.positions[node_id] = pos
            return True
        return False

    """
           Adds a node to the graph.
           @param node_id: The node ID.
           @param pos: The position of the node.
           @return: True if the node was added successfully, False o.w.
           Note: if the node id already exists the node will not be added.
           If no position is given, a random one will be generated.
           """

    def get_node(self, node_id: int):
        return self.nodes.get(node_id)
    """
    Returns the node with node_id.
    @param node_id: the key (or id) of the node.
    @return: the node with key or id of node_id.
    """

    def remove_node(self, node_id: int):
        kn = [i for i in self.nodes]
        if node_id not in kn:
            return False
        kni = [i for i in self.destOf]
        if node_id in kni:
            for key in self.destOf[node_id]:
                del self.srcOf[key][node_id]
                self.Edges -= 1
                if len(self.srcOf[key]) == 0:
                    del self.srcOf[key]
            del self.destOf[node_id]
        kno = [i for i in self.srcOf]
        if node_id in kno:
            for key in self.srcOf[node_id]:
                del self.destOf[key][node_id]
                self.Edges -= 1
                if len(self.destOf[key]) == 0:
                    del self.destOf[key]
            del self.srcOf[node_id]
        del self.nodes[node_id]
        del self.positions[node_id]
        self.MC += 1
        return True
    """
        Removes a node from the graph.
        @param node_id: The node ID
        @return: True if the node was removed successfully, False o.w.
        Note: if the node id does not exists the function will do nothing
        """

    def remove_edge(self, node_id1: int, node_id2: int):
        kn = [i for i in self.nodes]
        if node_id1 not in kn or node_id2 not in kn:
            return False
        kni = [i for i in self.destOf]
        if node_id2 in kni:
            del self.destOf[node_id2][node_id1]
            del self.srcOf[node_id1][node_id2]
            if len(self.destOf[node_id2]) == 0:
                del self.destOf[node_id2]
            if len(self.srcOf[node_id1]) == 0:
                del self.srcOf[node_id1]
            self.MC += 1
            self.Edges -= 1
            return True
        return False

    """
          Removes an edge from the graph.
          @param node_id1: The start node of the edge
          @param node_id2: The end node of the edge
          @return: True if the edge was removed successfully, False o.w.
          Note: If such an edge does not exists the function will do nothing
          """
