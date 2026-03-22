package ru.nsu.a.maslova1.pizzeria.storage;

import ru.nsu.a.maslova1.pizzeria.model.Order;
import ru.nsu.a.maslova1.pizzeria.model.OrderStatus;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

/**
 * Склад готовых заказов.
 * Выступает буфером между пекарями и курьерами.
 * Имеет ограниченную вместимость и обеспечивает потокобезопасный доступ.
 */
public class Warehouse {
    private Queue<Order> storage = new LinkedList<>();
    private int capacity;

    /**
     * Создаёт склад с указанной вместимостью.
     *
     * @param capacity максимальное количество заказов на складе
     */
    public Warehouse(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Помещает готовый заказ на склад.
     * Если склад полон, поток блокируется до освобождения места.
     * Статус заказа меняется на IN_WAREHOUSE.
     *
     * @param order готовый заказ от пекаря
     * @throws InterruptedException если поток был прерван во время ожидания
     */
    public synchronized void put(Order order) throws InterruptedException {
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

        storage.add(order);
        order.setStatus(OrderStatus.IN_WAREHOUSE);
        System.out.printf("Заказ на складе, ищем курьера\n");
        notifyAll();
    }

    /**
     * Забирает заказ со склада для доставки.
     * Если склад пуст, поток блокируется до появления заказа.
     * Статус заказа меняется на DELIVERED.
     *
     * @return заказ для доставки
     * @throws InterruptedException если поток был прерван во время ожидания
     */
    public synchronized Order getOrder() throws InterruptedException {
        while (storage.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Logger.getLogger(Warehouse.class.getName())
                        .warning("Interrupted while waiting in getOrder()");
                Thread.currentThread().interrupt();
                return null;
            }
        }

        notifyAll();
        Order order = storage.remove();
        order.setStatus(OrderStatus.DELIVERED);
        System.out.printf("Заказ передан в доставку\n");
        notifyAll();
        return order;

    }

    /**
     * @return максимальная вместимость склада
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * @return текущее количество заказов на складе
     */
    public int getSize() {
        return storage.size();
    }

    /**
     * Проверяет, пуст ли склад.
     *
     * @return true если заказов нет, иначе false
     */
    public synchronized boolean isEmpty() {
        return storage.isEmpty();
    }
}