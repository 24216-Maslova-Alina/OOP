package ru.nsu.a.maslova1.pizzeria.init;

import ru.nsu.a.maslova1.pizzeria.input.PizzeriaConfig;
import ru.nsu.a.maslova1.pizzeria.input.Client;
import ru.nsu.a.maslova1.pizzeria.model.OrderQueue;
import ru.nsu.a.maslova1.pizzeria.storage.Warehouse;
import ru.nsu.a.maslova1.pizzeria.workers.Baker;
import ru.nsu.a.maslova1.pizzeria.workers.Courier;

import java.util.ArrayList;
import java.util.List;

/**
 * Инициализатор пиццерии.
 * Создаёт все необходимые компоненты на основе конфигурации.
 * Отвечает за создание очереди, склада, клиента, пекарей и курьеров
 * с параметрами, указанными в конфигурационном файле.
 */
public class PizzeriaInitializer {

    /**
     * Инициализирует все компоненты пиццерии на основе переданной конфигурации.

     * @param config конфигурация пиццерии, содержащая параметры всех компонентов
     * @return контейнер со всеми созданными компонентами пиццерии
     */
    public static PizzeriaComponents init(PizzeriaConfig config) {

        OrderQueue queue = new OrderQueue();
        Warehouse warehouse = new Warehouse(config.warehouseCapacity);
        Client client = new Client(queue);

        List<Baker> bakers = new ArrayList<>();
        for (int speed : config.bakers) {
            bakers.add(new Baker(speed, queue, warehouse));
        }

        List<Courier> couriers = new ArrayList<>();
        for (int capacity : config.couriers) {
            couriers.add(new Courier(capacity, warehouse));
        }

        return new PizzeriaComponents(
                queue,
                warehouse,
                client,
                bakers,
                couriers,
                config.workTime
        );
    }
}