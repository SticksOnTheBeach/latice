package latice.model;

import latice.model.tile.Tile;

import java.util.ArrayList;

public class Deck implements TileContainer {


    private ArrayList<Tile> tiles = new ArrayList<Tile>();

    public Deck(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }


    @Override
    public void remove() {
        if (tiles.size() > 0) {
            tiles.remove(0);
        }
        //TODO gerer la liste vide
    }

    @Override
    public boolean isempty() {
        if (tiles.size() == 0) {
            return true;
        }
        return false;
    }
    @Override
    public String toString() {
        return "Deck{" +
                "tiles=" + tiles.toString() +
                '}';
    }
}
