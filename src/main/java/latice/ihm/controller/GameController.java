package latice.ihm.controller;

import latice.ihm.view.MainPane;
import latice.model.Player;
import latice.model.Position;
import latice.model.tile.Tile;

public class GameController {
	private MainPane mainPane;
	private RoundController roundController;
	private TileController tileController;
	
	public GameController(RoundController roundController, TileController tileController) {
		this.roundController = roundController;
		this.tileController = tileController;
	}
	
	public void setView(MainPane mainPane) {
		this.mainPane = mainPane;
	}
	
	/**
     * Gère le placement d'une tuile 
     */
    public boolean playTile(Tile tile, Position position) {
        Player currentPlayer = roundController.getCurrentPlayer();
        // inutile mais on peut le laisser quand même, puisque j'ai bloqué les rack en les mettant invisible pour le curseur
        if (!currentPlayer.getRack().getTiles().contains(tile)) {
            return false;
        }

        boolean success = tileController.placeTile(currentPlayer, tile, position);

        if (success) {
            // On ne rafraîchit pas le rack visuellement
            // Comme ça, le TileView se déplace sur le plateau et laisse un trou dans le rack
        	roundController.nextPlayerTurn();
            mainPane.updateDisplay();
            return true;
        }
        return false;
    }
    
    /**
     * Pioche une tuile et met à jour l'interface immédiatement.
     */
    public void drawTile() {
        Player currentPlayer = roundController.getCurrentPlayer();
        int currentIndex = roundController.getCurrentPlayerIndex();
        
        currentPlayer.getRack().addTileFromDeck(currentPlayer.getDeck());
        mainPane.rafraichirRackJoueur(currentIndex);
    }
    /**
     * Méthode pour passer son tour (Bouton "Fin de tour")
     */
    public void passTurn() {
        Player currentPlayer = roundController.getCurrentPlayer();
        int currentIndex = roundController.getCurrentPlayerIndex();
        roundController.nextPlayerTurn();
        mainPane.updateDisplay();
    }
    
    public Player getCurrentPlayer() {
        return roundController.getCurrentPlayer();
    }
    
    public int getRoundCount() {
        return roundController.getRoundCount();
    }
}