package latice.model;

import java.util.Objects;

public class Position {
    private int row;
    private int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
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
    public Position[] getAdjacentPositions() {
        return new Position[]{
                new Position(row - 1, col), // haut
                new Position(row + 1, col), // bas
                new Position(row, col - 1), // gauche
                new Position(row, col + 1)  // droite
        };
    }
}
