package latice.model;

import latice.model.tile.Tile;

import java.util.ArrayList;
import java.util.Collections;

public class Deck extends Container implements TileContainer {


    /**
     * Initializes a deck with a specific list of tiles.
     * 
     * @param tiles The initial list of tiles for this deck.
     */
    public Deck(ArrayList<Tile> tiles) {
        super(tiles);
    }

    @Override
    public String toString() {
        return "Deck{" + "tiles=" + super.toString() +'}';
    }
}