package api;

import java.util.*;

//class that implements node_data which represent a node in the graph
public class NodeData implements node_data{

    enum Colors{
        WHITE,
        GREY,
        BLACK
    }

    //class private variables
    private int key;  //represent the node's key
    private geo_location location;  //represent the node's location
    private double weight;  //represent the node's weight
    private String info;  //represent the node's info
    private int tag;  //represent the node's tag
    private HashMap<Integer, edge_data> neighbors;  //HashMap which represent the node's neighbors (the key of the nodes that this node has edge with and the edge data)
    private HashSet<Integer> destOf;  //hashset to know which nodes are connected to this node (which this node is the dest in the edge)
    private Colors color;
    private double pathDist;

    public NodeData(int key, geo_location location, double weight, String info, int tag, HashMap<Integer, edge_data> neighbors, HashSet<Integer> destOf) {
        this.key = key;
        this.location = location;
        this.weight = weight;
        this.pathDist = pathDist;
        this.info = info;
        this.tag = tag;
        this.neighbors = neighbors;
        this.destOf = destOf;
        this.color = Colors.WHITE;
    }
    public NodeData(int key, int tag, String info)
    {
        this.key = key;
        this.weight = weight;
        this.info = info;
        this.tag = tag;
        this.neighbors = new HashMap<Integer, edge_data>();
        this.destOf =  new HashSet<Integer>();
        this.color = Colors.WHITE;
        this.pathDist = pathDist;
    }
    public NodeData(int key)
    {
        this.key = key;
        this.weight = weight;
        this.info = info;
        this.tag = tag;
        this.neighbors = new HashMap<Integer, edge_data>();
        this.destOf =  new HashSet<Integer>();
        this.color = Colors.WHITE;
        this.pathDist = pathDist;
    }


    //function to get this node's key
    @Override
    public int getKey() {
        return this.key;
    }

    //function to get this node's location
    @Override
    public geo_location getLocation() {
        return this.location;
    }

    //function to set this node's location
    @Override
    public void setLocation(geo_location p) {
        this.location = p;
    }

    //function to get this node's weight
    @Override
    public double getWeight() {
        return this.weight;
    }

    //function to set this node's weight
    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    //function to get this node's info
    @Override
    public String getInfo() {
        return this.info;
    }

    //function to set this node's info
    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    //function to get this node's tag
    @Override
    public int getTag() {
        return this.tag;
    }

    //function to set this node's tag
    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    public Colors getColor() {
        return this.color;
    }

    public void setColor(Colors color) {
        this.color = color;
    }

    //function which return all the nodes in the neighbor hashMap of this node
    public Collection<edge_data> getNi(){
        return this.neighbors.values();
    }

    //function the gets a key and return the edge_data of this node and the node with the given key
    public edge_data getEdge(int key){
        return this.neighbors.get(key);
    }


    //function that gets a node_data and an edge_data and add the node with that edge to this node's neighbors
    public void addNi(node_data neighbor, edge_data edge){
        this.neighbors.put(neighbor.getKey(), edge);
    }

    //function that gets a key and remove the edge with the given key from the this node's neighbors hashMap
    public void removeNi(int key){
        if (this.neighbors.containsKey(key)) {  //if this key is in the hashMap
            this.neighbors.remove(key);  //remove him
        }
    }

    //function that gets a key and remove this key from the destOf Hashset of this node
    public void removeFromDestOf(int key){
        if (this.destOf.contains(key)){  //if this key is in the destOf HashSet
            destOf.remove(key);  //remove him
        }
    }
    public double getPathDist() {
        return this.pathDist;
    }

    public void setPathDist(double pathDist) {
        this.pathDist = pathDist;
    }
    //function that gets node_data and add this node to the destOf hashSet
    public void addNeighborOf(node_data src){
        this.destOf.add(src.getKey());  //add the key of the given node to the hashSet
    }

    //function that return all the keys of the nodes which points to this node (the edge is the "other node -> this node")
    public Collection<Integer> getDestOf(){
        return this.destOf;  //return the destOf hashSet
    }
}
