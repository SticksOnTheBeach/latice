package latice.model;

import latice.model.square.Square;
import latice.model.square.SquareType;
import latice.model.tile.Tile;

import java.util.ArrayList;

public class Referee {
    private Board gameboard;
    private boolean havePlayed = false;
    private int roundCount = 1;
    private int roundLimit;
    public Referee(Board gameboard, int roundLimit){
        this.gameboard = gameboard;
        this.roundLimit = roundLimit;
    }
    public boolean isValidMove(Position position, Tile tile) {
        if (gameboard.isBoardEmpty() && position.equals(new Position(4, 4))) {
            return true;
        }
        if (!gameboard.isEmpty(position)) {
            return false;
        }
        return hasValidNeighbors(position, tile);
    }


    /**
     * Compte le nombre de voisins qui matchent (même couleur OU même forme).
     */

    public boolean hasValidNeighbors(Position position, Tile tile) {
        boolean hasNeighbor = false;
        for (Position neighbor : position.getAdjacentPositions()) {
            Square neighborSquare = gameboard.getSquare(neighbor);
            if (neighborSquare != null && neighborSquare.isOccupied()) {
                hasNeighbor = true;
                Tile neighborTile = neighborSquare.getTile();
                if (neighborTile.getColor() != tile.getColor()
                        && neighborTile.getShape() != tile.getShape()) {
                    return false;
                }
            }
        }
        return hasNeighbor;
    }
    public int countMatchingNeighbors(Position position, Tile tile) {
        int count = 0;
        for (Position neighbor : position.getAdjacentPositions()) {
            Square neighborSquare = gameboard.getSquare(neighbor);
            if (neighborSquare != null && neighborSquare.isOccupied()) {
                Tile neighborTile = neighborSquare.getTile();
                if (neighborTile.getColor() == tile.getColor() || neighborTile.getShape() == tile.getShape()) {
                    count++;
                }
            }
        }
        return count;
    }

    public int calculatePoints(Position position, Tile tile) {
        int points = 0;
        int matchingSides = countMatchingNeighbors(position, tile);

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
    
    public boolean winingConditionEmpty(Player player) {
        if (player.getRack().isEmpty() && player.getDeck().isEmpty()) {
            return true;
        }
        return false;
    };
    
    public boolean winingConditionCycles(){
        if (this.roundCount > roundLimit){
            return true;
        }
        return false;
    }
    
    public ArrayList<Player> getWinner(ArrayList<Player> players) {
        int minTiles = players.get(0).getRack().size() + players.get(0).getDeck().size();
        for (Player player : players) {
            int remaining = player.getRack().size() + player.getDeck().size();
            if (remaining < minTiles) {
                minTiles = remaining;
            }
        }
        ArrayList<Player> winner = new ArrayList<>();
        for (Player player : players) {
            if (player.getRack().size() + player.getDeck().size() == minTiles) {
                winner.add(player);
            }
        }


        return winner;
    }

    public void setRoundCount(int roundCount) {
        this.roundCount = roundCount;
    }

    public int getRoundCount() {
        return roundCount;
    }
    public int getRoundLimit() {
        return roundLimit;
    }
    public void setRoundLimit(int roundLimit) {
        this.roundLimit = roundLimit;
    }
    public boolean isHasPlayedAction() { 
    	return havePlayed; 
    }
    
    
    public void setHasPlayedAction(boolean played) { 
    	havePlayed = played; 
    }

}
