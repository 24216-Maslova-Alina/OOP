package ru.nsu.a.maslova1.snake.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тесты для класса GameOverController (без JavaFX компонентов).
 */
class GameOverControllerTest {

    private GameOverController controller;

    /**
     * Создаёт контроллер перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        controller = new GameOverController();
    }

    /**
     * Проверяет установку ссылки на главный контроллер.
     */
    @Test
    void testSetMainController() {
        SnakeController snakeController = new SnakeController();
        controller.setMainController(snakeController);
        // Проверяем, что метод не выбрасывает исключений
        assertDoesNotThrow(() -> controller.setMainController(snakeController));
    }

    /**
     * Проверяет установку null в качестве контроллера.
     */
    @Test
    void testSetMainControllerNull() {
        assertDoesNotThrow(() -> controller.setMainController(null));
    }

    /**
     * Проверяет наличие метода handleRestart.
     */
    @Test
    void testHandleRestartMethodExists() {
        try {
            java.lang.reflect.Method method = GameOverController.class
                    .getDeclaredMethod("handleRestart");
            assertNotNull(method);
        } catch (NoSuchMethodException e) {
            fail("Метод handleRestart должен существовать");
        }
    }

    /**
     * Проверяет наличие метода setStats.
     */
    @Test
    void testSetStatsMethodExists() {
        try {
            java.lang.reflect.Method method = GameOverController.class
                    .getMethod("setStats", int.class, int.class);
            assertNotNull(method);
        } catch (NoSuchMethodException e) {
            fail("Метод setStats должен существовать");
        }
    }

    /**
     * Проверяет, что контроллер создаётся без ошибок.
     */
    @Test
    void testConstructorDoesNotThrow() {
        assertDoesNotThrow(() -> new GameOverController());
    }

    /**
     * Проверяет аннотацию FXML на handleRestart.
     */
    @Test
    void testHandleRestartHasFXMLAnnotation() {
        try {
            java.lang.reflect.Method method = GameOverController.class
                    .getDeclaredMethod("handleRestart");
            javafx.fxml.FXML annotation = method.getAnnotation(javafx.fxml.FXML.class);
            assertNotNull(annotation, "Метод handleRestart должен иметь аннотацию @FXML");
        } catch (NoSuchMethodException e) {
            fail("Метод handleRestart должен существовать");
        }
    }
}