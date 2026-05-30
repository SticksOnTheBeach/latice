package latice.ihm.controller;

import java.util.ArrayList;

import latice.ihm.view.GamePane;
import latice.ihm.view.model.ActionPromptDialog;
import latice.model.Player;
import latice.model.Position;
import latice.model.Referee;
import latice.model.tile.Tile;

public class GameController {
    private GamePane mainPane;
    private RoundController roundController;
    private TileController tileController;
    private Referee referee;

    public GameController(RoundController roundController, TileController tileController, Referee referee) {
        this.roundController = roundController;
        this.tileController = tileController;
        this.referee = referee;
    }

    public void setView(GamePane mainPane) {
        this.mainPane = mainPane;
    }

    /**
     * Gère le placement d'une tuile.
     */
    public boolean playTile(Tile tile, Position position) {
        Player currentPlayer = roundController.getCurrentPlayer();
        if (!currentPlayer.getRack().getTiles().contains(tile) || referee.isHasPlayedAction()) {
            return false;
        }

        boolean success = tileController.placeTile(currentPlayer, tile, position);

        if (success) {
            referee.setHasPlayedAction(true);

            // Vérifie d'abord la condition de victoire (rack + deck vides)
            if (referee.winingConditionEmpty(currentPlayer)) {
                ArrayList<Player> winner = referee.getWinner(roundController.getPlayers());
                mainPane.showWinner(winner);
                return true;
            }

            // Sinon on passe automatiquement au joueur suivant
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
     * Méthode pour passer son tour (Bouton "Fin de tour").
     * Le rack du joueur précédent est déjà rechargé dans TileController.placeTile().
     */
    public void passTurn() {
        roundController.nextPlayerTurn();

        // Vérifie la condition de victoire par nombre de cycles
        if (referee.winingConditionCycles()) {
            ArrayList<Player> winner = referee.getWinner(roundController.getPlayers());
            mainPane.showWinner(winner);
            return;
        }

        referee.setHasPlayedAction(false);
        mainPane.updateDisplay();
        promptForActionIfPossible();
    }

    private void promptForActionIfPossible() {
        Player currentPlayer = roundController.getCurrentPlayer();
        if (currentPlayer.getScore() >= 2) {
            ActionPromptDialog dialog = new ActionPromptDialog(mainPane);
            dialog.show();
        }
    }

    public Player getCurrentPlayer() {
        return roundController.getCurrentPlayer();
    }

    public int getRoundCount() {
        return roundController.getRoundCount();
    }
}