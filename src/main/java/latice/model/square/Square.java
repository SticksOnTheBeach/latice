package latice.model.square;
import latice.model.Position;
import latice.model.tile.Tile;

public class Square {

    private Tile tile;
    private SquareType type;
    
    public Square(Tile tile, SquareType type) {
        tile = tile;
        this.type = type;
    }
    public Square( SquareType type) {
        this.type = type;
    }

    public SquareType getType() {
        return type;
    }
    
    public void setTile(Tile tile) {
        this.tile = tile;
    }
    
    public boolean isOccupied() {
        return tile != null;
    }
    
}
