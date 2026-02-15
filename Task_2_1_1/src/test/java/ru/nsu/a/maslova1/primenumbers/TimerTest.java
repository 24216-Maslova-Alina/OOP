package ru.nsu.a.maslova1.primenumbers;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для Timer
 */
public class TimerTest {

    @Test
    public void testTimerMeasuresTime() throws InterruptedException {
        Timer timer = new Timer();

        timer.start();
        Thread.sleep(50); // Ждём 50 мс
        long elapsed = timer.end();

        // Проверяем, что время примерно 50 мс (±20 мс)
        assertTrue(elapsed >= 30 && elapsed <= 70);
    }

    @Test
    public void testTimerCanBeReused() throws InterruptedException {
        Timer timer = new Timer();

        // Первый замер
        timer.start();
        Thread.sleep(30);
        long first = timer.end();
        assertTrue(first >= 20 && first <= 50);

        // Второй замер
        timer.start();
        Thread.sleep(20);
        long second = timer.end();
        assertTrue(second >= 10 && second <= 40);
    }

    @Test
    public void testTimerReturnsPositiveValue() {
        Timer timer = new Timer();
        timer.start();
        long elapsed = timer.end();

        assertTrue(elapsed >= 0);
    }
}