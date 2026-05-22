package latice.test;


import java.util.ArrayList;
import java.util.List;

import latice.model.*;
import latice.model.square.Square;
import latice.model.square.SquareType;
import latice.model.tile.COLOR;
import latice.model.tile.SHAPE;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import latice.model.tile.Tile;

import static org.junit.jupiter.api.Assertions.*;

public class LaticeTestCases {

	private Game               game;
	private ArrayList<Player> players;

	@BeforeEach
	public void init() {
		players = new ArrayList<>();
		players.add(new Player(new Rack(), "testj1"));
		players.add(new Player(new Rack(), "testj2"));

		Referee referee = new Referee(new Board());
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


	@Test
	public void isEmptyReturnseWhenSquareHasNoTile() {
		Board board = new Board();
		board.createGameBoard();

		assertTrue(board.isEmpty(new Position(4, 4))); // aucune tuile posée
	}

	@Test
	public void isEmptyReturnsFalseenSquareIsOccupied() {
		Board board = new Board();
		board.createGameBoard();

		board.placeTile(new Position(4, 4), new Tile(COLOR.YELLOW, SHAPE.BIRD));

		assertFalse(board.isEmpty(new Position(4, 4)));
	}



	@Test
	public void isBoardEmptyReturnsFalseAfterOneTilePlaced() {
		Board board = new Board();
		board.createGameBoard();

		board.placeTile(new Position(4, 4), new Tile(COLOR.TEAL, SHAPE.DOLPHIN));

		assertFalse(board.isBoardEmpty());
	}

	@Test
	public void isBoardEmptyReturnsFalseAfterMultipleTilesPlaced() {
		Board board = new Board();
		board.createGameBoard();

		board.placeTile(new Position(4, 4), new Tile(COLOR.NAVY, SHAPE.FLOWER));
		board.placeTile(new Position(4, 5), new Tile(COLOR.NAVY, SHAPE.GECKO));

		assertFalse(board.isBoardEmpty());
	}


	@Test
	public void placeTileMakesSquareOccupied() {
		Board board = new Board();
		board.createGameBoard();

		Tile tile = new Tile(COLOR.MAGENTA, SHAPE.FEATHER);
		board.placeTile(new Position(4, 4), tile);

		assertFalse(board.isEmpty(new Position(4, 4)));
	}

	@Test
	public void placeTileStoresCorrectTile() {
		Board board = new Board();
		board.createGameBoard();

		Tile tile = new Tile(COLOR.REDGREEN, SHAPE.TURTLE);
		board.placeTile(new Position(3, 3), tile);

		assertEquals(tile, board.getSquare(new Position(3, 3)).getTile());
	}

	@Test
	public void getSquareReturnsNonNullForValidPosition() {
		Board board = new Board();
		board.createGameBoard();

		assertNotNull(board.getSquare(new Position(0, 0)));
	}

	@Test
	public void getSquareReturnsNullForOutOfBoundsPosition() {
		Board board = new Board();
		board.createGameBoard();

		assertNull(board.getSquare(new Position(99, 99)));
	}

	@Test
	public void getSquareReturnsCorrectSquareAfterPlaceTile() {
		Board board = new Board();
		board.createGameBoard();

		Tile tile = new Tile(COLOR.YELLOW, SHAPE.BIRD);
		board.placeTile(new Position(2, 2), tile);

		Square square = board.getSquare(new Position(2, 2));
		assertTrue(square.isOccupied());
		assertEquals(tile, square.getTile());
	}




	// Tests pour la classe Referee
	@Test
	public void firstMoveOnCenter() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		Tile tile = new Tile(COLOR.YELLOW, SHAPE.BIRD);
		Position center = new Position(4, 4);

		assertTrue(referee.isValidMove(center, tile));
	}

	@Test
	public void firstMoveNotOnCenter() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		Tile tile = new Tile(COLOR.YELLOW, SHAPE.BIRD);
		Position notCenter = new Position(0, 0);

