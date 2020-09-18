package touro.snake;

import java.util.Objects;

/**
 * Super class of all objects in the Garden.
 */
public class Square {
    private int x;
    private int y;

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }

    public int getY() { return y; }

    public void setX(int newX) { x = newX; }

    public void setY(int newY) { y = newY; }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Square)) return false;
        Square segment = (Square) o;
        return x == segment.x &&
                y == segment.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
