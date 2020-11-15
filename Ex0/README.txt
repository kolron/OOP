README EX0
This project is an implmentation of an undirectional unwieghted graph.
/////////////////////////////////////////////////////////////////////////////////

class: NodeDate

 GENERAL:
  this class implements the node_data interface and it represnets the nodes   in the graph.
  Each node has a key value, which makes it easier to preform several   actions on it since it allows for immediate access to a node if stored in the hashmap.
  Each node has a tag value, which is used to represent a node as seen (1)   or unseen(0) while going through the created hashmaps using the BFS algorithm, will expand upon later.
  Each node also has nodeNumber value, which increments for each node   created, this will be used to set our key, as we must ensure seperate keys.
  Each node has a string info, which represents whaterver's stored in the node
  and Finally, each node has a Hashmap "nei" which holds all of the node's nieghbors.

 METHODS:
  -toString simply returns a string represneting the node's key (not node's data)

  -getNeighborMap it returns a node's hashmap of neighbors

  -Several GET, SET, ADD, HAS methods which need no explaning.
  
  -One thing to be said about all ADD, REMOVE etc.. functions is that while adding or removing a node as a neighbor, the function must be preformed for both   
   the node we wish to remove or add and its neighbors, as each node has their own hashmap of neighbors. 
	EX: node1 and node2 are (the only) neighbors in the graph, if we wish to remove node1, we must remove node1 from node2 nieghbor's hashmap and remove node2 from node1 neighbor's hashmap

  -removeNode this functions removes a node from the hashmap as explained above. NOT to be used in another class. requires activation via removeNodeHelper.

  -removeNodeHelper this function is using the removeNode function above and allows removeNode to be used in other classes in an easier way. uses an Iterator iterate over a node's hashmap
   and disconnect it from all of its neighbors and disconnect all of its neighbors from him.
   

/////////////////////////////////////////////////////////////////////////////////

