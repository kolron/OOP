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
    private game_service game;


    public Controller(game_service game)
    {
        Gson gson = new Gson();
        this.game = game;
        this.graph = new DW_GraphDS(gson.fromJson(game.getGraph(), DW_GraphDS.WrapedDW_GraphDS.class));
        this.agents = new ArrayList<>();
        this.agentsThreads = new ArrayList<>();
        //TODO as per last todo you gotta load everything from the JSON into here.
        try
        {
            JSONObject gameServer = new JSONObject(new JSONObject(game.toString()).get("GameServer").toString());
            int numberOfAgents  = gameServer.getInt("agents");
            CL_Agent[] arr = new CL_Agent[numberOfAgents];
            for (int i = 0; i < numberOfAgents; i++) {
                CL_Agent newAgent = new CL_Agent(graph, i);
                newAgent.setController(this);
                newAgent.setGameService(this.game);
                agents.add(newAgent);
                if (i  < graph.nodeSize()){ //this if is just to make sure, if there's a scenario
                    this.game.addAgent(i);//where there are more agents than nodes, we won't run into an error
                }
               else
               {
                    this.game.addAgent(0);
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

    public ArrayList<CL_Agent> getAgents() { return agents; }
    public ArrayList<CL_Pokemon> getPokemons() { return pokemons; }

    /**
     * run function of the controller
     * he starts every thread of the agents threads
     * create new thread for move function and controlling the number of moves
     * update each agent
     * in the nd join all threads
     */
    @Override
    public void run() {
        for (Thread t: agentsThreads) { // starts all agents
            t.start();
        }
        Thread moveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (game.isRunning())
                {
                    game.move();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        moveThread.start();
        while(game.isRunning()) {
            try {
                JSONObject agentObj = new JSONObject(game.getAgents());   //TODO check getAgents function and why she return something weird
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

