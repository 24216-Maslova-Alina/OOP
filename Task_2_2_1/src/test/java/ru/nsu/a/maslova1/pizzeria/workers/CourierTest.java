package ru.nsu.a.maslova1.pizzeria.workers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import ru.nsu.a.maslova1.pizzeria.storage.Warehouse;

/**
 * Тестовый класс для проверки курьера {@link Courier}.
 * Проверяет создание курьера и его основные параметры.
 */
class CourierTest {

    /**
     * Проверяет создание курьера с указанной вместимостью.
     * Курьер должен корректно сохранять вместимость сумки и ссылку на склад.
     * Использует рефлексию для доступа к приватным полям.
     */
    @Test
    void testCourierCreation() throws Exception {
        Warehouse warehouse = new Warehouse(5);
        Courier courier = new Courier(3, warehouse);

        assertNotNull(courier);

        // Используем рефлексию для доступа к приватным полям
        Field capacityField = Courier.class.getDeclaredField("capacity");
        capacityField.setAccessible(true);
        assertEquals(3, capacityField.get(courier));

        Field warehouseField = Courier.class.getDeclaredField("warehouse");
        warehouseField.setAccessible(true);
        assertEquals(warehouse, warehouseField.get(courier));
    }

    /**
     * Проверяет, что курьер корректно забирает заказы со склада.
     * Создаёт заказ на складе, запускает курьера и проверяет его обработку.
     *
     * @throws InterruptedException если поток был прерван во время ожидания
     */
    @Test
    void testCourierDeliversOrders() throws InterruptedException {
        Warehouse warehouse = new Warehouse(5);
        Courier courier = new Courier(2, warehouse);

        // Создаём заказ и помещаем на склад
        ru.nsu.a.maslova1.pizzeria.model.Order order =
                new ru.nsu.a.maslova1.pizzeria.model.Order(1);
        warehouse.put(order);

        assertEquals(1, warehouse.getSize());

        courier.start();
        Thread.sleep(200); // Даём время на доставку
        courier.interrupt();

        // Проверяем, что заказ был забран со склада
        assertTrue(warehouse.isEmpty());
    }
}