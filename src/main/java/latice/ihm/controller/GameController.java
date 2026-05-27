package latice.ihm.controller;

import latice.ihm.view.GamePane;
import latice.ihm.view.model.ActionPromptDialog;
import latice.model.Player;
import latice.model.Position;
import latice.model.tile.Tile;

import java.util.ArrayList;

public class GameController {
    private GamePane mainPane;
    private RoundController roundController;
    private TileController tileController;


    private boolean hasPlayedAction = false;
    private boolean hasEverPlayed = false;
    public GameController(RoundController roundController, TileController tileController) {
        this.roundController = roundController;
        this.tileController = tileController;
    }

    public void setView(GamePane mainPane) {
        this.mainPane = mainPane;
    }

    /**
     * Gère le placement d'une tuile 
     */
    public boolean playTile(Tile tile, Position position) {
        Player currentPlayer = roundController.getCurrentPlayer();
        if (!currentPlayer.getRack().getTiles().contains(tile)|| hasPlayedAction) {
            return false;
        }

        boolean success = tileController.placeTile(currentPlayer, tile, position);

        if (success) {
            hasEverPlayed = true;

            // Vérifie d'abord la condition de victoire (rack + deck vides)
            if (tileController.getReferee().winingConditionEmpty(currentPlayer)) {
                ArrayList<Player> winner = tileController.getReferee().getWinner(roundController.getPlayers());
                mainPane.showWinner(winner);
                return true;
            }

            // Sinon, on passe automatiquement au joueur suivant
            passTurn();
            return true;
        }
        return false;
    }

    /**
     * Pioche UNE SEULE tuile et met à jour l'interface immédiatement.
     */
    public void drawTile() {
        Player currentPlayer = roundController.getCurrentPlayer();
        int currentIndex = roundController.getCurrentPlayerIndex();

        currentPlayer.getRack().drawOneTile(currentPlayer.getDeck());
        mainPane.rafraichirRackJoueur(currentIndex);
    }

    /**
     * Méthode pour passer son tour (Bouton "Fin de tour")
     */
    public void passTurn() {
        roundController.nextPlayerTurn();

        // Vérifie la condition de victoire par nombre de cycles
        if (tileController.getReferee().winingConditionCycles()) {
            ArrayList<Player> winner = tileController.getReferee().getWinner(roundController.getPlayers());
            mainPane.showWinner(winner);
            return;
        }

        hasPlayedAction = false;
        mainPane.updateDisplay();
        promptForActionIfPossible();
    }

    private void promptForActionIfPossible() {
        Player currentPlayer = roundController.getCurrentPlayer();
        if (currentPlayer.getScore() >= 2) {
            boolean accept = new ActionPromptDialog().askBuyAction();
            if (accept) {
                mainPane.openActionShop();
            }
        }
    }

    public Player getCurrentPlayer() {
        return roundController.getCurrentPlayer();
    }

    public int getRoundCount() {
        return roundController.getRoundCount();
    }

    public void setHasPlayedAction(boolean hasPlayedAction) {
        this.hasPlayedAction = hasPlayedAction;
    }

    public boolean isHasPlayedAction() {
        return hasPlayedAction;
    }

    public boolean isHasEverPlayed() {
        return hasEverPlayed;
    }

}