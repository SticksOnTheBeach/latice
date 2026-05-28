package latice.model;

import java.util.Objects;

public class Position {
    private int row;
    private int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(col, row);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Position other = (Position) obj;
        return Objects.equals(col, other.col) && Objects.equals(row, other.row);
    }
    private Position[] getAdjacentPositions(Position position) {
        return new Position[] { new Position(position.getPositionUp(), position.getCol()),
                new Position(position.getPositionDown(), position.getCol()),
                new Position(position.getRow(), position.getPositionLeft()),
                new Position(position.getRow(), position.getPositionRight())
        };
    }
    public int getPositionRight() {
    	return col+1;
    }
    
    public int getPositionLeft() {
    	return col-1;
    }
    
    public int getPositionUp() {
    	return row+1;
    }
    
    public int getPositionDown() {
    	return row-1;
    }
}
