# Ex3
This exercise is a continuation of sorts to the last 3 projects, and is an implementation of the API package in Ex2, but in python, not java.

## **On the Project**
This project is a continuation of sorts to the last 3 projects, and is an implementation of the API package in https://github.com/kolron/OOP/tree/main/Ex2, but in python, not java.
In this project we implement a directional weighted graph

## **On The Classes**
**NodeData** that implements the node_info interface.  
 The Inner class **NodeData** represents the nodes in the graph.  
 
 ***
 **DiGraph**  
 This class implements the GraphInterface, The class represents our graph using a dictionary that hold all the nodes.
 In addition to said dictionary, the graph holds 3 other Dictionaries - srcOf and destOf, these dictionaries hold each node's neighbors (according to the dictionary's name). 
 The positions dictionary holds each node's position as a string of "x,y,z".
 
 
 ***
 
**Methods in the class DiGraph:**    
 1. Standard Methods:  
    _1.getNode(int num)_: Returns the node_info of node with key num.     
  
    _2.all_in_edges_of_node(int num1)_: return a dictionary of all the nodes connected to node with key num1
   
    _3.all_out_edges_of_node(int num1)_: return a dictionary of all the nodes connected from node with key num1    
    
    _4.addNode(int num, tuple pos)_:  Add a node with key num and tuple(x,y,z) which represents a position in the graph Cannot add if key already exists.  
    
    _5.get_all_v()_: return a dictionary of all the nodes in the Graph
    
    _7.v_size()_: Returns the amount of nodes currently in the graph.
    
    _7.e_size()_: Returns the amount of edges in the graph.
    
    _8.get_mc()_: Returns the amount of modifications made on the graph.
    
  
  2. Other Methods:   
    _1.add_edge(int num1, int num2, double w)_: Connects 2 nodes in the graph, num1 being the src and num2 being the dest, and give their edge the weight w. Weight cannot be a negative number. 
     
      _2.remove_node(int num)_: Removes the node with key num from the graph.  
      
      _3.remove_edge(int num1, int num2)_: Removes the edge between node with key num1 and key num2.
    
   ***
 **GraphAlgo** 
  This class implements the GraphAlgoInterface interface  
  This class is a black box to which you insert a graph and preform several methods on.
***
**Methods in the class GraphAlgo:**
1. Standard Methods:  
    _1.getGraph()_: Return the weighted graph that is currently in this black box.
    
    _2.init(weighted_graph g)_: Initialize a new weighted graph to the black box - essentially changes the graph we are working on.  
      
    _3.weighted_graph copy()_: Creates a deep copy of the graph inside the black box  
    
 2. Other Methods:  
  _1. connected_component(int num1)_: Finds the Strongly Connected Component(SCC) that node num1 is a part of. SCC meaning that by traversing only on edges you can reach every node from every other node in the same SCC, using a variation of Tarjan algorithm. returns it as a list.
   
    _2.connected_components()_: Finds all the Strongly Connected Component(SCC) in the graph and returns them as a list of lists.
    
    _3.shortestPath(int src, int dest)_: Finds the shortest path (smallest weight) between node src and dest, returns the path itself and it's length(weight)
    
    _4.save_to_json(String name)_: This method saves the graph inside the black box to a file called name, it saves the graph in JSON format. 
    
    _5.load_from_json(String name)_: This method load the graph from a file called name, in JSON format, to the black box.
    
    _6.plot_graph()_: This method plots (Draws) the graph using matplotlib.
    
   ***

    
