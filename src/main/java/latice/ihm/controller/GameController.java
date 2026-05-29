package latice.ihm.controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import latice.ihm.view.GamePane;
import latice.ihm.view.menu.WinMenu;
import latice.ihm.view.model.ActionPromptDialog;
import latice.model.Player;
import latice.model.Position;
import latice.model.Rack;
import latice.model.Referee;
import latice.model.tile.Tile;

import java.util.ArrayList;

public class GameController {
    private GamePane mainPane;
    private RoundController roundController;
    private TileController tileController;
    private Referee refree;
    Stage stage;



    // hasEverPlayed reste local au controller (sert pour l'échange gratuit au tout 1er coup)

    public GameController(RoundController roundController, TileController tileController, Referee referee, Stage stage ) {
        this.roundController = roundController;
        this.tileController = tileController;
        this.refree = referee;
        this.stage = stage;
    }

    public void setView(GamePane mainPane) {
        this.mainPane = mainPane;
    }

    /**
     * Gère le placement d'une tuile 
     */
    public boolean playTile(Tile tile, Position position) {
        Player currentPlayer = roundController.getCurrentPlayer();
        // hasPlayedAction vient maintenant du Referee (refacto)
        if (!currentPlayer.getRack().getTiles().contains(tile) || refree.isHasPlayedAction()) {
            return false;
        }

        boolean success = tileController.placeTile(currentPlayer, tile, position);

        if (success) {
            refree.setHasPlayedAction(true);

            // Vérifie d'abord la condition de victoire (rack + deck vides)
            if (tileController.getReferee().winingConditionEmpty(currentPlayer)) {
                ArrayList<Player> winner = tileController.getReferee().getWinner(roundController.getPlayers());
                mainPane.showWinner(winner);
                return true;
            }
            //Refacto s'occupe du pass turn via cette methode car plus agreable et fluide
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
     * Méthode pour passer son tour (Bouton "Fin de tour")
     */
    public void passTurn() {
        roundController.nextPlayerTurn();

        Player currentPlayer = roundController.getCurrentPlayer();
        int currentIndex = roundController.getCurrentPlayerIndex();
        currentPlayer.getRack().addTileFromDeck(currentPlayer.getDeck());
        mainPane.rafraichirRackJoueur(currentIndex);

        // Vérifie la condition de victoire par nombre de cycles
        if (tileController.getReferee().winingConditionCycles()) {
            ArrayList<Player> winner = tileController.getReferee().getWinner(roundController.getPlayers());
            stage.setScene(new Scene(new WinMenu(stage, winner), 1000, 700));        }


        // hasPlayedAction est réinitialisé dans le Referee (refacto)
        refree.setHasPlayedAction(false);
        mainPane.updateDisplay();
        // TODO a discuter si on garde ou pas ici
        //promptForActionIfPossible();
    }
    private void promptForActionIfPossible() {
        Player currentPlayer = roundController.getCurrentPlayer();
        if (currentPlayer.getScore() >= 2) {
            boolean accept = new ActionPromptDialog().askBuyAction();
            if (accept) {

                mainPane.openActionShop();
                mainPane.updateDisplay();

            } else {
                passTurn();
            }
        } else {
            passTurn();
        }
    }

    public Player getCurrentPlayer() {
        return roundController.getCurrentPlayer();
    }

    public int getRoundCount() {
        return roundController.getRoundCount();
    }
}
