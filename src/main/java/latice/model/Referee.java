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
        if (gameboard.isBoardEmpty() && position.equals(new Position(4, 4))) {
            return true;
        }
        if (!gameboard.isEmpty(position)) {
            return false;
        }
        if (!hasAdjacentTile(position)) {
            return false;
        }
        if (!hasAdjacentSameColor(position, tile) && !hasAdjacentSameShape(position, tile)) {
            return false;
        }
        return true;
    }
    
    private Position[] getAdjacentPositions(Position position) {
    	return new Position[] { new Position(position.getPositionUp(), position.getCol()),
    							new Position(position.getPositionDown(), position.getCol()),
    							new Position(position.getRow(), position.getPositionLeft()),
    							new Position(position.getRow(), position.getPositionRight())
    	};
    }

    public boolean hasAdjacentTile(Position position) {
    	for (Position neighbor : getAdjacentPositions(position)) { // on parcours les différentes position AUTOUR de la position donnée
    		Square neighborSquare = gameboard.getSquare(neighbor);
    		if (neighborSquare != null && neighborSquare.isOccupied()) { // on vérifie si dans l'une des position voisines il y'a une cases occupé 
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * Vérifie si au moins une tuile voisine est de la même couleur.
     */
    public boolean hasAdjacentSameColor(Position position, Tile tile) {
        for (Position neighbor : getAdjacentPositions(position)) {
            Square neighborSquare = gameboard.getSquare(neighbor);
            if (neighborSquare != null && neighborSquare.isOccupied()) {
                if (neighborSquare.getTile().getColor() == tile.getColor()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Vérifie si au moins une tuile voisine est de la même forme.
     */
    public boolean hasAdjacentSameShape(Position position, Tile tile) {
        for (Position neighbor : getAdjacentPositions(position)) {
            Square neighborSquare = gameboard.getSquare(neighbor);
            if (neighborSquare != null && neighborSquare.isOccupied()) {
                if (neighborSquare.getTile().getShape() == tile.getShape()) {
                    return true;
                }
            }
        }
        return false;
    }

    public int calculatePoints(Position position, Tile tile) {
        int points = 0;
        int matchingSides = 0;

        if(hasAdjacentTile(position)) {
        	if (hasAdjacentSameColor(position, tile) || hasAdjacentSameShape(position, tile)) {
        		matchingSides++;
        	}
        }

        if (matchingSides >= 4) {
            points += 4;
        } else if (matchingSides == 3) {
            points += 2;
        } else if (matchingSides == 2) {
            points += 1;
        }

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