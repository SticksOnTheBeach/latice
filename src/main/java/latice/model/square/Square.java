package latice.model.square;
import latice.model.Position;
import latice.model.tile.Tile;

public class Square {

    private Position position;
    private Tile Tile;
    private SquareType type;
    public Square(Position position, Tile tile, SquareType type) {
        this.position = position;
        Tile = tile;
        this.type = type;
    }
    public Square(Position position, SquareType type) {
        this.position = position;
        this.type = type;
    }

}
