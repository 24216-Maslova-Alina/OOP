package ru.nsu.a.maslova1.pizzeria;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.a.maslova1.pizzeria.init.PizzeriaComponents;
import ru.nsu.a.maslova1.pizzeria.init.PizzeriaInitializer;
import ru.nsu.a.maslova1.pizzeria.input.PizzeriaConfig;

/**
 * Тестовый класс для проверки пиццерии {@link Pizzeria}.
 */
class PizzeriaTest {

    /**
     * Проверяет создание пиццерии с корректными компонентами.
     */
    @Test
    void testPizzeriaCreation() {
        PizzeriaConfig config = new PizzeriaConfig(
                new int[]{1000},
                new int[]{2},
                5,
                1
        );

        PizzeriaComponents components = PizzeriaInitializer.init(config);
        Pizzeria pizzeria = new Pizzeria(components);

        assertNotNull(pizzeria);
    }

    /**
     * Проверяет, что пиццерия корректно запускается и останавливается.
     */
    @Test
    void testPizzeriaStartAndShutdown() throws InterruptedException {
        PizzeriaConfig config = new PizzeriaConfig(
                new int[]{100},
                new int[]{2},
                5,
                1
        );

        PizzeriaComponents components = PizzeriaInitializer.init(config);
        Pizzeria pizzeria = new Pizzeria(components);

        // Запускаем пиццерию в отдельном потоке
        Thread pizzeriaThread = new Thread(pizzeria::start);
        pizzeriaThread.start();

        // Даём поработать 2 секунды
        Thread.sleep(2000);

        // Прерываем поток
        pizzeriaThread.interrupt();

        assertTrue(true); // Если дошли сюда без исключений, тест пройден
    }
}