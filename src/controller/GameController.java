package controller;

import model.Board;
import model.Move;
import model.Minimax;
import view.GameView;
import java.util.List; // เพิ่มการนำเข้า List

public class GameController {
    private Board board;
    private GameView view;
    private Minimax minimax;

    public GameController(GameView view, Board board) {
        this.view = view;
        this.board = board;
        this.minimax = new Minimax();
        view.setController(this);
    }

    public void handlePlayerMove(int fromRow, int fromCol, int toRow, int toCol) {
        try {
            Move move = new Move(fromRow, fromCol, toRow, toCol);
            List<Move> legalMoves = board.getLegalMoves(fromRow, fromCol);
            System.out.println("Controller: Checking move " + move + " against " + legalMoves);
            if (legalMoves != null && legalMoves.contains(move)) {
                board.makeMove(move);
                System.out.println("Controller: Player move executed: " + move);
                view.updateBoard();
                if (!board.isGameOver()) {
                    computerMove();
                }
            } else {
                System.out.println("Controller: Invalid move rejected.");
            }
        } catch (Exception e) {
            System.err.println("Error in handlePlayerMove: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void computerMove() {
        try {
            Move bestMove = minimax.findBestMove(board);
            if (bestMove != null) {
                board.makeMove(bestMove);
                System.out.println("Controller: Computer move executed: " + bestMove);
                view.updateBoard();
            } else {
                System.out.println("Controller: No valid move for computer.");
            }
        } catch (Exception e) {
            System.err.println("Error in computerMove: " + e.getMessage());
            e.printStackTrace();
        }
    }
}