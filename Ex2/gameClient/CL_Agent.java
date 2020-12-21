 package gameClient;

import api.*;
import gameClient.util.Point3D;
import org.json.JSONObject;
import java.util.*;

public class CL_Agent implements Runnable {
	public static final double EPS = 0.0001;
	private static int _count = 0;
	private static int _seed = 3331;
	private int id;
	private int dest;
	//	private long _key;
	private geo_location pos;
	private double speed;
	private edge_data currEdge;
	private node_data currNode;
	private directed_weighted_graph graph;
	private CL_Pokemon currFruit;
	private ArrayList<CL_Pokemon> nextPokemon;
	private long sgDt;
	private Controller controller;  //TODO maybe remove controller from agent
	private double value;
	private game_service gameService;


	public CL_Agent(directed_weighted_graph g, int start_node) {
		graph = g;
		setMoney(0);
		this.currNode = graph.getNode(start_node);
		pos = currNode.getLocation();
		id = -1;
		setSpeed(0);
		nextPokemon = new ArrayList<>();
	}

	public void update(String json) {
		JSONObject line;
		try {
			// "GameServer":{"graph":"A0","pokemons":3,"agents":1}}
			line = new JSONObject(json);
			JSONObject agent = line.getJSONObject("Agent");
			int id = agent.getInt("id");
			if (id == this.getID() || this.getID() == -1) {
				if (this.getID() == -1) {
					this.id = id;
				}
				double speed = agent.getDouble("speed");
				String position = agent.getString("pos");
				Point3D position3D = new Point3D(position);
				int src = agent.getInt("src");
				int dest = agent.getInt("dest");
				double value = agent.getDouble("value");
				this.pos = position3D;
				this.setCurrNode(src);
				this.setSpeed(speed);
				this.dest = dest;
				this.setMoney(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	//@Override
	public int getSrcNode() {
		return this.currNode.getKey();
	}

	public String toJSON() {
		int d = this.getNextNode();
		String ans = "{\"Agent\":{"
				+ "\"id\":" + this.id + ","
				+ "\"value\":" + this.value + ","
				+ "\"src\":" + this.currNode.getKey() + ","
				+ "\"dest\":" + d + ","
				+ "\"speed\":" + this.getSpeed() + ","
				+ "\"pos\":\"" + pos.toString() + "\""
				+ "}"
				+ "}";
		return ans;
	}

	private void setMoney(double v) {
		value = v;
	}

	public boolean setNextNode(int dest) {
		boolean ans = false;
		int src = this.currNode.getKey();
		this.currEdge = graph.getEdge(src, dest);
		if (currEdge != null) {
			ans = true;
		} else {
			currEdge = null;
		}
		return ans;
	}

	public void setCurrNode(int src) {
		this.currNode = graph.getNode(src);
	}

	public boolean isMoving() {
		return this.currEdge != null;
	}

	public String toString() {
		return toJSON();
	}

	public String toString1() {
		String ans = "" + this.getID() + "," + pos + ", " + isMoving() + "," + this.getValue();
		return ans;
	}

	public int getID() {
		// TODO Auto-generated method stub
		return this.id;
	}

	public geo_location getLocation() {
		// TODO Auto-generated method stub
		return pos;
	}


	public double getValue() {
		// TODO Auto-generated method stub
		return this.value;
	}


	public int getNextNode() {
		int ans = -2;
		if (this.currEdge == null) {
			ans = -1;
		} else {
			ans = this.currEdge.getDest();
		}
		return ans;
	}

	public double getSpeed() {
		return this.speed;
	}

	public void setSpeed(double v) {
		this.speed = v;
	}

	public CL_Pokemon getCurrFruit() {
		return currFruit;
	}

	public void setCurrFruit(CL_Pokemon curr_fruit) {
		this.currFruit = curr_fruit;
	}

	public edge_data getCurrEdge() {
		return this.currEdge;
	}

	public void setGameService(game_service gameService) {
		this.gameService = gameService;
	}

	public void setNextPokemon(String pokemons) {
		DWGraph_Algo ag = new DWGraph_Algo((DW_GraphDS) graph);
		ag.init(ag.copy());
		ArrayList<CL_Pokemon> pokemonArray = Arena.json2Pokemons(pokemons);
		for (CL_Pokemon pokemon : pokemonArray) {  //update the arena and the pokemon min dist for each pokemon
			Arena.updateEdge(pokemon, graph);
			pokemon.setMinDist(ag.shortestPathDist(this.currNode.getKey(), pokemon.get_edge().getDest()));
		}
		pokemonArray.sort(new Comparator<>() {  //sort the array by value/minDist
			@Override
			public int compare(CL_Pokemon p1, CL_Pokemon p2) {
				return Double.compare(p2.getValue()/p2.getMinDist(), p1.getValue()/p1.getMinDist());
			}
		});
		for (CL_Pokemon pokemon : pokemonArray) {
			nextPokemon.add(pokemon);
		}
	}

	//agent's algo
	@Override
	public void run() {
		DWGraph_Algo ag = new DWGraph_Algo((DW_GraphDS) graph);
		ag.init(ag.copy());
		List<node_data> path = null;  //path for the agent know where to go to
		System.out.printf("Agent %d created!\n", this.getID());
		while (gameService.isRunning()) {
			if (path == null || currFruit == null) {  //if path or currFruit is null or
				while (dest != -1) {
					try {
						Thread.sleep(1);  //sleep
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				setNextPokemon(gameService.getPokemons());  //search for the next pokemon
				if (nextPokemon.size() != 0) {
					CL_Pokemon nextPok = nextPokemon.remove(0);  //get the next pokemon
					setCurrFruit(nextPok);  //set him as a currFruit
					path = ag.shortestPath(this.getSrcNode(), nextPok.get_edge().getSrc());  //put in path the shortest path to the pokemon
					if (path != null && path.size() > 0) {  //if there are nodes in path to go to
						path.remove(0);  //remove the node that you already been at
					}
					path.add(graph.getNode(nextPok.get_edge().getDest()));  //add the node that the pokemon in on
				}
			}
			if (path != null && currFruit != null) {  //if both path and currFruit isn't null
				while (dest != -1)
				{
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (path.size() >= 1) {
					node_data nextNode = path.remove(0);  //take the next node to go to
					gameService.chooseNextEdge(id, nextNode.getKey());
				}
				else {
					//set both to null
					setCurrFruit(null);
					path = null;
				}
			}
		}
	}
}

