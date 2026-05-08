package latice.ihm.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import latice.model.Board;
import latice.model.Rack;

public class MainPane extends BorderPane {
    protected Label lblNbTours;
    protected int nbTours;

    public MainPane(Board board, Rack rack) {
        lblNbTours = new Label("Tour :");

        BoardPane boardPane = new BoardPane(board);
        RackBox rackBox = new RackBox(rack);
        
        // Tours
        setTop(lblNbTours);
        setAlignment(lblNbTours, Pos.CENTER);
        lblNbTours.setStyle("-fx-font-size: 20px; -fx-font-family: \"Arial\"; -fx-font-weight: bold;");
        
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
        
        // MainPane BorderPane Settings 
        
        //BorderPane.setAlignment(boardPane, Pos.CENTER);
        BorderPane.setMargin(boardPane, new Insets(20));
    }
}