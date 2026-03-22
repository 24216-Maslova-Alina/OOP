package ru.nsu.a.maslova1.pizzeria.input;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс для чтения конфигурации пиццерии из JSON-файла.
 */
public class ReadFile {

    private static final Logger logger = Logger.getLogger(ReadFile.class.getName());

    /**
     * Считывает конфигурацию из JSON-файла.
     *
     * @param file путь к файлу (например, information.json)
     * @return объект конфигурации PizzeriaConfig
     * @throws IOException если файл не найден или JSON некорректный
     */
    public static PizzeriaConfig read(String file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        try {
            PizzeriaConfig config = mapper.readValue(new File(file), PizzeriaConfig.class);

            validate(config);

            return config;

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Ошибка чтения конфигурации из файла: " + file, e);
            throw e; // не теряем исключение!
        }
    }

    /**
     * Проверка корректности конфигурации.
     */
    private static void validate(PizzeriaConfig config) {

        if (config.bakers == null || config.bakers.length == 0) {
            throw new IllegalArgumentException("Список пекарей пуст");
        }

        if (config.couriers == null || config.couriers.length == 0) {
            throw new IllegalArgumentException("Список курьеров пуст");
        }

        if (config.warehouseCapacity <= 0) {
            throw new IllegalArgumentException("Вместимость склада должна быть > 0");
        }

        if (config.workTime <= 0) {
            throw new IllegalArgumentException("Время работы должно быть > 0");
        }
    }
}