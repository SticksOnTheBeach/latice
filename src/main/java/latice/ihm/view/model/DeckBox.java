package latice.ihm.view.model;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import latice.ihm.controller.GameController;
import latice.ihm.controller.RoundController;
import latice.model.exceptions.InvalidImagePathException;
import latice.util.ImageLoader;

/**
 * Affichage visuel de la pioche d'un joueur.
 * Pour l'instant purement décoratif : la pioche se vide automatiquement
 * via TileController.placeTile() qui recharge le rack après chaque coup.
 */
public class DeckBox extends VBox {
	/*
	 *  TODO ; changer le systeme de pioche, la pioche n'est pas cliquable pour les joueurs, 
	 *  la pioche est juste un systeme automatique
	 *  
	 *  fair een sorte de vérifier, lorsqu'il s'agit duu joueur actuel qui joue, s'il est 
	 *  en capacité de pouvoir  joueur avec son rack actuel :
	 *  
	 *   1ere option : s'il ne peux pas jouer et que son rack est full : passTurn()ù
	 *   
	 *    2 eme opption: s'il ne peux pas jouer et que son rackj n'est pas full, la pioche
	 *    give automatiquement les tuiles  manquantes dans le rack du joueur, donc on poarcout
	 *    sa pioche, pour voir combien de tuiles il lui en manque et la pioche lui donne autaznt de tuile pour remplir son rack à 5
	 */
    private RoundController roundController;
    private GameController gameController;

    public DeckBox(RoundController roundController, GameController gameController) {
        this.roundController = roundController;
        this.gameController = gameController;

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

        HBox horizontalLayout = new HBox(deckView);
        horizontalLayout.setAlignment(Pos.CENTER);

        getChildren().add(horizontalLayout);
        setAlignment(Pos.CENTER);
        setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    }
}