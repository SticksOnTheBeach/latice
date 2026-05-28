package latice.model.action;

import latice.model.Player;
import latice.model.Referee;
import latice.model.tile.Tile;

import java.util.ArrayList;


public class ExchangeRackAction extends Action {

    public void exchangeRack(Player current, Referee referee) {
        if (!referee.isHasPlayedAction()) {
        current.getRack().exchangeAllTiles(current.getDeck());
        referee.setHasPlayedAction(true);
    } else {
        current.getRack().exchangeAllTiles(current.getDeck());
        current.setScore(current.getScore() - 2);
    }
}

    }



