package ru.nsu.a.maslova1.snake.model;

import java.util.ArrayList;

/**
 * Логика движения змейки по полю.
 */
public class Move {
    private Directions dirCurrent;
    private Directions dirNew;
    private final Eat eat;

    /**
     * Создаёт объект движения.
     * @param eat обработчик поедания яблок
     */
    public Move(Eat eat) {
        this.eat = eat;
    }

    /**
     * Выполняет один шаг движения змейки.
     * @param snake список точек змейки
     * @param apple список яблок
     * @param logic логика яблок
     */
    public void move(ArrayList<Point> snake, ArrayList<Point> apple, AppleLogic logic) {
        if (dirNew != null && !dirNew.isOpposite(dirCurrent)) {
            dirCurrent = dirNew;
        }

        Point head = snake.get(0);

        Point newHead = new Point(
                head.getPointX() + dirCurrent.getPointX(),
                head.getPointY() + dirCurrent.getPointY()
        );

        snake.add(0, newHead);
        if (!eat.collisionApple(newHead, apple, logic)) {
            snake.remove(snake.size() - 1);
        }
    }

    /**
     * Устанавливает текущее направление движения.
     * @param dir направление
     */
    public void setDirCurrent(Directions dir) {
        this.dirCurrent = dir;
    }

    /**
     * Устанавливает новое желаемое направление.
     * @param dir направление
     */
    public void setDirNew(Directions dir) {
        this.dirNew = dir;
    }
}