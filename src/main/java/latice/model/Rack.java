package latice.model;

import latice.model.tile.Tile;

import java.util.ArrayList;

public class Rack implements TileContainer{
int maxSize =5;
    private ArrayList<Tile> tiles = new ArrayList<Tile>();

    public Rack() {

    }


    public void addTileFromDeck(Deck deck) {
        while (tiles.size() < maxSize && deck.isempty() == false) {
                tiles.add(deck.getTiles().get(0));
                deck.remove();
        }

    }

    @Override
    public void remove() {
        if (tiles.size() > 0) {
            tiles.remove(0);
        }
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
        return "Rack{" +
                "tiles=" + tiles +
                '}';
    }

}
