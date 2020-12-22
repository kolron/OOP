# Ex2:
## **On the Project**
 This project is of an autonomous "game" that does the following:
 given a scenario(henceforth, scenario will refer to a graph, 'pokemons' and agents), create and draw the graph, agents and 'pokemons'.
 Each agent will try to eat (by reaching) the pokemon that is placed on an edge in the graph.
 As agents eat and reach a certain threshold, their speed increases. 
 The the directive of the game is to earn a higher score by eating a maximum amount of 'pokemons' and calling the move function as little times as possible in the given time    limit.is  
 
 The first part of the project (api directory) is an implementation of a directional weighted graph, a continuation of pevious projects in the repository.
 The second part of the project (gameClient) is the implementation of the game.
***
## **Package api**
## **On The Classes**
 **WGraph_DS**  
 This class implements the directed_weighted_graph interface.  
 The class represents our graph using a HashMap that hold all the nodes.    
 Each graph has 2 a HashMap holding its nodes with each node's keys.   
 ***
 
**Methods in the class WGraph_DS:**    
 1. Standard Methods:  
   _1.node_data getNode(int num)_: Returns the node_info of node with key num.     
  
    _2.edge_data getEdge(int num1, int num2)_: return the edge between node with key num1, and num2. 
        
    _3.void addNode(node_data nod)_:  add the node nod to the graph. Cannot add if key already exists.  
    
    _4.Collection<node_data> getV()_: Returns a shallow copy of all the nodes in the graph.
    
    _5.Collection<edge_data> getV(int num)_: Returns a collection containing all edges that oringinated from the node with key num.
    
    _6.int nodeSize()_: Returns the amount of nodes currently in the graph.
    
    _7.int edgeSize()_: Returns the amount of edges in the graph.
    
    _8.int getMC()_: Returns the amount of modifications made on the graph.
    
  
  2. Other Methods:   
    _1.void connect(int src, int dest, double w)_: Connect node src to node dest (directed), and give their edge the weight w. Weight cannot be a negative number. 
    
    _2.void connect(edge_data e)_: connect via edge_data e. This method connects according to the src, dest, and weight values in the edge_data e.
     
    _3.node_data removeNode(int num)_: Removes the node with key num from the graph.  
       
    _4.void removeEdge(int src, int dest)_: Removes the edge originating from node with key src to node with key dest. 
    
   ***
 **WGraph_Algo** 
  This class implements the weighted_graph_algorithms interface  
  This class is a BlackBox to which you insert a graph and preform several methods on.
***
**Methods in the class WGraph_Algo:**
1. Standard Methods:  
    _1.getGraph()_: Return the directed weighted graph that is currently in this Black Box.
    
     _2.init(directed_weighted_graph g)_: Initialize a new weighted graph to the Black Box - essentially changes the graph we are working on.  
      
     _3.directed_weighted_graph copy()_: Creates a deep copy of the graph inside the Black Box  
    
 2. Other Methods:  
  _1. boolean isConnected()_: Returns true if the graph is strongly connected - meaning you can reach each node, you can reach all other nodes in the graph by traversing on the       edges. 
   
    _2.double shortestPathDist(int src, int dest)_: This Method uses the Djikstra's algorithm for weighted graphs, to find the shortest distance 
    (=Smallest Weight) between 2 nodes src and dest.
    it returns the weight of that path.  
    
    _3.List<node_data> shortestPath(int src, int dest)_: This method too uses Djikstra's algorithm but now instead of returning
    the weight of the path from node src to node dest, it now returns an ArrayList containing the route itself.  
    
    _4.boolean save(String name)_: This method saves the graph as a JSON object, returns true if we managed to save.
    
    _5.boolean load(String name)_: This method load the graph from a file, written in JSON formatting, otherwise we won't be able to load. returns true if we managed to load the        graph
    
 ***
 
 **NodeData**
  This class implements the node_data interface.
  The class represents the nodes in our graph.
  ***
  **Representation of nodes**
  Each node has the following private fields:
  _1._ int key: represents the node's key
  _2._ geo_location location: represents the node's location, used in the second part of the project.
  _3._ double weight: represents the node's weight
  _4._ String info: represents the node's info
  _5._ int tag: represents the node's tag
  _6._ HashMap<Integer, edge_data> neighbors: HashMap which represent the node's neighbors.
  _7._ HashSet<Integer> destOf: hashset to know which nodes are the destination of an edge originating from this node.
  _8._ Colors color: represents the node's info.
 ***
 
**EdgeData**
 this class implements the edge_data interface.
 The class represents the edges(vertex) in our graph.
 ***
 ***Representation of edges**
 Each edge has the following private fields:
  _1._ int src: represents the edge's source node with key src.
  _2._ int dest;: represents the edge's destination node with key dest.
  _3._ double weight: represents the edge's weight
  _4._ String info: represents the edge's info
  _5._ int tag: represents the edge's tag
  ***
  
## **Package gameClient**
## **On The Classes**
**Arena**
***
**CL_Pokemon**
***
**CL_Agent**
***
**Controller**
***
***
**GUI classes**
**GUI**
***
**Panel**
***
**LoginScreen**
***
**Ex2**
  

