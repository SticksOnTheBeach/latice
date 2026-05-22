package latice.model;

import latice.model.square.Square;
import latice.model.square.SquareType;
import latice.model.tile.Tile;

import java.util.ArrayList;

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

    /**
     * Compte le nombre de voisins qui matchent (même couleur OU même forme).
     */
    private int countMatchingNeighbors(Position position, Tile tile) {
        int count = 0;
        for (Position neighbor : getAdjacentPositions(position)) {
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
            points += 3;
        } else if (matchingSides == 2) {
            points += 2;
        } else if (matchingSides == 1) {
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
        if (this.roundCount > 10){
            return true;
        }
        return false;
    }
    public Player getWinner(ArrayList<Player> players) {
        int minTiles = players.get(0).getRack().size() + players.get(0).getDeck().size();
        for (Player player : players) {
            int remaining = player.getRack().size() + player.getDeck().size();
            if (remaining < minTiles) {
                minTiles = remaining;
            }
        }
        ArrayList<Player> potentialWinner = new ArrayList<>();
        for (Player player : players) {
            if (player.getRack().size() + player.getDeck().size() == minTiles) {
                potentialWinner.add(player);
            }
        }
        // eviter les egalités au maximum avec celui qui a le plus de points
        Player winner = potentialWinner.get(0);
        for (Player player : potentialWinner) {
            if (player.getScore() > winner.getScore()) {
                winner = player;
            }
        }

        return winner;//TODO gerer les cas rare ou plusieurs joueurs on memes tuilles et points
    }
    public void setRoundCount(int roundCount) {
        this.roundCount = roundCount;
    }

    public int getRoundCount() {
        return roundCount;
    }
}