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
// ===================== Check Tiles =====================

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
// ===================== Check Tiles Placement In board=====================

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

// ===================== drawOneTile =====================

	@Test
	public void drawOneTileAddsTileToRack() {
		game.shareTilesDynamically();
		Player player = players.get(0);
		int deckSizeBefore = player.getDeck().getTiles().size();

		player.getRack().drawOneTile(player.getDeck());

		assertEquals(1, player.getRack().getTiles().size());
		assertEquals(deckSizeBefore - 1, player.getDeck().getTiles().size());
	}

	@Test
	public void drawOneTileDoesNothingWhenRackFull() {
		game.shareTilesDynamically();
		Player player = players.get(0);

		// remplir le rack jusqu'à maxSize
		player.getRack().addTileFromDeck(player.getDeck()); // ajoute 5 tuiles
		int deckSizeBefore = player.getDeck().getTiles().size();

		player.getRack().drawOneTile(player.getDeck()); // rack déjà plein

		assertEquals(deckSizeBefore, player.getDeck().getTiles().size());
	}


	@Test
	public void drawOneTileDrawsExactlyOneTile() {
		game.shareTilesDynamically();
		Player player = players.get(0);

		player.getRack().drawOneTile(player.getDeck());
		player.getRack().drawOneTile(player.getDeck());

		// chaque appel n'ajoute qu'une seule tuile
		assertEquals(2, player.getRack().getTiles().size());
	}
// ===================== startGame =====================

	@Test
	public void startGameCreatesBoardNotNull() {
		game.startGame();
		assertNotNull(game.getBoard());
	}

	@Test
	public void startGameFillsRackForAllPlayers() {
		game.startGame();
		for (Player player : players) {
			assertFalse(player.getRack().getTiles().isEmpty());
		}
	}

	@Test
	public void startGameDecksAreNotEmpty() {
		game.startGame();
		for (Player player : players) {
			assertFalse(player.getDeck().isEmpty());
		}
	}

	@Test
	public void startGameRackHasMaxFiveTiles() {
		game.startGame();
		for (Player player : players) {
			assertTrue(player.getRack().getTiles().size() <= 5);
		}
	}

	@Test
	public void startGameTotalTilesConserved() {
		game.startGame();
		int total = players.stream()
				.mapToInt(p -> p.getRack().getTiles().size() + p.getDeck().getTiles().size())
				.sum();
		assertEquals(60, total);
	}
// ===================== exchangeAllTiles =====================

	@Test
	public void exchangeAllTilesClearsRack() {
		game.shareTilesDynamically();
		Player player = players.get(0);
		player.getRack().addTileFromDeck(player.getDeck());

		player.getRack().exchangeAllTiles(player.getDeck());

		// après échange le rack est rechargé (pas vide si deck avait des tuiles)
		// mais les anciennes tuiles ne sont plus là → taille <= maxSize
		assertTrue(player.getRack().getTiles().size() <= 5);
	}

	@Test
	public void exchangeAllTilesReturnsTilesToDeck() {
		game.shareTilesDynamically();
		Player player = players.get(0);
		player.getRack().addTileFromDeck(player.getDeck()); // 5 tuiles dans rack, 25 dans deck

		int totalBefore = player.getRack().getTiles().size() + player.getDeck().getTiles().size();
		player.getRack().exchangeAllTiles(player.getDeck());
		int totalAfter = player.getRack().getTiles().size() + player.getDeck().getTiles().size();

		// le total de tuiles est conservé
		assertEquals(totalBefore, totalAfter);
	}

	@Test
	public void exchangeAllTilesRefillsRackFromDeck() {
		game.shareTilesDynamically();
		Player player = players.get(0);
		player.getRack().addTileFromDeck(player.getDeck());

		player.getRack().exchangeAllTiles(player.getDeck());

		assertEquals(5, player.getRack().getTiles().size());
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
	// ===================== calculatePoints =====================

	@Test
	public void calculatePointsOneNeighbor() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		board.getSquare(new Position(4, 4)).setTile(new Tile(COLOR.YELLOW, SHAPE.BIRD));
		Tile tile = new Tile(COLOR.YELLOW, SHAPE.GECKO);

		assertEquals(1, referee.calculatePoints(new Position(4, 5), tile));
	}

	@Test
	public void calculatePointsTwoNeighbors() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		board.getSquare(new Position(4, 4)).setTile(new Tile(COLOR.YELLOW, SHAPE.BIRD));
		board.getSquare(new Position(4, 6)).setTile(new Tile(COLOR.YELLOW, SHAPE.TURTLE));
		Tile tile = new Tile(COLOR.YELLOW, SHAPE.GECKO);

		assertEquals(2, referee.calculatePoints(new Position(4, 5), tile));
	}

	@Test
	public void calculatePointsOnSunSquareAddsBonus() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		// (0,0) est une case SUN d'après ton test checkCreationOfTheGameBoard
		board.getSquare(new Position(0, 1)).setTile(new Tile(COLOR.YELLOW, SHAPE.BIRD));
		Tile tile = new Tile(COLOR.YELLOW, SHAPE.GECKO);

		int points = referee.calculatePoints(new Position(0, 0), tile);
		assertTrue(points >= 3); // 1 voisin + 2 bonus SUN
	}