class: Graph_DS
 GENERAL:
   this class represents the graph using a Hashmap.
   each graph has a Hashmap nodes to represent all the nodes in the graph,
   each graph also has the edges and MC values which are used to represent the amount of edges in the graph and the amount of action taken on the graph, respectivly.


 METHODS:
 -Graph_DS() A default constructor
 -Graph_DS(ONE ARG) a copy constructor, it makes a deep copy of a graph (A new hashmap, node objects, etc etc.. constructor doesn't make pointers for the original graph but a brand new location in the memory for this new graph)
 -Several GET, has, add method, need no special explaining.
	few things to point out:
	getNode - will return null if key entered isn't in the graph.

	hasEdge - checks if 2 nodes are neighbors (using one's neighbor Hashmap) if they are then theres an edge between them.

	addNode - adds a node to the Hashmap. this node isn't yet connected to any other nodes(it has no edges conencted to it and it has no neighbors)!! 
	in order to connect this node to other nodes in the graph we must use the
        connect function

 -connect connects 2 nodes, first checks if they aren't already connected although even if they are it doesn't make a difference, adds each node as the other's neighbor and increment the amount of edges in the graph
 
 -removeNode Remove a node from the graph, disconnect from neighbors and decrease the corresponding num of edges.
  One thing to note is that this function calls for the removeNodeHelper function in NodeData. refer to removeNodeHelper above for explantion.
 
 -getV(int node_id) returns a collection representing node_id's neighbors.
 
 -nodeSize returns the number of node in the graph.

 -edgeSize returns the number of edges in th graph.

 -toString a toString fucntions which prints a representaion of the graph.
  for every node in the graph, adds him to the string, then his neighbors.
  once a node's neighbors have been finished, new line.
 	
/////////////////////////////////////////////////////////////////////////////////

class : Graph_Algo
 GENERAL:
 This class is essentially a black box to which we insert a graph, we can now perform the methods in the class on the graph.

 NOTE:
 Many methods in this class use the BFS algorithm, adapated to each individual scenario. in order not to be repetitive, and save words and time, if a methods uses the BFS algorithm I will mention it, but won't over explain.

 METHODS:
 -Graph_Algo constructor.

 -init  initiate a graph, allows us to put a new graph inside the black box and can now perform methods on the new graph/-

 -copy  makes a DEEP copy of the graph using the copy construcor in Graph_DS, refer to Graph_DS for explantion.

 -isConnected first method that uses the BFS algorithm(Aleit via the distance(1ARG) method, and not directly) . Refer to https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/ for an explanation of the algorithem. my implementaion is     taken from the geekforgeeks.org and stackoverflow.com websites, adapted.
  the method goes as follows:
  first checks a minimal condition for the graph to be connected.
  than sets tag of all nodes to 0 which means they are yet to be seen by the algorithm.
  creates and iterator with all nodes in the graph and initialize a src variable, from which we start traversal.
  than creates a new hashmap using the distance(src) method (Explaining later), loops through the nodes using our Iterator, if the node we are currently "looking" at isn't in the hashmap created from distance(src), than because of distance(src)'S 
  implementaion than that means it's distance from src is NULL, (No path = no Distance = Null). 
  if a node isn't in the created hashmap and calling a get function for it returns Null, that means theres no path and returns false.
  if all nodes are present in the created hashmap than return true, as the BFS algorithm reached all of the nodes and put them and their distance from src in the hashmap, and therefor the graph is connected.

 -distance(src) this method has a direct use of the BFS algorithm, and calculates the distance of all nodes in the graph from the src node, creates a hashmap and puts all nodes and their respective distances in it. distance of src from himself is 0
  creates a queue using a LinkedList and puts src first in it.
  creates a hashmap that will hold the nodes and their distances
  adds src to hashmap with distance 0 and sets its tag to 1, meaning its seen.
  now as long as the queue isn't empty, take a node n out of queue and check all of it's neighbors.
  if we haven't looked at a neighbor yet (if it's tag is 0) , set his tag as 1, and put him inside the hashmap with a distance 1 greater than n's distance from src(because he's 1 "layer" after it), and add it to the queue, so we can check its neighbors. 
  the algorithm is very simple, the fact that a neighbors tag is 0 is only possible if it is not a common neighbor of a node seen earlier, ensures that the algorithem won't look at nodes twice, and will look at them in the correct order, which ensures   the  
  algorithm's correctness. 

  -shortestPathDist calculates the distance from a src node to dest node, once again uses the BFS algorithm, via the distance(src, dest) which will be explained later.
   it sets tags of all nodes to 0
   and creates a hashmap much like isConnected using the distance(src, dest) method, checks if there's even a path from src to dest, otherwise return -1 to represent that there's no path, check that by seeing if dest is in the created hashmap
  (cannot use the isConnected method to check if there's a path between 2 nodes as there might be a graph (node1)----(node2) (node3), checking if its connected will return false but theres still a path from (node1) to (node2). 
  if there's a path then return the distance of the dest node from src, from the created hashmap. this must be the shortest distance as the BFS algorithm will always calculate the shortest distance since it puts the node in as soon as it is seen,
  always in order (explained above).
  

 -distance(src, dest) method that implements the BFS algorithm, this method is almost the same as the first distance(src) method, diffrence is that the first distance(src) method measures the distance of all nodes in the graph from src,
  while this distance(src, dest) method measures the distance of src from dest. it stops the BFS traversal once we reach dest.


 -shortestPath finds the shortest path between node src and dest, using the BFS algorithm via the shortestPathHelper method below.
  it sets all tags to 0 
  creates a hashmap of nodes and ArrayLists(NOT JUST NODES AND INTEGER) using the shortestPathHelper method. The ArrayLists in the hashmap hold the path from dest to src (or src to dest, same thing as this is an undirectional graph)
  if dest is in the created hashmap, return it and it's hashmap.

 -shortestPathHelper a method that uses the BFS algorithm.
  Like before creates a queue.
  creates an ArrayList to hold the path from src to src (this is just an ArrayList holding src) needed for the first query of path from src to src.
  and creates a hashMap that will hold the nodes their respective ArrayLists.
  now as long as the queue isn't empty:
  same as before, take n out of queue and look at it's neighbors.
  if tag isn't 1, set it to 1 and create an arrayList with the path it took to get from n to src and add nei in the beginning of this list (as the ArrayList represents the neighbor's path and not n's)
  put nei and the ArrayList in the hashmap and add it to the queue...
  check if dest was one of the neighbors, if it was than stop.
     
 







































	