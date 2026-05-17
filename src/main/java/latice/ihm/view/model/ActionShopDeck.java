package latice.ihm.view.model;

import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;
import latice.ihm.controller.GameController;
import latice.ihm.controller.RoundController;
import latice.model.Player;
import latice.model.Referee;
import latice.model.exceptions.InvalidImagePathException;
import latice.util.ImageLoader;

public class ActionShopDeck extends VBox {

    private static final double PANEL_WIDTH  = 220;
    private static final double PANEL_HEIGHT = 140;

    private GameController  gameController;
    private RoundController roundController;
    private Button          actionExchangeAllTiles;
    private Button          actionBuyExtraMove;
    private Label           points;
    private boolean         isOpen = false;
    private VBox            panel;
    private Polygon         arrow;

    public ActionShopDeck(GameController gameController,
                          RoundController roundController,
                          Referee referee) {
        this.gameController  = gameController;
        this.roundController = roundController;

        setAlignment(Pos.BOTTOM_CENTER);
        setPickOnBounds(false);

        // --- PANNEAU ORANGE ---
        panel = new VBox(8);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(8));
        panel.setPrefWidth(PANEL_WIDTH);
        panel.setMaxWidth(PANEL_WIDTH);
        panel.setPrefHeight(PANEL_HEIGHT);
        panel.setPickOnBounds(false);
        panel.setStyle(
            "-fx-background-color: #e87e04; " +
            "-fx-border-color: #b85e00; " +
            "-fx-border-width: 2px 2px 0px 2px; " +
            "-fx-background-radius: 8px 8px 0 0;"
        );

        points = new Label("POINTS : 0");
        points.setStyle(
            "-fx-font-weight: bold; " +
            "-fx-font-size: 11px; " +
            "-fx-font-family: \"Courier New\";"
        );

        HBox actionsRow = new HBox(12);
        actionsRow.setAlignment(Pos.CENTER);
        actionsRow.setPickOnBounds(false);

        actionExchangeAllTiles = buildActionButton("/images/game/exchange.png");
        actionBuyExtraMove     = buildActionButton("/images/game/extra.png");

        actionsRow.getChildren().addAll(
            wrapAction(actionExchangeAllTiles, "Échange rack\n(-2 pts)"),
            wrapAction(actionBuyExtraMove,     "Jouer 2 fois\n(-2 pts)")
        );

        panel.getChildren().addAll(points, actionsRow);

        // Panel masqué par défaut, décalé vers le bas (hors vue)
        panel.setVisible(false);
        panel.setTranslateY(PANEL_HEIGHT);

        // --- FLÈCHE ---
        arrow = new Polygon();
        arrow.getPoints().addAll(0.0, 14.0, 14.0, 0.0, 28.0, 14.0);
        arrow.setFill(Color.ORANGE);
        arrow.setStroke(Color.web("#b85e00"));
        arrow.setStrokeWidth(2);
        arrow.setCursor(Cursor.HAND);

        // Structure : [ panel (caché) au dessus | flèche en bas ]
        getChildren().addAll(panel, arrow);

        arrow.setOnMouseClicked(e -> {
            e.consume();
            togglePanel();
        });

        actionExchangeAllTiles.setOnAction(e -> {
            Player current = roundController.getCurrentPlayer();
            if (current.getScore() >= 2) {
                current.setScore(current.getScore() - 2);
                current.getRack().exchangeAllTiles(current.getDeck());
                refreshPoints();
            } else {
                showNotEnoughPoints();
            }
        });

        actionBuyExtraMove.setOnAction(e -> {
            Player current = roundController.getCurrentPlayer();
            if (current.getScore() >= 2) {
                current.setScore(current.getScore() - 2);
                // TODO : logique rejouer
                refreshPoints();
            } else {
                showNotEnoughPoints();
            }
        });
    }

    private void togglePanel() {
        if (!isOpen) {
            // Ouvrir : panneau monte depuis le bas vers sa position naturelle
            panel.setVisible(true);
            panel.setTranslateY(PANEL_HEIGHT);

            TranslateTransition ttPanel = new TranslateTransition(Duration.millis(280), panel);
            ttPanel.setToY(0);

            TranslateTransition ttArrow = new TranslateTransition(Duration.millis(280), arrow);
            ttArrow.setToY(-PANEL_HEIGHT);

            ttPanel.play();
            ttArrow.play();
        } else {
            // Fermer : panneau redescend
            TranslateTransition ttPanel = new TranslateTransition(Duration.millis(280), panel);
            ttPanel.setToY(PANEL_HEIGHT);
            ttPanel.setOnFinished(e -> panel.setVisible(false));

            TranslateTransition ttArrow = new TranslateTransition(Duration.millis(280), arrow);
            ttArrow.setToY(0);

            ttPanel.play();
            ttArrow.play();
        }
        isOpen = !isOpen;
    }

    private Button buildActionButton(String imagePath) {
        ImageView iv = new ImageView();
        try {
            iv.setImage(ImageLoader.load(imagePath));
        } catch (InvalidImagePathException e) {
            System.err.println("Image introuvable : " + e.getMessage());
        }
        iv.setFitWidth(48);
        iv.setFitHeight(55);
        Button btn = new Button("", iv);
        btn.setStyle(
            "-fx-background-color: rgba(0,0,0,0.15); " +
            "-fx-border-color: #b85e00; " +
            "-fx-border-width: 1px; " +
            "-fx-cursor: hand;"
        );
        return btn;
    }

    private VBox wrapAction(Button btn, String labelText) {
        Label lbl = new Label(labelText);
        lbl.setWrapText(true);
        lbl.setMaxWidth(80);
        lbl.setAlignment(Pos.CENTER);
        lbl.setStyle(
            "-fx-font-size: 9px; " +
            "-fx-font-family: \"Courier New\"; " +
            "-fx-text-alignment: center;"
        );
        VBox box = new VBox(3, btn, lbl);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private void showNotEnoughPoints() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Points insuffisants");
        alert.setHeaderText(null);
        alert.setContentText("Vous n'avez pas assez de points !");
        alert.showAndWait();
    }

    public void refreshPoints() {
        Player current = roundController.getCurrentPlayer();
        points.setText("POINTS : " + current.getScore());
    }
}