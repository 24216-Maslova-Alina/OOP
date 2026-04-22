package ru.nsu.a.maslova1.snake.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Проверка столкновений змейки с препятствиями и самой собой.
 */
public class Collision {

    /**
     * Проверяет столкновение головы змейки с её телом.
     *
     * @param snake список точек змейки
     *
     * @return true, если голова столкнулась с телом
     */
    public boolean collisionTail(ArrayList<Point> snake) {
        Point head = snake.get(0);
        for(int i = 1; i < snake.size(); i++) {
            Point p = snake.get(i);

            if(p.equals(head)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Проверяет выход змейки за границы поля.
     *
     * @param head координаты головы
     * @param cols ширина поля
     * @param rows высота поля
     *
     * @return true, если голова вышла за границы
     */
    public boolean collisionBorder(Point head, int cols, int rows) {
        int x = head.getPointX();
        int y = head.getPointY();
        return x < 0 || x >= cols || y < 0 || y >= rows;
    }

    /**
     * Проверяет столкновение головы змейки со стенами.
     *
     * @param walls список стен
     * @param head  координаты головы
     *
     * @return true, если голова столкнулась со стеной
     */
    public boolean collisionWall(List<Point> walls, Point head) {
        for(Point w : walls) {
            if(w.equals(head)) {
                return true;
            }
        }
        return false;
    }
}