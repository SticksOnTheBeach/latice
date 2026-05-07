package latice.ihm.view;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import latice.model.square.Square;

public class SquarePane extends StackPane {

    private static final int SIZE = 60;

    public SquarePane(Square square) {
        Rectangle rect = new Rectangle(SIZE, SIZE);

        switch (square.getType()) {
            case SUN:
                rect.setFill(Color.GOLD);
                break;
            case MOON:
                rect.setFill(Color.MEDIUMPURPLE);
                break;
            case NORMAL:
            default:
                rect.setFill(Color.LIGHTGRAY);
                break;
        }

        rect.setStroke(Color.DARKGRAY);
        rect.setStrokeWidth(1);

        Label symbol = new Label(square.getType().getSymbol());
        symbol.setTextFill(Color.BLACK);
        symbol.setStyle("-fx-font-size: 20px;");

        getChildren().addAll(rect, symbol);
    }
}