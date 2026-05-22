package latice.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import latice.model.tile.COLOR;
import latice.model.tile.SHAPE;
import latice.model.tile.Tile;

public class Game {

    // Tout passe par la liste players,
    // pour que getCurrentPlayer() et nextTurn() fonctionnent dans tous les cas.
    protected ArrayList<Player> players = new ArrayList<Player>();

    protected Referee referee;

    protected int CurrentCycle;
    protected Board board;

    protected int currentPlayerIndex;

    Random rand = new Random();



    /**
     * Initializes a game for a dynamic number of players.
     *
     * @param players A list containing all the players participating in the game.
     * @param referee The referee overseeing the game rules and logic.
     */
    public Game(ArrayList<Player> players, Referee referee, Board board) {
        this.players = players;
        this.referee = referee;
        this.board = board;
    }

    public void startGame() {
        board.createGameBoard();
        createTiles();
        shareTilesDynamically();
        choseFirstPlayer();
        for (Player player : players) {
            player.getRack().addTileFromDeck(player.getDeck());
        }
    }

    /**
     * Creates a complete set of tiles for the game and shuffles them randomly.
     *
     * @return A list of newly created and shuffled tiles.
     */
    public List<Tile> createTiles() {
        final List<Tile> listTile = new ArrayList<Tile>();
        for (COLOR color : COLOR.values()) {
            for (SHAPE shape : SHAPE.values()) {
                listTile.add(new Tile(color, shape));
                listTile.add(new Tile(color, shape));
            }
        }
        return listTile;
    }

    public String showRack(Player pLayer) {
        return pLayer.getRack().toString();
    }

    /**
     * Distributes tiles dynamically among all players.
     */
    public void shareTilesDynamically() {
        if (players == null || players.isEmpty()) {
            return;
        }

        final List<Tile> allTiles = createTiles();
        int nbOfPlayers = players.size();
        int tilesPerPlayer = allTiles.size() / nbOfPlayers;
        int startIndex = 0;
        for (Player player : players) {
            List<Tile> subList = allTiles.subList(startIndex, startIndex + tilesPerPlayer);
            ArrayList<Tile> playerTiles = new ArrayList<>(subList);
            player.setDeck(new Deck(playerTiles));
            player.getDeck().shuffle();
            startIndex += tilesPerPlayer;
        }

        System.out.println("Distribution terminée : " + tilesPerPlayer + " tuiles par joueur.");
    }

    /**
     * Checks if the list of players is empty.
     *
     * @return True if there are no players in the list, false otherwise.
     */
    public boolean isPlayersEmpty() {
        int numberOfPlayers = players.size();
        if (numberOfPlayers == 0) {
            return true;
        }
        return false;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void choseFirstPlayer() {
        this.currentPlayerIndex = rand.nextInt(players.size());
    }
	
	public void nextTurn() {
        this.currentPlayerIndex = (this.currentPlayerIndex + 1) % players.size();
        
        System.out.println("C'est au tour de : " + getCurrentPlayer().getName());
    }
    public void placeTile(Player player, Tile tile, Position position) { //pas utile pour l'instant voir si on la garde
        if (referee.isValidMove(position, tile )) {
            board.placeTile(position, tile);
            player.getRack().getTiles().remove(tile);//TODO eviter les appeles en cascade
        }

    }
    public Board getBoard() {
        return board;
    }
}