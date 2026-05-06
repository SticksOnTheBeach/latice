package latice.model;

import latice.model.tile.Tile;

import java.util.ArrayList;

public class Deck implements TileContainer {

    private ArrayList<Tile> tiles = new ArrayList<Tile>();

    /**
     * Initializes a deck with a specific list of tiles.
     * 
     * @param tiles The initial list of tiles for this deck.
     */
    public Deck(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    /**
     * Retrieves the list of tiles in the deck.
     * 
     * @return The current list of tiles.
     */
    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    /**
     * Removes the top tile from the deck.
     */
    @Override
    public void remove() {
        if (!tiles.isEmpty()) {
            tiles.remove(0);
        }
        //TODO gerer la liste vide
    }
    
    /**
     * Gets the number of tiles currently in the deck.
     * 
     * @return The exact number of tiles remaining.
     */
    public int size() {
        return tiles.size();
    }

    /**
     * Checks if the deck is empty.
     * 
     * @return True if there are no tiles left, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return tiles.isEmpty(); 
    }
    
    @Override
    public String toString() {
        return "Deck{" + "tiles=" + tiles.toString() +'}';
    }
}