package tests;

import Server.Game_Server_Ex2;
import api.DW_GraphDS;
import api.GeoLocation;
import api.game_service;
import com.google.gson.Gson;
import gameClient.CL_Agent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CL_AgentTest {

    @Test
    void update() {
        game_service g = Game_Server_Ex2.getServer(0);
        Gson gson = new Gson(); // create a gson object
        DW_GraphDS graphDS = new DW_GraphDS((DW_GraphDS.WrapedDW_GraphDS) gson.fromJson(g.getGraph(), DW_GraphDS.WrapedDW_GraphDS.class));
        GeoLocation pos = new GeoLocation(0,1,1);
        CL_Agent agent1 = new CL_Agent(graphDS,1,0,0,1,pos);
        CL_Agent agent2 = new CL_Agent(graphDS,2);
        assertNotEquals(agent1, agent2);
    }

    @Test
    void setNextNode() {
        game_service g = Game_Server_Ex2.getServer(0);
        Gson gson = new Gson(); // create a gson object
        DW_GraphDS graphDS = new DW_GraphDS((DW_GraphDS.WrapedDW_GraphDS) gson.fromJson(g.getGraph(), DW_GraphDS.WrapedDW_GraphDS.class));
        CL_Agent agent1 = new CL_Agent(graphDS, 1);
        agent1.setNextNode(3);
        assertEquals(-1 ,agent1.getNextNode());
        agent1.setNextNode(0);
        assertEquals(0 ,agent1.getNextNode());
    }

    @Test
    void getID() {
        game_service g = Game_Server_Ex2.getServer(0);
        Gson gson = new Gson(); // create a gson object
        DW_GraphDS graphDS = new DW_GraphDS((DW_GraphDS.WrapedDW_GraphDS) gson.fromJson(g.getGraph(), DW_GraphDS.WrapedDW_GraphDS.class));
        CL_Agent agent1 = new CL_Agent(graphDS, 1);
        assertEquals(-1, agent1.getID());
        agent1 = new CL_Agent(graphDS,33,0,0,1,new GeoLocation(0,1,1));
        assertEquals(33, agent1.getID());
    }

    @Test
    void getLocation() {
        game_service g = Game_Server_Ex2.getServer(0);
        Gson gson = new Gson(); // create a gson object
        DW_GraphDS graphDS = new DW_GraphDS((DW_GraphDS.WrapedDW_GraphDS) gson.fromJson(g.getGraph(), DW_GraphDS.WrapedDW_GraphDS.class));
        GeoLocation pos = new GeoLocation(0,1,1);
        CL_Agent agent1 = new CL_Agent(graphDS,1,0,0,1,pos);
        assertEquals(pos, agent1.getLocation());
    }

    @Test
    void getValue() {
        game_service g = Game_Server_Ex2.getServer(0);
        Gson gson = new Gson(); // create a gson object
        DW_GraphDS graphDS = new DW_GraphDS((DW_GraphDS.WrapedDW_GraphDS) gson.fromJson(g.getGraph(), DW_GraphDS.WrapedDW_GraphDS.class));
        GeoLocation pos = new GeoLocation(0,1,1);
        CL_Agent agent1 = new CL_Agent(graphDS,1,0,0,1,pos);
        assertEquals(0, agent1.getValue());
    }

    @Test
    void getSpeed() {
        game_service g = Game_Server_Ex2.getServer(0);
        Gson gson = new Gson(); // create a gson object
        DW_GraphDS graphDS = new DW_GraphDS((DW_GraphDS.WrapedDW_GraphDS) gson.fromJson(g.getGraph(), DW_GraphDS.WrapedDW_GraphDS.class));
        GeoLocation pos = new GeoLocation(0,1,1);
        CL_Agent agent1 = new CL_Agent(graphDS,1,0,0,1,pos);
        assertEquals(1, agent1.getSpeed());
        agent1.setSpeed(4.7);
        assertEquals(4.7, agent1.getSpeed());
    }
}