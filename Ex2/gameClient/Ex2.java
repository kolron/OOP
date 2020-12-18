package gameClient;

import Server.Game_Server_Ex2;
import api.game_service;

public class Ex2 {

    private game_service game;

    public static void main(String[] args) {
        int level_number = 0;
        game_service game = Game_Server_Ex2.getServer(level_number);

    }
}
