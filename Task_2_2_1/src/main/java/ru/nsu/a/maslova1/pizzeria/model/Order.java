package ru.nsu.a.maslova1.pizzeria.model;

/**
 * Представляет заказ на пиццу с уникальным идентификатором и статусом.
 * Статус заказа меняется по мере его обработки: от создания до доставки.
 */
public class Order {
    private int id;
    private OrderStatus status;

    /**
     * Создаёт новый заказ с указанным ID.
     * Начальный статус - CREATED.
     *
     * @param id уникальный номер заказа
     */
    public Order(int id) {
        this.id = id;
        this.status = OrderStatus.CREATED;
    }

    /**
     * Возвращает статус.
     *
     * @return текущий статус заказа
     */
    public OrderStatus getStatus() {
        return status;
    }

    /**
     * Возвращает Id.
     *
     * @return уникальный номер заказа
     */
    public int getId() {
        return id;
    }

    /**
     * Устанавливает новый номер заказа.
     *
     * @param id новый номер заказа
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Изменяет статус заказа.
     *
     * @param status новый статус
     */
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}