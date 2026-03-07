package ru.nsu.a.maslova1.pizzeria.workers;

import ru.nsu.a.maslova1.pizzeria.storage.Warehouse;
import ru.nsu.a.maslova1.pizzeria.model.Order;
import ru.nsu.a.maslova1.pizzeria.model.OrderQueue;
import ru.nsu.a.maslova1.pizzeria.model.OrderStatus;

/**
 * Пекарь, который готовит заказы.
 * Берёт заказы из очереди, готовит их указанное время
 * и передаёт готовые заказы на склад.
 */
public class Baker extends Thread{
    private int speed;
    OrderQueue queue;
    Warehouse warehouse;

    /**
     * Создаёт пекаря с заданной скоростью.
     *
     * @param speed время приготовления одного заказа (мс)
     * @param queue очередь заказов от клиентов
     * @param warehouse склад для готовых заказов
     */
    public Baker(int speed, OrderQueue queue, Warehouse warehouse) {
        this.speed = speed;
        this.queue = queue;
        this.warehouse = warehouse;
    }

    /**
     * Запускает процесс работы пекаря.
     * Пекарь работает, пока поток не прервут:
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Order currentOrder = queue.getOrder();
                currentOrder.setStatus(OrderStatus.BAKING);
                System.out.printf("%d Заказ готовится\n", currentOrder.getId());

                Thread.sleep(speed);

                currentOrder.setStatus(OrderStatus.READY);
                System.out.printf("%d Заказ готов\n", currentOrder.getId());

                warehouse.put(currentOrder);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}