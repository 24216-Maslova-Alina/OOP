package ru.nsu.a.maslova1.snake.controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;

/**
 * Тесты для класса SnakeController (проверка структуры без JavaFX).
 */
class SnakeControllerTest {

    private SnakeController controller;

    /**
     * Создаёт контроллер перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        controller = new SnakeController();
    }

    /**
     * Проверяет, что контроллер создаётся без ошибок.
     */
    @Test
    void testConstructorDoesNotThrow() {
        assertDoesNotThrow(() -> new SnakeController());
    }

    /**
     * Проверяет наличие метода initialize с аннотацией FXML.
     */
    @Test
    void testInitializeHasFXMLAnnotation() {
        try {
            Method method = SnakeController.class.getMethod("initialize");
            javafx.fxml.FXML annotation = method.getAnnotation(javafx.fxml.FXML.class);
            assertNotNull(annotation);
        } catch (NoSuchMethodException e) {
            fail("Метод initialize должен существовать");
        }
    }

    /**
     * Проверяет наличие метода restartGameFromOverlay.
     */
    @Test
    void testRestartGameFromOverlayExists() {
        try {
            Method method = SnakeController.class.getMethod("restartGameFromOverlay");
            assertNotNull(method);
        } catch (NoSuchMethodException e) {
            fail("Метод restartGameFromOverlay должен существовать");
        }
    }

    /**
     * Проверяет наличие приватного метода handleGameOver.
     */
    @Test
    void testHandleGameOverExists() {
        try {
            Method method = SnakeController.class.getDeclaredMethod("handleGameOver");
            assertNotNull(method);
        } catch (NoSuchMethodException e) {
            fail("Метод handleGameOver должен существовать");
        }
    }

    /**
     * Проверяет наличие приватного метода updatePauseButtonText.
     */
    @Test
    void testUpdatePauseButtonTextExists() {
        try {
            Method method = SnakeController.class.getDeclaredMethod("updatePauseButtonText");
            assertNotNull(method);
        } catch (NoSuchMethodException e) {
            fail("Метод updatePauseButtonText должен существовать");
        }
    }

    /**
     * Проверяет наличие приватного метода setupKeysHandling.
     */
    @Test
    void testSetupKeysHandlingExists() {
        try {
            Method method = SnakeController.class.getDeclaredMethod("setupKeysHandling");
            assertNotNull(method);
        } catch (NoSuchMethodException e) {
            fail("Метод setupKeysHandling должен существовать");
        }
    }

    /**
     * Проверяет наличие приватного метода handleKeys.
     */
    @Test
    void testHandleKeysExists() {
        try {
            Method method = SnakeController.class.getDeclaredMethod("handleKeys", javafx.scene.input.KeyEvent.class);
            assertNotNull(method);
        } catch (NoSuchMethodException e) {
            fail("Метод handleKeys должен существовать");
        }
    }

    /**
     * Проверяет, что handleKeys приватный.
     */
    @Test
    void testHandleKeysIsPrivate() {
        try {
            Method method = SnakeController.class.getDeclaredMethod("handleKeys", javafx.scene.input.KeyEvent.class);
            assertTrue(java.lang.reflect.Modifier.isPrivate(method.getModifiers()));
        } catch (NoSuchMethodException e) {
            fail("Метод handleKeys должен существовать");
        }
    }
}