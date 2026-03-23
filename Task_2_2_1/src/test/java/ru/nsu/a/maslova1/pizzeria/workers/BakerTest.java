package ru.nsu.a.maslova1.pizzeria.workers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import ru.nsu.a.maslova1.pizzeria.model.Order;
import ru.nsu.a.maslova1.pizzeria.model.OrderQueue;
import ru.nsu.a.maslova1.pizzeria.model.OrderStatus;
import ru.nsu.a.maslova1.pizzeria.storage.Warehouse;

/**
 * Тестовый класс для проверки пекаря {@link Baker}.
 * Проверяет создание пекаря и его основные параметры.
 */
class BakerTest {

    /**
     * Проверяет создание пекаря с указанными параметрами.
     * Пекарь должен корректно сохранять скорость, очередь и склад.
     * Использует рефлексию для доступа к приватным полям.
     */
    @Test
    void testBakerCreation() throws Exception {
        OrderQueue queue = new OrderQueue();
        Warehouse warehouse = new Warehouse(5);
        Baker baker = new Baker(1000, queue, warehouse);

        assertNotNull(baker);

        // Используем рефлексию для доступа к приватным полям
        Field speedField = Baker.class.getDeclaredField("speed");
        speedField.setAccessible(true);
        assertEquals(1000, speedField.get(baker));

        Field queueField = Baker.class.getDeclaredField("queue");
        queueField.setAccessible(true);
        assertEquals(queue, queueField.get(baker));

        Field warehouseField = Baker.class.getDeclaredField("warehouse");
        warehouseField.setAccessible(true);
        assertEquals(warehouse, warehouseField.get(baker));
    }

    /**
     * Проверяет, что пекарь корректно обрабатывает заказы.
     * Создаёт заказ, запускает пекаря и проверяет изменение статуса.
     *
     * @throws InterruptedException если поток был прерван во время ожидания
     */
    @Test
    void testBakerProcessesOrder() throws InterruptedException {
        OrderQueue queue = new OrderQueue();
        Warehouse warehouse = new Warehouse(5);
        Baker baker = new Baker(100, queue, warehouse);

        Order order = new Order(1);
        queue.addOrder(order);

        baker.start();
        Thread.sleep(200); // Даём время на обработку
        baker.interrupt();

        // Проверяем, что статус заказа изменился
        assertNotEquals(OrderStatus.CREATED, order.getStatus());
    }
}