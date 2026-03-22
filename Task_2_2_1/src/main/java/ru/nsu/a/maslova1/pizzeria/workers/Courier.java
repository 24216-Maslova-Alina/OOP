package ru.nsu.a.maslova1.pizzeria.workers;

import ru.nsu.a.maslova1.pizzeria.storage.Warehouse;
import ru.nsu.a.maslova1.pizzeria.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Курьер, который доставляет заказы со склада.
 * Забирает готовые заказы партиями (по вместимости сумки)
 * и "доставляет" их (делает паузу).
 */
public class Courier extends Thread {
    private static final Logger logger = Logger.getLogger(Courier.class.getName());
    private int capacity;
    Warehouse warehouse;

    /**
     * Создаёт курьера с указанной вместимостью сумки.
     *
     * @param capacity сколько заказов может взять за одну поездку
     * @param warehouse склад с готовыми заказами
     */
    public Courier(int capacity, Warehouse warehouse) {
        this.capacity = capacity;
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
                List<Order> deliveryBatch = new ArrayList<>();

                for (int i = 0; i < capacity; i++) {
                    if (Thread.currentThread().isInterrupted()) {
                        break;
                    }
                    if (i > 0 && warehouse.isEmpty()) {
                        break;
                    }
                    Order order = warehouse.getOrder();
                    if (order == null) {
                        break;
                    }
                    deliveryBatch.add(order);
                    System.out.printf("Курьер ввезет заказ %d\n", order.getId());
                }

                if (!deliveryBatch.isEmpty()) {
                    Thread.sleep(1000);
                    System.out.print("Заказы доставлены\n\n");
                }

            } catch (InterruptedException e) {
                logger.info("Courier stopped: " + Thread.currentThread().getName());
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
