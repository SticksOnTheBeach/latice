package latice.model;

import latice.console.Console;
import latice.model.square.Square;
import latice.model.square.SquareType;
import latice.model.tile.Tile;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private Map<Position, Square> board = new HashMap<>();
    private static final int length = 9;
    private static final int width = 9;
    public Board() {};
    
    public void createGameBoard(){
    	for (int i = 0; i < length; i++) {
    		for (int j = 0; j < width; j++) {
    			board.put((new Position(i, j)), new Square(SquareType.NORMAL));
    		}
    	}
        board.put(new Position(0, 0), new Square(SquareType.SUN));
        board.put(new Position(1, 1), new Square(SquareType.SUN));
        board.put(new Position(2, 2), new Square(SquareType.SUN));
        board.put(new Position(0, 4), new Square(SquareType.SUN));
        board.put(new Position(0, 8), new Square(SquareType.SUN));
        board.put(new Position(1, 7), new Square(SquareType.SUN));
        board.put(new Position(2, 6), new Square(SquareType.SUN));
        board.put(new Position(4, 8), new Square(SquareType.SUN));
        board.put(new Position(8, 8), new Square(SquareType.SUN));
        board.put(new Position(7, 7), new Square(SquareType.SUN));
        board.put(new Position(6, 6), new Square(SquareType.SUN));
        board.put(new Position(8, 4), new Square(SquareType.SUN));
        board.put(new Position(8, 0), new Square(SquareType.SUN));
        board.put(new Position(7, 1), new Square(SquareType.SUN));
        board.put(new Position(6, 2), new Square(SquareType.SUN));
        board.put(new Position(4, 0), new Square(SquareType.SUN));
        board.put(new Position(4, 4), new Square(SquareType.MOON));
    };
    public boolean isEmpty(Position position) {
        Square square = board.get(position);
        return !square.isOccupied();
    }
    
    public boolean isBoardEmpty() {
        for (Square square : board.values()) {
            if (square.isOccupied()) {
                return false;
            }
        }
        return true;
    }
    public void placeTile(Position position, Tile tile) {
        Square square = board.get(position);
        square.setTile(tile);
    }
    public Square getSquare(Position p) {
        return board.get(p);
    }

}
