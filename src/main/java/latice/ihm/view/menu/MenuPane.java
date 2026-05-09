package latice.ihm.view.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import latice.util.ImageLoader;

public class MenuPane extends BorderPane {
    // TOP
    private Label lblTitle;
    private Button btnParameters;

    // CENTER
    private Button btnPlay;
    private Button btnExit;

    public MenuPane() {

        /* TOP */
        lblTitle = new Label("Latice");
        lblTitle.setStyle(
            "-fx-font-size: 64px; " +
            "-fx-font-weight: bold; " +
            "-fx-font-family: \"Arial\"; " +
            "-fx-text-fill: black;"
        );

        ImageView imageView = new ImageView(ImageLoader.load("/images/menu/parametres.png"));
        imageView.setFitWidth(32);
        imageView.setFitHeight(32);
        imageView.setPreserveRatio(true);
        btnParameters = new Button("", imageView);
        btnParameters.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-cursor: hand;"
        );

        StackPane topBar = new StackPane();
        topBar.setPadding(new Insets(30));
        StackPane.setAlignment(btnParameters, Pos.CENTER_LEFT);
        StackPane.setAlignment(lblTitle, Pos.CENTER);
        topBar.getChildren().addAll(lblTitle, btnParameters);
        setTop(topBar);


        /* CENTER */
        btnPlay = new Button("▶  PLAY");
        btnExit = new Button("✖  EXIT");

        // Style cartoon pour Play
        btnPlay.setStyle(
            "-fx-font-size: 22px; " +
            "-fx-font-weight: bold; " +
            "-fx-font-family: \"Arial\"; " +
            "-fx-text-fill: white; " +
            "-fx-background-color: #27ae60; " +
            "-fx-background-radius: 50px; " +
            "-fx-border-color: #1a5e35; " +
            "-fx-border-width: 3px; " +
            "-fx-border-radius: 20px; " +
            "-fx-padding: 12px 40px; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 6, 0, 0, 4);"
        );

        // Style cartoon pour Exit
        btnExit.setStyle(
            "-fx-font-size: 22px; " +
            "-fx-font-weight: bold; " +
            "-fx-font-family: \"Arial\"; " +
            "-fx-text-fill: white; " +
            "-fx-background-color: #e74c3c; " +
            "-fx-background-radius: 50px; " +
            "-fx-border-color: #922b21; " +
            "-fx-border-width: 3px; " +
            "-fx-border-radius: 50px; " +
            "-fx-padding: 12px 40px; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 6, 0, 0, 4);"
        );

        // Hover sur Play
        btnPlay.setOnMouseEntered(e -> btnPlay.setStyle(
            "-fx-font-size: 22px; " +
            "-fx-font-weight: bold; " +
            "-fx-font-family: \"Arial\"; " +
            "-fx-text-fill: white; " +
            "-fx-background-color: #2ecc71; " +
            "-fx-background-radius: 50px; " +
            "-fx-border-color: #1a5e35; " +
            "-fx-border-width: 3px; " +
            "-fx-border-radius: 50px; " +
            "-fx-padding: 12px 40px; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 10, 0, 0, 6);"
        ));
        btnPlay.setOnMouseExited(e -> btnPlay.setStyle(
            "-fx-font-size: 22px; " +
            "-fx-font-weight: bold; " +
            "-fx-font-family: \"Arial\"; " +
            "-fx-text-fill: white; " +
            "-fx-background-color: #27ae60; " +
            "-fx-background-radius: 50px; " +
            "-fx-border-color: #1a5e35; " +
            "-fx-border-width: 3px; " +
            "-fx-border-radius: 50px; " +
            "-fx-padding: 12px 40px; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 6, 0, 0, 4);"
        ));

        // Hover sur Exit
        btnExit.setOnMouseEntered(e -> btnExit.setStyle(
            "-fx-font-size: 22px; " +
            "-fx-font-weight: bold; " +
            "-fx-font-family: \"Arial\"; " +
            "-fx-text-fill: white; " +
            "-fx-background-color: #e95f50; " +
            "-fx-background-radius: 50px; " +
            "-fx-border-color: #922b21; " +
            "-fx-border-width: 3px; " +
            "-fx-border-radius: 50px; " +
            "-fx-padding: 12px 40px; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 10, 0, 0, 6);"
        ));
        btnExit.setOnMouseExited(e -> btnExit.setStyle(
            "-fx-font-size: 22px; " +
            "-fx-font-weight: bold; " +
            "-fx-font-family: \"Arial\"; " +
            "-fx-text-fill: white; " +
            "-fx-background-color: #e74c3c; " +
            "-fx-background-radius: 50px; " +
            "-fx-border-color: #922b21; " +
            "-fx-border-width: 3px; " +
            "-fx-border-radius: 50px; " +
            "-fx-padding: 12px 40px; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 6, 0, 0, 4);"
        ));

        GridPane gridMid = new GridPane();
        gridMid.setPadding(new Insets(10, 10, 10, 10));
        gridMid.setVgap(20);
        gridMid.setHgap(10);
        gridMid.setAlignment(Pos.CENTER);
        gridMid.add(btnPlay, 0, 0);
        gridMid.add(btnExit, 0, 1);

        setCenter(gridMid);
    }
}