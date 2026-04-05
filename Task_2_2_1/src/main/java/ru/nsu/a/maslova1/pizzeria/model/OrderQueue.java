package ru.nsu.a.maslova1.pizzeria.model;

import java.util.logging.Logger;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Потокобезопасная очередь заказов.
 */
public class OrderQueue {
    private static final Logger logger = Logger.getLogger(OrderQueue.class.getName());

    private final Queue<Order> queue = new LinkedList<>();

    /**
     * Получает заказ из очереди.
     * Если очередь пуста, поток блокируется до появления нового заказа.
     *
     * @return заказ из очереди
     */
    public synchronized Order getOrder() {
        while (queue.isEmpty()) {
            if (Thread.currentThread().isInterrupted()) {
                return null;
            }
            try {
                wait();
            } catch (InterruptedException e) {
                logger.info("Thread(queue) was interrupted while waiting for order");
                Thread.currentThread().interrupt();
                return null;
            }
        }
        return queue.poll();
    }

    /**
     * Добавляет заказ в очередь.
     * Уведомляет все ожидающие потоки о появлении нового заказа.
     *
     * @param order заказ для добавления
     */
    public synchronized void addOrder(Order order) {
        queue.add(order);
        notifyAll();
    }

    /**
     * Проверяет, пуста ли очередь.
     *
     * @return true если очередь пуста, иначе false
     */
    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * Возвращает текущее количество заказов в очереди.
     *
     * @return размер очереди
     */
    public synchronized int size() {
        return queue.size();
    }
}