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
import latice.ihm.controller.GameController;
import latice.ihm.view.GamePane;

/**
 * Boîte de dialogue qui demande au joueur s'il veut acheter une action.
 * S'affiche par-dessus le centre du GamePane avec une animation scale + fade.
 *
 * Si le joueur clique Yes  -> ouvre le shop
 * Si le joueur clique No   -> passe directement au joueur suivant
 */
public class ActionPromptDialog extends StackPane {

    private static final int COUT_ACTION = 2;
    private static final int ANIM_DURATION = 250;

    private VBox dialogBox;
    private GamePane mainPane;
    private GameController gameController;

    public ActionPromptDialog(GamePane mainPane, GameController gameController) {
        this.mainPane = mainPane;
        this.gameController = gameController;

        setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        setAlignment(Pos.CENTER);

        // --- TITRE ---
        Label lblTitle = new Label("Do you want to buy an action ? (Cost : " + COUT_ACTION + " points)");
        lblTitle.setWrapText(true);
        lblTitle.setStyle(
            "-fx-text-fill: white; " +
            "-fx-font-size: 13px; " +
            "-fx-font-family: 'SF Pro Display', 'Helvetica Neue', Arial; " +
            "-fx-font-weight: 500; " +
            "-fx-text-alignment: center;"
        );

        // --- BOUTONS ---
        Button btnYes = new Button("Yes");
        Button btnNo  = new Button("No");

        String styleBtn =
            "-fx-background-color: rgba(255, 255, 255, 0.12); " +
            "-fx-background-radius: 16px; " +
            "-fx-border-color: rgba(255, 255, 255, 0.4); " +
            "-fx-border-width: 1px; " +
            "-fx-border-radius: 16px; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 12px; " +
            "-fx-font-family: 'SF Pro Display', 'Helvetica Neue', Arial; " +
            "-fx-font-weight: 500; " +
            "-fx-padding: 6px 22px; " +
            "-fx-cursor: hand;";

        String hoverStyleBtnYes =
            "-fx-background-color: green; " +
            "-fx-background-radius: 16px; " +
            "-fx-border-color: rgba(255, 255, 255, 0.6); " +
            "-fx-border-width: 1px; " +
            "-fx-border-radius: 16px; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 12px; " +
            "-fx-font-family: 'SF Pro Display', 'Helvetica Neue', Arial; " +
            "-fx-font-weight: 500; " +
            "-fx-padding: 6px 22px; " +
            "-fx-cursor: hand;";

        String hoverStyleBtnNo =
            "-fx-background-color: red; " +
            "-fx-background-radius: 16px; " +
            "-fx-border-color: rgba(255, 255, 255, 0.6); " +
            "-fx-border-width: 1px; " +
            "-fx-border-radius: 16px; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 12px; " +
            "-fx-font-family: 'SF Pro Display', 'Helvetica Neue', Arial; " +
            "-fx-font-weight: 500; " +
            "-fx-padding: 6px 22px; " +
            "-fx-cursor: hand;";

        btnYes.setStyle(styleBtn);
        btnYes.setOnMouseEntered(e -> btnYes.setStyle(hoverStyleBtnYes));
        btnYes.setOnMouseExited(e  -> btnYes.setStyle(styleBtn));

        btnNo.setStyle(styleBtn);
        btnNo.setOnMouseEntered(e -> btnNo.setStyle(hoverStyleBtnNo));
        btnNo.setOnMouseExited(e  -> btnNo.setStyle(styleBtn));

        btnYes.setOnAction(e -> {
            hide();
            mainPane.openActionShop();
           mainPane.updateDisplay();
        });
        btnNo.setOnAction(e -> {
            hide();
            // Si le joueur refuse l'action, on passe directement au joueur suivant
            gameController.passTurn();
        });

        HBox buttonsRow = new HBox(20, btnYes, btnNo);
        buttonsRow.setAlignment(Pos.CENTER);

        // --- BOÎTE GLASSMORPHISM ---
        dialogBox = new VBox(15, lblTitle, buttonsRow);
        dialogBox.setAlignment(Pos.CENTER);
        dialogBox.setPadding(new Insets(20, 25, 20, 25));
        dialogBox.setMaxWidth(280);
        dialogBox.setMaxHeight(140);
        dialogBox.setStyle(
            "-fx-background-color: rgba(255, 255, 255, 0.15); " +
            "-fx-background-radius: 16px; " +
            "-fx-border-color: rgba(255, 255, 255, 0.4); " +
            "-fx-border-width: 1.5px; " +
            "-fx-border-radius: 16px; " +
            "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 20, 0, 0, 6);"
        );

        getChildren().add(dialogBox);

        // État initial : invisible et petit (pour la transition d'entrée)
        setOpacity(0);
        dialogBox.setScaleX(0.5);
        dialogBox.setScaleY(0.5);
    }

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