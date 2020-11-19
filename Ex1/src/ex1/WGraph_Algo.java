package ex1;
import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms, Serializable
    {
        /**WGraph_Algo (CLASS)
         * this class is essentially a black box to which you insert a graph, and can now perform several functions on.
         */
    public weighted_graph graph;

        /**WGraph_Algo()
         * Standard constructor
         */
    public WGraph_Algo() {this.graph = new WGraph_DS();}


        /**init
         * @param g the weighted_graph to be initialized.
         * This function 'puts' a new graph inside the black box.
         */
    @Override
    public void init(weighted_graph g)
    {
        if (g instanceof WGraph_DS)
        this.graph = g;
    }

    @Override
    public  weighted_graph getGraph() { return this.graph;}

         /** copy
          * *This function
          * @returns a deep copy of this graph
          * Uses the deep copy constructor of WGraph_DS.
          */
    @Override
    public weighted_graph copy() {
        if (!(this.graph == null)) // if g isn't null
            return new WGraph_DS((WGraph_DS)this.graph); // return the new graph using the copy constructor in WGraph_DS
        else
            return null;
    }

        /** isConnceted
         * @return true if the graph is connected.
         * At first it checks if for some basic requirements for a quicker return.
         * Then, creates a new HashSet and Queue, traverses if all nodes in the graph, sets tag to 0 (=unseen)
         * than creates an iterator, which iterates over the graph and adds each node to the hashset of edges and queue of nodes.
         * now, after all nodes in the graph are in the queue and hashset.
         * pull the first node from the queue and loop over each of its neighbors, if its the first time we've seen a node set it's tag to 1 and add it
         * to the queue and edges.
         * eventually since we've traversed on all neighbors in the graph, and each time we "moved" from 1 neighbor to another
         * we essentially traversed on all nodes that have an edge ,now we compare if the number of the nodes in the graph
         * is equal to the amount of nodes that we reached in the algorithm (nodes that have an edge connected to them, that allows eventually to reach all the graph that way)
         * if it is, than we have reached the entire graph whilst traversing on edges, if false, it means there is a node that we didn't reach - a node that has no neighbors and
         * Is not connected to any other node.
         */
    @Override
    public boolean isConnected()
    {
        if (graph.nodeSize() <= 1) //graph with 1 or 0 nodes is connected.
        return true;

        if(this.graph.nodeSize()-1>this.graph.edgeSize())
            return false;

        HashSet<node_info> fromEdges = new HashSet<>();
        Queue<node_info> queue = new LinkedList<>();
        for (node_info t : graph.getV())
        {
            t.setTag(0);
        }

        Iterator<node_info> i =this.graph.getV().iterator();
        node_info src;
        if(i.hasNext())
        {
            src = i.next();
        }
        else {
            return true;
        }
        queue.add(src);
        fromEdges.add(src);
        while(!queue.isEmpty())
        {
            node_info temp = queue.poll();
            for(node_info n: ((WGraph_DS.NodeInfo)temp).getNi())
            {
                if(n.getTag()==0)
                {
                    queue.add(n);
                    n.setTag(1);
                    fromEdges.add(n);
                }
            }
        }
        return this.graph.nodeSize() == fromEdges.size();
    }


        /**Djik
         * This is an implementation of Djikstra's algorithm on weighted graph
         * @param src the node we start traversal from.
         * Algorithm finds smallest weight to each node from a node src, using a priority queue.
         * To set and hold the accumulative weight to a node, I used the Tag field on each node.
         * we started off by creating an Comparator to compare to nodes using their tag.
         * set all tags of all nodes to -1, meaning we have yet to see them. The first time we see a node we update it's tag to the accumulative
         * weight it took to reach it. since Weights are >= 0, -1 means we have yet to update the node.
         *
         * Created a priority queue to compare and sort the nodes.
         * Added source to the Priority Queue (=pQue)
         * now, for each node in the graph, starting with src,
         *            poll from queue, and for each of it's neighbors:
         *            if we have yet to set its tag even once, set the accumuliatve starting weight, and add the neighbor to the pQue.
         *            if we have seen the node before (tag!=1) check if its current weight is larger than the one we reaced it with in this iteration (if yes than current route = better)
         *            add n to the pQue.
         * once the pQue is empty we have finished traversal and found the shortest weight to each node from node src, and the tags are representing that weight.
         * we can now use this algorithm to check what the smallest distance is, and with a slight modification we can find the exact path.
         */
    public void Djik(int src) {
        node_info source = graph.getNode(src);
        for (node_info n : graph.getV()) {
            n.setTag(-1);
        }
        PriorityQueue<node_info> pQue = new PriorityQueue<>(new Comperator());
        source.setTag(0);
        pQue.add(source);
        while (!pQue.isEmpty())
        {
            WGraph_DS.NodeInfo curr = (WGraph_DS.NodeInfo) pQue.poll();
            for (node_info n : curr.getNi())
            {
                if (n.getTag() == -1)
                {
                    n.setTag(curr.getTag() + curr.getWeight(n.getKey()));
                    pQue.add(n);
                }
                if(n.getTag() > (curr.getTag() + curr.getWeight(n.getKey())))
                {
                    pQue.remove(n);
                    n.setTag((curr.getTag() + curr.getWeight(n.getKey())));
                    pQue.add(n);
                }
            }
        }

    }

        /** ShortestPathDist
         * @param src - start node for Djik
         * @param dest - the node we want to find the smallest weight to.
         * @return the weight to dest.
         * This function uses Djik to set all tags to the shortest weight from src, and eventually just returns the tag of dst.
         * the Djik function did all the work for us, we simply retrieved the information.
         *
         * before we call Djik we make some basic checks to see if there can be a path(if same node, weight is 0,
         *               (if no path (either node is null or Djik didn't reach the dest node)) than weight is -1.
         */
    @Override
    public double shortestPathDist(int src, int dest)
    {
        node_info source = graph.getNode(src);
        node_info dst = graph.getNode(dest);
        if (src == dest)
            return 0;
        if (source == null || dst == null)
            return -1;
        Djik(src);
        return dst.getTag();
    }

        /**shortestPath
         * @return an Arraylist that contains node of the shortest path from
         * @param src - start node to:
         * @param dest - end (target) node
         * This function is almost identical to the combination of Djik and ShortestPathDist.
         * It uses the same Djik algorithm but with a slight modification.
         * We created a new HashMap that holds a node and it's parent (parent = the node that led us to it in the algorithm)
         * that HashMap will allow us to retrace our steps from dest back to src.
         *             Algorithm same as before, but now if the path to node n is shorter, we put the node that led us to n in a HashMap with n, allowing us to retrace our steps.
         *             once all optimal weights have been found and the hashmap is complete with the shortest retraced path to each node from src,
         *             we first check if we even reached dest, if its not found in the parent HashMap than it has no parent -> no path -> return null
         *             if it is present there, create an ArrayList Path to hold the path, and as long as we didn't finish traversal(happens when dest is source)
         *             to path, add the retraced node starting from destination, backwards until we reach src.
         *             now since now path has the shortest path from dest TO src, we must reverse it to get the path from
         *             src to dest
         */
    @Override
    public List<node_info> shortestPath(int src, int dest)
    {
        HashMap<node_info, node_info> parent = new HashMap<>();
        node_info source = graph.getNode(src);
        node_info destination = graph.getNode(dest);
        for (node_info n : graph.getV()) {
            n.setTag(-1);
        }
        PriorityQueue<node_info> pQue = new PriorityQueue<>(new Comperator());
        source.setTag(0);
        pQue.add(source);
        while (!pQue.isEmpty())
        {
            WGraph_DS.NodeInfo curr = (WGraph_DS.NodeInfo) pQue.poll();
            for (node_info n : curr.getNi())
            {
                if (n.getTag() == -1)
                {
                    n.setTag(curr.getTag() + curr.getWeight(n.getKey()));
                    pQue.add(n);
                    parent.put(n,curr);
                }
                if(n.getTag() > (curr.getTag() + curr.getWeight(n.getKey())))
                {
                    pQue.remove(n);
                    n.setTag((curr.getTag() + curr.getWeight(n.getKey())));
                    pQue.add(n);
                    parent.put(n,curr);
                }
            }
        }

        if (!parent.containsKey(destination))
        {
            return null;
        }

        ArrayList<node_info> path = new ArrayList<>();
        while(parent.get(destination) != source)
        {
            path.add(parent.get(destination));
            destination = parent.get(destination);
        }
        path.add(parent.get(destination));
        Collections.reverse(path);
        return path;
    }

        /**save
         * standard save function, using serializable
         * create a file "Writing" (output) stream,
         * create an Object "Writing" (output) stream, that writes to the file Output stream.
         *
         * give the Object Output stream the graph to write to the file Output Stream.
         * close both streams.
         *
         * @param file - the file name (may include a relative path).
         * @return true if we managed to save the file
         * @return false if we didn't manage to save the file
         * @catch an IOException and prints the stack trace,
         */

    @Override
    public boolean save(String file) {
        try {
            FileOutputStream file_out = new FileOutputStream(file);
            ObjectOutputStream obj_out = new ObjectOutputStream(file_out);
            obj_out.writeObject(this.graph);
            obj_out.close();
            file_out.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
        /**load
         * standard load function, using serializable
         * create a file "Reading" (Input) stream,
         * create an Object "Reading" (Input) stream, that reads to the Object fromthe file Input stream.
         *
         * cast the read Object to a weighted graph.
         * close both streams
         *
         * @param file - the file name (may include a relative path).
         * @return true if we managed to load the Object
         * @return false if we didn't manage to load the Object
         * @catch an IOException and prints the stack trace,
         */
        @Override
    public boolean load(String file) {
            try {
                FileInputStream file_in = new FileInputStream(file);
                ObjectInputStream obj_in = new ObjectInputStream(file_in);
                graph = (weighted_graph) obj_in.readObject();
                obj_in.close();
                file_in.close();
                return true;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return false;

    }

        /** Comperator
         * and inner Comperator class that implements a compare function.
         */
        private class Comperator implements Comparator<node_info> , Serializable
        {
            /** compare
             * a function to override the default compare function,
             * used to compare between two nodes' tags.
             * used in the Djikstra algorithms.
             */
            @Override
            public int compare(node_info o1, node_info o2)
            {
                return Double.compare(o1.getTag(), o2.getTag());
            }
        }

}
