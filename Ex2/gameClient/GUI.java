package gameClient;

import Server.Game_Server_Ex2;
import api.DW_GraphDS;
import api.game_service;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    private ArrayList<CL_Agent> agents;
    private ArrayList<CL_Pokemon> pokemons;

    public GUI()
    {
        super();
        setLayout(null);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0,0,(int)d.getWidth()/2,(int)d.getHeight()/2);
        panel = new Panel();
        add(panel);
    }

    public void run()
    {
        setVisible(true);
        repaint();
    }
    public static void main(String args [])
    {
        game_service game = Game_Server_Ex2.getServer(11);
        System.out.println(game);
        GUI gui = new GUI();
        GsonBuilder builder = new GsonBuilder();
        Gson gs = builder.create();
        gui.graph = gs.fromJson(game.getGraph(), DW_GraphDS.class);
        gui.pokemons = Arena.json2Pokemons(game.getPokemons());
        gui.agents = new ArrayList<>();
        try {
            JSONObject gameServer = new JSONObject(new JSONObject(game.toString()).get("GameServer").toString());
            int numberOfAgents  = gameServer.getInt("agents");
            for (int i = 0; i < numberOfAgents; i++)
            {
                CL_Agent newAgent = new CL_Agent(gui.graph,i);
                gui.agents.add(newAgent);
            }
            gameServer = new JSONObject(game.getAgents());
            JSONArray arr = gameServer.getJSONArray("Agents");
            for (int i = 0; i <arr.length() ; i++) {
                gui.agents.get(i).update(arr.get(i).toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        gui.panel.update(gui.graph,gui.agents,gui.pokemons);
        gui.repaint();
        //g.panel.update();  //TODO fix save_load and check if what we did is good
    }
}
