package latice.ihm.view;

import javafx.scene.layout.HBox;
import latice.model.Rack;
import latice.model.tile.Tile;

public class RackBox extends HBox {

    public RackBox(Rack rack) {
        setSpacing(8);
        for (Tile tile : rack.getTiles()) {
            getChildren().add(new TileView(tile));
        }
    }
}
