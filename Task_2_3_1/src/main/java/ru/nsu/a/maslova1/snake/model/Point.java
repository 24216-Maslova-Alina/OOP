package ru.nsu.a.maslova1.snake.model;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Point other = (Point) obj;
        return x == other.x && y == other.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
