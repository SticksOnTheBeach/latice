package latice.ihm.view;

import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import latice.model.tile.Tile;

public class TileView extends StackPane {

    private static final int SIZE = 50;
    private Tile tile;

    public TileView(Tile tile) {
        this.tile = tile;

        Rectangle rect = new Rectangle(SIZE, SIZE);
        rect.setFill(getTileColor(tile));
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(1.5);

     // -- TILES NAMES -- "EN GROS" : We retrieve the tile names and keep only the first two letters with the substring() method
        Label label = new Label(tile.getShape().name().substring(0, 2));
        label.setTextFill(Color.WHITE);

        getChildren().addAll(rect, label);

        // DRAG AND DROP
        setOnDragDetected(event -> {
            Dragboard db = startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(tile.getColor().name() + "," + tile.getShape().name());
            db.setContent(content);
            event.consume();
        });
    }

    public Tile getTile() {
        return tile;
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