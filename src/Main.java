import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // สร้างหน้าต่างหลักของเกม
        JFrame frame = new JFrame("Thai Chess Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        
        // สร้างแผงสำหรับวาดกระดานหมากรุก
        ChessBoardPanel boardPanel = new ChessBoardPanel();
        frame.add(boardPanel);
        
        frame.setVisible(true);
        
        System.out.println("Thai Chess Game Started!");
    }
}

class ChessBoardPanel extends JPanel {
    private List<ChessPiece> pieces;

    public ChessBoardPanel() {
        pieces = new ArrayList<>();
        // เพิ่มชิ้นหมากรุกตัวอย่าง
        pieces.add(new ChessPiece(0, 0, Color.BLACK));
        pieces.add(new ChessPiece(7, 7, Color.WHITE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // วาดกระดานหมากรุก
        int tileSize = 80;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 == 0) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.GRAY);
                }
                g.fillRect(col * tileSize, row * tileSize, tileSize, tileSize);
            }
        }
        // วาดชิ้นหมากรุก
        for (ChessPiece piece : pieces) {
            piece.draw(g, tileSize);
        }
    }
}

class ChessPiece {
    private int row;
    private int col;
    private Color color;

    public ChessPiece(int row, int col, Color color) {
        this.row = row;
        this.col = col;
        this.color = color;
    }

    public void draw(Graphics g, int tileSize) {
        g.setColor(color);
        g.fillOval(col * tileSize, row * tileSize, tileSize, tileSize);
    }
}
