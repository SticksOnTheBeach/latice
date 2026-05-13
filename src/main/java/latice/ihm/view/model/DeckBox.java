package latice.ihm.view.model;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import latice.ihm.controller.GameController;
import latice.ihm.controller.RoundController;
import latice.model.Player;
import latice.util.ImageLoader;

public class DeckBox extends VBox {
	// TODO réalisation d'un interface pour le deck des joueurs
	private GameController gameController;
	private Button btnDeck;
	private RoundController roundController;

	
	public DeckBox(RoundController roundController, GameController gameController) {
		this.roundController = roundController;
		this.gameController = gameController;
		
		HBox horizontalLayout = new HBox();
		
        Image deckImage = ImageLoader.load("/images/game/deck.png");
        ImageView deckView = new ImageView(deckImage);
        
        // BOUTON CLIQUABLE POUR LA "PIOCHE" DE CHAQUE JOUEUR
        btnDeck = new Button();
        btnDeck.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
        btnDeck.setGraphic(deckView);
        btnDeck.setOnMouseClicked(event -> {

        	Player currentPlayer = roundController.getCurrentPlayer();
        	currentPlayer.getRack().addTileFromDeck(currentPlayer.getDeck());
        });
        
        horizontalLayout.getChildren().add(btnDeck);
        this.getChildren().add(horizontalLayout);
	}
}