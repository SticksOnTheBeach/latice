package latice.model;

import latice.model.tile.Tile;

import java.util.ArrayList;

public class Rack implements TileContainer{
    private ArrayList<Tile> tiles = new ArrayList<Tile>();

    public Rack(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }


    @Override
    public void addTile() {

    }

    @Override
    public void remove() {

    }

    @Override
    public void isempty() {

    }
}
