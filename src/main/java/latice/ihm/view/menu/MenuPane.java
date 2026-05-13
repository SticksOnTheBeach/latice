package latice.ihm.view.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import latice.ihm.view.style.Style;
import latice.util.ImageLoader;

public class MenuPane extends BorderPane {
    // TOP
    private Label lblTitle;
    private Button btnParameters;
    // CORRECTION : suppression des constantes locales redondantes
    // On utilise directement Style.BTN_PARAMETERS_STYLE etc.

    // CENTER
    private Button btnPlay;
    private Button btnExit;

    public MenuPane() {

        // Image de fond
        ImageView background = new ImageView(ImageLoader.load("/images/menu/background.png"));
        background.setPreserveRatio(false);
        background.fitWidthProperty().bind(widthProperty());
        background.fitHeightProperty().bind(heightProperty());

        setStyle("-fx-background-color: #1a2e35;");

        /* TOP */
        ImageView imageView = new ImageView(ImageLoader.load("/images/menu/parametres.png"));
        imageView.setFitWidth(32);
        imageView.setFitHeight(32);
        imageView.setPreserveRatio(true);
        btnParameters = new Button("", imageView);

        // Styles bouton paramètres
        btnParameters.setStyle(Style.BTN_PARAMETERS_STYLE);
        btnParameters.setOnMouseEntered(e -> btnParameters.setStyle(Style.BTN_PARAMETERS_HOVER_STYLE));
        btnParameters.setOnMouseExited(e -> btnParameters.setStyle(Style.BTN_PARAMETERS_STYLE));
        btnParameters.setOnMousePressed(e -> btnParameters.setStyle(Style.BTN_PARAMETERS_PRESSED_STYLE));
        btnParameters.setOnMouseReleased(e -> btnParameters.setStyle(Style.BTN_PARAMETERS_HOVER_STYLE));

        StackPane topBar = new StackPane();
        topBar.setPadding(new Insets(30));
        StackPane.setAlignment(btnParameters, Pos.CENTER_LEFT);
        topBar.getChildren().add(btnParameters);
        setTop(topBar);

        /* CENTER */
        btnPlay = new Button("> PLAY");
        btnExit = new Button("> EXIT");

        String btnStyle =
            "-fx-font-size: 20px; " +
            "-fx-font-weight: bold; " +
            "-fx-font-family: \"Courier New\"; " +
            "-fx-text-fill: #1a2e35; " +
            "-fx-background-color: #a8d8a8; " +
            "-fx-background-radius: 0px; " +
            "-fx-border-color: #4a9e4a #1a5e1a #1a5e1a #4a9e4a; " +
            "-fx-border-width: 4px; " +
            "-fx-padding: 12px 40px; " +
            "-fx-cursor: hand; " +
            "-fx-min-width: 200px;";

        String btnHoverStyle =
            "-fx-font-size: 20px; " +
            "-fx-font-weight: bold; " +
            "-fx-font-family: \"Courier New\"; " +
            "-fx-text-fill: #1a2e35; " +
            "-fx-background-color: #c8f8c8; " +
            "-fx-background-radius: 0px; " +
            "-fx-border-color: #4a9e4a #1a5e1a #1a5e1a #4a9e4a; " +
            "-fx-border-width: 4px; " +
            "-fx-padding: 12px 40px; " +
            "-fx-cursor: hand; " +
            "-fx-min-width: 200px;";

        String btnPressedStyle =
            "-fx-font-size: 20px; " +
            "-fx-font-weight: bold; " +
            "-fx-font-family: \"Courier New\"; " +
            "-fx-text-fill: #1a2e35; " +
            "-fx-background-color: #88b888; " +
            "-fx-background-radius: 0px; " +
            "-fx-border-color: #1a5e1a #4a9e4a #4a9e4a #1a5e1a; " +
            "-fx-border-width: 4px; " +
            "-fx-padding: 14px 38px 10px 42px; " +
            "-fx-cursor: hand; " +
            "-fx-min-width: 200px;";

        btnPlay.setStyle(btnStyle);
        btnExit.setStyle(btnStyle);

        btnPlay.setOnMouseEntered(e -> btnPlay.setStyle(btnHoverStyle));
        btnPlay.setOnMouseExited(e -> btnPlay.setStyle(btnStyle));
        btnPlay.setOnMousePressed(e -> btnPlay.setStyle(btnPressedStyle));
        btnPlay.setOnMouseReleased(e -> btnPlay.setStyle(btnHoverStyle));

        btnExit.setOnMouseEntered(e -> btnExit.setStyle(btnHoverStyle));
        btnExit.setOnMouseExited(e -> btnExit.setStyle(btnStyle));
        btnExit.setOnMousePressed(e -> btnExit.setStyle(btnPressedStyle));
        btnExit.setOnMouseReleased(e -> btnExit.setStyle(btnHoverStyle));

        GridPane gridMid = new GridPane();
        gridMid.setPadding(new Insets(10, 10, 10, 10));
        gridMid.setVgap(20);
        gridMid.setHgap(10);
        gridMid.setAlignment(Pos.CENTER);
        gridMid.add(btnPlay, 0, 0);
        gridMid.add(btnExit, 0, 1);

        setCenter(gridMid);

        getChildren().add(0, background);
    }
}