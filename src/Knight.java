
public class Knight extends Piece {
    
    public Knight(String color) {
        super(color);
        this.symbol = color.equals("white") ? "N" : "n";
    }

    @Override
    public boolean isValidMove(int fromX, int fromY, int toX, int toY, Board board) {
        // Knight moves in L-shape: 2 squares in one direction and 1 square perpendicular
        int deltaX = Math.abs(toX - fromX);
        int deltaY = Math.abs(toY - fromY);
        return (deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2);
    }
}
