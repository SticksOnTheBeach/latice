package latice.ihm.view.menu;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WinMenu extends VBox {
    private Label lblVictory;
    private Label lblListWinners;
    private Button btnReplay = new Button("> REPLAY");
    private Button btnExit = new Button("> EXIT");

    public WinMenu(Stage stage) {
    }
}

