package api;

import com.google.gson.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.*;
import api.geo_location;

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
        if (this.g.nodeSize() < 2){
            return true;
        }
        if (this.g.nodeSize() > this.g.edgeSize() + 1) {  //if there are more nodes then edges+1
            return false;  //return false
        }
        NodeData srcNode = null;
        for (node_data n : this.g.getV()) {
            srcNode = (NodeData)n;
            break;
        }
        dfs(srcNode);
        for (node_data n : this.g.getV()) {
            NodeData node = (NodeData) n;
            if (node.getColor() == NodeData.Colors.WHITE){
                return false;
            }
        }
        DW_GraphDS reversedGraph = (DW_GraphDS) this.copy();
        HashMap<node_data, node_data> tempPred = new HashMap<>(this.pred);
        HashMap<Integer, node_data> reversed = new HashMap<>();
        reversed = reverse_graph(reversedGraph);

        srcNode =(NodeData) reversed.get(srcNode.getKey());
        dfs(srcNode, reversed);
        for (node_data n : reversed.values()) {
            NodeData node = (NodeData) n;
            if (node.getColor() == NodeData.Colors.WHITE){
                return false;
            }
        }
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
        jNode.put("id", node.getKey());
        jNode.put("tag", node.getTag());
        jNode.put("info", node.getInfo());
        jNode.put("weight", node.getWeight());
        if(node.getLocation() == null)
        {
            jNode.put("pos", "0.0,0.0,0.0");
        }
        else
            jNode.put("pos", node.getLocation());
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

    /**
     * @param file - file name of JSON file
     * method to load a graph from a file in JSON formatting.
     * uses Gson's JsonDeserializer interface to deserialize the json object.
     */
    @Override
    public boolean load (String file)
    {
        try
        {
            Gson build = new GsonBuilder().registerTypeAdapter(DW_GraphDS.class, new GraphDeserializer()).create();
            FileReader reader = new FileReader(file);
            this.g = build.fromJson(reader, DW_GraphDS.class);
            System.out.println(this.g);
            return true;

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    /** addJsonNode
     * method to load nodes from a JSON object formatted in the following way:
     * **arrayName**:[{"id": num1, "pos":num2}, {"id":.. "pos":..}..]
     * into graph g
     */
    private void loadJsonNode(JsonArray nodes, directed_weighted_graph g)
    {
        int key;
        String str;
        for (JsonElement node : nodes) {
            key = node.getAsJsonObject().get("id").getAsInt();
            str = node.getAsJsonObject().get("pos").getAsString();
            node_data curr = new NodeData(key);
            curr.setLocation(loadJsonPos((str)));
            g.addNode(curr);
        }
    }

    /** loadJsonPos
     * method to load a node's pos(x,y,z) (as a geo_location object) from a JSON object
     * parses the pos field in the following format: **arrayName**:[{"id": num1, "pos":x,y,z}..]
     * as (double)x
     * (double)y
     * (double)z
     * and creates a geo_location object with these coordinates
     */
    private geo_location loadJsonPos(String str) {
        double x;
        double y;
        double z;
        String[] pos = str.split(",");
        x = Double.parseDouble(pos[0]);
        y = Double.parseDouble(pos[1]);
        z = Double.parseDouble(pos[2]);
        geo_location point = new GeoLocation(x,y,z);
        return point;
    }

    /** loadJsonEdge
     * method to "load" edges from a JSON object into graph g
     * gets the src, weight, and dest from a file formatted in the following way:
     * **arrayName**:[{"src": num1, "w":num2 "dest":num3 }, {"src":.. "w":.. "dest":..}..]
     * loads by connecting the edge with the respective nodes with the given weight.
     */
    private void loadJsonEdge(JsonArray edges, directed_weighted_graph g)
    {
        int src ;
        int dest  ;
        double weight ;
        for (JsonElement edge : edges)
        {
            src = edge.getAsJsonObject().get("src").getAsInt();
            weight = edge.getAsJsonObject().get("w").getAsDouble();
            dest = edge.getAsJsonObject().get("dest").getAsInt();
            g.connect(src, dest, weight);

        }
    }

    /** GraphDeserializer
     * a class required to be inorder to use Gson's JsonDeserializer<T> interface
     * in order to create a DW_GraphDS object
     * using the custom deserialization method deserialize as described in the documentation
     * https://www.javadoc.io/doc/com.google.code.gson/gson/2.6.2/com/google/gson/JsonDeserializer.html
     */
    class GraphDeserializer implements JsonDeserializer<DW_GraphDS> {
        @Override
        public DW_GraphDS deserialize(JsonElement str, Type DW_GraphDS, JsonDeserializationContext context) {
            directed_weighted_graph g = new DW_GraphDS();
            JsonObject obj = str.getAsJsonObject();
            JsonArray nodes = obj.get("Nodes").getAsJsonArray();
            JsonArray edges = obj.get("Edges").getAsJsonArray();
            try { //NODES MUST BE LOADED FIRST
                loadJsonNode(nodes, g);
            } catch (FailedToLoadNodesException e) {
                    throw new FailedToLoadNodesException("Failed to load nodes");
                }

            loadJsonEdge(edges, g);
            return (DW_GraphDS) g;
        }
    }

    //TODO insert here
    private void dfs(NodeData srcNode) {
        for (node_data n : this.g.getV()) {
            NodeData node = (NodeData) n;
            node.setColor(NodeData.Colors.WHITE);
        }
        dfs_visit(srcNode);
    }

    private void dfs_visit (NodeData node){
        node.setColor(NodeData.Colors.GREY);
        for (edge_data edge : node.getNi()) {
            NodeData neighbor = (NodeData) (this.g.getNode(edge.getDest()));
            if (neighbor.getColor() == NodeData.Colors.WHITE) {
                dfs_visit(neighbor);
            }
        }
        node.setColor(NodeData.Colors.BLACK);
    }

    private void dfs(NodeData srcNode, HashMap<Integer, node_data> nodes) {
        for (node_data n : nodes.values()) {
            NodeData node = (NodeData) n;
            node.setColor(NodeData.Colors.WHITE);
        }
        dfs_visit(srcNode, nodes);
    }

    private void dfs_visit (NodeData node, HashMap<Integer, node_data> nodes){
        node.setColor(NodeData.Colors.GREY);
        for (edge_data edge : node.getNi()) {
            NodeData neighbor = (NodeData) (nodes.get(edge.getDest()));
            if (neighbor.getColor() == NodeData.Colors.WHITE) {
                dfs_visit(neighbor, nodes);
            }
        }
        node.setColor(NodeData.Colors.BLACK);
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
    public HashMap<Integer, node_data> reverse_graph (directed_weighted_graph g) {
        DW_GraphDS graph = (DW_GraphDS)g;
        HashMap<Integer, node_data> nodes = new HashMap<>();
        for (node_data node : graph.getV()) {
            NodeData temp = new NodeData(node);
            nodes.put(temp.getKey(), temp);
        }
        for (node_data n : graph.getV()) {
            NodeData node = (NodeData)n;
            for (edge_data e : node.getNi()) {
                EdgeData edge = (EdgeData)e;
                EdgeData newEdge = new EdgeData(edge.getDest(), edge.getSrc(), edge.getWeight(), "", 1);
                ((NodeData)nodes.get(edge.getDest())).addNi(nodes.get(edge.getSrc()), newEdge);
                ((NodeData)nodes.get(edge.getSrc())).addNeighborOf(nodes.get(edge.getDest()));
            }
        }
        return nodes;
    }


    public class NodeInfoCompare implements Comparator<node_data> {

        @Override
        public int compare(node_data o1, node_data o2) {
            return Double.compare(o1.getTag(), o2.getTag());
        }
    }
    public class FailedToLoadNodesException extends RuntimeException
    {
        public FailedToLoadNodesException(String errorMessage)
        {
            super(errorMessage);
        }
    }

}