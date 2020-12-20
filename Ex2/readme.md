# Ex1:
## **On the Project**
 This project is an implementation of an unidirectional weighted graph, a continuation of https://github.com/kolron/OOP/tree/main/Ex0 - the first project on the subject of graphs.  
 Improved style, and code from previous project - reduced the amount methods and helper methods to make for a more understandable, simpler code.
***
## **On The Classes**
 **WGraph_DS**  
 This class implements the weighted_graph interface, and holds an inner class: **NodeInfo** that implements the node_info interface.  
 The class represents our graph using a HashMap that hold all the nodes.    
 The Inner class **NodeInfo** represents the nodes in the graph.  
 Each Node has 2 HashMaps:
   1. A HashMap holding its neighbors and their keys.
   2. A HasMap holding its neighbors and the weight to of the edge to them. 
   
 ***
 
**Methods in the class WGraph_DS:**    
 1. Standard Methods:  
    _1.node_info getNode(int num)_: Returns the node_info of node with key num.     
  
    _2.boolean hasEdge(int num1, int num2)_: Checks if there's an edge between node with key num1, and num2. 
   
    _3.double getEdge(int num1, int num2)_:  Gets the weight of the edge between node with key num1, and num2.    
    
    _4.void addNode(int num)_:  A node with key num. Cannot add if key already exists.  
    
    _5.Collection<node_info> getV()_: Returns a shallow copy of all the nodes in the graph.
    
    _6.Collection<node_info> getV(int num)_: Returns a collection containing all nodes connected to a node with key num.
    
    _7.int nodeSize()_: Returns the amount of nodes currently in the graph.
    
    _8.int edgeSize()_: Returns the amount of edges in the graph.
    
    _9.int getMC()_: Returns the amount of modifications made on the graph.
    
  
  2. Other Methods:   
    _1.void connect(int num1, int num2, double w)_: Connects 2 nodes in the graph, and give their edge the weight w. Weight cannot be a negative number. 
     
      _2.node_info removeNode(int num)_: Removes the node with key num from the graph.  
      
      _3.void removeEdge(int num1, int num2)_: Removes the edge between node with key num1 and key num2.
    
   ***
 **WGraph_Algo** 
  This class implements the weighted_graph_algorithms interface  
  This class is a BlackBox to which you insert a graph and preform several methods on.
***
**Methods in the class WGraph_Algo:**
1. Standard Methods:  
    _1.getGraph()_: Return the weighted graph that is currently in this BlackBox.
    
    _2.init(weighted_graph g)_: Initialize a new weighted graph to the BlackBox - essentially changes the graph we are working on.  
      
    _3.weighted_graph copy()_: Creates a deep copy of the graph inside the BlackBox  
    
 2. Other Methods:  
  _1. boolean isConnected()_: Returns true if the graph isConnected - meaning by traversing only on edges you can reach every node. 
   
    _2.double shortestPathDist(int src, int dest)_: This Method uses the Djikstra's algorithm for weighted graphs, to find the shortest distance 
    (=Smallest Weight) between 2 nodes src and dest.
    it returns the weight of that path.  
    
    _3.List<node_info> shortestPath(int src, int dest)_: This method too uses Djikstra's algorithm but now instead of returning
    the weight of the path from node src to node dest, it now returns an ArrayList containing the route itself.  
    
    _4.boolean save(String name)_: This method saves the graph inside the BlackBox to a file called name, it saves the graph as an object that is unreadable to the humans.  
    
    _5.boolean load(String name)_: This method load the graph from a file called name, to the BlackBox.
    
***
