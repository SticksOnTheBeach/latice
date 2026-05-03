package latice.model;

import latice.model.tile.Tile;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private ArrayList<Tile> tiles = new ArrayList<Tile>();
    public Deck(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

}
