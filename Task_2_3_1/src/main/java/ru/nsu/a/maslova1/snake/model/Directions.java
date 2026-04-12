package ru.nsu.a.maslova1.snake.model;

/**
 * Направления движения змейки.
 */
public enum Directions {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private int pointX;
    private int pointY;

    /**
     * Создаёт направление с заданным смещением.
     * @param x смещение по X
     * @param y смещение по Y
     */
    Directions(int x, int y) {
        this.pointX = x;
        this.pointY = y;
    }

    /**
     * Возвращает x.
     * @return смещение по X
     */
    public int getPointX() {
        return pointX;
    }

    /**
     * Возвращает y.
     * @return смещение по Y
     */
    public int getPointY() {
        return pointY;
    }

    /**
     * Проверяет, является ли переданное направление противоположным текущему.
     * @param other другое направление
     * @return true, если направления противоположны
     */
    public boolean isOpposite(Directions other) {
        return (this == DOWN && other == UP)
                || (this == UP && other == DOWN)
                || (this == LEFT && other == RIGHT)
                || (this == RIGHT && other == LEFT);
    }
}