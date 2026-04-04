package ru.nsu.a.maslova1.snake.logic;

import ru.nsu.a.maslova1.snake.model.Point;

import java.util.ArrayList;

public class Collision {

    public boolean collisionTail(ArrayList<Point> snake) {
        Point head = snake.get(0);
        for (int i = 1; i < snake.size(); i++) {
            Point p = snake.get(i);

            if (p.equals(head)) {
                return true;
            }
        }
        return false;
    }

    public boolean collisionWall(Point head, int cols, int rows) {
        int x = head.getX();
        int y = head.getY();
        return x < 0 || x >= cols || y < 0 || y >= rows;
    }
}
