package latice.ihm.controller;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.stage.Stage;
import latice.ihm.view.GamePane;
import latice.ihm.view.menu.WinMenu;
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
    private Stage stage;

    public GameController(RoundController roundController, TileController tileController, Referee referee, Stage stage) {
        this.roundController = roundController;
        this.tileController = tileController;
        this.referee = referee;
        this.stage = stage;
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
                showWinMenu();
                return true;
            }

            // Le passage de tour est géré via promptForActionIfPossible
            // (passage auto si l'action est refusée ou pas possible)
            promptForActionIfPossible();
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
            showWinMenu();
            return;
        }

        referee.setHasPlayedAction(false);
        mainPane.updateDisplay();
    }

    /**
     * Demande au joueur s'il veut acheter une action s'il a assez de points.
     * Sinon, passe automatiquement au joueur suivant.
     */
    private void promptForActionIfPossible() {
        Player currentPlayer = roundController.getCurrentPlayer();
        if (currentPlayer.getScore() >= 2) {
            // Le dialogue gère lui-même les callbacks via le GamePane
            // - Si Yes : ouvre le shop (le joueur appellera passTurn manuellement après)
            // - Si No : on passe au joueur suivant automatiquement
            ActionPromptDialog dialog = new ActionPromptDialog(mainPane, this);
            dialog.show();
        } else {
            // Pas assez de points : on passe directement au joueur suivant
            passTurn();
        }
    }

    /**
     * Affiche l'écran de fin de partie.
     */
    private void showWinMenu() {
        ArrayList<Player> winner = referee.getWinner(roundController.getPlayers());
        stage.setScene(new Scene(new WinMenu(stage, winner), 1000, 700));
    }

    public Player getCurrentPlayer() {
        return roundController.getCurrentPlayer();
    }

    public int getRoundCount() {
        return roundController.getRoundCount();
    }
}