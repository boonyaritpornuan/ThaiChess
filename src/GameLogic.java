
public class GameLogic {
    private Board board;
    private String currentPlayer;
    private MinimaxAI ai;

    public GameLogic() {
        this.board = new Board();
        this.currentPlayer = "white"; // White starts first
        this.ai = new MinimaxAI(new Difficulty(3));
    }

    public boolean makeMove(int fromX, int fromY, int toX, int toY) {
        Piece piece = getPieceAt(fromX, fromY);
        
        // Check if there's a piece at the source position and it belongs to the current player
        if (piece == null || !piece.getColor().equals(currentPlayer)) {
            return false;
        }
        
        // Check if the move is valid according to the piece's rules
        if (!board.isMoveValid(piece, fromX, fromY, toX, toY)) {
            return false;
        }
        
        // Execute the move
        board.movePiece(fromX, fromY, toX, toY);
        
        // Switch player
        currentPlayer = currentPlayer.equals("white") ? "black" : "white";
        
        return true;
    }

    public Piece getPieceAt(int x, int y) {
        return board.getPieceAt(x, y);
    }

    public void aiMove() {
        if (currentPlayer.equals("black")) {
            Move bestMove = ai.getBestMove(board);
            if (bestMove != null) {
                makeMove(bestMove.getFromX(), bestMove.getFromY(), bestMove.getToX(), bestMove.getToY());
            }
        }
    }

    public boolean isGameOver() {
        return board.isGameOver();
    }

    public void printBoard() {
        board.printBoard();
    }
}
