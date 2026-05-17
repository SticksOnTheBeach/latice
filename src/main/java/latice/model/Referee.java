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

    /* TODO : méthodes qui se charge de savoir si le placement de la tuile est correcte
     * si correct, on vérifie si y'a des tuiles aux alentours "valides"
     * si valides : alors on donne le nombres de points correspondant
     * puis on fait un getteur des points
     * implémentation dans les paramètres du constructeur : roundController afin de gérer
     * qui est entrain de jouer actuellement etc..
     */

    public boolean isValidMove(Position position, Tile tile) {
        // Vérification de la validation de la tuile à placer en premier
        if (gameboard.isBoardEmpty() && position.equals(new Position(4, 4))) {
            return true;
        }

        if (!gameboard.isEmpty(position)) { // Vérification que la case est libre
            return false;
        }
        if (!hasAdjacentTile(position, tile)) {
            return false;
        }
        return true;
    }

    public boolean hasAdjacentTile(Position position, Tile tile) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        boolean hasAdjacentTile = false; // Vérifier qu'une tuile adjacente existe
        for (int[] dir : directions) { // Parcourir les 4 directions
            Position neighbor = new Position(position.getRow() + dir[0], position.getCol() + dir[1]); // Récupérer les positions adjacentes
            Square neighborSquare = gameboard.getSquare(neighbor); // Récupérer les cases adjacentes
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