		assertFalse(referee.isValidMove(notCenter, tile));
	}

	@Test
	public void moveOnOccupiedSquare() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		Tile existingTile = new Tile(COLOR.YELLOW, SHAPE.BIRD);
		board.getSquare(new Position(4, 4)).setTile(existingTile);

		Tile newTile = new Tile(COLOR.NAVY, SHAPE.TURTLE);
		assertFalse(referee.isValidMove(new Position(4, 4), newTile));
	}

	@Test
	public void moveAdjacentWithSameColorIsValid() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		board.getSquare(new Position(4, 4)).setTile(new Tile(COLOR.MAGENTA, SHAPE.FEATHER));

		Tile newTile = new Tile(COLOR.MAGENTA, SHAPE.GECKO);
		assertTrue(referee.isValidMove(new Position(4, 5), newTile));
	}

	@Test
	public void moveAdjacentWithSameShapeIsValid() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		board.getSquare(new Position(4, 4)).setTile(new Tile(COLOR.TEAL, SHAPE.DOLPHIN));

		Tile newTile = new Tile(COLOR.NAVY, SHAPE.DOLPHIN);
		assertTrue(referee.isValidMove(new Position(4, 5), newTile));
	}

	@Test
	public void moveAdjacentWithDifferentColorAndShape() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		board.getSquare(new Position(4, 4)).setTile(new Tile(COLOR.YELLOW, SHAPE.BIRD));

		Tile newTile = new Tile(COLOR.TEAL, SHAPE.GECKO);
		assertFalse(referee.isValidMove(new Position(4, 5), newTile));
	}

	@Test
	public void moveWithNoAdjacentTileIsInvalid() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		Tile tile = new Tile(COLOR.NAVY, SHAPE.FLOWER);
		assertFalse(referee.isValidMove(new Position(2, 2), tile)); // case isolée, plateau vide
	}



	@Test
	public void hasAdjacentTileReturnsTrueWhenSameColoe() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		board.getSquare(new Position(3, 4)).setTile(new Tile(COLOR.REDGREEN, SHAPE.TURTLE));

		Tile tile = new Tile(COLOR.REDGREEN, SHAPE.GECKO);
		assertTrue(referee.hasAdjacentTile(new Position(4, 4)));
	}

	@Test
	public void hasAdjacentTileReturnsTrueWhenSameShape() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		board.getSquare(new Position(5, 4)).setTile(new Tile(COLOR.MAGENTA, SHAPE.DOLPHIN));

		Tile tile = new Tile(COLOR.TEAL, SHAPE.DOLPHIN);
		assertTrue(referee.hasAdjacentTile(new Position(4, 4)));
	}

	@Test
	public void hasAdjacentTileReturnsTrueWhenSameColor() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		board.getSquare(new Position(4, 3)).setTile(new Tile(COLOR.NAVY, SHAPE.BIRD));

		Tile tile = new Tile(COLOR.NAVY, SHAPE.FLOWER);
		assertTrue(referee.hasAdjacentTile(new Position(4, 4)));
	}

	@Test
	public void hasAdjacentTileReturnsTrueWhenSameShapes() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);
		board.getSquare(new Position(4, 5)).setTile(new Tile(COLOR.YELLOW, SHAPE.FEATHER));
		Tile tile = new Tile(COLOR.TEAL, SHAPE.FEATHER);
		assertTrue(referee.hasAdjacentTile(new Position(4, 4)));
	}

	@Test
	public void hasAdjacentTileReturnsFalse() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		Tile tile = new Tile(COLOR.YELLOW, SHAPE.GECKO);
		assertFalse(referee.hasAdjacentTile(new Position(4, 4))); // plateau vide
	}


	@Test
	public void hasAdjacentTileReturnsTrueWithMultipleNeighbors() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);
		board.getSquare(new Position(4, 3)).setTile(new Tile(COLOR.NAVY, SHAPE.BIRD));
		board.getSquare(new Position(3, 4)).setTile(new Tile(COLOR.MAGENTA, SHAPE.GECKO));

		Tile tile = new Tile(COLOR.MAGENTA, SHAPE.DOLPHIN);
		assertTrue(referee.hasAdjacentTile(new Position(4, 4)));
	}
}
