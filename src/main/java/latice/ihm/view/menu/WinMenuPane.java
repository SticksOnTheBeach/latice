package latice.ihm.view.menu;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import latice.model.Player;

import java.util.ArrayList;

public class WinMenuPane extends BorderPane {

    private Label lblVictory;
    private Button btnReplay = new Button("> REPLAY");
    private Button btnExit = new Button("> EXIT");

    public WinMenuPane(Stage stage, ArrayList<Player> winners) {

        this.setStyle("-fx-background-color: #1a2e35;");

        lblVictory = new Label("Victory !");
        lblVictory.setStyle("-fx-font-size: 32px; -fx-font-family: \"Courier New\"; -fx-font-weight: bold; -fx-text-fill: white;");

        VBox topBox = new VBox(lblVictory);
        topBox.setAlignment(Pos.CENTER);
        topBox.setStyle("-fx-padding: 40px 0 0 0;");
        this.setTop(topBox);

        VBox winnersBox = new VBox(12);
        winnersBox.setAlignment(Pos.CENTER);
        getWinnerLabel(winners, winnersBox);
        this.setCenter(winnersBox);

        HBox buttonBox = new HBox(20, btnReplay, btnExit);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setStyle("-fx-padding: 0 0 40px 0;");
        this.setBottom(buttonBox);

        String btnStyle =
                "-fx-font-size: 20px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-family: \"Courier New\"; " +
                        "-fx-text-fill: #1a2e35; " +
                        "-fx-background-color: #a8d8a8; " +
                        "-fx-background-radius: 0px; " +
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
                        "-fx-border-color: white; "+
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

        btnReplay.setStyle(btnStyle);
        btnExit.setStyle(btnStyle);

        btnReplay.setOnMouseEntered(e -> btnReplay.setStyle(btnHoverStyle));
        btnReplay.setOnMouseExited(e -> btnReplay.setStyle(btnStyle));
        btnReplay.setOnMousePressed(e -> btnReplay.setStyle(btnPressedStyle));
        btnReplay.setOnMouseReleased(e -> btnReplay.setStyle(btnHoverStyle));

        btnExit.setOnMouseEntered(e -> btnExit.setStyle(btnHoverStyle));
        btnExit.setOnMouseExited(e -> btnExit.setStyle(btnStyle));
        btnExit.setOnMousePressed(e -> btnExit.setStyle(btnPressedStyle));
        btnExit.setOnMouseReleased(e -> btnExit.setStyle(btnHoverStyle));

        btnReplay.setOnAction(e -> {
            stage.close();
            MainMenuPane newMenu = new MainMenuPane(stage);
            stage.setScene(new Scene(newMenu, 1000, 700));
            stage.show();
        });

        btnExit.setOnAction(e -> {
            stage.close();
        });
    }

    public void getWinnerLabel(ArrayList<Player> players, VBox container) {
        container.getChildren().clear();

        String titleStyle =
                "-fx-font-size: 22px; -fx-font-family: \"Courier New\"; " +
                        "-fx-text-fill: white; -fx-text-alignment: center;";

        if (players.size() == 1) {
            Label lbl = new Label(players.get(0).getName() + " a gagné !");
            lbl.setStyle(titleStyle);
            container.getChildren().add(lbl);
        } else {
            Label title = new Label("The winners are :");
            title.setStyle(titleStyle);
            container.getChildren().add(title);
            for (Player player : players) {
                Label lbl = new Label("• " + player.getName());
                lbl.setStyle(titleStyle);
                container.getChildren().add(lbl);
            }
        }
    }
}