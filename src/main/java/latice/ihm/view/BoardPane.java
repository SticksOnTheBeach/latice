package latice.ihm.view;

import javafx.scene.layout.GridPane;
import latice.model.Board;
import latice.model.Position;

public class BoardPane extends GridPane {

    public BoardPane(Board board) {
        // For each square of the board, add a SquarePane
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                SquarePane sp = new SquarePane(board.getSquare(new Position(i, j)));
                add(sp, j, i); // col, row
            }
        }
    }
}
