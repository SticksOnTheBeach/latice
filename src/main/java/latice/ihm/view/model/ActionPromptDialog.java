package latice.ihm.view.model;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import latice.ihm.view.GamePane;

/**
 * Boîte de dialogue qui demande au joueur s'il veut acheter une action.
 * S'affiche par-dessus le centre du GamePane avec une animation scale + fade.
 */
public class ActionPromptDialog extends StackPane {

    private static final int COUT_ACTION = 2;
    private static final int ANIM_DURATION = 250;

    private VBox dialogBox;
    private GamePane mainPane;

    public ActionPromptDialog(GamePane mainPane) {
        this.mainPane = mainPane;

        // Overlay semi-transparent qui couvre toute la zone du centerStack
        setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        setAlignment(Pos.CENTER);

        // --- TITRE ---
        Label lblTitle = new Label("Do you want to buy an action ? (Cost : " + COUT_ACTION + " points)");
        lblTitle.setWrapText(true);
        lblTitle.setStyle(
            "-fx-text-fill: white; " +
            "-fx-font-size: 16px; " +
            "-fx-font-family: 'SF Pro Display', 'Helvetica Neue', Arial; " +
            "-fx-font-weight: 500;"
        );

        // --- BOUTONS ---
        Button btnYes = buildButton("Yes");
        Button btnNo  = buildButton("No");

        btnYes.setOnAction(e -> {
            hide();
            mainPane.openActionShop();
        });
        btnNo.setOnAction(e -> hide());

        HBox buttonsRow = new HBox(20, btnYes, btnNo);
        buttonsRow.setAlignment(Pos.CENTER);

        // --- BOÎTE GLASSMORPHISM ---
        dialogBox = new VBox(20, lblTitle, buttonsRow);
        dialogBox.setAlignment(Pos.CENTER);
        dialogBox.setPadding(new Insets(30, 40, 30, 40));
        dialogBox.setMaxWidth(400);
        dialogBox.setStyle(
            "-fx-background-color: rgba(255, 255, 255, 0.15); " +
            "-fx-background-radius: 20px; " +
            "-fx-border-color: rgba(255, 255, 255, 0.4); " +
            "-fx-border-width: 1.5px; " +
            "-fx-border-radius: 20px; " +
            "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 25, 0, 0, 8);"
        );

        getChildren().add(dialogBox);

        // État initial : invisible et petit (pour la transition d'entrée)
        setOpacity(0);
        dialogBox.setScaleX(0.5);
        dialogBox.setScaleY(0.5);
    }

    private Button buildButton(String text) {
        Button btn = new Button(text);
        String style =
            "-fx-background-color: rgba(255, 255, 255, 0.12); " +
            "-fx-background-radius: 20px; " +
            "-fx-border-color: rgba(255, 255, 255, 0.4); " +
            "-fx-border-width: 1px; " +
            "-fx-border-radius: 20px; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-font-family: 'SF Pro Display', 'Helvetica Neue', Arial; " +
            "-fx-font-weight: 500; " +
            "-fx-padding: 8px 30px; " +
            "-fx-cursor: hand;";
        String hoverStyle =
            "-fx-background-color: rgba(255, 255, 255, 0.25); " +
            "-fx-background-radius: 20px; " +
            "-fx-border-color: rgba(255, 255, 255, 0.6); " +
            "-fx-border-width: 1px; " +
            "-fx-border-radius: 20px; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-font-family: 'SF Pro Display', 'Helvetica Neue', Arial; " +
            "-fx-font-weight: 500; " +
            "-fx-padding: 8px 30px; " +
            "-fx-cursor: hand;";

        btn.setStyle(style);
        btn.setOnMouseEntered(e -> btn.setStyle(hoverStyle));
        btn.setOnMouseExited(e  -> btn.setStyle(style));
        return btn;
    }

    /**
     * Affiche le dialogue par-dessus le centre du GamePane avec une animation.
     */
    public void show() {
        mainPane.addOverlay(this);

        FadeTransition fade = new FadeTransition(Duration.millis(ANIM_DURATION), this);
        fade.setFromValue(0);
        fade.setToValue(1);

        ScaleTransition scale = new ScaleTransition(Duration.millis(ANIM_DURATION), dialogBox);
        scale.setFromX(0.5);
        scale.setFromY(0.5);
        scale.setToX(1.0);
        scale.setToY(1.0);

        new ParallelTransition(fade, scale).play();
    }

    /**
     * Cache le dialogue avec une animation, puis le retire du GamePane.
     */
    private void hide() {
        FadeTransition fade = new FadeTransition(Duration.millis(ANIM_DURATION), this);
        fade.setFromValue(1);
        fade.setToValue(0);

        ScaleTransition scale = new ScaleTransition(Duration.millis(ANIM_DURATION), dialogBox);
        scale.setFromX(1.0);
        scale.setFromY(1.0);
        scale.setToX(0.5);
        scale.setToY(0.5);

        ParallelTransition pt = new ParallelTransition(fade, scale);
        pt.setOnFinished(e -> mainPane.removeOverlay(this));
        pt.play();
    }
}