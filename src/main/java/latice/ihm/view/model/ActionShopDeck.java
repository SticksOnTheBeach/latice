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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;
import latice.ihm.controller.GameController;
import latice.ihm.controller.RoundController;
import latice.ihm.view.GamePane;
import latice.model.Player;
import latice.model.Referee;
import latice.model.exceptions.InvalidImagePathException;
import latice.util.ImageLoader;

public class ActionShopDeck extends VBox {

    /* TODO : mettre en place la boutique d'actions en fonctions des points du joueur.
     * DEUX ACTIONS POSSIBLES:
     * - actionExchangeAllTiles : échange toutes les tuiles du rack par des nouvelles
     * - actionPlaceTiles : permet au joueur ayant l'action de pouvoir jouer une nouvelles fois
     *
     * compteur de points : il y'aura dans le deck de la boutique, le nombres de points du joueurs
     * L'ACTION ACHETÉ SERA DIRECTEMENT JOUÉ, parce que sinon faudrais faire un inventaire des actions
     * et je n'y vois pas l'intérêt (surtout la flemme).
     */

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
    private GamePane        mainPane;

    public ActionShopDeck(GameController gameController,
                          RoundController roundController,
                          Referee referee, GamePane mainPane) {
        this.gameController  = gameController;
        this.roundController = roundController;
        this.mainPane        = mainPane;

        setAlignment(Pos.BOTTOM_CENTER);
        setPickOnBounds(false);

        // --- PANNEAU ---
        panel = new VBox(8);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(8));
        panel.setPrefWidth(PANEL_WIDTH);
        panel.setMaxWidth(PANEL_WIDTH);
        panel.setPrefHeight(PANEL_HEIGHT);
        panel.setPickOnBounds(false);
        panel.setStyle(
            "-fx-background-color: rgba(255, 255, 255, 0.12); " +
            "-fx-background-radius: 16px 16px 0 0; " +
            "-fx-border-color: rgba(255, 255, 255, 0.2); " +
            "-fx-border-width: 1.5px 1.5px 0px 1.5px; " +
            "-fx-border-radius: 16px 16px 0 0; " +
            "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 15, 0, 0, -4);"
        );

        points = new Label("POINTS : 0");
        points.setStyle(
            "-fx-font-weight: bold; " +
            "-fx-font-size: 11px; " +
            "-fx-font-family: 'SF Pro Text', Helvetica, Arial, sans-serif; " +
            "-fx-text-fill: rgba(255, 255, 255, 0.95);"
        );

        HBox actionsRow = new HBox(12);
        actionsRow.setAlignment(Pos.CENTER);
        actionsRow.setPickOnBounds(false);

        actionExchangeAllTiles = buildActionButton("/images/game/exchange.png", "-2");
        actionBuyExtraMove     = buildActionButton("/images/game/extra.png", "-2");

        actionsRow.getChildren().addAll(
            wrapAction(actionExchangeAllTiles, "Échange rack\n(-2 pts)"),
            wrapAction(actionBuyExtraMove,     "Jouer 2 fois\n(-2 pts)")
        );

        panel.getChildren().addAll(points, actionsRow);
        panel.setVisible(false);
        panel.setTranslateY(PANEL_HEIGHT);

        // --- FLÈCHE (sert d'animation) ---
        arrow = new Polygon();
        arrow.getPoints().addAll(0.0, 14.0, 14.0, 0.0, 28.0, 14.0);
        arrow.setFill(Color.rgb(255, 255, 255, 0.15));
        arrow.setStroke(Color.rgb(255, 255, 255, 0.25));
        arrow.setStrokeWidth(1.5);
        arrow.setCursor(Cursor.HAND);
        getChildren().addAll(panel, arrow);

        arrow.setOnMouseClicked(e -> {
            e.consume();
            togglePanel();
        });

        actionExchangeAllTiles.setOnAction(e -> {
            Player current = roundController.getCurrentPlayer();
            if (current.getScore() >= 2 || gameController.isHasPlayedAction() == false) {
                if (gameController.isHasPlayedAction() == false) {
                    gameController.setHasPlayedAction(true);
                } else {
                    if (gameController.isHasPlayedAction() == true);
                    current.setScore(current.getScore() - 2);
                }
                current.getRack().exchangeAllTiles(current.getDeck());
                refreshPoints();
                mainPane.rafraichirRackJoueur(roundController.getCurrentPlayerIndex());
            } else {
                // condition si on a pas encore joué
                showNotEnoughPoints();
            }
        });

