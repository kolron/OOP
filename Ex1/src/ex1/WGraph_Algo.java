package ex1;
import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms, Serializable
    {
    public weighted_graph graph;


    public WGraph_Algo() {this.graph = new WGraph_DS();}

    @Override
    public void init(weighted_graph g)
    {
        if (g instanceof WGraph_DS)
        this.graph = g;
    }

    @Override
    public  weighted_graph getGraph() { return this.graph;}

    @Override
    public weighted_graph copy() {
        if (!(this.graph == null)) // if g isn't null
            return new WGraph_DS((WGraph_DS)this.graph); // return the new graph using the copy constructor in WGraph_DS
        else
            return null;
    }

    @Override
    public boolean isConnected()
    {
        if (graph.nodeSize() <= 1)
        return true;

        if(this.graph.nodeSize()-1>this.graph.edgeSize())
            return false;

        HashSet<node_info> edges = new HashSet<>();
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
        edges.add(src);
        while(!queue.isEmpty())
        {
            node_info temp = queue.poll();
            for(node_info n: ((WGraph_DS.NodeInfo)temp).getNi())
            {
                if(n.getTag()==0)
                {
                    queue.add(n);
                    n.setTag(1);
                    edges.add(n);
                }
            }
        }
        return this.graph.nodeSize() == edges.size();
    }



    public void Djik(int src, int dest) {
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

    @Override
    public double shortestPathDist(int src, int dest)
    {
        node_info source = graph.getNode(src);
        node_info desti = graph.getNode(dest);
        if (src == dest)
            return 0;
        if (source == null || desti == null)
            return -1;
        if (desti.getTag() == Double.MAX_VALUE)
            return -1;
        Djik(src, dest);
        return desti.getTag();
    }


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


        private class Comperator implements Comparator<node_info> , Serializable
        {
            @Override
            public int compare(node_info o1, node_info o2)
            {
                return Double.compare(o1.getTag(), o2.getTag());
            }
        }

}
