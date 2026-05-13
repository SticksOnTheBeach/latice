package latice.ihm.view.model;

import javafx.scene.layout.GridPane;
import latice.ihm.controller.GameController;
import latice.ihm.controller.RoundController;
import latice.ihm.controller.TileController;
import latice.ihm.view.MainPane;
import latice.model.Board;
import latice.model.Position;

public class BoardPane extends GridPane {

    // On passe mainPane pour que SquarePane puisse appeler
    // rafraichirRackJoueur() et updateDisplay() après un placement réussi
    public BoardPane(Board board, TileController tileController, RoundController roundController, MainPane mainPane, GameController gameController) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Position position = new Position(i, j);
                SquarePane squarePane = new SquarePane(board.getSquare(position), position, tileController, roundController, mainPane, gameController);
                add(squarePane, j, i);
            }
        }
    }
}