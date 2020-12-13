package api;

import org.w3c.dom.Node;

import java.util.*;
import java.io.*;
import com.google.gson.*;
import org.json.*;

public class DWGraph_Algo implements dw_graph_algorithms {
    private directed_weighted_graph g;
    private HashMap<node_data, node_data> pred;  //private hashmap for contain the pred of each node of the shortest path

    public DWGraph_Algo() {
        this.g = null;
        pred = new HashMap<node_data, node_data>();
    }


    @Override
    public void init(directed_weighted_graph g) {
        this.g = g;
        pred = new HashMap<node_data, node_data>();
    }

    @Override
    public directed_weighted_graph getGraph() {
        return this.g;
    }

    @Override
    public directed_weighted_graph copy() {
        directed_weighted_graph graph = new DW_GraphDS();
        Iterator<node_data> itr = g.getV().iterator();
        while (itr.hasNext()) {
            node_data current = itr.next();
            node_data newCopiedNode = new NodeData(current.getKey());
            graph.addNode(newCopiedNode);
            newCopiedNode.setInfo("NEW!!");
        }
        itr = g.getV().iterator();
        while (itr.hasNext()) {
            node_data current = itr.next();
            Iterator<edge_data> neiItr = g.getE(current.getKey()).iterator();
            while (neiItr.hasNext()) {
                edge_data e = neiItr.next();
                graph.connect(e.getSrc(), e.getDest(), e.getWeight());
            }
        }
        return graph;
    }

    @Override
    public boolean isConnected() {
        if (this.g.nodeSize() < 2) {  //if there is less than 2 nodes in the graph
            return true;
        }
        if (this.g.nodeSize() > this.g.edgeSize() + 1) {  //if there are more nodes then edges+1
            return false;  //return false
        }
        dfs();
        for (node_data n : this.g.getV()) {
            NodeData node = (NodeData) n;
            if (node.getColor() == NodeData.Colors.WHITE) {
                return false;
            }
        }
        HashMap<node_data, node_data> hash = new HashMap<>(this.pred);

        DW_GraphDS reversedGraph = (DW_GraphDS) reverse_graph(this);
        this.init(reversedGraph);
        dfs();
        for (node_data n : this.g.getV()) {
            NodeData node = (NodeData) n;
            if (node.getColor() == NodeData.Colors.WHITE) {
                this.init(g);
                this.pred = hash;
                return false;
            }
        }
        this.init(g);
        this.pred = hash;
        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        if (this.g.getNode(src) != null && this.g.getNode(dest) != null) {  //if the nodes are in the graph
            if (src == dest) {  //if the nodes are the same node
                return 0;
            }
            Dijkstra(src, dest);  //call the helping function dijkstra
            NodeData destNode = (NodeData) this.g.getNode(dest);
            return destNode.getPathDist();  //return the tag of the node with the dest key
        }
        return -1;  //if one of the nodes are not in the graph return -1
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        ArrayList<node_data> path = new ArrayList<node_data>();  //create new arrayList
        if (this.g.getNode(src) != null && this.g.getNode(dest) != null) {  //if the nodes are in the graph
            path.add(this.g.getNode(dest));  //add the node to the arrayList
            if (src == dest) {  //if the nodes are the same
                return path;  //retrun the arrayList
            } else {
                Dijkstra(src, dest);  //call the helping function dijkstra
                if (!this.pred.containsKey(this.g.getNode(dest))) {  //if pred doesn't not contain the dest key
                    return null;  //return null
                }
                node_data predNode = this.pred.get(this.g.getNode(dest));  //node to represent the pred of the dest node
                while (this.pred.get(predNode) != this.g.getNode(src) && this.pred.get(predNode) != null) {  //while loop until the pred node is our wanted src node
                    path.add(predNode);  //add the current node to the path
                    predNode = this.pred.get(predNode);  //set predNode to the pred of the current node
                }
                path.add(predNode);  //add the node to the path
                if (this.pred.get(predNode) != null) {
                    path.add(this.pred.get(predNode)); //add the pred of the current node to the path
                }
                Collections.reverse(path);  //use reverse function to reverse the values of path
                return path;  //return path
            }
        }
        return null;  //if the nodes are not in the graph
    }

