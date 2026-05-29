package latice.ihm.view.menu;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import latice.model.Player;

import java.util.ArrayList;

public class WinMenu extends BorderPane {

    private Label lblVictory;

    private Button btnReplay = new Button("> REPLAY");
    private Button btnExit = new Button("> EXIT");
    private BorderPane mainPane;

    public WinMenu(Stage stage, ArrayList<Player> winners) {
        mainPane = new BorderPane();

        lblVictory = new Label("Victoire !");
        VBox winnersBox = new VBox(8);
        winnersBox.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(winnersBox, Pos.CENTER);
        getWinnerLabel(winners, winnersBox);
        mainPane.setTop(lblVictory);
        mainPane.setCenter(winnersBox);
        mainPane.setBottom(new VBox(btnReplay, btnExit));
        this.getChildren().add(mainPane);
        mainPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-padding: 20px; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        getWinnerLabel(winners, winnersBox);

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
        btnExit.setOnMousePressed(e -> btnReplay.setStyle(btnPressedStyle));
        btnExit.setOnMouseReleased(e -> btnExit.setStyle(btnHoverStyle));


        btnReplay.setOnAction(e -> {
             // TODO : relancer une partie
         });
         btnExit.setOnAction(e -> {
             stage.close();
         });
    }public void getWinnerLabel(ArrayList<Player> players, VBox container) {
        container.getChildren().clear();

        String titleStyle =
                "-fx-font-size: 22px; -fx-font-family: \"Courier New\"; " +
                        "-fx-text-fill: white; -fx-text-alignment: center;";

        if (players.size() == 1) {
            Label lbl = new Label(players.get(0).getName() + " a gagné !");
            lbl.setStyle(titleStyle);
            container.getChildren().add(lbl);
        } else {
            Label title = new Label("Les gagnants sont :");
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


