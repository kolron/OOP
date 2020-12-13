package tests;

import api.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DWGraph_DS_Test {
    private static Random _rnd = null;
    private static int _errors = 0, _tests = 0, _number_of_exception = 0;
    private static String _log = "";

    @Test
    void getNode() {
        node_data n1 = new NodeData(1);
        node_data n2 = new NodeData(2);
        DW_GraphDS g0 = new DW_GraphDS();
        g0.addNode(n1);
        assertEquals((g0.getNode(1)).getKey(), 1);
        assertNull(g0.getNode(2));
        g0.addNode(n2);
        assertEquals(g0.getNode(2).getKey(), 2);

    }

    @Test
    void getEdge() {
        node_data n0 = new NodeData(0);
        node_data n1 = new NodeData(1);
        node_data n2 = new NodeData(2);
        DW_GraphDS g0 = new DW_GraphDS();
        g0.addNode(n0);
        g0.addNode(n1);
        g0.addNode(n2);
        assertNull(g0.getEdge(0, 1));
        g0.connect(1, 2, 1);
        assertNotNull(g0.getEdge(1, 2));
        assertNull(g0.getEdge(2, 1));
        g0.connect(2, 1, 2);
        assertNotNull(g0.getEdge(2, 1));


    }


    @Test
    void nodeSize() {
        node_data n0 = new NodeData(0);
        node_data n1 = new NodeData(1);

        directed_weighted_graph g = new DW_GraphDS();
        g.addNode(n0);
        g.addNode(n1);
        g.addNode(n1); //should be able to add an existing node without crashing, shouldn't increase size

        int s1 = g.nodeSize();
        assertEquals(2, s1);

        g.removeNode(2);
        g.removeNode(1);
        g.removeNode(1); //should be able to remove a non existent node.
        s1 = g.nodeSize();
        assertEquals(1, s1);

        g.removeNode(0);
        int s2 = g.nodeSize();
        assertEquals(0, s2);


    }

    @Test
    void edgeSize() {
        node_data n0 = new NodeData(0);
        node_data n1 = new NodeData(1);
        node_data n2 = new NodeData(2);
        node_data n3 = new NodeData(3);
        directed_weighted_graph g = new DW_GraphDS();
        g.addNode(n0);
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);
        g.connect(0, 1, 1);
        g.connect(0, 2, 2);
        g.connect(0, 3, 3);
        g.connect(3, 0, 1);
        int e_size;

        g.connect(0, 1, 13); //changing weight should not increase edgeSize
        e_size = g.edgeSize();
        assertEquals(13, g.getEdge(0, 1).getWeight());
        assertEquals(4, e_size);

        g.removeEdge(0, 1);
        e_size = g.edgeSize();
        assertEquals(3, e_size);

        g.removeEdge(0, 1); //should be able to remove edge that isn't there.
        e_size = g.edgeSize();
        assertEquals(3, e_size);
        assertNull(g.getEdge(0, 1)); //if no edge should return -1 and not null.

        double w03 = g.getEdge(0, 3).getWeight();
        double w30 = g.getEdge(3, 0).getWeight();
        assertEquals(w03, 3);
        assertEquals(w30, 1);

    }

    @Test
    void getV() {
        directed_weighted_graph g = new DW_GraphDS();
        node_data n0 = new NodeData(0);
        node_data n1 = new NodeData(1);
        node_data n2 = new NodeData(2);
        node_data n3 = new NodeData(3);
        g.addNode(n0);
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);
        g.connect(0, 1, 1);
        g.connect(0, 2, 2);
        g.connect(0, 3, 3);
        g.connect(1, 0, 4);
        Collection<node_data> v = g.getV();
        for (node_data n : v) {
            assertNotNull(n);
        }
    }


    @Test
    void connect() {
        node_data n0 = new NodeData(0);
        node_data n1 = new NodeData(1);
        node_data n2 = new NodeData(2);
        node_data n3 = new NodeData(3);
        directed_weighted_graph g = new DW_GraphDS();
        g.addNode(n0);
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);
        g.connect(0, 1, 1);
        g.connect(0, 2, 2);
        g.connect(0, 3, 3);
        g.removeEdge(2, 1);
        g.connect(0, 1, 1);
        assertNull(g.getEdge(1,0));
        g.connect(1,0,1);
        double w = g.getEdge(1, 0).getWeight();
        assertEquals(w, 1);
    }


    @Test
    void removeNode() {
    node_data n0 = new NodeData(0);
        node_data n1 = new NodeData(1);
        node_data n2 = new NodeData(2);
        node_data n3 = new NodeData(3);

        directed_weighted_graph g = new DW_GraphDS();
        g.addNode(n0);
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);
        g.connect(0, 1, 1);
        g.connect(0, 2, 2);
        g.connect(0, 3, 3);
        g.removeNode(4);
        g.removeNode(0);
        assertNull(g.getEdge(0, 1));
        assertNull(g.getEdge(1, 0));
        int e = g.edgeSize();
        assertEquals(0, e);
        assertEquals(3, g.nodeSize());
    }


    @Test

    void removeEdge() {
      node_data n0 = new NodeData(0);
        node_data n1 = new NodeData(1);
        node_data n2 = new NodeData(2);
        node_data n3 = new NodeData(3);

        directed_weighted_graph g = new DW_GraphDS();
        g.addNode(n0);
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);
        g.connect(0, 1, 1);
        g.connect(0, 2, 2);
        g.connect(2, 0, 2);
        g.connect(0, 3, 3);
        g.removeEdge(0, 3);
        g.removeEdge(0, 1);
        g.removeEdge(0, 1);//should be able to remove a non existing Edge
        assertNull(g.getEdge(0, 3));
        g.removeEdge(0, 2);
        assertNotNull(g.getEdge(2,0));

    }

    /**
     * Generate a random graph with v_size nodes and e_size edges
     * @param v_size
     * @param e_size
     * @param seed
     * @return
     */

    public static directed_weighted_graph graph_creator(int v_size, int e_size, int seed) {
        directed_weighted_graph g = new DW_GraphDS();
        _rnd = new Random(seed);
        node_data[] newNodes = new node_data[v_size];
        for(int i=0;i<v_size;i++)
        {
            newNodes[i] = new NodeData(i); //used array to hold the reference to the object created inside a loop.
            g.addNode(newNodes[i]);
        }
        // Iterator<node_data> itr = V.iterator(); // Iterator is a more elegant and generic way, but KIS is more important
        int[] nodes = nodes(g);
        while(g.edgeSize() < e_size) {
            int a = nextRnd(0,v_size);
            int b = nextRnd(0,v_size);
            int i = nodes[a];
            int j = nodes[b];
            double w = _rnd.nextDouble();
            g.connect(i,j, w);
        }
        return g;
    }

    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0+min, (double)max);
        int ans = (int)v;
        return ans;
    }
    private static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max-min;
        double ans = d*dx+min;
        return ans;
    }

    /**
     * Simple method for returning an array with all the node_data of the graph,
     * Note: this should be using an Iterator<node_edge> to be fixed in Ex1
     * @param g
     * @return
     */
    private static int[] nodes(directed_weighted_graph g) {
        int size = g.nodeSize();
        Collection<node_data> V = g.getV();
        node_data[] nodes = new node_data[size];
        V.toArray(nodes); // O(n) operation
        int[] ans = new int[size];
        for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
        Arrays.sort(ans);
        return ans;
    }
}

