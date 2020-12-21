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
        boolean t = game.login(Long.parseLong(obj.cred.id));
        if (t == true)
        {
            System.out.println("Logged in to level: " + obj.cred.level);
        }
        obj.controller = new Controller(game);
        obj.gui.setGame(game);
        obj.gui.setTitle(game.toString());
        obj.game = game;
        obj.controllerThread = new Thread(obj.controller);
        obj.controllerThread.start();
        obj.guiThread.start();
        obj.gui.setTitle("Ex2 level" + "" + obj.cred.level);
        obj.gui.setResizable(true);
        game.startGame();
        while (game.isRunning()) {
            obj.gui.update(game.getGraph(), game.getPokemons(), obj.controller.getAgents(), obj.game.timeToEnd());
            obj.gui.repaint();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //TODO check this
        try { // joining threads since program ended

            obj.guiThread.join(); // join gui thread
            obj.controllerThread.join(); // join controller thread
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

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
