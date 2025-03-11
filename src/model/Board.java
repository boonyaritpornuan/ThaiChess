package model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private char[][] board; // บอร์ด 8x8
    private boolean isWhiteTurn; // ขาว = ผู้เล่น, ดำ = คอมพิวเตอร์

    public Board() {
        board = new char[8][8];
        isWhiteTurn = true;
        initializeBoard();
    }

    private void initializeBoard() {
        // ขาว (ผู้เล่น)
        board[0][0] = board[0][7] = 'r'; // เม็ด
        board[0][1] = board[0][6] = 'n'; // ม้า
        board[0][2] = board[0][5] = 'b'; // โคน
        board[0][3] = 'q'; // เรือ
        board[0][4] = 'k'; // ขุน
        for (int i = 0; i < 8; i++) board[2][i] = 'p'; // เบี้ย

        // ดำ (คอมพิวเตอร์)
        board[7][0] = board[7][7] = 'R'; // เม็ด
        board[7][1] = board[7][6] = 'N'; // ม้า
        board[7][2] = board[7][5] = 'B'; // โคน
        board[7][3] = 'Q'; // เรือ
        board[7][4] = 'K'; // ขุน
        for (int i = 0; i < 8; i++) board[5][i] = 'P'; // เบี้ย
    }

    public char[][] getBoard() {
        return board;
    }

    public boolean isWhiteTurn() {
        return isWhiteTurn;
    }

    public void makeMove(Move move) {
        board[move.toRow][move.toCol] = board[move.fromRow][move.fromCol];
        board[move.fromRow][move.fromCol] = ' ';
        isWhiteTurn = !isWhiteTurn;
    }

    public void undoMove(Move move, char captured) {
        board[move.fromRow][move.fromCol] = board[move.toRow][move.toCol];
        board[move.toRow][move.toCol] = captured;
        isWhiteTurn = !isWhiteTurn;
    }

    public List<Move> generateMoves() {
        List<Move> moves = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((isWhiteTurn && Character.isLowerCase(board[i][j])) ||
                    (!isWhiteTurn && Character.isUpperCase(board[i][j]))) {
                    addPieceMoves(moves, i, j);
                }
            }
        }
        return moves;
    }

    public List<Move> getLegalMoves(int row, int col) {
        List<Move> moves = new ArrayList<>();
        addPieceMoves(moves, row, col);
        return moves;
    }

    private void addPieceMoves(List<Move> moves, int row, int col) {
        char piece = board[row][col];
        boolean isWhite = Character.isLowerCase(piece);
        int[][] directions;

        switch (Character.toLowerCase(piece)) {
            case 'p': // เบี้ย
                int direction = isWhite ? -1 : 1;
                if (isValid(row + direction, col) && (board[row + direction][col] == ' ' || isEnemy(row + direction, col, isWhite))) {
                    moves.add(new Move(row, col, row + direction, col));
                }
                break;
            case 'r': // เม็ด (เหมือน Rook)
                directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
                addDirectionalMoves(moves, row, col, directions, isWhite);
                break;
            case 'n': // ม้า (เหมือน Knight)
                directions = new int[][]{{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2}};
                addJumpMoves(moves, row, col, directions, isWhite);
                break;
            case 'b': // โคน (เหมือน Bishop)
                directions = new int[][]{{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
                addDirectionalMoves(moves, row, col, directions, isWhite);
                break;
            case 'q': // เรือ (เหมือน Queen)
                directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
                addDirectionalMoves(moves, row, col, directions, isWhite);
                break;
            case 'k': // ขุน (เหมือน King)
                directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
                addJumpMoves(moves, row, col, directions, isWhite);
                break;
        }
    }

    private void addDirectionalMoves(List<Move> moves, int row, int col, int[][] directions, boolean isWhite) {
        for (int[] dir : directions) {
            int r = row + dir[0], c = col + dir[1];
            while (isValid(r, c)) {
                if (board[r][c] == ' ') {
                    moves.add(new Move(row, col, r, c));
                } else if (isEnemy(r, c, isWhite)) {
                    moves.add(new Move(row, col, r, c));
                    break;
                } else {
                    break;
                }
                r += dir[0];
                c += dir[1];
            }
        }
    }

    private void addJumpMoves(List<Move> moves, int row, int col, int[][] directions, boolean isWhite) {
        for (int[] dir : directions) {
            int r = row + dir[0], c = col + dir[1];
            if (isValid(r, c) && (board[r][c] == ' ' || isEnemy(r, c, isWhite))) {
                moves.add(new Move(row, col, r, c));
            }
        }
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    private boolean isEnemy(int row, int col, boolean isWhite) {
        return board[row][col] != ' ' && (isWhite != Character.isLowerCase(board[row][col]));
    }

    public int evaluate() {
        int score = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                char piece = board[i][j];
                switch (piece) {
                    case 'k': score += 200; break; // ขุน
                    case 'K': score -= 200; break;
                    case 'q': score += 9; break; // เรือ
                    case 'Q': score -= 9; break;
                    case 'r': score += 5; break; // เม็ด
                    case 'R': score -= 5; break;
                    case 'b': score += 3; break; // โคน
                    case 'B': score -= 3; break;
                    case 'n': score += 3; break; // ม้า
                    case 'N': score -= 3; break;
                    case 'p': score += 1; break; // เบี้ย
                    case 'P': score -= 1; break;
                }
            }
        }
        return score;
    }

    public boolean isGameOver() {
        return board[0][4] == ' ' || board[7][4] == ' '; // ขุนตาย
    }
}