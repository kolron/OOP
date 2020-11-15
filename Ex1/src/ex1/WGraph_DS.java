package ex1;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class WGraph_DS<V,E> implements weighted_graph, Serializable {

    public class NodeInfo implements node_info
    {
        private int key;
        private double tag;
        private String info;
        private int nodeNumber=0;
        public HashMap<Integer,HashMap<node_info,Double>> nei;

        public NodeInfo(int key,int tag,String info)
        {
            this.key = key;
            setTag(tag);
            setInfo(info);
            nodeNumber++;
            this.nei = new HashMap<Integer,HashMap<node_info,Double>>();
        }
        public NodeInfo(int num)
        {
            this(num,0,"");
        }

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
        public void setTag(double t)
        {
            this.tag = t;
        }
        public String toString()
        {
            return "Node ("+getKey()+")";
        }

        /** toString
         * returns a string representing the node's KEY not node's INFO
         * @return
         */

        public HashMap<Integer, HashMap<node_info, Double>> getNeighborMap()
    {
        return this.nei;
    }
        /** getNeighborMap
         gets a nodes' neighbors using a hashmap (each node has a hashmap of neighbors)
         */
        public void removeNode(node_info node)
        {
            if(node != null)
            {
                NodeInfo n = (NodeInfo) node;
                this.nei.remove(node.getKey()); //remove neighbors from hashmap
                n.nei.remove(getKey()); // remove node from neighbor's hashmap
            }
        }
        /** removeNode
         * Remove a node from the neighbor list(and the other way around).
         * Not to be used directly in other Classes. use RemoveNodeHelper for that.
         */

        public void removeNodeHelper()
        {
            node_info temp;
            Iterator<HashMap<node_info,Double>> i = this.nei.values().iterator();
            while (i.hasNext())
            {
                temp = (node_info) i.next();
                i.remove();
                removeNode(temp);
            }
        }
        /** removeNodeHelper
         * A helper function removeNode that uses it
         * This function disconnects this node from all of it's neighbors,
         * And the other way around, used in other functions to enable fast runtime and a simpler removeNodeHelper
         */
    }
    public class Edge
    {
        double weight;
        NodeInfo src;
        NodeInfo dest;

        public Edge(NodeInfo dest, double weight) {
            super();
            this.dest = dest;
            this.weight = weight;
        }
    }
        public HashMap<Integer,node_info> nodes;

        public int edges;
        public int  MC;

        public WGraph_DS() {
            nodes = new HashMap<Integer, node_info>();
            edges = 0;
            MC =0;
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
            return n.getNeighborMap().containsKey(node2); //if getNeighborMap has the other node than theres an edge.
        }
        /** hasEdge
         * Checks if 2 nodes are neighbors, if they are there's an edge between them
         * Checks a node's neighbor Hashmap for the other node, if present then they are neighbors and theres an edge.
         */

    @Override
    public double getEdge(int node1, int node2) {
        return 0;
    }

    @Override
    public void addNode(int key) {

    }

    @Override
    public void connect(int node1, int node2, double w) {

    }

    @Override
    public Collection<node_info> getV() {
        return null;
    }

    @Override
    public Collection<node_info> getV(int node_id) {
        return null;
    }

    @Override
    public node_info removeNode(int key) {
        return null;
    }

    @Override
    public void removeEdge(int node1, int node2) {

    }

    @Override
    public int nodeSize() {
        return 0;
    }

    @Override
    public int edgeSize() {
        return 0;
    }

    @Override
    public int getMC() {
        return 0;
    }

}



