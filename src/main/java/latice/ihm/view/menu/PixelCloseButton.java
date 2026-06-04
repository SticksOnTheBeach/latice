package latice.ihm.view.menu;

import javafx.scene.control.Button;

/**
 * Bouton "fermer" en style pixel art (croix rouge).
 * À utiliser en haut à gauche de chaque menu pour quitter le jeu.
 *
 * Usage :
 *   Button btnClose = new PixelCloseButton(() -> System.exit(0));
 *   StackPane.setAlignment(btnClose, Pos.CENTER_LEFT);
 *   topBar.getChildren().add(btnClose);
 */
public class PixelCloseButton extends Button {

    private static final String BASE_STYLE =
        "-fx-font-family: \"Courier New\", monospace; " +
        "-fx-font-size: 18px; " +
        "-fx-font-weight: bold; " +
        "-fx-text-fill: white; " +
        "-fx-background-color: #e74c3c; " +
        "-fx-background-radius: 8px; " +
        "-fx-border-color: #ff6b5c #9c2820 #9c2820 #ff6b5c; " +
        "-fx-border-width: 3px; " +
        "-fx-border-radius: 8px; " +
        "-fx-min-width: 40px; " +
        "-fx-min-height: 40px; " +
        "-fx-max-width: 40px; " +
        "-fx-max-height: 40px; " +
        "-fx-padding: 0; " +
        "-fx-cursor: hand; " +
        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 5, 0, 2, 3);";

    private static final String HOVER_STYLE =
        "-fx-font-family: \"Courier New\", monospace; " +
        "-fx-font-size: 18px; " +
        "-fx-font-weight: bold; " +
        "-fx-text-fill: white; " +
        "-fx-background-color: #ff6b5c; " +
        "-fx-background-radius: 8px; " +
        "-fx-border-color: #ff6b5c #9c2820 #9c2820 #ff6b5c; " +
        "-fx-border-width: 3px; " +
        "-fx-border-radius: 8px; " +
        "-fx-min-width: 40px; " +
        "-fx-min-height: 40px; " +
        "-fx-max-width: 40px; " +
        "-fx-max-height: 40px; " +
        "-fx-padding: 0; " +
        "-fx-cursor: hand; " +
        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 7, 0, 2, 4);";

    private static final String PRESSED_STYLE =
        "-fx-font-family: \"Courier New\", monospace; " +
        "-fx-font-size: 18px; " +
        "-fx-font-weight: bold; " +
        "-fx-text-fill: white; " +
        "-fx-background-color: #9c2820; " +
        "-fx-background-radius: 8px; " +
        "-fx-border-color: #9c2820 #ff6b5c #ff6b5c #9c2820; " +
        "-fx-border-width: 3px; " +
        "-fx-border-radius: 8px; " +
        "-fx-min-width: 40px; " +
        "-fx-min-height: 40px; " +
        "-fx-max-width: 40px; " +
        "-fx-max-height: 40px; " +
        "-fx-padding: 0; " +
        "-fx-cursor: hand;";

    public PixelCloseButton() {
        super("✕");
        setStyle(BASE_STYLE);
        setOnMouseEntered(e -> setStyle(HOVER_STYLE));
        setOnMouseExited(e  -> setStyle(BASE_STYLE));
        setOnMousePressed(e -> setStyle(PRESSED_STYLE));
        setOnMouseReleased(e -> setStyle(HOVER_STYLE));

        // Par défaut, ferme le jeu
        setOnAction(e -> System.exit(0));
    }
}
