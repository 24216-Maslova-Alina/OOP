package ru.nsu.a.maslova1.pizzeria.input;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для проверки конфигурации пиццерии {@link PizzeriaConfig}.
 * Проверяет создание объекта конфигурации и корректность полей.
 */
class PizzeriaConfigTest {

    /**
     * Проверяет создание конфигурации с помощью параметризованного конструктора.
     * Все поля должны быть заполнены переданными значениями.
     */
    @Test
    void testConfigCreation() {
        int[] bakers = {1000, 1500};
        int[] couriers = {2, 3};

        PizzeriaConfig config = new PizzeriaConfig(bakers, couriers, 10, 30);

        assertArrayEquals(bakers, config.bakers);
        assertArrayEquals(couriers, config.couriers);
        assertEquals(10, config.warehouseCapacity);
        assertEquals(30, config.workTime);
    }

    /**
     * Проверяет создание конфигурации с помощью пустого конструктора.
     * Все поля должны быть инициализированы значениями по умолчанию.
     */
    @Test
    void testEmptyConstructor() {
        PizzeriaConfig config = new PizzeriaConfig();
        assertNull(config.bakers);
        assertNull(config.couriers);
        assertEquals(0, config.warehouseCapacity);
        assertEquals(0, config.workTime);
    }
}