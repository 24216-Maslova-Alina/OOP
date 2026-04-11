package ru.nsu.a.maslova1.snake.model;

import java.util.ArrayList;

public class Eat {
    private int count = 2;

    public boolean collisionApple(Point head, ArrayList<Point> apple, AppleLogic logic) {
        for (Point fruit : apple) {
            if (head.equals(fruit)) {
                count++;
                apple.remove(fruit);
                return true;
            }
        }
        if (head.equals(logic.getGoldApple())) {
            count += 3;
            logic.removeGoldApple();
            return true;
        }
        return false;
    }

    public int countingScore() {
        return count;
    }

    public void resetStore() {
        count = 2;
    }
}
