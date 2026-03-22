package ru.nsu.a.maslova1.pizzeria.init;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ru.nsu.a.maslova1.pizzeria.model.OrderQueue;
import ru.nsu.a.maslova1.pizzeria.storage.Warehouse;
import ru.nsu.a.maslova1.pizzeria.input.Client;
import ru.nsu.a.maslova1.pizzeria.workers.Baker;
import ru.nsu.a.maslova1.pizzeria.workers.Courier;
import org.junit.jupiter.api.Test;
import java.util.List;

/**
 * Тестовый класс для проверки компонентов пиццерии {@link PizzeriaComponents}.
 * Проверяет корректное создание объекта со всеми необходимыми компонентами.
 */
class PizzeriaComponentsTest {

    /**
     * Проверяет создание объекта PizzeriaComponents со всеми компонентами.
     * Объект должен содержать корректные ссылки на очередь, склад,
     * клиента, пекарей, курьеров и время работы.
     */
    @Test
    void testComponentsCreation() {
        OrderQueue queue = new OrderQueue();
        Warehouse warehouse = new Warehouse(5);
        Client client = new Client(queue);
        List<Baker> bakers = List.of(new Baker(1000, queue, warehouse));
        List<Courier> couriers = List.of(new Courier(2, warehouse));

        PizzeriaComponents components = new PizzeriaComponents(
                queue, warehouse, client, bakers, couriers, 30
        );

        assertNotNull(components.queue);
        assertNotNull(components.warehouse);
        assertNotNull(components.client);
        assertEquals(1, components.bakers.size());
        assertEquals(1, components.couriers.size());
        assertEquals(30, components.workTime);
    }
}