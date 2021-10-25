package battleship;

import battleship.models.Game;

public class Program {

    public static void main(String[] args) {
        Game game = new Game(args);
        game.start();

        while (true) {
            game.readCommand();
        }
    }
}
