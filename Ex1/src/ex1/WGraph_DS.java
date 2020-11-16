package ex1;
import java.io.Serializable;
import java.util.*;

public class WGraph_DS implements weighted_graph, Serializable {

    public class NodeInfo implements node_info
    {
        private int key;
        private double tag;
        private String info;
        private int nodeNumber = 0;
        private HashMap<Integer,node_info> nei;
        private HashMap<Integer, Double> neiWeight;

        public NodeInfo(int key,int tag,String info)
        {
            this.key = key;
            setTag(tag);
            setInfo(info);
            nodeNumber++;
            this.nei = new HashMap<Integer,node_info>();
            this.neiWeight = new HashMap<Integer, Double>();
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
        public HashMap<Integer, node_info> getNeighborMap()
        {
            return this.nei;
        }
        /** getNeighborMap
         gets a nodes' neighbors using a hashmap (each node has a hashmap of neighbors)
         */
        public Collection<node_info> getNi() { return this.nei.values(); }

        public void removeNode(node_info node)
        {
            if(node != null)
            {
                NodeInfo n = (NodeInfo) node;
                this.nei.remove(node.getKey()); //remove neighbors from hashmap
                n.nei.remove(getKey()); // remove node from neighbor's hashmap
                this.neiWeight.remove(node.getKey());
                n.neiWeight.remove(node.getKey());
            }
        }
        /** removeNode
         * Remove a node from the neighbor list(and the other way around).
         * Not to be used directly in other Classes. use RemoveNodeHelper for that.
         */

        public void removeNodeHelper()
        {
            node_info temp;
            Iterator<node_info> i = this.nei.values().iterator();
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
                NodeInfo n = (NodeInfo) t;
                n.removeNodeHelper(); //removeNodeHelper (remove from neighbor's map)
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


}



