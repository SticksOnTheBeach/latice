package latice.test;


import java.util.ArrayList;
import java.util.List;

import latice.model.*;
import latice.model.action.BuyExtraMoveAction;
import latice.model.action.ExchangeRackAction;
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
		game = new Game(players, new Board());
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
	public void noTileRemovedIfDeckIsEmpty() {
		game.shareTilesDynamically();
		Player player = players.get(0);

		// vider le deck
		while (!player.getDeck().isEmpty()) {
			player.getDeck().remove();
		}
		assertTrue(player.getDeck().isEmpty());

		player.getRack().addTileFromDeck(player.getDeck());

		assertTrue(player.getDeck().isEmpty());
		assertTrue(player.getRack().getTiles().isEmpty());
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




	// ===================== calculatePoints =====================

	@Test
	public void calculatePointsOneNeighbor() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		board.getSquare(new Position(4, 4)).setTile(new Tile(COLOR.YELLOW, SHAPE.BIRD));
		Tile tile = new Tile(COLOR.YELLOW, SHAPE.GECKO);

		assertEquals(0, referee.calculatePoints(new Position(4, 5), tile));
	}

	@Test
	public void calculatePointsTwoNeighbors() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		board.getSquare(new Position(4, 4)).setTile(new Tile(COLOR.YELLOW, SHAPE.BIRD));
		board.getSquare(new Position(4, 6)).setTile(new Tile(COLOR.YELLOW, SHAPE.TURTLE));
		Tile tile = new Tile(COLOR.YELLOW, SHAPE.GECKO);

		assertEquals(1, referee.calculatePoints(new Position(4, 5), tile));
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
		assertTrue(points >= 2); // 1 voisin + 2 bonus SUN
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
// ===================== hasValidNeighbors =====================

	@Test
	public void hasValidNeighbors_returnsFalse_whenNoNeighbors() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		Tile tile = new Tile(COLOR.YELLOW, SHAPE.BIRD);
		assertFalse(referee.hasValidNeighbors(new Position(2, 2), tile));
	}

	@Test
	public void hasValidNeighbors_returnsTrue_whenNeighborSameColor() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		board.getSquare(new Position(4, 4)).setTile(new Tile(COLOR.YELLOW, SHAPE.BIRD));
		Tile tile = new Tile(COLOR.YELLOW, SHAPE.GECKO);

		assertTrue(referee.hasValidNeighbors(new Position(4, 5), tile));
	}

	@Test
	public void hasValidNeighbors_returnsTrue_whenNeighborSameShape() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		board.getSquare(new Position(4, 4)).setTile(new Tile(COLOR.TEAL, SHAPE.DOLPHIN));
		Tile tile = new Tile(COLOR.NAVY, SHAPE.DOLPHIN);

		assertTrue(referee.hasValidNeighbors(new Position(4, 5), tile));
	}

	@Test
	public void hasValidNeighbors_returnsFalse_whenNeighborDifferentColorAndShape() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		board.getSquare(new Position(4, 4)).setTile(new Tile(COLOR.YELLOW, SHAPE.BIRD));
		Tile tile = new Tile(COLOR.TEAL, SHAPE.GECKO);

		assertFalse(referee.hasValidNeighbors(new Position(4, 5), tile));
	}

	@Test
	public void hasValidNeighbors_returnsFalse_whenOneValidAndOneInvalidNeighbor() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		board.getSquare(new Position(4, 4)).setTile(new Tile(COLOR.YELLOW, SHAPE.BIRD));   // même couleur ✓
		board.getSquare(new Position(4, 6)).setTile(new Tile(COLOR.TEAL, SHAPE.GECKO));    // aucun match ✗
		Tile tile = new Tile(COLOR.YELLOW, SHAPE.DOLPHIN);

		assertFalse(referee.hasValidNeighbors(new Position(4, 5), tile));
	}

	@Test
	public void hasValidNeighbors_returnsTrue_whenTwoValidNeighbors() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		board.getSquare(new Position(4, 4)).setTile(new Tile(COLOR.YELLOW, SHAPE.BIRD));
		board.getSquare(new Position(4, 6)).setTile(new Tile(COLOR.TEAL, SHAPE.DOLPHIN));
		Tile tile = new Tile(COLOR.YELLOW, SHAPE.DOLPHIN); // même couleur que (4,4), même forme que (4,6)

		assertTrue(referee.hasValidNeighbors(new Position(4, 5), tile));
	}

