package tests;

import api.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class DW_Graph_Algo_Test {
    @Test
    void isConnected() {

        directed_weighted_graph g0 = new DW_GraphDS();
        dw_graph_algorithms ag0 = new DWGraph_Algo();
        node_data n0 = new NodeData(0);
        node_data n1 = new NodeData(1);
        node_data n2 = new NodeData(2);
        node_data n3 = new NodeData(3);
        ag0.init(g0);

        //  g0.addNode(n0);
        g0.addNode(n1);
        assertTrue(ag0.isConnected());
        g0.addNode(n2);
        g0.addNode(n3);
        g0.connect(1, 2, 1);
        g0.connect(2, 3, 1);
//        assertFalse(ag0.isConnected());






        g0.connect(3, 2, 1);
//        assertFalse(ag0.isConnected());
        g0.connect(3, 1, 1);
        assertTrue(ag0.isConnected());


        g0.removeEdge(1, 2);
        assertFalse(ag0.isConnected());
        g0.connect(1, 2, 1);
        assertTrue(ag0.isConnected());
        g0.connect(1, 3, 1);
        g0.removeNode(2);
        assertTrue(ag0.isConnected());

    }

    @Test
    void shortestPathDist() {
        directed_weighted_graph g0 = new DW_GraphDS();
        dw_graph_algorithms ag0 = new DWGraph_Algo();
        node_data n0 = new NodeData(0);
        node_data n1 = new NodeData(1);
        node_data n2 = new NodeData(2);
        node_data n3 = new NodeData(3);
        g0.addNode(n1);
        g0.addNode(n2);
        g0.addNode(n3);
        g0.connect(1, 2, 5);
        g0.connect(2, 3, 5);
        g0.connect(1, 3, 15);
        ag0.init(g0);
        assertEquals(0, ag0.shortestPathDist(1, 1));
        assertEquals(10, ag0.shortestPathDist(1, 3));//should go 1>2>3

        g0.connect(1, 3, 3.5);
        assertEquals(3.5, ag0.shortestPathDist(1, 3));//should go 1>3

        g0.connect(1, 3, 11);
        g0.addNode(n0);
        g0.connect(2, 0, 0.1);
        g0.connect(0, 2, 0.1);
        g0.connect(0, 1, 0.1);
        g0.connect(1, 2, 10);
        assertEquals(11, ag0.shortestPathDist(1, 3));//should go 13

    }

    @Test
    void shortestPath() {
        //self made tests
        //tests will be based off path we know is correct from test ShortestPathDist
        directed_weighted_graph g0 = new DW_GraphDS();
        dw_graph_algorithms ag0 = new DWGraph_Algo();
        node_data n0 = new NodeData(0);
        node_data n1 = new NodeData(1);
        node_data n2 = new NodeData(2);
        node_data n3 = new NodeData(3);
        g0.addNode(n1);
        g0.addNode(n2);
        g0.addNode(n3);
        g0.connect(1, 2, 5);
        g0.connect(2, 3, 3);
        g0.connect(1, 3, 15);
        g0.connect(3, 2, 1);
        ag0.init(g0);

        List<node_data> list0 = ag0.shortestPath(1, 1); //should go from 1 -> 1
        int[] list0Test = {1, 1};
        int i = 0;
        for (node_data n : list0) {

            assertEquals(n.getKey(), list0Test[i]);
            i++;
        }

        List<node_data> list1 = ag0.shortestPath(1, 2); //should go  1 -> 2
        int[] list1Test = {1, 2};
        i = 0;
        for (node_data n : list1) {

            assertEquals(n.getKey(), list1Test[i]);
            i++;
        }
        g0.connect(1, 3, 2);
        List<node_data> list2 = ag0.shortestPath(1, 2); //should go 1 -> 3 -> 2
        int[] list2Test = {1, 3, 2};
        i = 0;
        for (node_data n : list2) {

            assertEquals(n.getKey(), list2Test[i]);
            i++;
        }

        g0.connect(1, 3, 10);
        List<node_data> list3 = ag0.shortestPath(1,3);
        int[] list3Test = {1, 2, 3};
        i = 0;
        for(node_data n: list3) {

            assertEquals(n.getKey(), list3Test[i]);
            i++;
        }
/*

        List<node_data> list4 = ag0.shortestPath(1,4);
        int[] list4Test = {1, 4};
        i = 0;
        for(node_data n: list4) {

            assertEquals(n.getKey(), list4Test[i]);
            i++;
        }

        List<node_data> list5 = ag0.shortestPath(4,1);
        int[] list5Test = {4, 1};
        i = 0;
        for(node_data n: list5) {

            assertEquals(n.getKey(), list5Test[i]);
            i++;
        }
*/
    }
    @Test
    void save_load()
    {
        directed_weighted_graph g0 = new DW_GraphDS();
        dw_graph_algorithms ag0 = new DWGraph_Algo();
        node_data n1 = new NodeData(1);
        node_data n2 = new NodeData(2);
        node_data n3 = new NodeData(3);
        ag0.init(g0);
        g0.addNode(n1);
        g0.addNode(n2);
        g0.addNode(n3);
        g0.connect(1, 2, 1);
        g0.connect(2, 3, 1);
        String str = "g0.obj";
        ag0.save(str);

        directed_weighted_graph g1 = new DW_GraphDS();

        node_data n11 = new NodeData(1);
        node_data n22 = new NodeData(2);
        node_data n33 = new NodeData(3);
        g1.addNode(n11);
        g1.addNode(n22);
        g1.addNode(n33);
        g1.connect(1, 2, 1);
        g1.connect(2, 3, 1);
        ag0.load(str);
        assertEquals(ag0.getGraph(),g1); //TODO write a correct compere function to check if graphgs are equal or not.
        g0.removeNode(1);
        assertNotEquals(ag0.getGraph(),g1);



    }

}
