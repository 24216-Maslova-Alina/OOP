package ru.nsu.a.maslova1.pizzeria.model;

/**
 * Возможные статусы заказа в процессе обработки.
 * Последовательность: CREATED → BAKING → IN_WAREHOUSE → READY → DELIVERED
 */
public enum OrderStatus {
    CREATED,
    BAKING,
    IN_WAREHOUSE,
    READY,
    DELIVERED
}
