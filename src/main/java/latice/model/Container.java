package latice.model;

import latice.model.tile.Tile;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Container {
    protected ArrayList<Tile> tiles;
    public Container(ArrayList<Tile> tiles) {

        this.tiles = tiles;
    }
    public Container() {
        this.tiles = new ArrayList<Tile>();
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
    public void remove() {
        if (!tiles.isEmpty()) {
            tiles.remove(0);
        }
    }
    public  void shuffle() {
        Collections.shuffle(tiles);
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
    public boolean isEmpty() {
        return tiles.isEmpty();
    }

    @Override
    public String toString() {
        return
                "tiles=" + tiles.toString()
                ;
    }
}
