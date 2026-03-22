package ru.nsu.a.maslova1.pizzeria.init;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ru.nsu.a.maslova1.pizzeria.input.PizzeriaConfig;
import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для проверки инициализатора пиццерии {@link PizzeriaInitializer}.
 * Проверяет корректное создание всех компонентов на основе конфигурации.
 */
class PizzeriaInitializerTest {

    /**
     * Проверяет инициализацию всех компонентов пиццерии из конфигурации.
     * Должны быть созданы очередь, склад, клиент, пекари и курьеры
     * с параметрами, указанными в конфигурации.
     */
    @Test
    void testInit() {
        int[] bakers = {1000, 1500};
        int[] couriers = {2, 3};
        PizzeriaConfig config = new PizzeriaConfig(bakers, couriers, 10, 30);

        PizzeriaComponents components = PizzeriaInitializer.init(config);

        assertNotNull(components.queue);
        assertNotNull(components.warehouse);
        assertNotNull(components.client);
        assertEquals(2, components.bakers.size());
        assertEquals(2, components.couriers.size());
        assertEquals(30, components.workTime);
        assertEquals(10, components.warehouse.getCapacity());
    }

    /**
     * Проверяет, что количество созданных пекарей соответствует
     * количеству скоростей в конфигурации.
     */
    @Test
    void testBakerCount() {
        int[] bakers = {1000, 1500, 2000};
        int[] couriers = {2, 3};
        PizzeriaConfig config = new PizzeriaConfig(bakers, couriers, 10, 30);

        PizzeriaComponents components = PizzeriaInitializer.init(config);

        assertEquals(3, components.bakers.size());
    }

    /**
     * Проверяет, что количество созданных курьеров соответствует
     * количеству вместимостей в конфигурации.
     */
    @Test
    void testCourierCount() {
        int[] bakers = {1000, 1500};
        int[] couriers = {2, 3, 4};
        PizzeriaConfig config = new PizzeriaConfig(bakers, couriers, 10, 30);

        PizzeriaComponents components = PizzeriaInitializer.init(config);

        assertEquals(3, components.couriers.size());
    }
}