
public class Bishop extends Piece {
    
    public Bishop(String color) {
        super(color);
        this.symbol = color.equals("white") ? "B" : "b";
    }

    @Override
    public boolean isValidMove(int fromX, int fromY, int toX, int toY, Board board) {
        // Bishop moves diagonally
        int deltaX = Math.abs(toX - fromX);
        int deltaY = Math.abs(toY - fromY);
        return deltaX == deltaY;
    }
}
