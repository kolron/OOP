package gameClient.util;

import api.DW_GraphDS;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.Arena;
import gameClient.CL_Agent;
import gameClient.CL_Pokemon;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Panel extends JPanel {
    private DW_GraphDS graph;
    private ArrayList<CL_Agent> agents;
    private ArrayList<CL_Pokemon> pokemons;
    private Range xr;
    private Range yr;
    private long time;

    public Panel ()
    {
        super();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0,0,d.width/2,d.height/2);
        setBackground(Color.white);
        xr = new Range(20,this.getWidth()-20);
        yr = new Range(20,this.getHeight()-20);
    }

    @Override
    public void paint(Graphics g)
    {
        g.drawString("Time:" + this.time, 30,30);
        // paint Nodes
        if(graph!=null)
        {
            Range2Range t = Arena.w2f(graph,new Range2D(xr,yr));
            for (node_data n : graph.getV())
            {
                geo_location l =  t.world2frame(n.getLocation());
                g.setColor(Color.BLACK);
                g.fillOval((int) l.x()-5, (int)l.y()-5, 10,10);
                for(edge_data e :graph.getE(n.getKey())) // paint lines
                {
                    node_data t1 = graph.getNode(e.getDest());
                    geo_location l2 = t.world2frame(t1.getLocation());
                    g.setColor(Color.gray);
                    g.drawLine((int)l.x(),(int)l.y(),(int)l2.x(),(int)l2.y());
                }
            }

            g.setColor(Color.RED);
            for (CL_Agent agent:agents)
            {
                geo_location l = t.world2frame(agent.getLocation());
                g.fillOval((int)l.x()-8,(int)l.y()-8,15,15);
            }
            // pokemons
            for (CL_Pokemon pokemon:pokemons)
            {
                if(pokemon.getType()==-1) {
                    g.setColor(Color.BLUE);
                }
                else {
                    g.setColor(Color.yellow);
                }
                geo_location l = t.world2frame(pokemon.getLocation());
                g.fillOval((int)l.x()-8,(int)l.y()-8,15,15);
            }
        }
    }

    public void update(DW_GraphDS graph, ArrayList<CL_Agent> agents, ArrayList<CL_Pokemon> pokemons)
    {
        setGraph(graph);
        setAgents(agents);
        setPokemons(pokemons);
        repaint();
    }

    public DW_GraphDS getGraph() {
        return graph;
    }

    public void setGraph(DW_GraphDS graph) {
        this.graph = graph;
    }

    public ArrayList<CL_Agent> getAgents() {
        return agents;
    }

    public void setAgents(ArrayList<CL_Agent> agents) {
        this.agents = agents;
    }

    public ArrayList<CL_Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(ArrayList<CL_Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    public Range getXr() {
        return xr;
    }

    public void setXr(Range xr) {
        this.xr = xr;
    }

    public Range getYr() {
        return yr;
    }

    public void setYr(Range yr) {
        this.yr = yr;
    }
}
