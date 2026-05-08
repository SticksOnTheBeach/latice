package latice.ihm.view;

import javafx.scene.layout.GridPane;
import latice.model.Board;
import latice.model.Position;

public class BoardPane extends GridPane {

    public BoardPane(Board board) {
    	// for each square of the board, add a squarePane 
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Position position = new Position(i, j);
                SquarePane squarePane = new SquarePane(board.getSquare(position), position);
                add(squarePane, j, i);
            }
        }
    }
}