// ===================== countMatchingNeighbors =====================

	@Test
	public void countMatchingNeighbors_returnsZero_whenNoNeighbors() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		Tile tile = new Tile(COLOR.YELLOW, SHAPE.BIRD);
		assertEquals(0, referee.countMatchingNeighbors(new Position(2, 2), tile));
	}

	@Test
	public void countMatchingNeighbors_returnsOne_whenOneNeighborSameColor() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		board.getSquare(new Position(4, 4)).setTile(new Tile(COLOR.YELLOW, SHAPE.BIRD));
		Tile tile = new Tile(COLOR.YELLOW, SHAPE.GECKO);

		assertEquals(1, referee.countMatchingNeighbors(new Position(4, 5), tile));
	}

	@Test
	public void countMatchingNeighbors_returnsOne_whenOneNeighborSameShape() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		board.getSquare(new Position(4, 4)).setTile(new Tile(COLOR.TEAL, SHAPE.DOLPHIN));
		Tile tile = new Tile(COLOR.NAVY, SHAPE.DOLPHIN);

		assertEquals(1, referee.countMatchingNeighbors(new Position(4, 5), tile));
	}

	@Test
	public void countMatchingNeighbors_returnsZero_whenNoMatch() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		board.getSquare(new Position(4, 4)).setTile(new Tile(COLOR.YELLOW, SHAPE.BIRD));
		Tile tile = new Tile(COLOR.TEAL, SHAPE.GECKO);

		assertEquals(0, referee.countMatchingNeighbors(new Position(4, 5), tile));
	}

	@Test
	public void countMatchingNeighbors_returnsTwo_whenTwoNeighborsBothMatch() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		board.getSquare(new Position(4, 4)).setTile(new Tile(COLOR.YELLOW, SHAPE.BIRD));
		board.getSquare(new Position(4, 6)).setTile(new Tile(COLOR.TEAL, SHAPE.DOLPHIN));
		Tile tile = new Tile(COLOR.YELLOW, SHAPE.DOLPHIN); // match couleur (4,4) + match forme (4,6)

		assertEquals(2, referee.countMatchingNeighbors(new Position(4, 5), tile));
	}

	@Test
	public void countMatchingNeighbors_returnsOne_whenTwoNeighborsOnlyOneMatches() {
		Board board = new Board();
		board.createGameBoard();
		Referee referee = new Referee(board);

		board.getSquare(new Position(4, 4)).setTile(new Tile(COLOR.YELLOW, SHAPE.BIRD));   // match couleur ✓
		board.getSquare(new Position(4, 6)).setTile(new Tile(COLOR.TEAL, SHAPE.GECKO));    // aucun match ✗
		Tile tile = new Tile(COLOR.YELLOW, SHAPE.DOLPHIN);

		assertEquals(1, referee.countMatchingNeighbors(new Position(4, 5), tile));
	}

// ===================== BuyExtraMoveAction =====================

	@Test
	public void buyExtraMove_deductsPointsAndResetsFlag_whenConditionsMet() {
		Rack rack = new Rack();
		Player player = new Player(rack, "TestPlayer");
		ArrayList<Tile> tiles = new ArrayList<>();
		for (int i = 0; i < 10; i++) tiles.add(new Tile(COLOR.NAVY, SHAPE.DOLPHIN));
		player.setDeck(new Deck(tiles));
		player.setScore(5);

		Referee referee = new Referee(new Board());
		referee.setHasPlayedAction(true);

		new BuyExtraMoveAction().buyExtraMove(player, referee);

		assertEquals(3, player.getScore());
		assertFalse(referee.isHasPlayedAction());
	}

	@Test
	public void buyExtraMove_doesNothing_whenScoreTooLow() {
		Rack rack = new Rack();
		Player player = new Player(rack, "TestPlayer");
		player.setDeck(new Deck(new ArrayList<>()));
		player.setScore(1);

		Referee referee = new Referee(new Board());
		referee.setHasPlayedAction(true);

		new BuyExtraMoveAction().buyExtraMove(player, referee);

		assertEquals(1, player.getScore());
		assertTrue(referee.isHasPlayedAction());
	}

	@Test
	public void buyExtraMove_doesNothing_whenFlagIsFalse() {
		Rack rack = new Rack();
		Player player = new Player(rack, "TestPlayer");
		player.setDeck(new Deck(new ArrayList<>()));
		player.setScore(5);

		Referee referee = new Referee(new Board());
		referee.setHasPlayedAction(false);

		new BuyExtraMoveAction().buyExtraMove(player, referee);

		assertEquals(5, player.getScore());
		assertFalse(referee.isHasPlayedAction());
	}

	@Test
	public void buyExtraMove_worksWithExactlyTwoPoints() {
		Rack rack = new Rack();
		Player player = new Player(rack, "TestPlayer");
		player.setDeck(new Deck(new ArrayList<>()));
		player.setScore(2);

		Referee referee = new Referee(new Board());
		referee.setHasPlayedAction(true);

		new BuyExtraMoveAction().buyExtraMove(player, referee);

		assertEquals(0, player.getScore());
		assertFalse(referee.isHasPlayedAction());
	}

// ===================== ExchangeRackAction =====================

	@Test
	public void exchangeRack_setsFlag_whenNoActionPlayedYet() {
		game.startGame();
		Player player = players.get(0);
		Referee referee = new Referee(game.getBoard());
		referee.setHasPlayedAction(false);

		new ExchangeRackAction().exchangeRack(player, referee);

		assertTrue(referee.isHasPlayedAction());
		assertEquals(5, player.getRack().getTiles().size());
	}

	@Test
	public void exchangeRack_deductsTwoPoints_whenActionAlreadyPlayed() {
		game.startGame();
		Player player = players.get(0);
		player.setScore(4);
		Referee referee = new Referee(game.getBoard());
		referee.setHasPlayedAction(true);

		new ExchangeRackAction().exchangeRack(player, referee);

		assertEquals(2, player.getScore());
		assertEquals(5, player.getRack().getTiles().size());
	}

// ===================== Deck =====================

	@Test
	public void deck_getFirstTile_returnsFirstElement() {
		ArrayList<Tile> tiles = new ArrayList<>();
		Tile first = new Tile(COLOR.TEAL, SHAPE.DOLPHIN);
		tiles.add(first);
		tiles.add(new Tile(COLOR.NAVY, SHAPE.DOLPHIN));
		Deck d = new Deck(tiles);

		assertEquals(first, d.getFirstTile());
	}

	@Test
	public void deck_getFirstTile_returnsNull_whenEmpty() {
		assertNull(new Deck(new ArrayList<>()).getFirstTile());
	}


}
