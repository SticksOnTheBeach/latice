package latice.model.action;

import latice.model.Player;
import latice.model.Referee;

public class BuyExtraMoveAction extends Action {

    public void buyExtraMove(Player current, Referee referee) {
        if (current.getScore() >= 2 && referee.isHasPlayedAction()) {
            current.setScore(current.getScore() - 2);
            referee.setHasPlayedAction(false);
        }
    }
}