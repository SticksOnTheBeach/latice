package latice.model;

import latice.model.tile.Tile;

import java.util.ArrayList;

public class Rack extends Container implements TileContainer{
	int maxSize =5;
    
    // Changer la méthode Rack, afin qu'au lancement/à l'initialisation du jeu, il est préalablement 5 tiles
    public Rack() {
        super();
    }


    public void addTileFromDeck(Deck deck) {
        while (tiles.size() < maxSize && deck.isEmpty() == false) {
                tiles.add(deck.getTiles().get(0));
                deck.remove();
        }

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
    
    
    public void exchangeAllTiles(Deck deck) {
        // on remet les tuiles du rack dans le deck
        deck.getTiles().addAll(tiles);
        deck.shuffle();

        // on clear le rack
        tiles.clear();

        // uis on pioche depuis le deck qu'on a
        addTileFromDeck(deck);
    }
    @Override
    public String toString() {
        return "Rack{" +
                "tiles=" + super.toString() +
                '}';
    }

}
