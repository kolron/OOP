package gameClient;

import api.DW_GraphDS;
import api.game_service;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        this.graph = new DW_GraphDS(gson.fromJson(game.getGraph(), DW_GraphDS.WrapedDW_GraphDS.class));
        this.agents = new ArrayList<>();
        this.agentsThreads = new ArrayList<>();
        //TODO as per last todo you gotta load everything from the JSON into here.
        try
        {
            JSONObject gameServer = new JSONObject(new JSONObject(game.toString()).get("GameServer").toString());
            int numberOfAgents  = gameServer.getInt("agents");
            for (int i = 0; i < numberOfAgents; i++) {
                CL_Agent newAgent = new CL_Agent(graph, i);
                newAgent.setController(this);
                agents.add(newAgent);
                if (gameServer.getInt("Nodes")<i) {
                    currGame.addAgent(i);
                }
                else
                {
                    currGame.addAgent(0);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        Thread moveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (currGame.isRunning())
                {
                    currGame.move();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        moveThread.start();
        while(currGame.isRunning()) {
            currGame.move();
            try {
                JSONObject agentObj = new JSONObject(currGame.getAgents());   //TODO check getAgents function and why she return something weird
                JSONArray agentArr = agentObj.getJSONArray("Agents");
                for (int i = 0; i < agentArr.length(); i++) {
                    agents.get(i).update(agentArr.get(i).toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            moveThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Thread t : agentsThreads) { // join all threads
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }
}

