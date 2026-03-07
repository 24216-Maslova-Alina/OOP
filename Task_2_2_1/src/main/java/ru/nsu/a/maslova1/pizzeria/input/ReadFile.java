// ПЕРЕДЕЛАТЬ ФОРМАТ ВХОДНЫХ ДАННЫХ
package ru.nsu.a.maslova1.pizzeria.input;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Считывает конфигурацию пиццерии из файла.
 * Формат файла:
 * - количество пекарей
 * - скорость каждого пекаря (сколько заказов готовит за такт)
 * - количество курьеров
 * - вместимость сумки каждого курьера
 * - вместимость склада
 * - таймер (время работы пиццерии)
 */
public class ReadFile {
    private int bakers;
    private int couriers;
    private final int[] bakersSpeed;
    private final int[] trunkCapacity;
    private final int warehouseCapacity;
    private final int timer;

    /**
     * Создаёт объект с настройками из указанного файла.
     *
     * @param file путь к файлу конфигурации
     * @throws FileNotFoundException если файл не найден
     */
    public ReadFile(String file) throws FileNotFoundException {
        Scanner scanner =  new Scanner(new File(file));

        bakers = scanner.nextInt();
        this.bakersSpeed = new int[bakers];

        for (int i = 0; i < bakers; i++) {
            bakersSpeed[i] = scanner.nextInt();
        }

        couriers = scanner.nextInt();
        this.trunkCapacity = new int[couriers];

        for (int i = 0; i < couriers; i++) {
            trunkCapacity[i] = scanner.nextInt();
        }

        warehouseCapacity = scanner.nextInt();
        timer = scanner.nextInt();

        scanner.close();
    }

    /**
     * @return количество пекарей
     */
    public int getBakers() {
        return bakers;
    }

    /**
     * @return массив скоростей пекарей
     */
    public int[] getBakersSpeed() {
        return bakersSpeed;
    }

    /**
     * @return количество курьеров
     */
    public int getCouriers() {
        return couriers;
    }

    /**
     * @return массив вместимостей сумок курьеров
     */
    public int[] getTrunkCapacity() {
        return trunkCapacity;
    }

    /**
     * @return вместимость склада
     */
    public int getWarehouseCapacity() {
        return warehouseCapacity;
    }

    /**
     * @return время работы пиццерии в тактах
     */
    public int getTimer() {
        return timer;
    }
}