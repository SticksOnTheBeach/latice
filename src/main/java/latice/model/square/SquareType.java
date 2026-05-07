package latice.model.square;

public enum SquareType {
    SUN( "☼"),
    NORMAL("."),
    MOON( "☾");
    private final String symbol;

    SquareType(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}