package latice.model.action;

import latice.model.Player;
import latice.model.Referee;

public class BuyExtraMoveAction extends Action {
/* TODO : fix le fait que l'on puisse jouer deux fois 
 * pour l'instant quand on appuie ça nous qu'on a jamais fais de tour, donc faudras fix ça
 */
    public void buyExtraMove(Player current, Referee referee) {
        if (current.getScore() >= 2 && referee.isHasPlayedAction()) {
            current.setScore(current.getScore() - 2);
            referee.setHasPlayedAction(false);
        }
    }
}