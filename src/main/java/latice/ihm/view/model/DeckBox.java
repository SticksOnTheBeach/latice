package latice.ihm.view.model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import latice.ihm.controller.GameController;
import latice.ihm.controller.RoundController;
import latice.model.Player;
import latice.model.exceptions.InvalidImagePathException;
import latice.util.ImageLoader;

/**
 * Affichage visuel de la pioche d'un joueur.
 * Pour l'instant purement décoratif : la pioche se vide automatiquement
 * via TileController.placeTile() qui recharge le rack après chaque coup.
 */
public class DeckBox extends VBox {
    private Label nbTuilles;
    private Player player;

    public DeckBox(Player player) {
        this.player = player;

        ImageView deckView = new ImageView();
        nbTuilles = new Label(""+player.getDeck().size());
        nbTuilles.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: white");
        try {
            deckView.setImage(ImageLoader.load("/images/game/deck.png"));
        } catch (InvalidImagePathException e) {
            System.err.println("ERROR : image du Deck introuvable ! : " + e.getMessage());
        }
        deckView.setFitWidth(60);
        deckView.setFitHeight(60);
        deckView.setPreserveRatio(true);
        deckView.setSmooth(true);

        StackPane horizontalLayout = new StackPane(deckView, nbTuilles);
        horizontalLayout.setAlignment(Pos.CENTER);

        getChildren().add(horizontalLayout);
        setAlignment(Pos.CENTER);
        setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    }
    public void refresh() {
        nbTuilles.setText(""+player.getDeck().size());
    }
}