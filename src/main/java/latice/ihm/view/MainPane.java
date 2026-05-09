package latice.ihm.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import latice.ihm.controller.TileController;
import latice.ihm.view.model.BoardPane;
import latice.ihm.view.model.RackBox;
import latice.model.Board;
import latice.model.Game; // N'oublie pas l'import !
import latice.model.Player;

public class MainPane extends BorderPane {
    protected Label lblNbRound;
    protected int nbRound = 1;
    protected Label lblPlayerRound;
    private Game game; 

    public MainPane(Board board, Game game, TileController tileController) {
        this.game = game;

        lblNbRound = new Label("Round : " + nbRound);
        lblPlayerRound = new Label("Player Turn :");
        
        lblNbRound.setStyle("-fx-text-fill: white;");
        lblPlayerRound.setStyle("-fx-text-fill: white;");

        // On demande au jeu : Qui joue en premier ?
        Player currentPlayer = game.getCurrentPlayer();
        BoardPane boardPane = new BoardPane(board, tileController, currentPlayer);
        RackBox rackBox = new RackBox(currentPlayer.getRack());
        
        HBox hbTop = new HBox(20);
        
        // Round
        hbTop.setPadding(new Insets(15, 0, 50, 0));
        hbTop.getChildren().addAll(lblPlayerRound, lblNbRound);
        hbTop.setAlignment(Pos.CENTER);
        setTop(hbTop);
        setAlignment(hbTop, Pos.CENTER);
        hbTop.setStyle("-fx-font-size: 20px; -fx-font-family: \"Arial\"; -fx-font-weight: bold;");
        
        // Board
        setCenter(boardPane);
        boardPane.setAlignment(Pos.CENTER);
        
        // --- Rack ---
        // OLD //
        /* Rack
        setBottom(rackBox);
        rackBox.setAlignment(Pos.CENTER);
        rackBox.setPadding(new Insets(0, 50, 10, 50));*/

        
        // NEW //
        // On crée un conteneur pour empêcher le BorderPane 
        // d'étirer RackBox sur toute la largeur
        HBox bottomContainer = new HBox();
        bottomContainer.setAlignment(Pos.CENTER);
        bottomContainer.setPadding(new Insets(15, 0, 50, 0));
        bottomContainer.getChildren().add(rackBox);
        setBottom(bottomContainer);
        
        BorderPane.setMargin(boardPane, new Insets(20));
        updateDisplay();
    }
    
    public void updateDisplay() {
        Player current = game.getCurrentPlayer();
        lblPlayerRound.setText("Player Turn : " + current.getName());
    }
}