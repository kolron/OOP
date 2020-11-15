package ex0;

import java.util.*;

public class Graph_Algo implements graph_algorithms {
    private Graph_DS g;


    public Graph_Algo(graph g) {
        if (g instanceof Graph_DS)
            this.g = (Graph_DS) g;
    }

    public Graph_Algo() {
        this(null);
    } //Graph_Algo is essentially a type of black box that takes a graph and preforms algorithms on it

    /**
     * Graph_Algo
     * A constructor that takes as an arguments g of type graph.
     */
    @Override
    public void init(graph g) //init == initialize
    {
        if (g instanceof Graph_DS)
            this.g = (Graph_DS) g;
    }
    /**
     * init
     * Allows us to initialize a graph to the Graph_algo class,
     * and allows us to change the graph inside this 'black box'.
     */
    @Override
    public graph copy() {
        if (!(this.g == null)) // if g isn't null
            return new Graph_DS(this.g); // return the new graph using the copy constructor in Graph_DS
        else
            return null;
    }

    /**
     * copy
     * This function produces a Hashmap which is a deep copy of the graph
     * uses the Graph_DS copy constructor which makes a deep copy.
     */

    @Override
    public boolean isConnected() {
        if (!(this.g.nodeSize() - 1 <= this.g.edgeSize()))
            return false;
        for (node_data n : g.getV()) { // set all tags to 0 (= "unseen")
            n.setTag(0);
        }
        Iterator<node_data> i = g.getV().iterator(); // create an iterator i with all the nodes in the graph
        node_data src = null; // initialize src variable
        if (i.hasNext())
            src = i.next(); // put first node as src
        else
            return true; // if there are no nodes, could be either true or false, depending on how you define it, i set it as true.

        HashMap<node_data, Integer> dis_Data = distance(src); // create a hashmap named dis_Data to keep all the nodes and their distance from node src
        while (i.hasNext()) // loop through all the other nodes in the graph
        {
            src = i.next(); // take the next node
            if (dis_Data.get(src) == null) // if node in the graph is not inside the hashmap it returns null
                return false; // and means that the graph is not connected
        }
        return true; // means all nodes are inside hash map thus the graph is connected
    }

    /**
     * isConnected
     * Using the BFS algorithm  (VIA distance METHOD)the function goes
     * through the whole graph from a source node.
     * if the distance algorithm returns null, it looked for a node that isn't in the hashmap, meaning the graph isn't connected.
     * If the graph is connected all nodes will be present in distance hashmap.
     * Taken from an online source(Stackoverflow.com geekforgeeks.com)
     */

    public HashMap<node_data, Integer> distance(node_data src) {
        LinkedList<node_data> queue = new LinkedList<>();
        queue.add(src); // add src to queue.
        HashMap<node_data, Integer> ret = new HashMap<node_data, Integer>(); // Integer represents the distance of each node from the src.
        ret.put(src, 0);// insert src into hashmap.
        src.setTag(1); // set the tag of src to 1(=Seen).
        while (!queue.isEmpty()) {
            node_data n = queue.poll(); // take a node out of queue and put int as a node n.
            for (node_data nei : n.getNi()) // check all of n's neighbors.
            {
                if (nei.getTag() == 0) //  if we hasn't a neighbor yet only possible if it isn't a common neighbor of both src and n (and future n's).
                {
                    nei.setTag(1); // set it as 1.
                    ret.put(nei, ret.get(n) + 1); // because the neighbor isn't src's neighbor, its distance from src will be 1 greater than n's.
                    queue.add(nei); // add the neighbor to the queue.
                }
            }
        }
        return ret;
    }

    /**
     * distance (ONE ARG)
     * This function goes through the graph using BFS algorithm and helps us with the isConnected function.
     * It creates a queue and a hashmap on which it calculates the distance, and returns the hashmap with the distances from the source node.
     * The nodes' tag value as a way to check if we've already 'seen' the node with BFS
     * The Integer argument in the hashmap is a way to measure distance from src node.
     */

    @Override
    public int shortestPathDist(int src, int dest) {

        for (node_data n : g.getV()) {
            n.setTag(0); // all tags = 0
        }
        HashMap<node_data, Integer> dis_Data = distance(g.getNode(src), g.getNode(dest)); // get the hashmap with distances from src
        if (dis_Data.containsKey(g.getNode(dest))) // make sure dest is in the hashmap
            return dis_Data.get(g.getNode(dest)); // if so return its distance from src
        else
            return -1; // If theres no path return a negative number to represent that (returned -1 because of test's requirements)
    }

