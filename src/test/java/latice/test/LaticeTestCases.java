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

	private Game game;
	Rack rackj1 = new Rack();
	Rack rackj2 = new Rack();
	Rack rackj3 = new Rack();
	Player player1 = new Player(rackj1, "testj1");
	Player player2 = new Player(rackj2, "testj2");
	Player player3 = new Player(rackj3, "testj3");

	@BeforeEach
	public void init() {
		Referee referee = new Referee();
		game = new Game(player1, player2, referee);
	}
	//TODO : ajouter des test pour plus de 2 joueurs ou si il n'y a aucun joueur

	@Test
	public void checkNumberOfTiles() {
		List<Tile> listTiles = new ArrayList<Tile>();
		listTiles = game.createTiles();
		assertEquals(60, listTiles.size());
	}

	@Test
	public void checkNumberOfEachDeckForTwoPlayer() {
		game.shareTilesDynamically();

		assertEquals(30, player1.getDeck().getTiles().size());
		assertEquals(30, player2.getDeck().getTiles().size());
		assertEquals(player1.getDeck().getTiles().size(), player2.getDeck().getTiles().size());
	}

	@Test
	public void removeTheTilesFromTheDeck() {
		game.shareTilesBetweenTwoPlayers(player1, player2);
        while (!player1.getDeck().isEmpty()){
			player1.getDeck().remove();
		}
		assertTrue(player1.getDeck().isEmpty());
	}

    @Test
	public void addTilesFromTheRack() {
		game.shareTilesBetweenTwoPlayers(player1, player2);
		rackj1.addTileFromDeck(player1.getDeck());
		assertEquals(25, player1.getDeck().getTiles().size());
		assertEquals(5, rackj1.getTiles().size());
	}

	@Test
	public void addTilesFromTheRackWhenDeckisLessThan5Tiles(){
		game.shareTilesBetweenTwoPlayers(player1, player2);
		for(int i = 27; i > 0; i--){
			player1.getDeck().remove();
		}
		rackj1.addTileFromDeck(player1.getDeck());

		assertEquals(0, player1.getDeck().getTiles().size());
		assertEquals(3, rackj1.getTiles().size());
	}
	@Test
	void checkCreationOfTheGameBoard(){
		Board board = new Board();
		board.createGameBoard();
 assertEquals(SquareType.SUN, board.getSquare(new Position(0, 0)).getType());
 //TODO : verifier toutes les cases du plateau
	}
}
