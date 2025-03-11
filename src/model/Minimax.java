package model;

import java.util.List;

public class Minimax {
    private static final int MAX_DEPTH = 3;

    public Move findBestMove(Board board) {
        int bestValue = Integer.MIN_VALUE;
        Move bestMove = null;
        List<Move> moves = board.generateMoves();

        for (Move move : moves) {
            char captured = board.getBoard()[move.toRow][move.toCol];
            board.makeMove(move);
            int value = minimax(board, 0, false);
            board.undoMove(move, captured);
            if (value > bestValue) {
                bestValue = value;
                bestMove = move;
            }
        }
        return bestMove;
    }

    private int minimax(Board board, int depth, boolean isMaximizing) {
        if (depth == MAX_DEPTH || board.isGameOver()) {
            return board.evaluate();
        }

        List<Move> moves = board.generateMoves();
        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (Move move : moves) {
                char captured = board.getBoard()[move.toRow][move.toCol];
                board.makeMove(move);
                int eval = minimax(board, depth + 1, false);
                board.undoMove(move, captured);
                maxEval = Math.max(maxEval, eval);
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Move move : moves) {
                char captured = board.getBoard()[move.toRow][move.toCol];
                board.makeMove(move);
                int eval = minimax(board, depth + 1, true);
                board.undoMove(move, captured);
                minEval = Math.min(minEval, eval);
            }
            return minEval;
        }
    }
}