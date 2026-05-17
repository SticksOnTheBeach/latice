package latice.ihm.view.model;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import latice.ihm.controller.GameController;
import latice.ihm.controller.RoundController;
import latice.model.Player;
import latice.model.Referee;

public class ActionShopDeck extends HBox {
	private GameController gameController;
	private RoundController roundController;
	
	private Button actionExchangeAllTiles;
	private Button actionPlaceTiles;
	private Label points;
	private Referee referee;
	
	public ActionShopDeck(GameController gameController, RoundController roundController, Referee referee) {

		/* TODO : mettre en place la boutique d'actions en fonctions des points du joueur.
		 * DEUX ACTIONS POSSIBLES: 
		 * - actionExchangeAllTiles : échange toutes les tuiles du rack par des nouvelles 
		 * - actionPlaceTiles : permet au joueur ayant l'action de pouvoir jouer une nouvelles fois
		 * 
		 * compteur de points : il y'aura dans le deck de la boutique, le nombres de points du joueurs
		 * L'ACTION ACHETÉ SERA DIRECTEMENT JOUÉ, parce que sinon faudrais faire un inventaire des actions
		 * et je n'y vois pas l'intérêt (surtout la flemme).
		 * 
		*/
		this.gameController = gameController;
		this.roundController = roundController;
		this.referee = referee;
		
		points = new Label();
	    refreshPoints();
	    
	    // Bouton échange rack
	    Label lblExchangeAllTiles = new Label("Échanger rack (-2 pts)");
	    actionExchangeAllTiles = new Button("");
	    actionExchangeAllTiles.setOnAction(e -> {
	        Player current = roundController.getCurrentPlayer();
	        if (current.getScore() >= 2) {
	            current.setScore(current.getScore() - 2);
	            current.getRack().exchangeAllTiles(current.getDeck());
	            refreshPoints();
	        } else {
	        	Alert alert = new Alert(Alert.AlertType.WARNING);
	            alert.setTitle("Points insuffisants");
	            alert.setHeaderText(null);
	            alert.setContentText("Vous n'avez pas assez de points !");
	            alert.showAndWait();
	        }
	    });
	    getChildren().addAll(points, actionExchangeAllTiles);
	}
	
	
	private void refreshPoints() {
	    Player current = roundController.getCurrentPlayer();
	    points.setText("Points : " + current.getScore());
	}
}

