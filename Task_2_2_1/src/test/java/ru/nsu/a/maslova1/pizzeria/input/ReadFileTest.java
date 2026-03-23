package ru.nsu.a.maslova1.pizzeria.input;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.io.TempDir;

/**
 * Тестовый класс для проверки чтения конфигурации из файла {@link ReadFile}.
 * Проверяет корректное чтение JSON-файла и обработку ошибок.
 */
class ReadFileTest {

    @TempDir
    Path tempDir;

    /**
     * Проверяет чтение корректного JSON-файла конфигурации.
     * Должен быть создан объект PizzeriaConfig с правильными значениями.
     *
     * @throws IOException если возникает ошибка при чтении файла
     */
    @Test
    void testReadValidConfig() throws IOException {
        String json = "{\n"
                + "  \"bakers\": [1000, 1500],\n" +
                "  \"couriers\": [2, 3],\n" +
                "  \"warehouseCapacity\": 10,\n" +
                "  \"workTime\": 30\n" +
                "}";

        File configFile = tempDir.resolve("config.json").toFile();
        java.nio.file.Files.write(configFile.toPath(), json.getBytes());

        PizzeriaConfig config = ReadFile.read(configFile.getAbsolutePath());

        assertNotNull(config);
        assertArrayEquals(new int[]{1000, 1500}, config.bakers);
        assertArrayEquals(new int[]{2, 3}, config.couriers);
        assertEquals(10, config.warehouseCapacity);
        assertEquals(30, config.workTime);
    }

    /**
     * Проверяет выбрасывание исключения при чтении несуществующего файла.
     * Должно быть выброшено IOException.
     */
    @Test
    void testReadNonExistentFile() {
        assertThrows(IOException.class, () -> {
            ReadFile.read("non_existent_file.json");
        });
    }

    /**
     * Проверяет валидацию конфигурации - пустой список пекарей.
     * Должно быть выброшено IllegalArgumentException.
     */
    @Test
    void testValidateEmptyBakers() {
        // Создаём временный файл с некорректной конфигурацией
        String invalidJson = "{\n"
                + "  \"bakers\": [],\n"
                + "  \"couriers\": [2, 3],\n"
                + "  \"warehouseCapacity\": 10,\n"
                + "  \"workTime\": 30\n"
                + "}";

        File configFile = tempDir.resolve("invalid_config.json").toFile();

        assertThrows(IllegalArgumentException.class, () -> {
            java.nio.file.Files.write(configFile.toPath(), invalidJson.getBytes());
            ReadFile.read(configFile.getAbsolutePath());
        });
    }

    /**
     * Проверяет валидацию конфигурации - пустой список курьеров.
     * Должно быть выброшено IllegalArgumentException.
     */
    @Test
    void testValidateEmptyCouriers() {
        String invalidJson = "{\n"
                + "  \"bakers\": [1000, 1500],\n"
                + "  \"couriers\": [],\n"
                + "  \"warehouseCapacity\": 10,\n"
                + "  \"workTime\": 30\n"
                + "}";

        File configFile = tempDir.resolve("invalid_config2.json").toFile();

        assertThrows(IllegalArgumentException.class, () -> {
            java.nio.file.Files.write(configFile.toPath(), invalidJson.getBytes());
            ReadFile.read(configFile.getAbsolutePath());
        });
    }

    /**
     * Проверяет валидацию конфигурации - нулевая вместимость склада.
     * Должно быть выброшено IllegalArgumentException.
     */
    @Test
    void testValidateZeroWarehouseCapacity() {
        String invalidJson = "{\n"
                + "  \"bakers\": [1000, 1500],\n"
                + "  \"couriers\": [2, 3],\n"
                + "  \"warehouseCapacity\": 0,\n"
                + "  \"workTime\": 30\n"
                + "}";

        File configFile = tempDir.resolve("invalid_config3.json").toFile();

        assertThrows(IllegalArgumentException.class, () -> {
            java.nio.file.Files.write(configFile.toPath(), invalidJson.getBytes());
            ReadFile.read(configFile.getAbsolutePath());
        });
    }
}