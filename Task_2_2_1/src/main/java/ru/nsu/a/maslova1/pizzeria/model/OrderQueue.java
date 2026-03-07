package ru.nsu.a.maslova1.pizzeria.model;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Потокобезопасная очередь заказов.
 * Используется для передачи заказов от клиентов пекарям.
 * Реализует паттерн Producer-Consumer с синхронизацией.
 */
public class OrderQueue {
    public Queue<Order> queue = new LinkedList<>();

    /**
     * Получает заказ из очереди.
     * Если очередь пуста, поток блокируется до появления нового заказа.
     *
     * @return заказ из очереди
     * @throws InterruptedException если поток был прерван во время ожидания
     */
    public synchronized Order getOrder() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
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
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * Возвращает текущее количество заказов в очереди.
     *
     * @return размер очереди
     */
    public int size() {
        return queue.size();
    }
}