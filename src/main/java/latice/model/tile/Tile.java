package latice.model.tile;

public class Tile {
    COLOR color;
    SHAPE shape;

    public Tile(COLOR color, SHAPE shape) {
        this.color = color;
        this.shape = shape;
    }
    @Override

    public String toString() {
        return "tile " + " [" + shape + ", " + color + "]";
    }
}
