package ru.nsu.a.maslova1.snake.logic;

import ru.nsu.a.maslova1.snake.model.Point;

import java.util.ArrayList;

public class Move {
    private Directions dirCurrent;
    private Directions dirNew;
    private final Eat eat;

    public Move(Eat eat) {
        this.eat = eat;
    }

    public void move(ArrayList<Point> snake, ArrayList<Point> apple) {
        if (dirNew != null && !dirNew.isOpposite(dirCurrent)) {
            dirCurrent = dirNew;
        }

        Point head = snake.get(0);

        Point newHead = new Point(
            head.getX() + dirCurrent.getX(),
            head.getY() + dirCurrent.getY()
        );

        snake.add(0, newHead);
        if (!eat.collisionApple(newHead, apple)) {
            snake.remove(snake.size() - 1);
        }
    }

    public void setDirCurrent(Directions dir) {
        this.dirCurrent = dir;
    }

    public void setDirNew(Directions dir) {
        this.dirNew = dir;
    }
}
