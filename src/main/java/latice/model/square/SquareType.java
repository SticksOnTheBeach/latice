package latice.model.square;

public enum SquareType {
    SUN("☼", "/images/bg_sun.png"),
    NORMAL(".", "/images/bg_sea.png"),
    MOON("☾", "/images/bg_moon.png");

    private final String symbol;
    private final String imagePath;

    SquareType(String symbol, String imagePath) {
        this.symbol = symbol;
        this.imagePath = imagePath;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getImagePath() {
        return imagePath;
    }
}