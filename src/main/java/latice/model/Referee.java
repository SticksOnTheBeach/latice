package latice.model;

public class Referee {
    private Player player;
    private Board gameboard;

    public Referee(){};

    public Referee(Player player, Board gameboard) {
        this.player = player;
        this.gameboard = gameboard;
    }
    
}
