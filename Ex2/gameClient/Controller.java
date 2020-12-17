package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;


public class Controller implements Runnable
{
    private ArrayList<CL_Agent> agents;
    private ArrayList<Thread> threads;
    private ArrayList<CL_Pokemon> pokemons;
    private DW_GraphDS graph;
    private game_service currGame;


    public Controller(game_service game)
    {
        this.currGame = game;
        this.graph = new DW_GraphDS(); //TODO load correctly from game JSON, not sure how to do that, LOAD is 100% working though, so figure it out.
        this.agents = new ArrayList<>();
        this.threads = new ArrayList<>();
        //TODO as per last todo you gotta load everything from the JSON into here.

        //create a thread for every agent
        for (CL_Agent agent: agents)
        {
            Thread t = new Thread(agent);
            threads.add(t);
        }

    }


    @Override
    public void run() {

        for (Thread t: threads) // starts all agents
        {
            t.start();
        }

    }
}

