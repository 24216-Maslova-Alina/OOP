package ru.nsu.a.maslova1.snake.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тесты для класса Collision.
 */
class CollisionTest {

    private Collision collision;
    private ArrayList<Point> snake;

    /**
     * Создаёт объекты перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        collision = new Collision();
        snake = new ArrayList<>();
    }

    /**
     * Проверяет столкновение головы с хвостом.
     */
    @Test
    void testCollisionTailTrue() {
        snake.add(new Point(5, 5));
        snake.add(new Point(5, 6));
        snake.add(new Point(5, 5));
        assertTrue(collision.collisionTail(snake));
    }

    /**
     * Проверяет отсутствие столкновения с хвостом.
     */
    @Test
    void testCollisionTailFalse() {
        snake.add(new Point(5, 5));
        snake.add(new Point(5, 6));
        snake.add(new Point(5, 7));
        assertFalse(collision.collisionTail(snake));
    }

    /**
     * Проверяет выход за границы поля.
     */
    @Test
    void testCollisionBorderTrue() {
        Point head = new Point(- 1, 5);
        assertTrue(collision.collisionBorder(head, 25, 25));
    }

    /**
     * Проверяет нахождение в границах поля.
     */
    @Test
    void testCollisionBorderFalse() {
        Point head = new Point(10, 10);
        assertFalse(collision.collisionBorder(head, 25, 25));
    }

    /**
     * Проверяет столкновение со стеной.
     */
    @Test
    void testCollisionWallTrue() {
        List<Point> walls = List.of(new Point(10, 10));
        Point head = new Point(10, 10);
        assertTrue(collision.collisionWall(walls, head));
    }

    /**
     * Проверяет отсутствие столкновения со стеной.
     */
    @Test
    void testCollisionWallFalse() {
        List<Point> walls = List.of(new Point(10, 10));
        Point head = new Point(5, 5);
        assertFalse(collision.collisionWall(walls, head));
    }
}