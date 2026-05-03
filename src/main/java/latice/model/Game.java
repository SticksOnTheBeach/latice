package latice.model;

import java.util.ArrayList;

public class Game {


    ArrayList<Player> players = new ArrayList<Player>();
    Referee referee;
    int CurrentCycle;
    int currentPlayer;
    public Game(ArrayList<Player> players, Referee referee, int currentCycle, int currentPlayer) {
        this.players = players;
        this.referee = referee;
        CurrentCycle = currentCycle;
        this.currentPlayer = currentPlayer;
    }
}
