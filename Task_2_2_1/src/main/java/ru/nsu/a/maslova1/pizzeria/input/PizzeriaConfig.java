package ru.nsu.a.maslova1.pizzeria.input;

/**
 * Конфигурация пиццерии.
 * Используется для загрузки данных из JSON.
 */
public class PizzeriaConfig {

    public int[] bakers;              // скорости пекарей
    public int[] couriers;            // вместимость курьеров
    public int warehouseCapacity;     // размер склада
    public int workTime;              // время работы (сек)

    /**
     * Пустой конструктор обязателен для Jackson.
     */
    public PizzeriaConfig() {
    }

    /**
     * Удобный конструктор (не обязателен, но полезен).
     */
    public PizzeriaConfig(
            int[] bakers,
            int[] couriers,
            int warehouseCapacity,
            int workTime
    ) {
        this.bakers = bakers;
        this.couriers = couriers;
        this.warehouseCapacity = warehouseCapacity;
        this.workTime = workTime;
    }
}