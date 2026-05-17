package latice.model;

import latice.model.tile.Tile;

import java.util.ArrayList;

public class Rack implements TileContainer{
	int maxSize =5;
    private ArrayList<Tile> tiles = new ArrayList<Tile>();
    
    
    // Changer la méthode Rack, afin qu'au lancement/à l'initialisation du jeu, il est préalablement 5 tiles
    public Rack() {
    	
    }


    public void addTileFromDeck(Deck deck) {
        while (tiles.size() < maxSize && deck.isEmpty() == false) {
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

    public int size(){
        return tiles.size();
    }

    @Override
    public boolean isEmpty() {
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

    public ArrayList<Tile> getTiles() {
        return tiles;
    }
    
    /**
     * Pioche une seule et unique tuile depuis le deck.
     */
    public void drawOneTile(Deck deck) {
        // On vérifie qu'il y a de la place (moins de maxSize) et que la pioche n'est pas vide
        if (tiles.size() < maxSize && !deck.isEmpty()) {
            tiles.add(deck.getTiles().get(0));
            deck.remove();
        }
    }
}
