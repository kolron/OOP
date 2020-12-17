package api;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class DW_GraphDS implements directed_weighted_graph {
    private HashMap<Integer, node_data> g;
    private int mc;
    private int edgeSize;
    private HashSet<Integer> keys;

    public DW_GraphDS( directed_weighted_graph graph){
        DW_GraphDS g_ds = (DW_GraphDS) graph;
//        this.g = new HashMap<>(g_ds.g);
        this.g = new HashMap<>();
        this.g.putAll(g_ds.g);
//        this.keys = new HashSet<>(g_ds.keys);
        this.keys = new HashSet<>();
        this.keys.addAll(g_ds.keys);
        this.edgeSize = graph.edgeSize();
        this.mc = graph.getMC();
    }

    public DW_GraphDS(){
        this.g = new HashMap<Integer, node_data>();
        this.keys = new HashSet<Integer>();
        this.edgeSize = 0;
        this.mc = 0;
    }


    //function that gets a key and return the node with the given key
    @Override
    public node_data getNode(int key) {
        return this.g.get(key);
    }

    //function that gets 2 keys and return the edge between the two nodes with the given keys
    /**GetEdge
     * returns the edge between src and dest, if the edge or either node don't exist return null
     */
    @Override
    public edge_data getEdge(int src, int dest) {
        if (g.get(src) != null){  //if the src node is in the graph
            NodeData s = (NodeData)g.get(src);  //src node for the node with the given src key
            return s.getEdge(dest);  //return the edge between the src and dest
        }
        return null;  //if sec node is not in the graph return null
    }

    //function that gets node_data and add him to the graph
    @Override
    public void addNode(node_data n) {
        if (keys.contains(n.getKey())){  //if this node is already in the graph
            return;  //return in order to stop the function
        }
        keys.add(n.getKey());  //add the node's key into the hashSet of the nodes
        g.put(n.getKey(), n);  //add this node to the hashMap with his key
        this.mc++;  //increase mc
    }

    //function that gets 2 keys (src and dest) and double weight and make a connection between the 2 nodes with the given keys
    @Override
/**connect
 * @param src connect this node with
 * @param dest this node.
 * @param w the weight of the edge between them.
 *
 * This function connects 2 nodes, and gives their edge the weight w.
 * First of all check if neither nodes aren't null or otherwise the same, and the given weight is positive )
 * Second, check if they are already connected, if they are simply update the weight between them if its not already it.
 * If they aren't connected, connect them by created an appropriate edge and add the edge and each node to the corresponding hashMaps.
 */    public void connect(int src, int dest, double w) {
        if(src != dest && w>0){  //if src and dest are different nodes and weight is a positive number
            NodeData src_node = (NodeData) this.g.get(src);  //the src node with the given src key
            NodeData dest_node = (NodeData) this.g.get(dest);  //the dest node with the given dest key
            if (src_node != null && dest_node != null){  //if the nodes existed in the graph
                if(src_node.getEdge(dest) == null){  //if there is no existing edge between src to dest
                    EdgeData edge = new EdgeData(src, dest, w, "", 0);  //create an edge with the given src dest and weight
                    src_node.addNi(dest_node, edge);  //add the dest node to src node's neighbors
                    dest_node.addNeighborOf(src_node);  //add to dest src node to the destOf hashSet
                    this.mc++;  //increase mc by 1
                    this.edgeSize++;  //increase edgeSize by 1
                }
                else{  //if there is an existing edge between src to dest
                    EdgeData e = (EdgeData) (src_node.getEdge(dest));
                    e.setWeight(w);
                    this.mc++;  //increase mc by 1
                }
            }
        }
    }
    /**con ect
     * @param e the edge we wish to connect
     * Unlike previous connect method This method connects 2 nodes, via and edge and not via 2 nodes and weight.
     * It operates in an identical matter -
     * first of all check if neither nodes are null or otherwise the same, or the given weight of the provided edge is greater than 0 )
     * if all conditions meet, add the node and the edge directly to the correct hashmaps of both nodes.
     */
    public void connect(edge_data e) {
        EdgeData edge = (EdgeData) e;
        if(edge.getSrc() != edge.getDest() && edge.getWeight()>0){  //if src and dest are different nodes and weight is a positive number
            NodeData src_node = (NodeData) this.g.get(edge.getSrc());  //the src node with the given src key
            NodeData dest_node = (NodeData) this.g.get(edge.getDest());  //the dest node with the given dest key
            if (src_node != null && dest_node != null){  //if the nodes existed in the graph
                if(src_node.getEdge(edge.getDest()) == null){  //if there is no existing edge between src to dest
//                    EdgeData edge = new EdgeData(src, dest, w, "", 0);  //create an edge with the given src dest and weight
                    src_node.addNi(dest_node, edge);  //add the dest node to src node's neighbors
                    dest_node.addNeighborOf(src_node);  //add to dest src node to the destOf hashSet
                    this.mc++;  //increase mc by 1
                    this.edgeSize++;  //increase edgeSize by 1
                }
                else{  //if there is an existing edge between src to dest
//                    EdgeData edge = new EdgeData(src, dest, w, "", 0);  //create an edge with the given src dest and weight
                    //TODO instead of creating new edge and adding it to the Hashmap use setWeight function to update the weight
                    src_node.addNi(dest_node, edge);  //add the dest node to src node's neighbors
                    dest_node.addNeighborOf(src_node);  //add to dest src node to the destOf hashSet
                    this.mc++;  //increase mc by 1
                }
            }
        }
    }

    /** GetV
     * @return a shallow copy of all the nodes in the graph.
     */
    //function that return all thr nodes in the graph
    @Override
    public Collection<node_data> getV() {
        return this.g.values();  //return the values of g (node_data)
    }

    /** GetE
     * @param node_id is the key of a node.
     * @return a collection representing all the edge that originated from this node.
     */
    //function that gets key and return all the edges which starts from this node
    @Override
    public Collection<edge_data> getE(int node_id) {
        return ((NodeData)this.g.get(node_id)).getNi();  //return all the edges which start from the node with the given key
    }
    /** removeNode
     * @param key the node to be removed.
     * This function checks whether or not the node is in the graph first.
     * If it is, remove the corresponding number of edges, and update the MC accordingly,
     * It removes the node from all of its neighbors and updates each neighbor's HashMap correctly.
     * In addition it removes the node from the destOf HashMap of nodes who were the destination of an edge from this node.
     */
    //function that gets key and remove the node and all the connected edge to the node from the graph
    @Override
    public node_data removeNode(int key) {
        if (this.g.get(key) != null){  //if the node with the given key is in the graph
            NodeData removedNode = (NodeData)this.g.get(key);  //get that node and cast him to NodeData
            for (int srcKey : removedNode.getDestOf()) {  //go through each node that has a edge that ends in the node which we want to remove
                ((NodeData)this.g.get(srcKey)).removeNi(key);  //go to the src node and remove the wanted node from his neighbors hashMap
//                removedNode.removeFromDestOf(srcKey);  //from the wanted node remove the src node from his destOf hashMap
                this.edgeSize--;  //decrease the amount of edges by 1
                this.mc++;  //increase the amount of changes in the graph by 1
            }
            for (edge_data edge : removedNode.getNi()) {  //go through all the edge which starts at the wanted node
                NodeData destNode = (NodeData)this.g.get(edge.getDest());  //get the dest node of this edge
                destNode.removeFromDestOf(key);  //from this dest node remove the wanted node from his destOf hashSet
                this.edgeSize--;  //decrease the amount of edges by 1
                this.mc++;  //increase the amount of changes in the graph by 1
            }
            this.g.remove(key);  //remove the node with the given key from the graph
            this.mc++;  //increase the amount of changes in the graph by 1
            return removedNode;  //return the removed node
        }
        return null;  //if this node is not existing on the graph return null
    }

    /**RemoveEdge
     * @param src
     * @param dest
     * finds the correct edge between src and dest to surgically remove dest from src's neighbor HashMap,
     * and remove src from the destOf hashmap of dest
     * update edges and MC accordingly
     */
    //function that gets 2 keys and remove the edge between the nodes with the given keys
    @Override
    public edge_data removeEdge(int src, int dest) {
        if (this.g.containsKey(src) && this.g.containsKey(dest)){  //if the nodes with the given keys are in the graph
            NodeData srcNode = (NodeData) this.g.get(src);  //the node with the src key
            NodeData destNode = (NodeData) this.g.get(dest);  //the node with the dest key
            EdgeData removedEdge = (EdgeData) srcNode.getEdge(dest);  //the edge which start at the src node and end in the dest node
            srcNode.removeNi(dest);  //remove the dest node from the src neighbors
            destNode.removeFromDestOf(src);  //remove the src node from the destOf HashSet of the dest node
            if (removedEdge != null){  //if the removed edge is not null (there was an edge in the beginning)
                this.edgeSize--;  //decrease the amount of edges by 1
                this.mc++;  //increase the mount of changes in the graph
                return removedEdge;  //remove the data of the edge we removed
            }
            return null;  //if the removed edge value is null return null
        }
        return null;  //if the nodes are not in the graph return null
    }

    //function that return the amount of nodes in the graph
    @Override
    public int nodeSize() {
        return this.g.size();  //return the amount of nodes that are in the graph g
    }

    //function that return the amount of edges in the graph
    @Override
    public int edgeSize() {
        return this.edgeSize;
    }

    //function that return the amount of changes that happened in the graph
    @Override
    public int getMC() {
        return this.mc;
    }

    /** equals
     *
     * @param o the graph we are checking equality to
     * @return true if graphs are equal, false if not.
     * This function replaces the standard equal function in Object.
     * graph equality is defined here if all nodes are equal in both graphs.
     * (Equal nodes are nodes with the same key, Weights, Neighbors.)
     * First it checks some for some standard requirements for equality (equal size of edges and nodes)
     * than it checks if every node with a key K is equal to the corresponding node in graph o(=g), with the same key.
     * than does the same for graph o.
     * return true only if passed all tests.
     */
    public boolean equals(Object o)
    {
        DW_GraphDS  g = (DW_GraphDS) o;

        if(g.edgeSize() != this.edgeSize() || g.nodeSize() != this.nodeSize())
        {
            return false;
        }

        for (node_data n : this.getV())
        {
            if (g.getNode(n.getKey()) == null || !(this.getNode(n.getKey())).equals(n)) return false;

        }
        for (node_data n : g.getV())
        {
            if ((this.getNode(n.getKey()) == null || !(g.getNode(n.getKey())).equals(n))) return false;
        }
        return true;
    }
}