package latice.model;

public class Referee {
    private Player player;
    private Board gameboard;

    public Referee(){};

    public Referee(Player player, Board gameboard) {
        this.player = player;
        this.gameboard = gameboard;
    }
    public boolean isValidMove(Position position){
        if(gameboard.isBoardEmpty() && position.equals(new Position(4, 4))){
            return true;
        }
        return false;
    }
}
