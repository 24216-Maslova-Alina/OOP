package ru.nsu.a.maslova1.pizzeria.storage;

import ru.nsu.a.maslova1.pizzeria.model.Order;
import ru.nsu.a.maslova1.pizzeria.model.OrderQueue;
import ru.nsu.a.maslova1.pizzeria.model.OrderStatus;

public class Warehouse {
    private final int capacity;
    private final OrderQueue storage;

    public Warehouse(int capacity) {
        this.capacity = capacity;
        this.storage = new OrderQueue(capacity);
    }

    public void put(Order order) {
        storage.addOrder(order);
        order.setStatus(OrderStatus.IN_WAREHOUSE);
        System.out.print("Заказ на складе, ищем курьера\n");
    }

    public Order getOrder() throws InterruptedException {
        Order order = storage.getOrder();
        if (order != null) {
            order.setStatus(OrderStatus.DELIVERED);
            System.out.print("Заказ передан в доставку\n");
        }
        return order;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSize() {
        return storage.size();
    }

    public boolean isEmpty() {
        return storage.isEmpty();
    }
}