package latice.model;

import java.util.ArrayList;
import java.util.List;
import latice.model.tile.COLOR;
import latice.model.tile.SHAPE;
import latice.model.tile.Tile;

public class Game {

    // Tout passe par la liste players,
    // pour que getCurrentPlayer() et nextTurn() fonctionnent dans tous les cas.
    private ArrayList<Player> players = new ArrayList<Player>();
    private Board board;



    /**
     * Initializes a game for a dynamic number of players.
     *
     * @param players A list containing all the players participating in the game.
     */
    public Game(ArrayList<Player> players, Board board) {
        this.players = players;

        this.board = board;
    }

    public void startGame() {
        board.createGameBoard();
        shareTilesDynamically();
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
    public Board getBoard() {
        return this.board;
    }

}