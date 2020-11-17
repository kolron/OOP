package ex1;
import java.io.Serializable;
import java.util.*;

public class WGraph_DS implements weighted_graph, Serializable {
    /** WGraph_DS (CLASS)
     * Class that represents a graph using a hashmap which holds all existing nodes,
     * num of edges, number of all created nodes, and amount of operation made on the graph.
     */
    public class NodeInfo implements node_info, Serializable {
        private int key;
        private double tag;
        private String info;
        private HashMap<Integer, node_info> nei;
        private HashMap<Integer, Double> neiWeight;

        /** NodeInfo (CLASS)
         *  Inner class inside WGraph_DS, represents nodes inside the graph.
         */

        public NodeInfo(int key, double tag, String info) {
            this.key = key;
            setTag(tag);
            setInfo(info);
            this.nei = new HashMap<Integer, node_info>();
            this.neiWeight = new HashMap<Integer, Double>();
        }

        /**
         * NodeInfo(3Args)
         * Constructor for NodeInfo, creates a node with a given
         * key, tag, info,
         * An HashMap nei that contains all neighbors and their keys,
         * and an HashMap that contains all neighbors and the weight of the edge to them.
         */


        public NodeInfo(int num) {
            this(num, 0, "");
        }
        /** NodeInfo(1Arg)
         * A constructor that creates a node, with only a given key
         */

        @Override
        public int getKey() {
            return key;
        }

        @Override
        public String getInfo() {
            return info;
        }

        @Override
        public void setInfo(String s) {
            this.info = s;
        }

        @Override
        public double getTag() {
            return tag;
        }

        @Override
        public void setTag(double t) {
            this.tag = t;
        }

        public double getWeight(int key) {
            return this.neiWeight.get(key);
        }

        /** getWeight
         * This Function returns the weight of the edge
         * from this NodeInfo to another, using the neiWeight hashmap, which represents the edges' weight
         * from a node to his neighbors.
         */

        public String toString() {
            return "Node (" + getKey() + ")";
        }

        /**
         * toString
         * returns a string representing the node's KEY not node's INFO
         */
        public HashMap<Integer, node_info> getNeighborMap() {
            return this.nei;
        }

        /**
         * getNeighborMap
         * gets a nodes' neighbors using a hashmap (each node has a hashmap of neighbors)
         */

        public Collection<node_info> getNi() {
            return this.nei.values();
        }

        /** getNi
         * get a collection of this nodes' neighbors.
         */

        public void removeNode(node_info node) {
            if (node != null) {
                NodeInfo n = (NodeInfo) node;
                this.nei.remove(node.getKey()); //remove neighbors from hashmap
                n.nei.remove(getKey()); // remove node from neighbor's hashmap
                this.neiWeight.remove(node.getKey());
                n.neiWeight.remove(node.getKey());
            }
        }

        /**
         * removeNode
         * Remove a node from the this nodes' neighbor list(and the other way around), and remove the edge between them.
         * called to by RemoveNodeHelper
         */

        public void removeNodeHelper()
        {
            node_info temp;
            Iterator<node_info> i = this.getNi().iterator();
            while (i.hasNext()) {
                temp = i.next();
                i.remove();
                removeNode(temp);
            }
        }
        /**
         * removeNodeHelper
         * A helper function to removeNode that uses it
         * This function disconnects this node from all of it's neighbors,
         * And the other way around. calls to RemoveNode
         *
         */

        public boolean equals(Object o) {
            NodeInfo node = (NodeInfo) o;
            if (node.getKey() != this.getKey())
                return false;
            for (node_info n : node.getNi()) {
                if (!hasEdge(node.getKey(), n.getKey())) return false;
                if (node.getWeight(n.getKey()) != this.getWeight(n.getKey())) return false;
            }
            return true;
        }
    }

    /** equals
     *  This function replaces the default equals function between two objects.
     *  It compares this current node to a node "node", by checking if their keys,
     *  checking if every neighbor of this node is a neighbor to the other node
     *  and checks if the weight to all neighbors is the same.
     */

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        public HashMap<Integer,node_info> nodes;
        private int nodeNumber;
        public int edges;
        public int  MC;

        public WGraph_DS()
        {
            nodes = new HashMap<Integer, node_info>();
            nodeNumber = 0;
            edges = 0;
            MC =0;

        }

