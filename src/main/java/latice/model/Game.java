package latice.model;

import latice.model.tile.COLOR;
import latice.model.tile.SHAPE;
import latice.model.tile.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
	
	protected Player player1;
	protected Player player2;
	
    protected ArrayList<Player> players = new ArrayList<Player>();
    
    protected Referee referee;
    
    protected int CurrentCycle;
    
    protected int currentPlayer;  
    
    public Game(Player player1, Player player2, Referee referee) {
    	// Method for playing with 2 players
        this.player1 = player1;
        this.player2 = player2;
        this.referee = referee;

    }
    
    
    // Method to make a game with more than 2 players
    public Game(ArrayList<Player> players, Referee referee) {
        this.players = players;
        this.referee = referee;
    }
     
    public List<Tile> createAndShuffleTiles() {
        final List<Tile> listTile =new ArrayList<Tile>();
        for (COLOR color : COLOR.values() ){
            for (SHAPE shape : SHAPE.values() ){
                listTile.add(new Tile(color, shape));
                listTile.add(new Tile(color, shape));
            }
        }
        Collections.shuffle(listTile);

        return  listTile;
    }
    
	public void shareTilesBetweenTwoPlayers(Player player1, Player player2 ) {
	    final List<Tile> listTile = createAndShuffleTiles();
	    ArrayList<Tile> listTileP1 =new ArrayList<Tile>();
	    ArrayList<Tile> listTileP2 =new ArrayList<Tile>();
	    int size = listTile.size();
	    
	    if (isPlayersEmpty()) {
	    	return;
	    }
	    
	    for (int i = 0; i < size / 2; i++)
	        listTileP1.add(listTile.get(i));
	    for (int i = size / 2; i < size; i++)
	        listTileP2.add(listTile.get(i));
	    this.player1.setDeck(new Deck(listTileP1));
	    this.player2.setDeck(new Deck(listTileP2));
	
	}
	
	public void shareTilesDynamically() {}
	
	public boolean isPlayersEmpty() {
		int numberOfPlayers = players.size();
		if (numberOfPlayers == 0) {
			return true;
		}
		return false;
	}
}
