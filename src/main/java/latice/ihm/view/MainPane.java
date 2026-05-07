package latice.ihm.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import latice.model.Board;

public class MainPane extends BorderPane {
	protected Label lblNbTours;
	protected int nbTours;
	
	public MainPane(Board board) {
	    lblNbTours = new Label("Tour : 0");

	    // BoardPane boardPane = new BoardPane(board);

	    setTop(lblNbTours);
	    setCenter(this);
	    BorderPane.setAlignment(this, Pos.CENTER);
	    BorderPane.setMargin(this, new Insets(20));
	}
}
