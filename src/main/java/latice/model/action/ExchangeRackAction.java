package latice.model.action;

import latice.model.Player;
import latice.model.Referee;
import latice.model.tile.Tile;

import java.util.ArrayList;

public class ExchangeRackAction extends Action {

    public boolean changeRack(Player current, Referee referee, ArrayList<Tile> tilesToKeep) {
        // On ne peut pas garder toutes ses tuiles
        if (tilesToKeep.size() >= current.getRack().size()) {
            return false;
        }

        boolean isFree = !referee.isHasPlayedAction();
        if (current.getScore() >= 2 || isFree) {
            if (isFree) {
                referee.setHasPlayedAction(true);
            } else {
                current.setScore(current.getScore() - 2);
            }
            current.getRack().exchangeAllTiles(current.getDeck());
            return true;
        }
        return false;
    }
    }



