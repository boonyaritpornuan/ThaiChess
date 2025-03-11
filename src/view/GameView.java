package view;

import model.Board;
import model.Move;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameView extends JFrame {
    private final int TILE_SIZE = 60;
    private Board board;
    private JPanel boardPanel;
    private int selectedRow = -1, selectedCol = -1;
    private List<Move> legalMoves;
    private controller.GameController controller;

    public GameView(Board board) {
        this.board = board;
        setTitle("Thai Chess (Makruk)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        initializeBoardPanel();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeBoardPanel() {
        boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBoard(g);
            }
        };
        boardPanel.setPreferredSize(new Dimension(8 * TILE_SIZE, 8 * TILE_SIZE));
        boardPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                int row = 7 - (e.getY() / TILE_SIZE); // กลับด้าน: แถว 7 ล่าง, 0 บน
                int col = e.getX() / TILE_SIZE;
                System.out.println("Mouse clicked at: (" + row + ", " + col + ")");
                handleClick(row, col);
            }
        });
        add(boardPanel, BorderLayout.CENTER);
    }

    private void drawBoard(Graphics g) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int displayRow = 7 - i; // กลับด้าน: แถว 7 วาดล่าง
                g.setColor((i + j) % 2 == 0 ? Color.WHITE : Color.GRAY);
                g.fillRect(j * TILE_SIZE, displayRow * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                if (selectedRow == i && selectedCol == j) {
                    g.setColor(Color.YELLOW);
                    g.fillRect(j * TILE_SIZE, displayRow * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
                if (legalMoves != null) {
                    for (Move move : legalMoves) {
                        if (move != null && move.toRow == i && move.toCol == j) {
                            g.setColor(Color.GREEN);
                            g.fillRect(j * TILE_SIZE, displayRow * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                        }
                    }
                }
                char piece = board.getBoard()[i][j];
                if (piece != ' ') {
                    g.setColor(Color.BLACK);
                    Font font = new Font("Arial", Font.BOLD, 20);
                    g.setFont(font);
                    g.drawString(String.valueOf(piece), j * TILE_SIZE + TILE_SIZE / 2 - 5, displayRow * TILE_SIZE + TILE_SIZE / 2 + 5);
                }
            }
        }
    }

    private void handleClick(int row, int col) {
        System.out.println("Handling click at: (" + row + ", " + col + ")");
        if (selectedRow == -1) {
            char piece = board.getBoard()[row][col];
            System.out.println("Piece at clicked position: " + piece);
            if (piece != ' ' && board.isWhiteTurn() == Character.isLowerCase(piece)) {
                selectedRow = row;
                selectedCol = col;
                legalMoves = board.getLegalMoves(row, col);
                System.out.println("Legal moves: " + legalMoves);
                boardPanel.repaint();
            } else {
                System.out.println("No valid piece to select or not your turn.");
            }
        } else {
            Move move = new Move(selectedRow, selectedCol, row, col);
            System.out.println("Attempting move: " + move);
            if (legalMoves != null && legalMoves.contains(move)) {
                if (controller != null) {
                    controller.handlePlayerMove(selectedRow, selectedCol, row, col);
                    System.out.println("Move executed.");
                } else {
                    System.out.println("Controller is null!");
                }
            } else {
                System.out.println("Invalid move: Not in legal moves.");
            }
            selectedRow = -1;
            selectedCol = -1;
            legalMoves = null;
            boardPanel.repaint();
        }
    }

    public void updateBoard() {
        boardPanel.repaint();
    }

    public void setController(controller.GameController controller) {
        this.controller = controller;
        System.out.println("Controller set: " + (controller != null));
    }
}