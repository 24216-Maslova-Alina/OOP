package ru.nsu.a.maslova1.snake.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

/**
 * Тесты для класса Move.
 */
class MoveTest {

    private Move move;
    private Eat eat;
    private ArrayList<Point> snake;
    private ArrayList<Point> apples;
    private AppleLogic logic;

    /**
     * Создаёт объекты перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        eat = new Eat();
        move = new Move(eat);
        logic = new AppleLogic();
        snake = new ArrayList<>();
        apples = new ArrayList<>();

        snake.add(new Point(5, 5));
        snake.add(new Point(4, 5));
        snake.add(new Point(3, 5));

        move.setDirCurrent(Directions.RIGHT);
    }

    /**
     * Проверяет движение вправо.
     */
    @Test
    void testMoveRight() {
        int initialSize = snake.size();
        move.move(snake, apples, logic);
        assertEquals(new Point(6, 5), snake.get(0));
        assertEquals(initialSize, snake.size());
    }

    /**
     * Проверяет движение влево.
     */
    @Test
    void testMoveLeft() {
        move.setDirCurrent(Directions.LEFT);
        move.move(snake, apples, logic);
        assertEquals(new Point(4, 5), snake.get(0));
    }

    /**
     * Проверяет движение вверх.
     */
    @Test
    void testMoveUp() {
        move.setDirCurrent(Directions.UP);
        move.move(snake, apples, logic);
        assertEquals(new Point(5, 4), snake.get(0));
    }

    /**
     * Проверяет движение вниз.
     */
    @Test
    void testMoveDown() {
        move.setDirCurrent(Directions.DOWN);
        move.move(snake, apples, logic);
        assertEquals(new Point(5, 6), snake.get(0));
    }

    /**
     * Проверяет установку текущего направления.
     */
    @Test
    void testSetDirCurrent() {
        move.setDirCurrent(Directions.UP);
        move.move(snake, apples, logic);
        assertEquals(new Point(5, 4), snake.get(0));
    }

    /**
     * Проверяет, что нельзя повернуть в противоположную сторону.
     */
    @Test
    void testCannotTurnOpposite() {
        move.setDirCurrent(Directions.RIGHT);
        move.setDirNew(Directions.LEFT);
        move.move(snake, apples, logic);
        assertEquals(new Point(6, 5), snake.get(0));
    }

    /**
     * Проверяет рост змейки при поедании яблока.
     */
    @Test
    void testSnakeGrowsWhenEating() {
        apples.add(new Point(6, 5));
        int initialSize = snake.size();
        move.move(snake, apples, logic);
        assertEquals(initialSize + 1, snake.size());
    }
}