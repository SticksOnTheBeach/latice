package latice.model.tile;

public class Tile {
    private COLOR color;
    private SHAPE shape;

    /**
     * Initializes a new Tile with a specific color and shape.
     * 
     * @param color The color of the tile.
     * @param shape The shape of the tile.
     */
    public Tile(COLOR color, SHAPE shape) {
        this.color = color;
        this.shape = shape;
    }
    
    /**
     * @return The color of the tile.
     */
    public COLOR getColor() {
    	return color;
    }
    
    /**
     * @return The shape of the tile.
     */
    public SHAPE getShape() {
    	return shape;
    }

    @Override
    public String toString() {
        return "tile [" + shape + ", " + color + "]";
    }
}