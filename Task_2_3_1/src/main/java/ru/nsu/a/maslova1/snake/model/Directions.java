package ru.nsu.a.maslova1.snake.model;

public enum Directions {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private int x;
    private int y;

    Directions(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isOpposite(Directions other) {
        return (this == DOWN && other == UP)
                || (this == UP && other == DOWN)
                || (this == LEFT && other == RIGHT)
                || (this == RIGHT && other == LEFT);
    }
}
