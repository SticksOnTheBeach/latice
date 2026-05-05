package latice.application;

import latice.console.Console;
import latice.model.*;

import static latice.console.Console.message;

public class LaticeApplicationConsole {

	public static void main(String[] args) {
		Referee referee = new Referee();
		Rack rackj1 = new Rack();
		Rack rackj2 = new Rack();

		Player player1 = new Player(rackj1, "testj1");
		Player player2 = new Player(rackj2, "testj2");
		Game game = new Game(player1, player2, referee);
		game.shareTilesBetweenTwoPlayers(player1, player2);
		rackj1.addTileFromDeck(player1.getDeck());
		rackj2.addTileFromDeck(player2.getDeck());

		message(player1.toString());
		message(player2.toString());
		message(rackj1.toString());
		message(rackj2.toString());

	}
}
