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
import latice.ihm.view.MainPane;
import latice.model.Player;
import latice.model.Referee;
import latice.model.exceptions.InvalidImagePathException;
import latice.util.ImageLoader;

public class ActionShopDeck extends VBox {
    private GameController gameController;
    private RoundController roundController;
    private Button actionExchangeAllTiles;
    private Button actionBuyExtraMove;
    private Label points;
    private boolean isOpen = false;
    private VBox panel;

    public ActionShopDeck(GameController gameController, 
                          RoundController roundController, 
                          Referee referee) {
        this.gameController = gameController;
        this.roundController = roundController;

        setAlignment(Pos.TOP_CENTER);

        // --- FLÈCHE ---
        Polygon arrow = new Polygon();
        arrow.getPoints().addAll(0.0, 20.0, 20.0, 0.0, 40.0, 20.0);
        arrow.setFill(Color.ORANGE);
        arrow.setStroke(Color.BLACK);
        arrow.setCursor(Cursor.HAND);

        // --- PANNEAU ---
        panel = new VBox(5);
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setPadding(new Insets(6));
        panel.setStyle("-fx-background-color: #e87e04; -fx-border-color: black; -fx-border-width: 1;");
        panel.setPrefWidth(150);

        // Points
        points = new Label("POINTS : 0");
        points.setStyle("-fx-font-weight: bold; -fx-font-size: 9px;");

        // Actions côte à côte
        HBox actionsRow = new HBox(10);
        actionsRow.setAlignment(Pos.CENTER);

        actionExchangeAllTiles = buildActionButton(
            "/images/game/exchange.png",
            "Échange toutes les tuiles de votre Rack"
        );
        actionBuyExtraMove = buildActionButton(
            "/images/game/extra.png",
            "Vous permet de jouer 2 fois"
        );

        actionsRow.getChildren().addAll(
            wrapAction(actionExchangeAllTiles, "Échange toutes les tuiles de votre Rack"),
            wrapAction(actionBuyExtraMove, "Vous permet de jouer 2 fois")
        );

        panel.getChildren().addAll(points, actionsRow);

        // Panel caché par défaut
        panel.setTranslateY(-200);
        panel.setVisible(false);

        getChildren().addAll(arrow, panel);

        // --- TOGGLE au clic ---
        arrow.setOnMouseClicked(e -> togglePanel());

        // --- Listeners actions ---
        actionExchangeAllTiles.setOnAction(e -> {
            Player current = roundController.getCurrentPlayer();
            if (current.getScore() >= 2) {
                current.setScore(current.getScore() - 2);
                current.getRack().exchangeAllTiles(current.getDeck());
                refreshPoints();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Points insuffisants");
                alert.setHeaderText(null);
                alert.setContentText("Vous n'avez pas assez de points !");
                alert.showAndWait();
            }
        });

        actionBuyExtraMove.setOnAction(e -> {
            Player current = roundController.getCurrentPlayer();
            if (current.getScore() >= 2) {
                current.setScore(current.getScore() - 2);
                // TODO : logique rejouer
                refreshPoints();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Points insuffisants");
                alert.setHeaderText(null);
                alert.setContentText("Vous n'avez pas assez de points !");
                alert.showAndWait();
            }
        });
    }

    private void togglePanel() {
        if (!isOpen) {
            // Ouvrir
            panel.setVisible(true);
            TranslateTransition tt = new TranslateTransition(Duration.millis(300), panel);
            tt.setFromY(-panel.getPrefHeight());
            tt.setToY(0);
            tt.play();
        } else {
            // Fermer
            TranslateTransition tt = new TranslateTransition(Duration.millis(300), panel);
            tt.setFromY(0);
            tt.setToY(-200);
            tt.setOnFinished(e -> panel.setVisible(false));
            tt.play();
        }
        isOpen = !isOpen;
    }

    // Bouton avec image
    private Button buildActionButton(String imagePath, String tooltip) {
        ImageView iv = new ImageView();
        try {
            iv.setImage(ImageLoader.load(imagePath));
        } catch (InvalidImagePathException e) {
            System.err.println("Image introuvable : " + e.getMessage());
        }
        iv.setFitWidth(50);
        iv.setFitHeight(60);
        Button btn = new Button("", iv);
        btn.setStyle("-fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 1;");
        return btn;
    }

    // VBox image + label
    private VBox wrapAction(Button btn, String labelText) {
        Label lbl = new Label(labelText);
        lbl.setWrapText(true);
        lbl.setMaxWidth(60);
        lbl.setAlignment(Pos.CENTER);
        lbl.setStyle("-fx-font-size: 9px;");
        VBox box = new VBox(5, btn, lbl);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    public void refreshPoints() {
        Player current = roundController.getCurrentPlayer();
        points.setText("POINTS : " + current.getScore());
    }
}