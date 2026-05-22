package latice.application;

import latice.console.Console;
import latice.model.*;
import latice.model.tile.Tile;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static latice.console.Console.message;

public class LaticeApplicationConsole {

	public static void main(String[] args) {
		// Instanciation des objets : Arbitres, et rack
		// Instanciation des objets : Joueur
		ArrayList players = new ArrayList<Player>();
		players.add(new Player(new Rack(), "testj1"));
		players.add(new Player(new Rack(), "testj2"));
		Board board = new Board();
		Referee referee = new Referee(board);

		// Instanciation du jeu
		Game game = new Game(players, referee, board);
		game.startGame();
		board.showGameBoard();
		message(game.showRack(game.getCurrentPlayer())); // TODO simplifier la methode pour eviter les apells en cascade

	}
}
