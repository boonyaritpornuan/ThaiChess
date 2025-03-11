public class Rook extends Piece {
    
    public Rook(String color) {
        super(color);
        this.symbol = color.equals("white") ? "R" : "r";
    }

    @Override
    public boolean isValidMove(int fromX, int fromY, int toX, int toY, Board board) {
        // Rook moves in straight lines (horizontally or vertically)
        return fromX == toX || fromY == toY;
    }
}


