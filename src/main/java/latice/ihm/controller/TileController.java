package latice.ihm.controller;

import latice.model.*;
import latice.model.square.Square;
import latice.model.tile.Tile;

public class TileController {

    private Board board;
    private Referee referee;

    public TileController(Board board,  Referee referee) {

        this.board = board;
        this.referee = referee;
    }

    /**
     * Vérifie si une tuile peut être placée sur une case.
     *
     * @param position La position sur laquelle on veut poser la tuile.
     * @return True si la case est libre, false sinon.
     */
    public boolean canPlaceTile(Position position, Tile tile) {
        Square square = board.getSquare(position);
        if (square == null || square.isOccupied()) {
            return false;
        }
        return referee.isValidMove(position, tile);
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
        if (!canPlaceTile(position, tile)) return false;

        board.getSquare(position).setTile(tile);
        player.getRack().getTiles().remove(tile);
        player.getRack().addTileFromDeck(player.getDeck());

        // Calcul et attribution des points
        int pointsGagnes = referee.calculatePoints(position, tile);
        System.out.println("Points gagnés : " + pointsGagnes + " | Score total : " + (player.getScore() + pointsGagnes));
        player.setScore(player.getScore() + pointsGagnes);


        return true;
    }
    
    
    
}