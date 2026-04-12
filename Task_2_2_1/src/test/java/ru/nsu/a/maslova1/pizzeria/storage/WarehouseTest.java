package ru.nsu.a.maslova1.pizzeria.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.a.maslova1.pizzeria.model.Order;
import ru.nsu.a.maslova1.pizzeria.model.OrderStatus;

/**
 * Тестовый класс для проверки работы склада {@link Warehouse}.
 * Проверяет размещение заказов на складе, их извлечение и контроль вместимости.
 */
class WarehouseTest {

    /**
     * Проверяет создание склада с указанной вместимостью.
     * Склад должен быть пустым после создания.
     */
    @Test
    void testWarehouseCreation() {
        Warehouse warehouse = new Warehouse(5);
        assertEquals(5, warehouse.getCapacity());
        assertEquals(0, warehouse.getSize());
        assertTrue(warehouse.isEmpty());
    }

    /**
     * Проверяет размещение заказа на складе и его извлечение.
     * При размещении статус заказа должен измениться на IN_WAREHOUSE,
     * при извлечении - на DELIVERED.
     *
     * @throws InterruptedException если поток был прерван во время ожидания
     */
    @Test
    void testPutAndGetOrder() throws InterruptedException {
        Warehouse warehouse = new Warehouse(3);
        Order order = new Order(1);

        warehouse.put(order);
        assertEquals(1, warehouse.getSize());
        assertFalse(warehouse.isEmpty());
        assertEquals(OrderStatus.IN_WAREHOUSE, order.getStatus());

        Order retrieved = warehouse.getOrder();
        assertEquals(1, retrieved.getId());
        assertEquals(OrderStatus.DELIVERED, retrieved.getStatus());
        assertEquals(0, warehouse.getSize());
        assertTrue(warehouse.isEmpty());
    }

    /**
     * Проверяет ограничение вместимости склада.
     * Количество заказов на складе не должно превышать установленную вместимость.
     *
     * @throws InterruptedException если поток был прерван во время ожидания
     */
    @Test
    void testWarehouseCapacity() throws InterruptedException {
        Warehouse warehouse = new Warehouse(2);

        warehouse.put(new Order(1));
        warehouse.put(new Order(2));

        assertEquals(2, warehouse.getSize());

        // Проверяем, что размер не превышает вместимость
        assertTrue(warehouse.getSize() <= warehouse.getCapacity());
    }

    /**
     * Проверяет, что склад корректно определяет своё состояние пустоты.
     */
    @Test
    void testWarehouseEmpty() throws InterruptedException {
        Warehouse warehouse = new Warehouse(2);
        assertTrue(warehouse.isEmpty());

        warehouse.put(new Order(1));
        assertFalse(warehouse.isEmpty());

        warehouse.getOrder();
        assertTrue(warehouse.isEmpty());
    }
}