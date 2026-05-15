package latice.model;

import latice.model.square.Square;
import latice.model.tile.Tile;

public class Referee {
    private Player player;
    private Board gameboard;

    public Referee(Board gameboard) {
        this.gameboard = gameboard;
    }

    ;

    public Referee(Player player, Board gameboard) {
        this.player = player;
        this.gameboard = gameboard;
    }

    public boolean isValidMove(Position position, Tile tile) { //verification de la validation de la tuile a placer en premier
        if (gameboard.isBoardEmpty() && position.equals(new Position(4, 4))) {
            return true;
        }


        if (!gameboard.isEmpty(position)) { // verification de la case est libre en 2 eme car moins lourd
            return false;
        }
        if (!hasAdjacentTile(position, tile)) {
            return false;
        }
        return true;

    }

    public boolean hasAdjacentTile(Position position, Tile tile) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        boolean hasAdjacentTile = false; // verifier qu'ubne tuile adjacente existe
        for (int[] dir : directions) {// parcourir les 4 directions
            Position neighbor = new Position(position.getRow() + dir[0], position.getCol() + dir[1]);//recuperer les positions adjacentes
            Square neighborSquare = gameboard.getSquare(neighbor);// recuperer les cases adjacentes
            if (neighborSquare != null && neighborSquare.isOccupied()) {
                hasAdjacentTile = true;
                Tile neighborTile = neighborSquare.getTile();
                boolean sameColor = neighborTile.getColor() == tile.getColor();
                boolean sameShape = neighborTile.getShape() == tile.getShape();
                if (sameColor || sameShape) {
                    return true;
                }
            }
        }
        return false;
    }
}
