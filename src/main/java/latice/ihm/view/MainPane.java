package latice.ihm.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
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

        // Rack
        setBottom(rackBox);
        rackBox.setAlignment(Pos.CENTER);
        
        // MainPane BorderPane Settings 
        
        //BorderPane.setAlignment(boardPane, Pos.CENTER);
        BorderPane.setMargin(boardPane, new Insets(20));
    }
}