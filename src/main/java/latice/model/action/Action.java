package latice.model.action;

import latice.model.Player;

public abstract class Action {
    private Player player;

    public boolean hasEnoughPoints(Player current) {
        if (current.getScore() < 2) {
            return false;
        }
        return true;
    }
    public Player getPlayer() {
        return player;
    }

}
