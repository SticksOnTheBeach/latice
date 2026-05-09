package latice.ihm.view.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import latice.util.ImageLoader;

public class MenuPane extends BorderPane {
    private Label lblTitle;
    private Button btnParameters;

    public MenuPane() {
        // Titre 
        lblTitle = new Label("Latice");
        lblTitle.setStyle(
            "-fx-font-size: 64px; " +
            "-fx-font-weight: bold; " +
            "-fx-font-family: \"Arial\"; " +
            "-fx-text-fill: white;"
        );

        // Bouton paramètres
        ImageView imageView = new ImageView(ImageLoader.load("/images/parametres.png"));
        imageView.setFitWidth(32);
        imageView.setFitHeight(32);
        imageView.setPreserveRatio(true);
        btnParameters = new Button("", imageView);
        btnParameters.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-cursor: hand;"
        );

        // StackPane pour superposer le bouton à gauche et le titre au centre
        StackPane topBar = new StackPane();
        topBar.setPadding(new Insets(30));

        StackPane.setAlignment(btnParameters, Pos.CENTER_LEFT);
        StackPane.setAlignment(lblTitle, Pos.CENTER);

        topBar.getChildren().addAll(lblTitle, btnParameters);

        setTop(topBar);
    }
}