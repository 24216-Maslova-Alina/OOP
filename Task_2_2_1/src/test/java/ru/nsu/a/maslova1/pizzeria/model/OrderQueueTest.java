package ru.nsu.a.maslova1.pizzeria.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для проверки работы очереди заказов {@link OrderQueue}.
 * Проверяет добавление и извлечение заказов, а также состояние очереди.
 */
class OrderQueueTest {

    /**
     * Проверяет добавление заказа в очередь и его извлечение.
     * После добавления размер очереди должен увеличиться,
     * после извлечения - вернуться к исходному.
     *
     * @throws InterruptedException если поток был прерван во время ожидания
     */
    @Test
    void testAddAndGetOrder() throws InterruptedException {
        OrderQueue queue = new OrderQueue();
        Order order = new Order(1);

        queue.addOrder(order);
        assertEquals(1, queue.size());
        assertFalse(queue.isEmpty());

        Order retrieved = queue.getOrder();
        assertEquals(1, retrieved.getId());
        assertEquals(0, queue.size());
        assertTrue(queue.isEmpty());
    }

    /**
     * Проверяет, что новая очередь создаётся пустой.
     * Размер очереди должен быть 0, метод isEmpty() должен возвращать true.
     */
    @Test
    void testEmptyQueue() {
        OrderQueue queue = new OrderQueue();
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    /**
     * Проверяет корректную работу очереди с несколькими заказами.
     * Заказы должны извлекаться в порядке FIFO (первый пришёл - первый ушёл).
     *
     * @throws InterruptedException если поток был прерван во время ожидания
     */
    @Test
    void testMultipleOrders() throws InterruptedException {
        OrderQueue queue = new OrderQueue();

        queue.addOrder(new Order(1));
        queue.addOrder(new Order(2));
        queue.addOrder(new Order(3));

        assertEquals(3, queue.size());

        Order order1 = queue.getOrder();
        assertEquals(1, order1.getId());
        assertEquals(2, queue.size());

        Order order2 = queue.getOrder();
        assertEquals(2, order2.getId());
        assertEquals(1, queue.size());
    }
}