

public class Difficulty {
    private int level;

    public Difficulty(int level) {
        this.level = Math.max(1, Math.min(10, level)); // ปรับระดับให้อยู่ใน 1-10
    }

    public int getDepth() {
        return level; // ความลึกในการค้นหาเท่ากับระดับความยาก
    }
}
