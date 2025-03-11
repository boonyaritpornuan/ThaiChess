
public class Move {
    private int fromX;
    private int fromY;
    private int toX;
    private int toY;
    private int score;

    public Move(int fromX, int fromY, int toX, int toY) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }

    public Move(int fromX, int fromY, int toX, int toY, int score) {
        this(fromX, fromY, toX, toY);
        this.score = score;
    }

    public int getFromX() {
        return fromX;
    }

    public int getFromY() {
        return fromY;
    }

    public int getToX() {
        return toX;
    }

    public int getToY() {
        return toY;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
