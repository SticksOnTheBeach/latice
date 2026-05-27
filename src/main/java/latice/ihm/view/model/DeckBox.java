package latice.ihm.view.model;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import latice.ihm.controller.GameController;
import latice.ihm.controller.RoundController;
import latice.model.exceptions.InvalidImagePathException;
import latice.util.ImageLoader;

public class DeckBox extends VBox {
    private GameController gameController;
    private Button btnDeck;
    private RoundController roundController;

    public DeckBox(RoundController roundController, GameController gameController) {
        this.roundController = roundController;
        this.gameController = gameController;
        
        HBox horizontalLayout = new HBox();
        ImageView deckView = new ImageView();

        try {
            deckView.setImage(ImageLoader.load("/images/game/deck.png"));
        } catch (InvalidImagePathException e) {
            System.err.println("ERROR : image du Deck introuvable ! : " + e.getMessage());
        }

        deckView.setFitWidth(60);
        deckView.setFitHeight(60);
        deckView.setPreserveRatio(true);
        deckView.setSmooth(true);
        
        // BOUTON CLIQUABLE POUR LA "PIOCHE" DE CHAQUE JOUEUR
        btnDeck = new Button();
        btnDeck.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
        btnDeck.setGraphic(deckView);
        btnDeck.setOnMouseClicked(event -> {
          //  this.gameController.drawTile();
            //TODO : a revoir car pas dans les regles du jeu et pose probleme car pioche infini, revoir la logique ou changer son fonctionnement"
        });
        
        horizontalLayout.getChildren().add(btnDeck);
        horizontalLayout.setAlignment(Pos.CENTER);
        this.getChildren().add(horizontalLayout);
        this.setAlignment(Pos.CENTER);
        this.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    }
}