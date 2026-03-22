package ru.nsu.a.maslova1.pizzeria.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import ru.nsu.a.maslova1.pizzeria.model.OrderQueue;
import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для проверки клиента {@link Client}.
 * Проверяет создание клиента и его остановку.
 */
class ClientTest {

    /**
     * Проверяет создание клиента с указанной очередью заказов.
     * Клиент должен корректно сохранять ссылку на очередь.
     */
    @Test
    void testClientCreation() {
        OrderQueue queue = new OrderQueue();
        Client client = new Client(queue);

        assertNotNull(client);
        assertEquals(queue, client.orderQueue);
    }

    /**
     * Проверяет остановку клиента.
     * После вызова shutdown() клиент должен прекратить генерацию заказов.
     *
     * @throws InterruptedException если поток был прерван во время ожидания
     */
    @Test
    void testClientShutdown() throws InterruptedException {
        OrderQueue queue = new OrderQueue();
        Client client = new Client(queue);

        client.start();
        Thread.sleep(100); // Даём клиенту время запуститься

        client.shutdown();

        // Ждём завершения потока
        client.join(2000);

        // Проверяем, что клиент остановился
        assertFalse(client.isAlive());
    }

    /**
     * Проверяет, что клиент добавляет заказы в очередь.
     * После запуска клиента в очереди должны появляться заказы.
     *
     * @throws InterruptedException если поток был прерван во время ожидания
     */
    @Test
    void testClientAddsOrders() throws InterruptedException {
        OrderQueue queue = new OrderQueue();
        Client client = new Client(queue);

        client.start();
        Thread.sleep(500); // Даём время создать несколько заказов

        client.shutdown();
        client.join(2000);

        // Проверяем, что в очереди есть заказы
        assertTrue(queue.size() > 0);
    }
}