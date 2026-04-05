package ru.nsu.a.maslova1.pizzeria.storage;

import ru.nsu.a.maslova1.pizzeria.model.Order;
import ru.nsu.a.maslova1.pizzeria.model.OrderQueue;
import ru.nsu.a.maslova1.pizzeria.model.OrderStatus;

public class Warehouse {
    private final OrderQueue storage = new OrderQueue();
    private final int capacity;

    public Warehouse(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void put(Order order) {
        while (storage.size() >= capacity) {
            if (Thread.currentThread().isInterrupted()) {
                return;
            }
            try {
                System.out.print("На складе нет свободных мест\n");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        storage.addOrder(order);
        order.setStatus(OrderStatus.IN_WAREHOUSE);
        System.out.print("Заказ на складе, ищем курьера\n");
        notifyAll();
    }

    public synchronized Order getOrder() throws InterruptedException {
        while (storage.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }

        Order order = storage.getOrder();
        order.setStatus(OrderStatus.DELIVERED);
        System.out.print("Заказ передан в доставку\n");
        notifyAll();
        return order;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSize() {
        return storage.size();
    }

    public synchronized boolean isEmpty() {
        return storage.isEmpty();
    }
}