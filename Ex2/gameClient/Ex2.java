package gameClient;

import Server.Game_Server_Ex2;
import api.game_service;

public class Ex2 {
    private Credentials cred;
    private GUI gui;
    private Thread guiThread;
    private Controller controller;
    private Thread controllerThread;
    private game_service game;
    private LoginScreen login;
    private Thread loginThread;

    /**
     * main function. where we run and start the game
     * this function take the level from the server by the user input and initiate all the variables.
     * this function starts the gui and controller threads and start them with the game thread
     * in the end we join all the threads
     * @param args
     */
    public static void main(String[] args) {
        Ex2 obj = new Ex2();

        if (args.length != 2)
        {
            obj.cred = obj.new Credentials();
            obj.login = new LoginScreen(obj.cred);
            obj.loginThread = new Thread(obj.login);
            obj.gui = new GUI();
            obj.guiThread = new Thread(obj.gui);
            obj.loginThread.start();
            try
            {
                obj.loginThread.join();
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        else {
            obj.cred = obj.new Credentials(args[0], Integer.parseInt(args[1]));
            obj.gui = new GUI();
            obj.guiThread = new Thread(obj.gui);
        }
        game_service game = Game_Server_Ex2.getServer(obj.cred.level);
        boolean t = game.login(Long.parseLong(obj.cred.id));  //login for the server
        if (t == true)
        {
            System.out.println("Logged in to level: " + obj.cred.level);
        }
        obj.controller = new Controller(game);  //creating controller
        obj.gui.setGame(game);  //set the game in the gui class
        obj.gui.setTitle(game.toString());  //set title
        obj.game = game;  //set game
        obj.controllerThread = new Thread(obj.controller);  //creating the controller thread
        obj.controllerThread.start();  //start the controller thread
        obj.guiThread.start();  //start the gui thread
        obj.gui.setTitle("Ex2 level" + "" + obj.cred.level);  //set to proper title
        obj.gui.setResizable(true);
        game.startGame();  //start the game
        while (game.isRunning()) {  //while the game is still running
            //update and repaint
            obj.gui.update(game.getGraph(), game.getPokemons(), obj.controller.getAgents(), obj.game.timeToEnd());  //update the game with the given data
            obj.gui.repaint();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // joining threads because game ended
        try {
            obj.guiThread.join(); // join gui thread
            obj.controllerThread.join(); // join controller thread
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //inner class for the user input
    public class Credentials
    {
        private int level;
        private String id;

        public Credentials()
        {
            setId("");
            setLevel(0);
        }

        public Credentials(String id, int level)
        {
            setId(id);
            setLevel(level);
        }

        public String getId(){return this.id;}
        public void setId(String id) { this.id = id; }

        public void setLevel(int level) { this.level = level; }
        public int getLevel() {return this.level;}
    }
}
