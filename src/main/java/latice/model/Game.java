package latice.model;

import latice.model.tile.COLOR;
import latice.model.tile.SHAPE;
import latice.model.tile.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {


    ArrayList<Player> players = new ArrayList<Player>();
    Referee referee;
    int CurrentCycle;
    int currentPlayer;
    ArrayList<Player> playerList;
    Player player1;
    Player player2;

    public Game(Player player1, Player player2, Referee referee) {
        this.player1 = player1;
        this.player2 = player2;
        this.referee = referee;

    }
    public Game(ArrayList<Player> players, Referee referee) {
        this.players = players;
        this.referee = referee;

    }
    public List<Tile> createAndShuffleTiles() {
        final List<Tile> listTile =new ArrayList<Tile>();
        for (COLOR color : COLOR.values() ){
            for (SHAPE shape : SHAPE.values() ){
                listTile.add(new Tile(color, shape));
                listTile.add(new Tile(color, shape));
            }
        }
        Collections.shuffle(listTile);

        return  listTile;
        }
public void shareTilesBetweenTwoPlayers(Player player1, Player player2 ) {
    final List<Tile> listTile = createAndShuffleTiles();
    ArrayList<Tile> listTileP1 =new ArrayList<Tile>();
    ArrayList<Tile> listTileP2 =new ArrayList<Tile>();
    int size = listTile.size();
    for (int i = 0; i < size / 2; i++)
        listTileP1.add(listTile.get(i));
    for (int i = size / 2; i < size; i++)
        listTileP2.add(listTile.get(i));
    this.player1.setDeck(new Deck(listTileP1));
    this.player2.setDeck(new Deck(listTileP2));

}
}
