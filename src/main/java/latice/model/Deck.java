package latice.model;

import latice.model.tile.Tile;

import java.util.ArrayList;
import java.util.Collections;

public class Deck extends Container {


    /**
     * Initializes a deck with a specific list of tiles.
     * 
     * @param tiles The initial list of tiles for this deck.
     */
    public Deck(ArrayList<Tile> tiles) {
        super(tiles);
    }

    public Tile getFirstTile() {
            return tiles.get(0); //TODO: check if empty before

    }

    @Override
    public String toString() {
        return "Deck{" + "tiles=" + super.toString() +'}';
    }
}