package latice.ihm.view.model;

import javafx.scene.layout.GridPane;
import latice.ihm.controller.RoundController; // N'oublie pas l'import
import latice.ihm.controller.TileController;
import latice.model.Board;
import latice.model.Position;

public class BoardPane extends GridPane {

    // On remplace Player par RoundController
    public BoardPane(Board board, TileController tileController, RoundController roundController) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Position position = new Position(i, j);
                // On passe le RoundController à chaque case
                SquarePane squarePane = new SquarePane(board.getSquare(position), position, tileController, roundController);
                add(squarePane, j, i);
            }
        }
    }
}