package gameClient;

import api.DW_GraphDS;
import api.game_service;
import com.google.gson.Gson;
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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

            }
        }
        if (jsonPokemon != null){
            if (!jsonPokemon.equals(this.jsonPokemons)){
                ArrayList<CL_Pokemon> temp = new ArrayList<>(); // create a pokemon array
                try {
                    JSONArray jsonPokemonsArray = (JSONArray) (new JSONObject(jsonPokemon)).get("Pokemons");
                    for (int i =0;i<jsonPokemonsArray.length();i++){ //loop through
                        temp.add(CL_Pokemon.init_from_json(jsonPokemonsArray.get(i).toString()));
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

