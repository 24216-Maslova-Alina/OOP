package ru.nsu.a.maslova1.snake.model;

/**
 * Точка на игровом поле с координатами X и Y.
 */
public class Point {
    private int pointX;
    private int pointY;

    /**
     * Создаёт точку с заданными координатами.
     *
     * @param x координата X
     * @param y координата Y
     */
    public Point (int x, int y) {
        this.pointX = x;
        this.pointY = y;
    }

    /**
     * Сравнивает две точки на равенство координат.
     *
     * @param obj объект для сравнения
     *
     * @return true, если координаты совпадают
     */
    @Override
    public boolean equals (Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass () != obj.getClass ()) {
            return false;
        }

        Point other = (Point) obj;
        return pointX == other.pointX && pointY == other.pointY;
    }

    /**
     * Возвращает x.
     *
     * @return координата X
     */
    public int getPointX () {
        return pointX;
    }

    /**
     * Возвращает y.
     *
     * @return координата Y
     */
    public int getPointY () {
        return pointY;
    }
}