package ru.nsu.a.maslova1.pizzeria.init;

import java.util.List;
import ru.nsu.a.maslova1.pizzeria.input.Client;
import ru.nsu.a.maslova1.pizzeria.model.OrderQueue;
import ru.nsu.a.maslova1.pizzeria.storage.Warehouse;
import ru.nsu.a.maslova1.pizzeria.workers.Baker;
import ru.nsu.a.maslova1.pizzeria.workers.Courier;

/**
 * Контейнер для всех компонентов пиццерии.
 * Содержит ссылки на основные объекты, необходимые для работы пиццерии:
 * очередь заказов, склад, клиента, пекарей, курьеров и время работы.
 * Используется для передачи всех компонентов в конструктор пиццерии.
 */
public class PizzeriaComponents {

    public final OrderQueue queue;
    public final Warehouse warehouse;
    public final Client client;
    public final List<Baker> bakers;
    public final List<Courier> couriers;
    public final int workTime;

    /**
     * Создаёт контейнер со всеми компонентами пиццерии.
     *
     * @param queue     очередь заказов
     * @param warehouse склад готовых заказов
     * @param client    клиент, генерирующий заказы
     * @param bakers    список пекарей
     * @param couriers  список курьеров
     * @param workTime  время работы пиццерии в секундах
     */
    public PizzeriaComponents(
            OrderQueue queue,
            Warehouse warehouse,
            Client client,
            List<Baker> bakers,
            List<Courier> couriers,
            int workTime
    ) {
        this.queue = queue;
        this.warehouse = warehouse;
        this.client = client;
        this.bakers = bakers;
        this.couriers = couriers;
        this.workTime = workTime;
    }
}