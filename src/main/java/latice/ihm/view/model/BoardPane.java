package latice.ihm.view.model;

import javafx.scene.layout.GridPane;
import latice.ihm.controller.TileController;
import latice.ihm.view.MainPane;
import latice.model.Board;
import latice.model.Game;
import latice.model.Player;
import latice.model.Position;

public class BoardPane extends GridPane {
    public BoardPane(Board board, TileController tileController, Player player, Game game, MainPane mainPane) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Position position = new Position(i, j);
                SquarePane squarePane = new SquarePane(board.getSquare(position), position, tileController, player, game, mainPane);
                add(squarePane, j, i);
            }
        }
    }
}