        public WGraph_DS(WGraph_DS g)
        {
            if (g != null)
            {
                nodes = new HashMap<Integer, node_info>();
                for (node_info n : g.nodes.values())
                {
                    NodeInfo t = new NodeInfo(n.getKey(), n.getTag(), n.getInfo());
                    nodes.put(t.getKey(), t);

                }
                for (node_info n : g.nodes.values())
                {
                    NodeInfo t = (NodeInfo) n;
                    int tKey=t.getKey();
                    NodeInfo temp = (NodeInfo)nodes.get(t.getKey());
                    for(node_info nei: t.getNeighborMap().values())
                    {
                        int neiKey = (nei.getKey());

                        if(!hasEdge(neiKey, temp.getKey()))
                        {
                            connect(tKey, neiKey, t.getWeight(neiKey));
                        }
                    }
                }
                edges = g.edges;
                MC = g.MC;
                nodeNumber = g.nodeNumber;
            }
            else
            {
                nodes = new HashMap<Integer, node_info>();
                nodeNumber = 0;
                MC = 0;
                edges = 0;
            }
        }

    @Override
    public node_info getNode(int key) {
        if (nodes.containsKey(key))
        {
            return nodes.get(key);
        }
        return null;
    }

    @Override
        public boolean hasEdge(int node1, int node2)
        {
            NodeInfo n = (NodeInfo)nodes.get(node1); // cast to NodeData
            return n.nei.containsKey(node2); //if getNeighborMap has the other node than theres an edge.
        }
        /** hasEdge
         * Checks if 2 nodes are neighbors, if they are there's an edge between them
         * Checks a node's neighbor Hashmap for the other node, if present then they are neighbors and theres an edge.
         */

    @Override
    public double getEdge(int node1, int node2)
    {
        if (!(hasEdge(node1, node2))) { return -1; }
        NodeInfo n = (NodeInfo)nodes.get(node1); // cast to NodeData
        return n.neiWeight.get(node2);
    }

    @Override
    public void addNode(int key)
    {   if (!(nodes.containsKey(key)))
        nodes.put(key, new NodeInfo(key)); //puts node in the hashmap
        nodeNumber++;
        MC++;
    }

    @Override
    public void connect(int node1, int node2, double w)
    {
        NodeInfo n1= (NodeInfo)nodes.get(node1);
        NodeInfo n2= (NodeInfo)nodes.get(node2);

        if (n1 == null || n2 == null) { return; }
        if(node1 != node2 && w >= 0)
        {
            if (hasEdge(node1, node2))
            {
                n1.neiWeight.put(node2, w);
                n2.neiWeight.put(node1, w);
                MC++;
            }
            else
            {
                n1.nei.put(node2, n2);
                n2.nei.put(node1, n1);
                n1.neiWeight.put(node2, w);
                n2.neiWeight.put(node1, w);
                MC++;
                edges++;
            }

        }
    }
    @Override
    public Collection<node_info> getV() {
        return this.nodes.values();
    }
    @Override
    public Collection<node_info> getV(int node_id)
    {
        NodeInfo n = (NodeInfo) nodes.get(node_id);
        ArrayList<node_info> ret = new ArrayList<>();
        for (node_info t : n.nei.values())
        {
            ret.add(n);
        }
        return ret;
    }

    @Override
    public node_info removeNode(int key) {
        {
            NodeInfo t = (NodeInfo)nodes.get(key);
            if (t != null) {
                edges -= t.getNi().size(); // remove an edge for each neighbor
                t.removeNodeHelper(); //removeNodeHelper (remove from neighbor's map)
                nodes.remove(key); // remove the key
                MC += t.getNi().size() + 1; // removed edges, plus removed key.
            }
            return t;
        }
    }


    @Override
    public void removeEdge(int node1, int node2)
    {
        NodeInfo n1 = (NodeInfo)nodes.get(node1);
        NodeInfo n2 = (NodeInfo)nodes.get(node2);
        if (hasEdge(node1, node2))
        {
            n1.removeNode(nodes.get(node2));
            edges--;
            MC++;
        }
    }

    @Override
    public int nodeSize() {
        return nodes.size();
    }

    @Override
    public int edgeSize() {
        return edges;
    }

    @Override
    public int getMC() {
        return MC;
    }

    public boolean equals(Object o)
    {
        WGraph_DS  g = (WGraph_DS) o;

        if(g.edges != this.edges || g.nodeSize() != this.nodeSize())
        {
            return false;
        }

        for (node_info n : nodes.values())
        {
            if (g.getNode(n.getKey()) == null || !(this.getNode(n.getKey())).equals(n)) return false;

        }
        for (node_info n : g.nodes.values())
        {
            if ((this.getNode(n.getKey()) == null || !(g.getNode(n.getKey())).equals(n))) return false;
        }
        return true;
    }

}



