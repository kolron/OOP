package gameClient;

import api.DW_GraphDS;
import api.game_service;
import com.google.gson.Gson;
import gameClient.util.Panel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUI extends JFrame implements Runnable
{
    private Panel panel;
    private DW_GraphDS graph;
    private String jsonGraph;
    private ArrayList<CL_Agent> agents;
    private ArrayList<CL_Pokemon> pokemons;
    private String jsonPokemons;
    private game_service game;


    public GUI()
    {
        super();
        setLayout(null);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0,0,(int)d.getWidth()/2,(int)d.getHeight()/2);
        panel = new Panel();
        add(panel);
    }

    public game_service getGame() {
        return game;
    }

    public void setGame(game_service game) {
        this.game = game;
    }

    public void run()
    {
        setVisible(true);
        repaint();

//        setVisible(true);
//        while (isVisible()) // while the screen is visible wait
//        {
//            try {
//                Thread.sleep(20);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }


    public void update(String jsonGraph, String jsonPokemon, ArrayList<CL_Agent> agents, long time){
        if (agents != null){
            this.agents = agents;
        }
        if (jsonGraph != null){
            if (!jsonGraph.equals(this.jsonGraph)){
                this.jsonGraph = jsonGraph;
                Gson gson = new Gson();
                DW_GraphDS.WrapedDW_GraphDS wrapedGraph = gson.fromJson(game.getGraph(), DW_GraphDS.WrapedDW_GraphDS.class);
                this.graph = new DW_GraphDS(wrapedGraph);
//                this.graph = new DW_GraphDS((DW_GraphDS.WrapedDW_GraphDS) wrapedGraph);
            }
        }
//        if (jsonPokemon != null){
//            this.pokemons = jsonPokemon;
//        }
        if (jsonPokemon != null){
            if (jsonPokemon != this.jsonPokemons){
                ArrayList<CL_Pokemon> temp = new ArrayList<>(); // create a pokemon array
                try {
                    JSONArray jsonPokemonsArray = (JSONArray) (new JSONObject(jsonPokemon)).get("Pokemons"); // create a JSON array
                    for (int i =0;i<jsonPokemonsArray.length();i++){ //loop through
                        temp.add(CL_Pokemon.init_from_json(jsonPokemonsArray.get(i).toString()));  // create  a pokemon and add it to the array list
                    }
                    pokemons =  temp;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                this.jsonPokemons = jsonPokemon; // update string
            }
        }
        this.panel.update(this.graph,this.agents,this.pokemons,time); // call update panel and update the panel
    }
}

//TODO it is a test for the gui class

//    public static void main(String[] args)
//    {
//        game_service game = Game_Server_Ex2.getServer(11);
//        System.out.println(game);
//        GUI gui = new GUI();
//        GsonBuilder builder = new GsonBuilder();
//        Gson gs = builder.create();
//        Gson gson = new Gson();
//        DW_GraphDS.WrapedDW_GraphDS wrapedGraph = gson.fromJson(game.getGraph(), DW_GraphDS.WrapedDW_GraphDS.class);
//        gui.graph = new DW_GraphDS((DW_GraphDS.WrapedDW_GraphDS) wrapedGraph);
//        gui.pokemons = Arena.json2Pokemons(game.getPokemons());
//        gui.agents = new ArrayList<>();
//        try {
//            JSONObject gameServer = new JSONObject(new JSONObject(game.toString()).get("GameServer").toString());
//            int numberOfAgents  = gameServer.getInt("agents");
//            for (int i = 0; i < numberOfAgents; i++)
//            {
//                CL_Agent newAgent = new CL_Agent(gui.graph,i);
//                gui.agents.add(newAgent);
//                game.addAgent(0);
//            }
//            gameServer = new JSONObject(game.getAgents());   //TODO check getAgents function and why she return something weird
//            JSONArray arr = gameServer.getJSONArray("Agents");
//            for (int i = 0; i <arr.length() ; i++) {
//                gui.agents.get(i).update(arr.get(i).toString());
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        gui.panel.update(gui.graph,gui.agents,gui.pokemons);
//        gui.setVisible(true);
//        gui.setResizable(true);
//        Dimension screanSize = Toolkit.getDefaultToolkit().getScreenSize();
//        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////        gui.setBounds(20, 20, (int) (gui.panel.getXr() * screanSize.getWidth()), (int)screanSize.getHeight());
//        gui.repaint();
//        gui.panel.update(gui.graph,gui.agents,gui.pokemons);
//    }
