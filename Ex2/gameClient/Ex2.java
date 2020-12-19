package gameClient;

import Server.Game_Server_Ex2;
import api.game_service;

public class Ex2 {

//    private MyFrame myFrame;
//    private LoginFrame loginFrame;
//    private Thread loginframethread;
//    private Thread framethread;
//    private SharedLevelBuffer sharedLevelBuffer;
//    private AgentCommander a;
//    private Thread commander;
//    private game_service game;
    private GUI gui;
    private Thread guiThread;
    private Controller controller;
    private Thread controllerThread;
    private game_service game;

    public static void main(String[] args) {
        int level_number = 11;
        game_service game = Game_Server_Ex2.getServer(level_number);
        Ex2 obj = new Ex2();
        obj.controller = new Controller(game);
        obj.game = game;
        obj.gui.setGame(game);
        obj.gui.setTitle(game.toString());
        game.startGame();
        obj.controllerThread = new Thread(obj.controller);
        obj.controllerThread.start();
        obj.guiThread.start();

        Thread moveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (game.isRunning()){
                    try {
                        game.move();
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        moveThread.start();

        while (game.isRunning()){
//            obj.gui.update(game.getGraph(), obj.controller.getPokemons(), obj.controller.getAgents(), obj.game.timeToEnd());
            obj.gui.update(game.getGraph(), game.getPokemons(), obj.controller.getAgents(), obj.game.timeToEnd());
            obj.gui.repaint();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //TODO check this
            try { // joining threads since program ended
                moveThread.join();
                obj.guiThread.join(); // join frame thread
                obj.controllerThread.join(); // join commander thread
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
