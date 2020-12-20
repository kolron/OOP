package gameClient;

import api.DW_GraphDS;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.util.Range2Range;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Panel extends JPanel {
    private DW_GraphDS graph;
    private ArrayList<CL_Agent> agents;
    private ArrayList<CL_Pokemon> pokemons;
    private Dimension d;
    private Range xr;
    private Range yr;

    private long time;

    public Panel ()
    {
        super();
        d = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0,0,d.width/2,d.height/2);
        setBackground(Color.white);
        xr = new Range(20,this.getWidth()-20);
        yr = new Range(20,this.getHeight()-20);
    }

    @Override
    public void paint(Graphics g)
    {
        g.drawString("Time left: " + (int)(this.time/1000), 30,30);
        int score = 0;
        for (CL_Agent agent : this.agents) {
            score += agent.getValue();
        }
        g.drawString("Score: " + score, 30, 50);
        // paint Nodes
        if(graph!=null)
        {
            Range2Range t = Arena.w2f(graph,new Range2D(xr,yr));
            for (node_data node : graph.getV())
            {
                geo_location nodeLocation =  t.world2frame(node.getLocation());
                g.setColor(Color.BLACK);
                g.fillOval((int) nodeLocation.x()-5, (int)nodeLocation.y()-5, 10,10);
                for(edge_data edge :graph.getE(node.getKey())) // paint lines
                {
                    node_data destNode = graph.getNode(edge.getDest());
                    geo_location destNodeLocation = t.world2frame(destNode.getLocation());
                    g.setColor(Color.gray);
                    g.drawLine((int)nodeLocation.x(),(int)nodeLocation.y(),(int)destNodeLocation.x(),(int)destNodeLocation.y());
                }
            }

            g.setColor(Color.RED);
            for (CL_Agent agent:agents)
            {
                geo_location agentLocation = t.world2frame(agent.getLocation());
                g.fillOval((int)agentLocation.x()-8,(int)agentLocation.y()-8,15,15);
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
                geo_location pokemonLocation = t.world2frame(pokemon.getLocation());
                g.fillOval((int)pokemonLocation.x()-8,(int)pokemonLocation.y()-8,15,15);
            }
        }
    }

    public void update(DW_GraphDS graph, ArrayList<CL_Agent> agents, ArrayList<CL_Pokemon> pokemons, long time)
    {
        setGraph(graph);
        setAgents(agents);
        setPokemons(pokemons);
        setTime(time);
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

    public void setTime(long time) { this.time = time; }

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
