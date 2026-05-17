package latice.ihm.view.model;

import javafx.scene.control.Label;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import latice.ihm.controller.GameController;
import latice.ihm.controller.RoundController;
import latice.ihm.controller.TileController;
import latice.ihm.view.MainPane;
import latice.model.Player;
import latice.model.Position;
import latice.model.square.Square;

public class SquarePane extends StackPane {

    private static final int SIZE = 60;
    private Position position;
    private Square square;
    private boolean isOccupied = false;
    private GameController gameController;

    // on ajoute mainPane en paramètre pour pouvoir appeler
    // rafraichirRackJoueur() et updateDisplay() après un placement réussi
    public SquarePane(Square square, Position position, TileController tileController, RoundController roundController, MainPane mainPane, GameController gameController) {
        this.square = square;
        this.position = position;
        this.gameController = gameController;

        Rectangle rect = new Rectangle(SIZE, SIZE);

        switch (square.getType()) {
            case SUN:    rect.setFill(Color.GOLD);          break;
            case MOON:   rect.setFill(Color.MEDIUMPURPLE);  break;
            case NORMAL: default: rect.setFill(Color.LIGHTGRAY); break;
        }

        rect.setStroke(Color.DARKGRAY);
        rect.setStrokeWidth(1);

        Label symbol = new Label(square.getType().getSymbol());
        symbol.setTextFill(Color.BLACK);
        symbol.setStyle("-fx-font-size: 20px;");

        getChildren().addAll(rect, symbol);

        setOnDragOver(event -> {
            if (!isOccupied && event.getGestureSource() instanceof TileView && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        setOnDragDropped(event -> {
            System.out.println("DragDropped sur " + position.getRow() + "," + position.getCol());
            if (!isOccupied) {
                TileView tileView = (TileView) event.getGestureSource();
                boolean success = gameController.playTile(tileView.getTile(), position);                
                if (success) {
                    placeTile(tileView);
                    event.setDropCompleted(true);
                } else {
                    event.setDropCompleted(false);
                }
            }
            event.consume();
        });
    }

    private void placeTile(TileView tileView) {
        getChildren().clear();
        getChildren().add(tileView);
        isOccupied = true;
        tileView.setOnDragDetected(event -> event.consume());
    }

    public Position getPosition() {
        return position;
    }
}