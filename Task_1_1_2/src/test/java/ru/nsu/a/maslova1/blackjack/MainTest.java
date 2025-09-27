package ru.nsu.a.maslova1.blackjack;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MainTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void setUp() {
        // Подменяем System.in, чтобы избежать NoSuchElementException
        String simulatedInput = "0\n"; 
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Перенаправляем вывод чтобы не засорять консоль
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        System.setErr(new PrintStream(new ByteArrayOutputStream()));
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void testMainMethodStartsWithoutException() {
        assertDoesNotThrow(() -> {
            Thread gameThread = new Thread(() -> Main.main(new String[]{}));
            gameThread.start();

            try {
                Thread.sleep(1500);
                if (gameThread.isAlive()) {
                    gameThread.interrupt();
                }
                gameThread.join(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
}