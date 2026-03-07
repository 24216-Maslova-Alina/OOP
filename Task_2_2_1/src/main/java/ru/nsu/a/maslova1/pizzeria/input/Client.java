package ru.nsu.a.maslova1.pizzeria.input;

import ru.nsu.a.maslova1.pizzeria.model.Order;
import ru.nsu.a.maslova1.pizzeria.model.OrderQueue;

/**
 * Клиент, который генерирует заказы и добавляет их в очередь.
 * Работает в отдельном потоке, создавая заказы через случайные интервалы.
 */
public class Client extends Thread{
    private volatile boolean running = false;
    private int orderCounter = 0;
    OrderQueue orderQueue;

    /**
     * Создаёт клиента с указанной очередью заказов.
     *
     * @param orderQueue очередь для добавления заказов
     */
    public Client(OrderQueue orderQueue) {
        this.orderQueue = orderQueue;
    }

    /**
     * Запускает генерацию заказов.
     * Создаёт заказы с увеличивающимися номерами и добавляет их в очередь.
     * Между заказами случайная пауза (100-1100 мс).
     * Работает до вызова {@link #shutdown()} или прерывания потока.
     */
    @Override
    public void run() {
        running = true;
        while (running) {
            Order order = new Order(++orderCounter);
            orderQueue.addOrder(order);

            int pause = 100 + (int)(Math.random() * 1000);
            try {
                Thread.sleep(pause);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    /**
     * Останавливает генерацию заказов.
     * Устанавливает флаг running в false и прерывает поток.
     */
    public void shutdown() {
        running = false;
        this.interrupt();
    }
}