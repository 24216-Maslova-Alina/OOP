package ru.nsu.a.maslova1.pizzeria;

import ru.nsu.a.maslova1.pizzeria.init.PizzeriaComponents;
import ru.nsu.a.maslova1.pizzeria.input.Client;
import ru.nsu.a.maslova1.pizzeria.model.OrderQueue;
import ru.nsu.a.maslova1.pizzeria.storage.Warehouse;
import ru.nsu.a.maslova1.pizzeria.workers.Baker;
import ru.nsu.a.maslova1.pizzeria.workers.Courier;

import java.util.List;

public class Pizzeria {

    private final Client client;
    private final List<Baker> bakers;
    private final List<Courier> couriers;
    private final OrderQueue queue;
    private final Warehouse warehouse;
    private final int workTime;

    public Pizzeria(PizzeriaComponents components) {
        this.client = components.client;
        this.bakers = components.bakers;
        this.couriers = components.couriers;
        this.queue = components.queue;
        this.warehouse = components.warehouse;
        this.workTime = components.workTime;
    }

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

    private void finishAllOrders() {
        System.out.println("Завершаем все начатые заказы...\n");

        // 1. Останавливаем клиента (больше заказов не будет)
        client.shutdown();

        // 2. Даем пекарям и курьерам доделать текущие заказы
        while (!queue.isEmpty() || !warehouse.isEmpty()) {
            try {
                Thread.sleep(1000);
                System.out.println("Очередь: " + queue.size() +
                        ", Склад: " + warehouse.getSize());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        // 3. ТОЛЬКО ПОСЛЕ обработки всех заказов прерываем работников
        for (Thread baker : bakers) {
            baker.interrupt();
        }
        for (Thread courier : couriers) {
            courier.interrupt();
        }

        // 4. Ждем завершения всех потоков
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