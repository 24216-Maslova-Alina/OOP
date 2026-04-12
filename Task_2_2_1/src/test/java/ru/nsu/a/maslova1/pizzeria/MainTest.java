package ru.nsu.a.maslova1.pizzeria;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для проверки главного класса приложения {@link Main}.
 */
class MainTest {

    /**
     * Простой тест, проверяющий, что класс Main существует и может быть загружен.
     */
    @Test
    void testMainClassExists() {
        assertNotNull(Main.class);
    }
}