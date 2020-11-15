package ex0;

import java.util.*;

public class NodeData implements node_data
{
    private int key;
    private int tag;
    private String info;
    private static int nodeNumber=0;
    private HashMap<Integer,node_data> nei;

    public NodeData()
    {
        this(nodeNumber,0,"");
    }

    public NodeData(int key,int tag,String info)
    {
        this.key = key;
        setTag(tag);
        setInfo(info);
        nodeNumber++;
        this.nei = new HashMap<Integer, node_data>();

    }

    public String toString()
    {
        return "Node ("+getKey()+")";
    }

    /** toString
     * returns a string representing the node's KEY not node's INFO
     */

    public HashMap<Integer,node_data> getNeighborMap()
    {
        return this.nei;
    }
    /** getNeighborMap
     gets a nodes' neighbors using a hashmap (each node has a hashmap of neighbors)
     */

    @Override
    public int getKey()
    {
        return key;
    }

    @Override
    public int getTag()
    {
        return tag;
    }

    @Override
    public void setTag(int tag)
    {
        this.tag = tag;
    }

    @Override
    public String getInfo()
    {
        return info;
    }

    @Override
    public void setInfo(String info)
    {
        this.info = info;
    }

    @Override
    public Collection<node_data> getNi()
    {
        return this.nei.values();
    }

    @Override
    public boolean hasNi(int key)
    {
        return this.nei.containsKey(key);
    }

    @Override
    public void addNi(node_data t)
    {
        if (!hasNi(t.getKey()) && !this.equals(t)) // if t isn't a neighbor or t isn't itself
        {
            NodeData n = (NodeData)t; // Cast t as NodeData
            this.nei.put(t.getKey(),t); //adds t as a neighbor
            n.nei.put(getKey(),this);  // other way around.
        }
    }
    /** addNi
     * adds a node as a neighbor (for both source and t node).
     */

    @Override
    public void removeNode(node_data node)
    {
        NodeData n = (NodeData) node;
        this.nei.remove(node.getKey()); //remove neighbors from hashmap
        n.nei.remove(getKey()); // remove node from neighbor's hashmap
    }
    /** removeNode
     * Remove a node from the neighbor list(and the other way around).
     * Not to be used directly in other Classes. use RemoveNodeHelper for that.
     */

    public void removeNodeHelper()
    {
        node_data temp;
        Iterator<node_data>  i = this.nei.values().iterator();
        while (i.hasNext())
        {
            temp = i.next();
            i.remove();
            removeNode(temp);
        }
    }
    /** removeNodeHelper
     * A helper function removeNode that uses it
     * This function disconnects this node from all of it's neighbors,
     * And the other way around, used in other functions to enable fast runtime and a simpler removeNodeHelper
     */


}
