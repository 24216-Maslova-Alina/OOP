package ru.nsu.a.maslova1.pizzeria.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для проверки функциональности заказа {@link Order}.
 * Проверяет создание заказа, изменение статуса и идентификатора.
 */
class OrderTest {

    /**
     * Проверяет создание заказа с корректными начальными значениями.
     * Заказ должен иметь указанный ID и статус CREATED.
     */
    @Test
    void testOrderCreation() {
        Order order = new Order(1);
        assertEquals(1, order.getId());
        assertEquals(OrderStatus.CREATED, order.getStatus());
    }

    /**
     * Проверяет возможность изменения статуса заказа.
     * Статус должен корректно меняться на новый.
     */
    @Test
    void testOrderStatusChange() {
        Order order = new Order(1);
        order.setStatus(OrderStatus.BAKING);
        assertEquals(OrderStatus.BAKING, order.getStatus());

        order.setStatus(OrderStatus.READY);
        assertEquals(OrderStatus.READY, order.getStatus());
    }

    /**
     * Проверяет возможность изменения идентификатора заказа.
     * ID заказа должен корректно обновляться.
     */
    @Test
    void testOrderIdChange() {
        Order order = new Order(1);
        order.setId(5);
        assertEquals(5, order.getId());
    }
}