package ru.nsu.a.maslova1.pizzeria.workers;

import ru.nsu.a.maslova1.pizzeria.storage.Warehouse;
import ru.nsu.a.maslova1.pizzeria.model.Order;
import ru.nsu.a.maslova1.pizzeria.model.OrderQueue;

/**
 * Курьер, который доставляет заказы со склада.
 * Забирает готовые заказы партиями (по вместимости сумки)
 * и "доставляет" их (делает паузу).
 */
public class Courier extends Thread{
    private int capacity;
    OrderQueue queue;
    Warehouse warehouse;

    /**
     * Создаёт курьера с указанной вместимостью сумки.
     *
     * @param capacity сколько заказов может взять за одну поездку
     * @param queue очередь заказов (не используется, но передаётся)
     * @param warehouse склад с готовыми заказами
     */
    public Courier(int capacity, OrderQueue queue, Warehouse warehouse) {
        this.capacity = capacity;
        this.queue = queue;
        this.warehouse = warehouse;
    }

    /**
     * Запускает процесс доставки.
     * Курьер работает, пока поток не прервут:
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                for (int i = 0; i < capacity; i++) {
                    if (i > 0 && warehouse.isEmpty()) {
                        break;
                    }
                    Order order = warehouse.getOrder();
                    System.out.printf("Курьер ввезет заказ %d\n", order.getId());
                }
                sleep(1000);
                System.out.print("Заказы доставлены\n\n");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}