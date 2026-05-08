package latice.ihm.view;

import javafx.scene.layout.GridPane;
import latice.ihm.controller.TileController;
import latice.model.Board;
import latice.model.Player;
import latice.model.Position;

public class BoardPane extends GridPane {

    public BoardPane(Board board, TileController tileController, Player player) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Position position = new Position(i, j);
                SquarePane squarePane = new SquarePane(board.getSquare(position), position, tileController, player);
                add(squarePane, j, i);
            }
        }
    }
}