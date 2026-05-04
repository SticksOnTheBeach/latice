package latice.model.square;
import latice.model.Position;
import latice.model.tile.Tile;

public class Square {

    private Tile Tile;
    private SquareType type;
    public Square(Tile tile, SquareType type) {
        Tile = tile;
        this.type = type;
    }
    public Square( SquareType type) {
        this.type = type;
    }

}
