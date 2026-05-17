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

    private GameController gameController;
    private RoundController roundController;
    private Button actionExchangeAllTiles;
    private Button actionBuyExtraMove;
    private Label points;
    private boolean isOpen = false;
    private VBox panel;
    private Polygon arrow;

    public ActionShopDeck(GameController gameController,
                          RoundController roundController,
                          Referee referee) {
        this.gameController = gameController;
        this.roundController = roundController;

        setAlignment(Pos.BOTTOM_CENTER);
        setPickOnBounds(false); // ne bloque pas les clics sur les éléments derrière

        // --- PANNEAU ORANGE (caché par défaut) ---
        panel = new VBox(8);
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setPadding(new Insets(10));
        panel.setPrefWidth(250);
        panel.setStyle(
            "-fx-background-color: #e87e04; " +
            "-fx-border-color: #b85e00; " +
            "-fx-border-width: 2px 2px 0px 2px;"
        );

        // Label points
        points = new Label("POINTS : 0");
        points.setStyle("-fx-font-weight: bold; -fx-font-size: 12px; -fx-font-family: \"Courier New\";");

        // Actions côte à côte
        HBox actionsRow = new HBox(15);
        actionsRow.setAlignment(Pos.CENTER);

        actionExchangeAllTiles = buildActionButton("/images/game/exchange.png");
        actionBuyExtraMove     = buildActionButton("/images/game/extra.png");

        actionsRow.getChildren().addAll(
            wrapAction(actionExchangeAllTiles, "Échange rack\n(-2 pts)"),
            wrapAction(actionBuyExtraMove,     "Jouer 2 fois\n(-2 pts)")
        );

        panel.getChildren().addAll(points, actionsRow);

        // Panneau caché par défaut (décalé vers le bas = sa propre hauteur)
        panel.setVisible(false);
        panel.setTranslateY(200); // sera recalculé à l'ouverture

        // --- FLÈCHE ---
        arrow = new Polygon();
        arrow.getPoints().addAll(0.0, 18.0, 18.0, 0.0, 36.0, 18.0);
        arrow.setFill(Color.ORANGE);
        arrow.setStroke(Color.web("#b85e00"));
        arrow.setStrokeWidth(2);
        arrow.setCursor(Cursor.HAND);

        // La flèche et le panneau sont empilés : panneau en haut, flèche en bas
        getChildren().addAll(panel, arrow);

        // --- TOGGLE au clic sur la flèche ---
        arrow.setOnMouseClicked(e -> togglePanel());

        // --- Actions ---
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
            // Ouvrir : le panneau remonte depuis le bas
            panel.setVisible(true);
            panel.applyCss();
            panel.layout();
            double hauteur = panel.getHeight() > 0 ? panel.getHeight() : 150;

            panel.setTranslateY(hauteur);
            TranslateTransition tt = new TranslateTransition(Duration.millis(300), panel);
            tt.setToY(0);
            // Faire monter la flèche avec le panneau
            TranslateTransition ttArrow = new TranslateTransition(Duration.millis(300), arrow);
            ttArrow.setToY(-hauteur);
            tt.play();
            ttArrow.play();
        } else {
            // Fermer : le panneau redescend
            double hauteur = panel.getHeight() > 0 ? panel.getHeight() : 150;

            TranslateTransition tt = new TranslateTransition(Duration.millis(300), panel);
            tt.setToY(hauteur);
            tt.setOnFinished(e -> panel.setVisible(false));

            TranslateTransition ttArrow = new TranslateTransition(Duration.millis(300), arrow);
            ttArrow.setToY(0);

            tt.play();
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
        iv.setFitWidth(55);
        iv.setFitHeight(65);
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
        lbl.setStyle("-fx-font-size: 10px; -fx-font-family: \"Courier New\"; -fx-text-alignment: center;");
        VBox box = new VBox(4, btn, lbl);
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