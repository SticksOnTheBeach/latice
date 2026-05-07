package latice.ihm.view;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import latice.model.tile.Tile;

public class TileView extends StackPane {

    private static final int SIZE = 50;

    public TileView(Tile tile) {
        Rectangle rect = new Rectangle(SIZE, SIZE);
        rect.setFill(getTileColor(tile));
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(1.5);

        Label label = new Label(tile.getShape().name().substring(0, 2));
        label.setTextFill(Color.WHITE);

        getChildren().addAll(rect, label);
    }

    private Color getTileColor(Tile tile) {
        switch (tile.getColor()) {
            case YELLOW:   return Color.GOLD;
            case NAVY:     return Color.NAVY;
            case MAGENTA:  return Color.MAGENTA;
            case REDGREEN: return Color.DARKGREEN;
            case TEAL:     return Color.TEAL;
            default:       return Color.GRAY;
        }
    }
}