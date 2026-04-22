package ru.nsu.a.maslova1.snake.model;

import java.util.ArrayList;

/**
 * Логика поедания яблок и подсчёта очков.
 */
public class Eat {
    private int count = 2;

    /**
     * Проверяет столкновение головы змейки с яблоками и обрабатывает поедание.
     *
     * @param head  координаты головы
     * @param apple список обычных яблок
     * @param logic логика яблок для доступа к золотому яблоку
     *
     * @return true, если яблоко съедено
     */
    public boolean collisionApple (Point head, ArrayList<Point> apple, AppleLogic logic) {
        for (Point fruit : apple) {
            if (head.equals (fruit)) {
                count++;
                apple.remove (fruit);
                return true;
            }
        }
        if (head.equals (logic.getGoldApple ())) {
            count += 3;
            logic.removeGoldApple ();
            return true;
        }
        return false;
    }

    /**
     * Возвращает текущий счет.
     *
     * @return текущий счёт
     */
    public int countingScore () {
        return count;
    }

    /**
     * Сбрасывает счёт к начальному значению.
     */
    public void resetStore () {
        count = 2;
    }
}