package ru.nsu.a.maslova1.snake.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.a.maslova1.snake.model.Point;

/**
 * Тесты для класса GameConfig.
 */
class GameConfigTest {

    /**
     * Проверяет, что количество столбцов положительное.
     */
    @Test
    void testColsIsPositive () {
        assertTrue (GameConfig.COLS > 0, "Количество столбцов должно быть положительным");
    }

    /**
     * Проверяет, что количество строк положительное.
     */
    @Test
    void testRowsIsPositive () {
        assertTrue (GameConfig.ROWS > 0, "Количество строк должно быть положительным");
    }

    /**
     * Проверяет, что список стен не null.
     */
    @Test
    void testWallsIsNotNull () {
        assertNotNull (GameConfig.WALLS, "Список стен не должен быть null");
    }

    /**
     * Проверяет, что список стен не пустой.
     */
    @Test
    void testWallsIsNotEmpty () {
        assertFalse (GameConfig.WALLS.isEmpty (), "Список стен не должен быть пустым");
    }

    /**
     * Проверяет, что список стен неизменяемый.
     */
    @Test
    void testWallsIsUnmodifiable () {
        assertThrows (UnsupportedOperationException.class, () -> {
            GameConfig.WALLS.add (new Point (0, 0));
        }, "Список стен должен быть неизменяемым");
    }

    /**
     * Проверяет наличие ожидаемых стен в списке.
     */
    @Test
    void testWallsContainExpectedPoints () {
        assertTrue (GameConfig.WALLS.contains (new Point (5, 5)), "Стена (5,5) должна " +
                "присутствовать");
        assertTrue (GameConfig.WALLS.contains (new Point (24, 5)), "Стена (24,5) должна " +
                "присутствовать");
        assertTrue (GameConfig.WALLS.contains (new Point (15, 10)), "Стена (15,10) должна " +
                "присутствовать");
    }

    /**
     * Проверяет, что в центре поля нет стены.
     */
    @Test
    void testWallsDoNotContainCenter () {
        assertFalse (GameConfig.WALLS.contains (new Point (12, 12)), "В центре поля не должно " +
                "быть стены");
    }

    /**
     * Проверяет, что все стены находятся в границах поля.
     */
    @Test
    void testAllWallsWithinBounds () {
        for (Point wall : GameConfig.WALLS) {
            assertTrue (wall.getPointX () >= 0 && wall.getPointX () < GameConfig.COLS,
                    "Стена " + wall + " выходит за границы по X");
            assertTrue (wall.getPointY () >= 0 && wall.getPointY () < GameConfig.ROWS,
                    "Стена " + wall + " выходит за границы по Y");
        }
    }

    /**
     * Проверяет, что в списке стен нет дубликатов.
     */
    @Test
    void testNoDuplicateWalls () {
        long distinctCount = GameConfig.WALLS.stream ().distinct ().count ();
        assertEquals (GameConfig.WALLS.size (), distinctCount, "В списке стен не должно быть " +
                "дубликатов");
    }

    /**
     * Проверяет значения констант.
     */
    @Test
    void testConstantsAreFinal () {
        assertEquals (25, GameConfig.COLS);
        assertEquals (25, GameConfig.ROWS);
    }
}