
public class Coordinate {

    public int x;
    public int y;
    public Double distance;

    public Coordinate() {
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(Double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "[x, y]: [" + x + ", " + y + "] _ distance to the sample: " + distance;
    }
}
