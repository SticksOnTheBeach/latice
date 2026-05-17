package latice.ihm.view.model;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import latice.ihm.controller.GameController;
import latice.ihm.controller.RoundController;

public class ActionShopDeck extends HBox {
	private GameController gameController;
	private RoundController roundController;
	
	private Button actionExchangeAllTiles;
	private Button actionPlaceTiles;
	private Label points;
	
	public ActionShopDeck(GameController gameController, RoundController roundController) {
		
		/* TODO : mettre en place la boutique d'actions en fonctions des points du joueur.
		 * DEUX ACTIONS POSSIBLES: 
		 * - actionExchangeAllTiles : échange toutes les tuiles du rack par des nouvelles 
		 * - actionPlaceTiles : permet au joueur ayant l'action de pouvoir jouer une nouvelles fois
		 * 
		 * compteur de points : il y'aura dans le deck de la boutique, le nombres de points du joueurs
		 * L'ACTION ACHETÉ SERA DIRECTEMENT JOUÉ, parce que sinon faudrais faire un inventaire des actions
		 * et je n'y vois pas l'intérêt.
		 * 
		*/
	}
}
