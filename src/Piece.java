
public abstract class Piece {
    protected String color;
    protected String symbol;

    public Piece(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public String getSymbol() {
        return symbol;
    }

    // Abstract method to check if a move is valid
    public abstract boolean isValidMove(int fromX, int fromY, int toX, int toY, Board board);
}
