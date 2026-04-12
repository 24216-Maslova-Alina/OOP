package ru.nsu.a.maslova1.pizzeria;

import java.util.List;
import ru.nsu.a.maslova1.pizzeria.init.PizzeriaComponents;
import ru.nsu.a.maslova1.pizzeria.input.Client;
import ru.nsu.a.maslova1.pizzeria.model.OrderQueue;
import ru.nsu.a.maslova1.pizzeria.storage.Warehouse;
import ru.nsu.a.maslova1.pizzeria.workers.Baker;
import ru.nsu.a.maslova1.pizzeria.workers.Courier;

/**
 * Главный класс пиццерии, который управляет работой всех компонентов системы.
 * Координирует взаимодействие между клиентом, пекарями, курьерами,
 * очередью заказов и складом готовой продукции.
 */
public class Pizzeria {

    private final Client client;
    private final List<Baker> bakers;
    private final List<Courier> couriers;
    private final OrderQueue queue;
    private final Warehouse warehouse;
    private final int workTime;

    /**
     * Конструктор пиццерии.
     *
     * @param components объект, содержащий все необходимые компоненты для работы пиццерии
     */
    public Pizzeria(PizzeriaComponents components) {
        this.client = components.client;
        this.bakers = components.bakers;
        this.couriers = components.couriers;
        this.queue = components.queue;
        this.warehouse = components.warehouse;
        this.workTime = components.workTime;
    }

    /**
     * Запускает работу пиццерии.
     * Запускает потоки клиента, пекарей и курьеров, затем ожидает
     * указанное время работы и инициирует завершение.
     */
    public void start() {
        System.out.println("Пиццерия открывается!\n");

        client.start();

        for (Thread baker : bakers) {
            baker.start();
        }

        for (Thread courier : couriers) {
            courier.start();
        }

        try {
            Thread.sleep(workTime * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        shutdown();
    }

    /**
     * Инициирует процесс корректного завершения работы пиццерии.
     * Останавливает поступление новых заказов и ожидает завершения всех
     * начатых заказов перед остановкой потоков работников.
     */
    private void shutdown() {
        System.out.println("\nПиццерия закрывается!");

        client.shutdown();

        finishAllOrders();

        for (Thread baker : bakers) {
            baker.interrupt();
        }

        for (Thread courier : couriers) {
            courier.interrupt();
        }
    }

    /**
     * Завершает все начатые заказы.
     * Сначала останавливает клиента, затем ожидает опустошения очереди заказов
     * и склада. После этого прерывает потоки пекарей и курьеров и ожидает
     * их завершения.
     */
    private void finishAllOrders() {
        System.out.println("Завершаем все начатые заказы...\n");

        client.shutdown();

        while (!queue.isEmpty() || !warehouse.isEmpty()) {
            try {
                Thread.sleep(1000);
                System.out.println("Очередь: " + queue.size()
                        + ", Склад: " + warehouse.getSize());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        for (Thread baker : bakers) {
            baker.interrupt();
        }
        for (Thread courier : couriers) {
            courier.interrupt();
        }

        try {
            for (Thread baker : bakers) {
                baker.join(5000); // timeout 5 сек
            }
            for (Thread courier : couriers) {
                courier.join(5000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Все заказы выполнены! Пиццерия закрыта.\n");
    }
}