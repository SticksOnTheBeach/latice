package latice.model;

import latice.model.square.Square;
import latice.model.square.SquareType;
import latice.model.tile.Tile;

public class Referee {
    private Player player;
    private Board gameboard;
    protected static int points=0;



    private int roundCount = 1;
    public Referee() {
        this.roundCount = 1;
    }

    public Referee(Board gameboard) {
        this.gameboard = gameboard;
    }

    

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
                	points += 2;
                    return true;
                }
            }
        }
        return false;
    }
    
    
    
    public int calculatePoints(Position position, Tile tile) {
        int points = 0;
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int matchingSides = 0;

        for (int[] dir : directions) {
            Position neighbor = new Position(
                position.getRow() + dir[0], 
                position.getCol() + dir[1]
            );
            Square neighborSquare = gameboard.getSquare(neighbor);
            if (neighborSquare != null && neighborSquare.isOccupied()) {
                Tile neighborTile = neighborSquare.getTile();
                if (neighborTile.getColor() == tile.getColor() 
                    || neighborTile.getShape() == tile.getShape()) {
                    matchingSides++;
                }
            }
        }

        // Règles :
        // 2 côtés qui matchent (Double) = 1 point
        // 3 côtés (Trefoil) = 2 point
        // 4 côtés (Latice) = 4 points
        if (matchingSides >= 4) {
            points += 4;      // Latice
        } else if (matchingSides == 3) {
            points += 2;      // Trefoil
        } else if (matchingSides == 2) {
            points += 1;      // Double
        }
        // Case soleil = +2 points bonus
        Square placedSquare = gameboard.getSquare(position);
        if (placedSquare != null && placedSquare.getType() == SquareType.SUN) {
            points += 2;
        }

        return points;
    }
    public boolean winingCondition(Player player) {
        if (player.getRack().isEmpty() && player.getDeck().isEmpty()) {
        return true;
        }
        if (this.roundCount >= 10){
            return true;
        }
        return false;

    };
    public void getWinner(){
    }
    public void setRoundCount(int roundCount) {
        this.roundCount = roundCount;
    }

    public int getRoundCount() {
        return roundCount;
    }
}