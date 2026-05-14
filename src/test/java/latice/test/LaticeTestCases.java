package latice.test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import latice.model.*;
import latice.model.square.SquareType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import latice.model.tile.Tile;

public class LaticeTestCases {

	private Game               game;
	private ArrayList<Player> players;

	@BeforeEach
	public void init() {
		players = new ArrayList<>();
		players.add(new Player(new Rack(), "testj1"));
		players.add(new Player(new Rack(), "testj2"));

		Referee referee = new Referee();
		game = new Game(players, referee, new Board());
	}

	//TODO : ajouter des test si il n'y a aucun joueur

	@Test
	public void checkNumberOfTiles() {
		List<Tile> listTiles = game.createTiles();
		assertEquals(60, listTiles.size());
	}

	@Test
	public void checkNumberOfEachDeckForTwoPlayers() {
		game.shareTilesDynamically();
		for (Player player : players) {
			assertEquals(30, player.getDeck().getTiles().size());
		}
	}

	@Test
	public void checkNumberOfEachDeckForThreePlayers() {
		players.add(new Player(new Rack(), "testj3"));
		game.shareTilesDynamically();
		for (Player player : players) {
			assertEquals(20, player.getDeck().getTiles().size());
		}
	}


	@Test
	public void checkTotalTilesAfterShare() {
		game.shareTilesDynamically();
		int total = players.stream()
				.mapToInt(p -> p.getDeck().getTiles().size())
				.sum();
		assertEquals(60, total);
	}
@Test
	public void removeAllTilesFromDeck() {
		game.shareTilesDynamically();
		Player player = players.get(0);
		while (!player.getDeck().isEmpty()) {
			player.getDeck().remove();
		}
		assertTrue(player.getDeck().isEmpty());
	}
		@Test
	public void addTilesFromTheRack() {
		game.shareTilesDynamically();
			Player player = players.get(0);

			player.getRack().addTileFromDeck(player.getDeck());
		assertEquals(25, player.getDeck().getTiles().size());
		assertEquals(5, player.getRack().getTiles().size());
	}

	@Test
	public void addTilesFromRackWhenDeckHasLessThanFiveTiles() {
		game.shareTilesDynamically();
		Player player = players.get(0);

		// ne laisser que 3 tuiles dans le deck
		for (int i = 0; i < 27; i++) {
			player.getDeck().remove();
		}
		assertEquals(3, player.getDeck().getTiles().size());
		player.getRack().addTileFromDeck(player.getDeck());
		assertEquals(0, player.getDeck().getTiles().size());
		assertEquals(3, player.getRack().getTiles().size());
	}

	@Test
	void checkCreationOfTheGameBoard(){
		Board board = new Board();
		board.createGameBoard();
 assertEquals(SquareType.SUN, board.getSquare(new Position(0, 0)).getType());
 //TODO : verifier toutes les cases du plateau
	}
}
