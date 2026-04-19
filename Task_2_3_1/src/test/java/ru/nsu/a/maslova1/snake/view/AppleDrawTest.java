package ru.nsu.a.maslova1.snake.view;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import ru.nsu.a.maslova1.snake.model.Point;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Тесты для AppleDraw.
 */
class AppleDrawTest {

    private static final String CLASS_NAME = "ru.nsu.a.maslova1.snake.view.AppleDraw";

    /**
     * Проверяет, что класс AppleDraw существует.
     */
    @Test
    void testAppleDrawClassExists() {
        try {
            Class<?> clazz = Class.forName(CLASS_NAME);
            assertNotNull(clazz);
        } catch (ClassNotFoundException e) {
            fail("Класс AppleDraw не найден: " + e.getMessage());
        }
    }

    /**
     * Проверяет наличие метода drawApple с правильными параметрами.
     */
    @Test
    void testAppleDrawHasDrawAppleMethod() {
        try {
            Class<?> clazz = Class.forName(CLASS_NAME);
            Method method = clazz.getMethod("drawApple", ArrayList.class, Point.class);
            assertNotNull(method);
            assertEquals(ArrayList.class, method.getParameterTypes()[0]);
            assertEquals(Point.class, method.getParameterTypes()[1]);
        } catch (NoSuchMethodException e) {
            fail("Метод drawApple(ArrayList<Point>, Point) не найден");
        } catch (ClassNotFoundException e) {
            fail("Класс AppleDraw не найден");
        }
    }

    /**
     * Проверяет наличие конструктора с GraphicsContext.
     */
    @Test
    void testAppleDrawHasConstructor() {
        try {
            Class<?> clazz = Class.forName(CLASS_NAME);
            Constructor<?> constructor = clazz.getConstructor(javafx.scene.canvas.GraphicsContext.class);
            assertNotNull(constructor);
        } catch (NoSuchMethodException e) {
            fail("Конструктор AppleDraw(GraphicsContext) не найден");
        } catch (ClassNotFoundException e) {
            fail("Класс AppleDraw не найден");
        }
    }

    /**
     * Проверяет, что AppleDraw наследуется от BaseDraw.
     */
    @Test
    void testAppleDrawExtendsBaseDraw() {
        try {
            Class<?> clazz = Class.forName(CLASS_NAME);
            Class<?> superClass = clazz.getSuperclass();
            assertEquals(BaseDraw.class, superClass, "AppleDraw должен наследовать BaseDraw");
        } catch (ClassNotFoundException e) {
            fail("Класс AppleDraw не найден");
        }
    }
}