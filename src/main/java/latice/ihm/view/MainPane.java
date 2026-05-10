package latice.ihm.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import latice.ihm.controller.RoundController;
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
    
    private RoundController roundController;
    private HBox bottomContainer;

    public MainPane(Board board, Game game, TileController tileController, RoundController roundController) {
        this.game = game;
        this.roundController = roundController;
        	
        lblNbRound = new Label("Round : " + nbRound);
        lblPlayerRound = new Label("Player Turn :");
        
        lblNbRound.setStyle("-fx-text-fill: white;");
        lblPlayerRound.setStyle("-fx-text-fill: white;");

        Player currentPlayer = roundController.getCurrentPlayer();

        BoardPane boardPane = new BoardPane(board, tileController, roundController);
        RackBox rackBox = new RackBox(currentPlayer.getRack());
        HBox hbTop = new HBox(20);
        
        // Round
        hbTop.setPadding(new Insets(15, 0, 50, 0));
        hbTop.getChildren().addAll(lblPlayerRound, lblNbRound);
        hbTop.setAlignment(Pos.CENTER);
        setTop(hbTop);
        setAlignment(hbTop, Pos.CENTER);
        hbTop.setStyle("-fx-font-size: 20px; -fx-font-family: \"Arial\"; -fx-font-weight: bold;");
        
        // --- BOUTON DE TEST  ---
        Button btnNextTurn = new Button("Fin de tour");
        btnNextTurn.setStyle("-fx-background-color: #a8d8a8; -fx-cursor: hand; -fx-font-weight: bold;");
        btnNextTurn.setOnAction(e -> {
            // Quand on clique, on change de joueur et on rafraîchit l'écran
            this.roundController.nextPlayerTurn();
            updateDisplay(); 
        });
        hbTop.getChildren().add(btnNextTurn);
        
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
        /*HBox bottomContainer = new HBox();
        bottomContainer.setAlignment(Pos.CENTER);
        bottomContainer.setPadding(new Insets(15, 0, 50, 0));
        bottomContainer.getChildren().add(rackBox);
        setBottom(bottomContainer);*/
        
        // --- Rack ---
        bottomContainer = new HBox();
        bottomContainer.setAlignment(Pos.CENTER);
        bottomContainer.setPadding(new Insets(15, 0, 50, 0));
        setBottom(bottomContainer);
        
        BorderPane.setMargin(boardPane, new Insets(20));
        updateDisplay();
    }
    
    public void updateDisplay() {
        Player current = roundController.getCurrentPlayer();
        lblPlayerRound.setText("Player Turn : " + current.getName());
        bottomContainer.getChildren().clear(); 
        RackBox newRack = new RackBox(current.getRack());
        bottomContainer.getChildren().add(newRack);
    }
}