        actionBuyExtraMove.setOnAction(e -> {
            Player current = roundController.getCurrentPlayer();
            if (current.getScore() >= 2 && gameController.isHasPlayedAction() == true) {
                current.setScore(current.getScore() - 2);
                refreshPoints();
                gameController.setHasPlayedAction(false);
            } else {
                if (gameController.isHasPlayedAction() == false) {
                    showNoPlayedYet();
                } else {
                    showNotEnoughPoints();
                }
            }
        });
    }

    private void togglePanel() {
        if (!isOpen) {
            panel.setVisible(true);
            panel.setTranslateY(PANEL_HEIGHT);

            TranslateTransition ttPanel = new TranslateTransition(Duration.millis(280), panel);
            ttPanel.setToY(0);

            TranslateTransition ttArrow = new TranslateTransition(Duration.millis(280), arrow);
            ttArrow.setToY(-PANEL_HEIGHT);

            ttPanel.play();
            ttArrow.play();
        } else {
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

    private Button buildActionButton(String imagePath, String costText) {
        ImageView iv = new ImageView();
        try {
            iv.setImage(ImageLoader.load(imagePath));
        } catch (InvalidImagePathException e) {
            System.err.println("Image introuvable : " + e.getMessage());
        }
        iv.setFitWidth(48);
        iv.setFitHeight(55);

        Label lblCost = new Label(costText);
        lblCost.setStyle(
            "-fx-font-size: 22px; " +
            "-fx-font-weight: bold; " +
            "-fx-font-family: 'SF Pro Text', Helvetica, Arial, sans-serif; " +
            "-fx-text-fill: #a1a1a1;"
        );
        lblCost.setOpacity(0);

        StackPane graphicContainer = new StackPane(iv, lblCost);
        Button btn = new Button("", graphicContainer);

        btn.setStyle(
            "-fx-background-color: rgba(255, 255, 255, 0.08); " +
            "-fx-background-radius: 10px; " +
            "-fx-border-color: rgba(255, 255, 255, 0.15); " +
            "-fx-border-width: 1px; " +
            "-fx-border-radius: 10px; " +
            "-fx-cursor: hand;"
        );

        btn.setOnMouseEntered(e -> {
            iv.setOpacity(0.15);
            lblCost.setOpacity(1.0);

            btn.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.18); " +
                "-fx-background-radius: 10px; " +
                "-fx-border-color: rgba(255, 255, 255, 0.3); " +
                "-fx-border-width: 1px; " +
                "-fx-border-radius: 10px; " +
                "-fx-cursor: hand;"
            );
        });

        btn.setOnMouseExited(e -> {
            iv.setOpacity(1.0);
            lblCost.setOpacity(0);

            btn.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.08); " +
                "-fx-background-radius: 10px; " +
                "-fx-border-color: rgba(255, 255, 255, 0.15); " +
                "-fx-border-width: 1px; " +
                "-fx-border-radius: 10px; " +
                "-fx-cursor: hand;"
            );
        });

        return btn;
    }

    private VBox wrapAction(Button btn, String labelText) {
        Label lbl = new Label(labelText);
        lbl.setWrapText(true);
        lbl.setMaxWidth(80);
        lbl.setAlignment(Pos.CENTER);

        lbl.setStyle(
            "-fx-font-size: 9px; " +
            "-fx-font-family: 'SF Pro Text', Helvetica, Arial, sans-serif; " +
            "-fx-text-alignment: center; " +
            "-fx-text-fill: rgba(255, 255, 255, 0.7);"
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

    private void showNoPlayedYet() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Pas encore joué");
        alert.setHeaderText(null);
        alert.setContentText("Vous n'avez pas encore fait de tour !");
        alert.showAndWait();
    }

    public void refreshPoints() {
        Player current = roundController.getCurrentPlayer();
        points.setText("POINTS : " + current.getScore());
    }
}