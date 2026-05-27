package latice.ihm.controller;

import latice.ihm.view.GamePane;
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


	public GameController(RoundController roundController, TileController tileController, Referee referee) {
		this.roundController = roundController;
		this.tileController = tileController;
        this.refree = referee;	}
	
	public void setView(GamePane mainPane) {
		this.mainPane = mainPane;
	}
	
	/**
     * Gère le placement d'une tuile 
     */
    public boolean playTile(Tile tile, Position position) {
        Player currentPlayer = roundController.getCurrentPlayer();
        if (!currentPlayer.getRack().getTiles().contains(tile)|| refree.isHasPlayedAction()) {
            return false;
        }

        boolean success = tileController.placeTile(currentPlayer, tile, position);

        if (success) {
            // On ne rafraîchit pas le rack visuellement
            // Comme ça, le TileView se déplace sur le plateau et laisse un trou dans le rack
            if (tileController.getReferee().winingConditionEmpty(currentPlayer)) {
                ArrayList<Player> winner = tileController.getReferee().getWinner(roundController.getPlayers());
                mainPane.showWinner(winner);
                return true;
            }

            refree.setHasPlayedAction(true);
            mainPane.updateDisplay();

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
        Player currentPlayer = roundController.getCurrentPlayer();
        int currentIndex = roundController.getCurrentPlayerIndex();
        currentPlayer.getRack().addTileFromDeck(currentPlayer.getDeck());
        mainPane.rafraichirRackJoueur(currentIndex);

        roundController.nextPlayerTurn();
        if (tileController.getReferee().winingConditionCycles()) {
            ArrayList<Player> winner = tileController.getReferee().getWinner(roundController.getPlayers());
            mainPane.showWinner(winner);
            return;
        }

        mainPane.updateDisplay();
        refree.setHasPlayedAction(false);

    }
    
    public Player getCurrentPlayer() {
        return roundController.getCurrentPlayer();
    }
    
    public int getRoundCount() {
        return roundController.getRoundCount();
    }

}