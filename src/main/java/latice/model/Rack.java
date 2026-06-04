package latice.model;

import latice.model.tile.Tile;

import java.util.ArrayList;

public class Rack extends Container {
	int maxSize =5;
    
    // Changer la méthode Rack, afin qu'au lancement/à l'initialisation du jeu, il est préalablement 5 tiles
    public Rack() {
        super();
    }


    public void addTileFromDeck(Deck deck) {
        while (tiles.size() < maxSize && deck.isEmpty() == false) {
                tiles.add(deck.getFirstTile());
                deck.remove();
        }

    }

    /**
     * Pioche une seule et unique tuile depuis le deck.
     */
    public void drawOneTile(Deck deck) {
        // On vérifie qu'il y a de la place (moins de maxSize) et que la pioche n'est pas vide probablement inutile comme methode
        if (tiles.size() < maxSize && !deck.isEmpty()) {
            tiles.add(deck.getFirstTile());
            deck.remove();
        }
    }


    public void exchangeAllTiles(Deck deck) {
        ArrayList<Tile> temporaryRack = new ArrayList<>(tiles);
        tiles.clear();
        addTileFromDeck(deck);
        deck.getTiles().addAll(temporaryRack);
        deck.shuffle();


    }

/*
NOT USED FOR NOW, BUT COULD BE USEFUL IN THE FUTURE IF WE WANT TO IMPLEMENT A "EXCHANGE SOME TILES" FEATURE
    public void exchangeSomeTiles(Deck deck, ArrayList<Tile> tilesToKeep) {
        int targetSize = tiles.size();

        for (Tile tile : tiles) {
            if (!tilesToKeep.contains(tile)) {
                deck.getTiles().add(tile);
            }
        }
        deck.shuffle();
        tiles.clear();
        tiles.addAll(tilesToKeep);
        addTileFromDeck(deck);
    }
*/
    @Override
    public String toString() {
        return "Rack{" +
                "tiles=" + super.toString() +
                '}';
    }

}
