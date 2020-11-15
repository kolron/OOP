package ex0;
    
    import java.util.*;
    
    public class Graph_DS implements graph
    {
        private HashMap<Integer,node_data> nodes; // Hashmap to contain all the nodes in the graph according to key
        private int edges; //edges in this graph
        private int MC; // action taken on graph
    
        public Graph_DS()
        {
           this.nodes = new HashMap<Integer, node_data>();
           edges = 0;
           MC = 0;
        }
        /** Graph_DS
         * A default constructor of the graph
         */
        public Graph_DS( Graph_DS g)
        {
            this.nodes = new HashMap<Integer, node_data>(); // make a new hashmap
            for(node_data n : g.nodes.values()) // loop through all the nodes
            {
                node_data t = new NodeData(n.getKey(),0,"");
                this.nodes.put(n.getKey(),t); // put the node using its key in the new hashmap
            }
            for(node_data n : g.nodes.values())
            {
               node_data t = this.nodes.get(n.getKey());// get the instance of the node with the matching key from the original graph
               for(node_data j: n.getNi())
               {
                   t.addNi(this.getNode(j.getKey())); //add t to neighbors list
               }
            }
            this.edges = g.edges;
            this.MC = g.MC;
        }
        /** Graph_DS(ONE ARG)
         * A copy constructor to the graph, making a deep copy
         */
    
        @Override
        public node_data getNode(int key)
        {
            return this.nodes.get(key);
        }
        /** getNode
         * Function to get a node using it's key
         * if node is not present in graph, can return NULL.
         */
    
        @Override
        public boolean hasEdge(int node1, int node2)
        {
            NodeData n = (NodeData)nodes.get(node1); // cast to NodeData
            return n.getNeighborMap().containsKey(node2); //if getNeighborMap has the other node than theres an edge.
        }
        /** hasEdge
         * Checks if 2 nodes are neighbors, if they are there's an edge between them
         * Checks a node's neighbor Hashmap for the other node, if present then they are neighbors and theres an edge.
         */
    
        @Override
        public void addNode(node_data n)
        {
            this.nodes.put(n.getKey(),n); //puts node in the hashmap
            MC++;
        }
    
        @Override
        public void connect(int node1, int node2)
        {
            if (!nodes.get(node1).hasNi(node2))
            {
                nodes.get(node1).addNi(nodes.get(node2));
                edges++;
                MC++;
            }
        }
        /** connect
         * Function that connects two nodes
         * simply adds each node to the other's list of neighbors
         */
    
        @Override
        public Collection<node_data> getV()
        {
            return this.nodes.values();
        }
    
        @Override
        public node_data removeNode(int key)
        {
            node_data t = nodes.get(key); // get the appropriate node from the hashmap
            if (t!=null)
            {
                edges -= t.getNi().size(); // remove an edge for each neighbor
                NodeData n = (NodeData) t; //Had to make another node n like in other cases in order to use removeNodeHelper function
                n.removeNodeHelper(); //removeNodeHelper (remove from neighbor's map)
                nodes.remove(key); // remove the key
                MC += t.getNi().size() + 1; // removed edges, plus removed key.
            }
            return t;
        }
        /** removeNode
         * Remove a node from the graph, removeNodeHelper from neighbors and decrease the corresponding num of edges
         * This function uses the removeNodeHelper function in NodeData, which in turn uses the removeNode function in NodeData
         */
    
        @Override
        public Collection<node_data> getV(int node_id)
        {
            return nodes.get(node_id).getNi();
        }
        /** getV
         * Returns a collection representing node_id's neighbors.
         */
    
        @Override
        public void removeEdge(int node1, int node2)
        {
            if (nodes.get(node1).hasNi(node2))
            {
                nodes.get(node1).removeNode(nodes.get(node2));
                edges--;
                MC++;
            }
        }
        /** removeEdge
         * Remove an edge from the graph
         * It removes each node from the other's collection of neighbors)
         */
    
        @Override
        public int nodeSize()
        {
            return nodes.size();
        }
    
        /** nodeSize
         * Returns the number of nodes
         * */
    
        @Override
        public int edgeSize()
        {
            return edges;
        }
        /** edgeSize
         * returns how many edges in the graph
         */
    
        @Override
        public int getMC()
        {
            return MC;
        }
    
    
        public String toString()
        {
            node_data temp;
            String ret = "";
            for (node_data node_data : nodes.values())
            {
                temp = node_data; // take the next node
                ret += temp + "  " + temp.getNi() + "\n"; // once finished with a node, new line.
            }
            return ret;
        }
        /** toString
         * A toString function that loops through each node in the graph,
         * adds him and neighbors to a string and returns that, representing the graph.
         */
    }

