package ru.nsu.a.maslova1.pizzeria;

import ru.nsu.a.maslova1.pizzeria.input.Client;
import ru.nsu.a.maslova1.pizzeria.input.ReadFile;
import ru.nsu.a.maslova1.pizzeria.model.OrderQueue;
import ru.nsu.a.maslova1.pizzeria.storage.Warehouse;
import ru.nsu.a.maslova1.pizzeria.workers.Baker;
import ru.nsu.a.maslova1.pizzeria.workers.Courier;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Главный класс пиццерии.
 * Управляет созданием и запуском всех компонентов:
 * - чтение конфигурации из файла
 * - создание очереди заказов и склада
 * - запуск клиента (генератора заказов)
 * - запуск пекарей и курьеров
 * - контроль времени работы и завершения
 */
public class Pizzeria {
    private final String file;
    private final OrderQueue orderQueue = new OrderQueue();
    private final Client client;
    private final Warehouse warehouse;
    private final ReadFile input;
    private final int workTime;
    private final List<Thread> bakers = new ArrayList<>();
    private final List<Thread> couriers = new ArrayList<>();

    /**
     * Создаёт пиццерию на основе файла конфигурации.
     *
     * @param file путь к файлу с настройками
     * @throws FileNotFoundException если файл не найден
     */
    public Pizzeria (String file) throws FileNotFoundException {
        this.file = file;
        this.input = new ReadFile(file);
        this.warehouse = new Warehouse(input.getWarehouseCapacity());
        this.client = new Client(orderQueue);
        this.workTime = input.getTimer();

        // Создание пекарей
        int[] bakerSpeeds = input.getBakersSpeed();
        for (int speed : bakerSpeeds) {
            Baker baker = new Baker(speed, orderQueue, warehouse);
            bakers.add(baker);
        }

        // Создание курьеров
        int[] courierCapacities = input.getTrunkCapacity();
        for (int capacity : courierCapacities) {
            Courier courier = new Courier(capacity, orderQueue, warehouse);
            couriers.add(courier);
        }
    }

    /**
     * Запускает работу пиццерии.
     * 1. Запускает клиента, пекарей и курьеров
     * 2. Работает в течение workTime секунд
     * 3. Запускает процедуру завершения
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
            Thread.sleep(workTime * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Завершение работы
        shutdown();
    }

    /**
     * Останавливает генерацию новых заказов
     * и запускает завершение текущих.
     */
    private void shutdown() {
        System.out.println("\nПиццерия закрывается!");

        // Останавливаем генератор заказов
        client.shutdown();

        finishAllOrders();
    }

    /**
     * Ожидает выполнения всех начатых заказов.
     * Проверяет очередь и склад, пока они не опустеют.
     * Затем прерывает потоки пекарей и курьеров.
     */
    private void finishAllOrders() {
        System.out.println("Завершаем все начатые заказы...\n");

        // Ждем, пока доделают все заказы
        while (!orderQueue.isEmpty() || !warehouse.isEmpty()) {
            try {
                Thread.sleep(1000);
                System.out.println("Очередь: " + orderQueue.size() +
                        ", Склад: " + warehouse.getSize());
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

        System.out.println("Все заказы выполнены! Пиццерия закрыта.\n");
    }

    /**
     * Точка входа в программу.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        try {
            Pizzeria pizzeria = new Pizzeria("information.txt");
            pizzeria.start();
        } catch (FileNotFoundException e) {
            System.err.println("Файл конфигурации не найден!\n");
        }
    }
}