    /**
     * shortestPathDist
     * uses the BFS algorithm (VIA distance function)
     * operates much like the isConnected function.
     * calculates the shortest distance from node src to dest, and returns the path as well
     * Uses the distance function below.
     */

    public HashMap<node_data, Integer> distance(node_data src, node_data dest) {
        LinkedList<node_data> queue = new LinkedList<>();
        queue.add(src); // add src to queue.
        HashMap<node_data, Integer> ret = new HashMap<node_data, Integer>(); // Integer represents the distance of each node from the src.
        ret.put(src, 0);// insert src into hashmap.
        src.setTag(1); // set the tag of src to 1(=Seen).
        while (!queue.isEmpty()) {
            node_data n = queue.poll(); // take a node out of queue and put int as a node n.
            for (node_data nei : n.getNi()) // check all of n's neighbors.
            {
                if (nei.getTag() == 0) //  if we hasn't a neighbor yet only possible if it is a common neighbor of both src and n.
                {
                    nei.setTag(1); // set it as 1.
                    ret.put(nei, ret.get(n) + 1); // because the neighbor isn't src's neighbor, its distance from src will be 1 greater than n's.
                    queue.add(nei); // add the neighbor to the queue.
                }
                if (dest.equals(nei)) // if neighbor was dest
                {
                    break; // stop
                }
            }
        }
        return ret;
    }

    /**
     * distance (TWO ARGS)
     * Function that calculates the distances of nodes from src using the BFS algorithm.
     * This function is almost the same as the first distance function.
     * Difference is that this function calculates the distance node src to node dest, while the other function calculates distance of all nodes from src.
     * Returns a hashmap with distances of nodes from the src node.
     */


    @Override
    public List<node_data> shortestPath(int src, int dest) {
        for (node_data n : g.getV()) // all tags = 0
        {
            n.setTag(0);
        }
        HashMap<node_data, ArrayList<node_data>> _shortestPath = shortestPathHelper(g.getNode(src), g.getNode(dest));
        if (_shortestPath.containsKey(g.getNode(dest)))
            return _shortestPath.get(g.getNode(dest)); // return the path found from dest to src in the ShortestPathHelper function
        else
            return new ArrayList<node_data>(); //if dest isn't in the Hashmap then there's no path.
    }

    /**
     * shortestPath
     * Find the shortest path from src to dest and returning it as an ArrayList
     * Uses the shortestPathHelper function
     */

    public HashMap<node_data, ArrayList<node_data>> shortestPathHelper(node_data src, node_data dest) {
        LinkedList<node_data> queue = new LinkedList<node_data>();
        queue.add(src);
        HashMap<node_data, ArrayList<node_data>> ret = new HashMap<node_data, ArrayList<node_data>>();
        ArrayList<node_data> Src_To_Src = new ArrayList<node_data>(); //ArrayList to hold path from src to src, only holds src and itself(needed in order to have an answer for first query)
        Src_To_Src.add(src);
        ret.put(src, Src_To_Src);
        src.setTag(1);
        while (!queue.isEmpty()) {
            node_data n = queue.poll(); // same as all other similar functions.
            for (node_data nei : n.getNi()) {
                if (nei.getTag() == 0) {
                    nei.setTag(1);
                    ArrayList<node_data> temp = new ArrayList<node_data>(ret.get(n)); //Parameterized constructor call to create an ArrayList with the path it took us to get from src to n
                    temp.add(0, nei); // add this neighbor and put it in the start.
                    ret.put(nei, temp); // add nei and the created arraylist to the hashmap(answer for query of path from nei to src)
                    queue.add(nei);
                }
                if (dest.equals(nei)) // if neighbor was dest
                    break; // stop
                }
            }
        return ret;
        }
    /** shortestPathHelper
     *  uses the BFS algorithm
     *  This function fills a hashmap with an ArrayList holding every node's path to src.
     *  If we reached dest than we can stop as we found what we want.
     *  This function is similar to previous functions here (distance):
     *  It operates nearly in the same way, but the Hashmap that it returns is different than the previous function's Hashmap.
     */
    }
    


