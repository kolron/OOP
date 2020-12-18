package gameClient;

import api.DW_GraphDS;
import api.game_service;
import com.google.gson.Gson;

import java.util.ArrayList;


public class Controller implements Runnable
{
    private ArrayList<CL_Agent> agents;
    private ArrayList<Thread> agentsThreads;
    private ArrayList<CL_Pokemon> pokemons;
    private DW_GraphDS graph;
    private game_service currGame;


    public Controller(game_service game)
    {
        Gson gson = new Gson();
        this.currGame = game;
        this.graph = new DW_GraphDS((DW_GraphDS.WrapedDW_GraphDS) gson.fromJson(game.getGraph(), DW_GraphDS.WrapedDW_GraphDS.class));
        this.agents = new ArrayList<>();
        this.agentsThreads = new ArrayList<>();
        //TODO as per last todo you gotta load everything from the JSON into here.

        //create a thread for every agent
        for (CL_Agent agent: agents)
        {
            Thread t = new Thread(agent);
            agentsThreads.add(t);
        }

    }

    @Override
    public void run() {

        for (Thread t: agentsThreads) { // starts all agents
            t.start();
        }

    }
}

