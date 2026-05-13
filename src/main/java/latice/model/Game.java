package latice.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import latice.model.tile.COLOR;
import latice.model.tile.SHAPE;
import latice.model.tile.Tile;

public class Game {
	
    // CORRECTION : suppression de player1/player2 séparés.
    // Tout passe maintenant par la liste players, 
    // pour que getCurrentPlayer() et nextTurn() fonctionnent dans tous les cas.
    protected ArrayList<Player> players = new ArrayList<Player>();
    
    protected Referee referee;
    
    protected int CurrentCycle;
    
    protected int currentPlayerIndex;  
    
    Random rand = new Random();

    /**
     * Initializes a game for two specific players.
     * 
     * @param player1 The first player of the game.
     * @param player2 The second player of the game.
     * @param referee The referee overseeing the game rules and logic.
     */
    public Game(Player player1, Player player2, Referee referee) {
        this.players.add(player1);
        this.players.add(player2);
        this.referee = referee;
        this.currentPlayerIndex = rand.nextInt(players.size());
    }
    
    /**
     * Initializes a game for a dynamic number of players.
     * 
     * @param players A list containing all the players participating in the game.
     * @param referee The referee overseeing the game rules and logic.
     */
    public Game(ArrayList<Player> players, Referee referee) {
        this.players = players;
        this.referee = referee;
        this.currentPlayerIndex = rand.nextInt(players.size());
    }
     
    /**
     * Creates a complete set of tiles for the game and shuffles them randomly.
     * 
     * @return A list of newly created and shuffled tiles.
     */
    public List<Tile> createAndShuffleTiles() {
        final List<Tile> listTile =new ArrayList<Tile>();
        for (COLOR color : COLOR.values() ){
            for (SHAPE shape : SHAPE.values() ){
                listTile.add(new Tile(color, shape));
                listTile.add(new Tile(color, shape));
            }
        }
        Collections.shuffle(listTile);

        return listTile;
    }
    
    /**
     * Distributes the generated tiles equally between two specific players.
     * 
     * @param player1 The first player to receive half of the generated tiles.
     * @param player2 The second player to receive the remaining half of the tiles.
     */
	public void shareTilesBetweenTwoPlayers(Player player1, Player player2) {
	    final List<Tile> listTile = createAndShuffleTiles();
	    ArrayList<Tile> listTileP1 = new ArrayList<Tile>();
	    ArrayList<Tile> listTileP2 = new ArrayList<Tile>();
	    int size = listTile.size();
	    
	    for (int i = 0; i < size / 2; i++)
	        listTileP1.add(listTile.get(i));
	    for (int i = size / 2; i < size; i++)
	        listTileP2.add(listTile.get(i));

	    player1.setDeck(new Deck(listTileP1));
	    player2.setDeck(new Deck(listTileP2));
	}
	
	/**
     * Distributes tiles dynamically among all players.
     */
    public void shareTilesDynamically() {
    	if (players == null || players.isEmpty()) {
            return; 
        }

        final List<Tile> allTiles = createAndShuffleTiles();
        int nbOfPlayers = players.size();
        int tilesPerPlayer = allTiles.size() / nbOfPlayers;
        int startIndex = 0; 
        for (Player player : players) {
            List<Tile> subList = allTiles.subList(startIndex, startIndex + tilesPerPlayer);
            ArrayList<Tile> playerTiles = new ArrayList<>(subList);
            player.setDeck(new Deck(playerTiles));
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
	
	public void nextTurn() {
        this.currentPlayerIndex = (this.currentPlayerIndex + 1) % players.size();
        
        System.out.println("C'est au tour de : " + getCurrentPlayer().getName());
    }
}