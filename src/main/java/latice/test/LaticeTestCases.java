package latice.test;

import static org.junit.Assert.*;

import latice.model.*;
import latice.model.tile.Tile;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class LaticeTestCases {

	private Game game;
	Rack rackj1 = new Rack();
	Rack rackj2 = new Rack();
	Player player1 = new Player(rackj1, "testj1");
	Player player2 = new Player(rackj2, "testj2");
	@Before
	public void init() {
		Referee referee = new Referee();
		game = new Game(player1, player2, referee);
	}

	@Test
	public void checkNumberOfTiles() {
		List<Tile> listTiles = new ArrayList<Tile>();
		listTiles = game.createAndShuffleTiles();
		assertEquals(60, listTiles.size());
	}
	@Test
	public void checkNumberOfEachDeck() {
		game.shareTilesBetweenTwoPlayers(player1, player2);

		assertEquals(30, player1.getDeck().getTiles().size());
		assertEquals(30, player2.getDeck().getTiles().size());
		assertEquals(player1.getDeck().getTiles().size(), player2.getDeck().getTiles().size());//verify if the two deck are equals
	}

	@Test
	public void removeTheTilesFromTheDeck() {
		game.shareTilesBetweenTwoPlayers(player1, player2);
        while (player1.getDeck().isempty() == false){
			player1.getDeck().remove();
		}
		assertEquals(true, player1.getDeck().isempty());
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
}