    @Override
    public boolean save(String file) {
        String json;
        try {
            json  = jsonGraph(g);
            try {
                PrintWriter writer = new PrintWriter(file);
                writer.write(json);
                writer.close();
                return true;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (JSONException je)
        {
            je.printStackTrace();
        }

        return false;

    }
    /** jsonGraph
     * this method takes a directed weighted graph and turns in to a write-able JSON object
     * nodes and edges in the graph are represented by a JSONArray, while the graph itself is the JSONObject,
     * iterates through all nodes, and than all of each node's edges and adds it to the respective array,
     * once finished adds the 2 arrays to the graph(Json).
     */
    private String jsonGraph(directed_weighted_graph g) throws JSONException {
        JSONObject graph = new JSONObject();
        JSONArray nodes = new JSONArray();
        JSONArray edges = new JSONArray();
        Iterator<node_data> nodeItr = g.getV().iterator();
        while (nodeItr.hasNext()) { //Iterate over all the nodes
            nodes.put(jsonNodes(nodeItr.next())); // add each node to node array as a JSONObject
            }


        nodeItr = g.getV().iterator(); //remake node Iterator
        while(nodeItr.hasNext()) {
            Iterator<edge_data> EdgeItr = g.getE(nodeItr.next().getKey()).iterator();
            while(EdgeItr.hasNext())
            {
               edges.put(jsonEdges(EdgeItr.next()));
            }
        }
        graph.put("Nodes", nodes);
        graph.put("Edges",edges);
        return graph.toString();

    }

    /** jsonNodes
     * method to turn each node_data object into a JSONObject, that represents a node, with their key and location.
     */
    private JSONObject jsonNodes(node_data node) throws JSONException {
        JSONObject jNode = new JSONObject();
        jNode.put("Key", node.getKey());
        jNode.put("Tag", node.getTag());
        jNode.put("Info", node.getInfo());
        jNode.put("Weight", node.getWeight());
        jNode.put("Location", node.getLocation());
        return jNode;
    }
    /** jsonNodes
     * method to turn each edge_data object into a JSONObject, that represents an edge, with their src ,destination and weight
     */
    private JSONObject jsonEdges(edge_data e) throws JSONException {
        JSONObject jEdge = new JSONObject();
        jEdge.put("src", e.getSrc());
        jEdge.put("dest",e.getDest());
        jEdge.put("w",e.getWeight());

        return jEdge;
    }

    @Override
    public boolean load (String file)
    {
        try
        {
            GsonBuilder build = new GsonBuilder();
            Gson gs = build.create();
            FileReader reader = new FileReader(file);
            this.g = gs.fromJson(reader, DW_GraphDS.class);
            System.out.println(this.g);
            return true;

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    private void dfs() {
        for (node_data n : this.g.getV()) {
            NodeData node = (NodeData) n;
            node.setColor(NodeData.Colors.WHITE);
        }
        pred.clear();
//        time = 0;    //TODO in pseudo code there is a time variables
        for (node_data n : this.g.getV()) {
            NodeData node = (NodeData) n;
            dfs_visit(node);
            break;
        }
    }



    private void dfs_visit (NodeData node){
            node.setColor(NodeData.Colors.GREY);
//        time = time + 1 ;
//        node.setFirstTime(time);
            for (edge_data edge : node.getNi()) {
                NodeData neighbor = (NodeData) (this.g.getNode(edge.getDest()));
                if (neighbor.getColor() == NodeData.Colors.WHITE) {
                    pred.put(neighbor, node);
                    dfs_visit(neighbor);
                }
            }
            node.setColor(NodeData.Colors.BLACK);
//        node.setLastTime = ++time;
        }

        private void Dijkstra ( int src, int dist){

            PriorityQueue<NodeData> pQueue = new PriorityQueue<NodeData>(this.g.nodeSize(), new NodeInfoCompare());  //create priorityQueue
            this.pred.clear();  //clear the pred hashMap

            for (node_data node : this.g.getV()) {  //loop for to go through every node in the graph
                ((NodeData) (node)).setPathDist(-1);  //set every node tag to -1
            }

            NodeData srcNode = (NodeData) this.g.getNode(src);
            pQueue.add(srcNode);  //add to the PQ the src node
            srcNode.setPathDist(0);  //set the tag of the src node to 0

            while (!pQueue.isEmpty()) {  //while out PQ is not empty
                NodeData n = pQueue.poll();  //poll from the pq the node at the head
                for (edge_data edge : n.getNi()) {  //go through every neighbor of this node
                    NodeData neighbor = (NodeData) this.g.getNode(edge.getDest());

                    if (neighbor.getPathDist() == -1) {  //if this neighbor's tag is -1 (first time we visit him)
                        neighbor.setPathDist(n.getPathDist() + edge.getWeight());  //set his tag to his pred tag + the weight of the edge between those 2
                        pQueue.remove(neighbor);  //remove the neighbor from the pq
                        pQueue.add((NodeData) neighbor);  //add the neighbor to the pq after we updated him
                        pred.put(neighbor, n);  //update the neighbor in the pred hashMap
                    } else {  //if his tag isn't -1 (we already visited him)
                        if (neighbor.getPathDist() > n.getPathDist() + edge.getWeight()) {  //if the neighbor's tag is bigger the the tag of him pred and the weight of the edge
                            neighbor.setPathDist(n.getPathDist() + edge.getWeight());  //update the tag to the smallest value
                            pQueue.remove(neighbor);  //remove the neighbor from the pq
                            pQueue.add(neighbor);  //add the neighbor to the pq after we updated him
                            pred.put(neighbor, n);  //update the neighbor in the pred hashMap
                        }
                    }
                }
            }
        }

        public void reverse_edge (directed_weighted_graph g, edge_data edge){
            EdgeData reverseEdge = new EdgeData(edge.getDest(), edge.getSrc(), edge.getWeight(), "", 1);
            DW_GraphDS graph = (DW_GraphDS) g;
            if (edge.getTag() != 1) {
                graph.connect(reverseEdge);
                graph.removeEdge(edge.getSrc(), edge.getDest());

            }
        }
        public directed_weighted_graph reverse_graph (dw_graph_algorithms g){

            DW_GraphDS g1 = (DW_GraphDS) g.copy();
            for (node_data n : g1.getV()) {
                NodeData node = (NodeData) n;

                for (edge_data e : node.getNi()) {
                    EdgeData edge = (EdgeData) e;
                    reverse_edge(g1, edge);
                }
            }
            return g1;
        }


        public class NodeInfoCompare implements Comparator<node_data> {

            @Override
            public int compare(node_data o1, node_data o2) {
                return Double.compare(o1.getTag(), o2.getTag());
            }
        }
    }