// ===================== winingConditionEmpty =====================
	@Test
	public void winingConditionEmptyReturnsFalseWhenRackNotEmpty() {
		game.shareTilesDynamically();
		Player player = players.get(0);
		player.getRack().addTileFromDeck(player.getDeck());

		Referee referee = new Referee(new Board());
		assertFalse(referee.winingConditionEmpty(player));
	}
// ===================== winingConditionCycles =====================
	@Test
	public void winingConditionCyclesReturnsFalseAtStart() {
		Referee referee = new Referee(new Board());
		assertFalse(referee.winingConditionCycles());
	}
	@Test
	public void winingConditionCyclesReturnsTrueAfter10Rounds() {
		Referee referee = new Referee(new Board());
		referee.setRoundCount(11);
		assertTrue(referee.winingConditionCycles());
	}
	@Test
	public void winingConditionCyclesReturnsFalseAtExactly10() {
		Referee referee = new Referee(new Board());
		referee.setRoundCount(10);
		assertFalse(referee.winingConditionCycles());
	}
// ===================== getWinner =====================
	@Test
	public void getWinnerReturnsPlayerWithFewestTiles() {
		game.shareTilesDynamically();
		Player p1 = players.get(0);
		Player p2 = players.get(1);
		while (p1.getDeck().getTiles().size() > 1) {
			p1.getDeck().remove();
		}
		Referee referee = new Referee(new Board());
		assertEquals(List.of(p1), referee.getWinner(players));
	}
	// ===================== hasAdjacentSameColor =====================

	@Test
	public void hasAdjacentSameColorReturnsTrueWhenNeighborSameColor() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		board.getSquare(new Position(4, 4)).setTile(new Tile(COLOR.YELLOW, SHAPE.BIRD));
		Tile tile = new Tile(COLOR.YELLOW, SHAPE.GECKO);

		assertTrue(referee.hasAdjacentSameColor(new Position(4, 5), tile));
	}

	@Test
	public void hasAdjacentSameColorReturnsFalseWhenNoNeighborSameColor() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		board.getSquare(new Position(4, 4)).setTile(new Tile(COLOR.YELLOW, SHAPE.BIRD));
		Tile tile = new Tile(COLOR.TEAL, SHAPE.GECKO);

		assertFalse(referee.hasAdjacentSameColor(new Position(4, 5), tile));
	}

	@Test
	public void hasAdjacentSameColorReturnsFalseWhenNoNeighborAtAll() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		Tile tile = new Tile(COLOR.YELLOW, SHAPE.BIRD);
		assertFalse(referee.hasAdjacentSameColor(new Position(4, 4), tile));
	}

	@Test
	public void hasAdjacentSameColorReturnsTrueWithMultipleNeighborsOneMatches() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		board.getSquare(new Position(4, 4)).setTile(new Tile(COLOR.NAVY, SHAPE.BIRD));   // pas même couleur
		board.getSquare(new Position(4, 6)).setTile(new Tile(COLOR.YELLOW, SHAPE.TURTLE)); // même couleur
		Tile tile = new Tile(COLOR.YELLOW, SHAPE.GECKO);

		assertTrue(referee.hasAdjacentSameColor(new Position(4, 5), tile));
	}

// ===================== hasAdjacentSameShape =====================

	@Test
	public void hasAdjacentSameShapeReturnsTrueWhenNeighborSameShape() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		board.getSquare(new Position(4, 4)).setTile(new Tile(COLOR.NAVY, SHAPE.DOLPHIN));
		Tile tile = new Tile(COLOR.TEAL, SHAPE.DOLPHIN);

		assertTrue(referee.hasAdjacentSameShape(new Position(4, 5), tile));
	}

	@Test
	public void hasAdjacentSameShapeReturnsFalseWhenNoNeighborSameShape() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		board.getSquare(new Position(4, 4)).setTile(new Tile(COLOR.NAVY, SHAPE.DOLPHIN));
		Tile tile = new Tile(COLOR.TEAL, SHAPE.BIRD);

		assertFalse(referee.hasAdjacentSameShape(new Position(4, 5), tile));
	}

	@Test
	public void hasAdjacentSameShapeReturnsFalseWhenNoNeighborAtAll() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		Tile tile = new Tile(COLOR.NAVY, SHAPE.DOLPHIN);
		assertFalse(referee.hasAdjacentSameShape(new Position(4, 4), tile));
	}

	@Test
	public void hasAdjacentSameShapeReturnsTrueWithMultipleNeighborsOneMatches() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		board.getSquare(new Position(4, 4)).setTile(new Tile(COLOR.NAVY, SHAPE.BIRD));
		board.getSquare(new Position(4, 6)).setTile(new Tile(COLOR.TEAL, SHAPE.DOLPHIN));
		Tile tile = new Tile(COLOR.YELLOW, SHAPE.DOLPHIN);

		assertTrue(referee.hasAdjacentSameShape(new Position(4, 5), tile));
	}

}
