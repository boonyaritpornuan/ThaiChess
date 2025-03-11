public class Board {
    private static final int SIZE = 8; // ขนาดกระดาน 8x8
    private Piece[][] pieces; // ตารางเก็บตำแหน่งตัวหมาก

    public Board() {
        this.pieces = new Piece[SIZE][SIZE];
        initializePieces(); // ตั้งค่าตัวหมากเริ่มต้น
    }

    // ตั้งค่าตำแหน่งตัวหมากเริ่มต้นของหมากรุกไทย
    private void initializePieces() {
        // ตัวอย่าง: วางตัวหมากฝั่งขาว (ด้านล่าง)
        pieces[0][0] = new Rook("white");
        pieces[1][0] = new Knight("white");
        pieces[2][0] = new Bishop("white");
        // ... เพิ่มตัวหมากอื่นๆ ตามกฎหมากรุกไทย
    }

    // ตรวจสอบการเคลื่อนที่ของตัวหมาก
    public boolean isMoveValid(Piece piece, int fromX, int fromY, int toX, int toY) {
        // เช็คกฎการเคลื่อนที่เฉพาะของแต่ละตัวหมาก
        return piece.isValidMove(fromX, fromY, toX, toY, this);
    }

    // อัปเดตตำแหน่งตัวหมาก
    public void movePiece(int fromX, int fromY, int toX, int toY) {
        pieces[toX][toY] = pieces[fromX][fromY];
        pieces[fromX][fromY] = null;
    }

    // ตรวจสอบการจบเกม (เช่น กษัตริย์ถูกกิน)
    public boolean isGameOver() {
        // เช็คว่าฝ่ายใดชนะตามกฎหมากรุกไทย
        return false; // ต้องเขียนจริงตามกฎ
    }

    // แสดงกระดาน (สำหรับ调试)
    public void printBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(pieces[i][j] != null ? pieces[i][j].getSymbol() : " ");
            }
            System.out.println();
        }
    }
    
    // Get piece at specific position
    public Piece getPieceAt(int x, int y) {
        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
            return pieces[x][y];
        }
        return null;
    }
}