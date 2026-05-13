package latice.ihm.controller;

import latice.model.Board;
import latice.model.Player;
import latice.model.Position;
import latice.model.square.Square;
import latice.model.tile.Tile;

public class TileController {

    private Board board;

    public TileController(Board board) {
        this.board = board;
    }

    /**
     * Vérifie si une tuile peut être placée sur une case.
     *
     * @param position La position sur laquelle on veut poser la tuile.
     * @return True si la case est libre, false sinon.
     */
    public boolean canPlaceTile(Position position) {
        Square square = board.getSquare(position);
        if (square == null) {
        	return false;
        }
        return !square.isOccupied();
    }

    /**
     * Place une tuile sur le board et la retire du rack du joueur.
     *
     * @param player   Le joueur qui pose la tuile.
     * @param tile     La tuile à poser.
     * @param position La position où poser la tuile.
     * @return True si le placement a réussi, false sinon.
     */
    public boolean placeTile(Player player, Tile tile, Position position) {
        if (!canPlaceTile(position)) return false;

        // Place la tuile sur le board
        board.getSquare(position).setTile(tile);
        player.getRack().getTiles().remove(tile);
        player.getRack().addTileFromDeck(player.getDeck());

        return true;
    }
}