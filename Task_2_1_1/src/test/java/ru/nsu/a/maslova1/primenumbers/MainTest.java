package ru.nsu.a.maslova1.primenumbers;

import org.junit.jupiter.api.Test;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для Main.
 */
public class MainTest {

    private void createTestFile(String content) throws IOException {
        try (FileWriter writer = new FileWriter("test.txt")) {
            writer.write(content);
        }
    }

    @Test
    public void testMainWithPrimes() throws IOException {
        createTestFile("[2, 3, 5, 7, 11]");
        assertDoesNotThrow(() -> Main.main(new String[]{}));
    }

    @Test
    public void testMainWithNotPrimes() throws IOException {
        createTestFile("[4, 6, 8, 10]");
        assertDoesNotThrow(() -> Main.main(new String[]{}));
    }

    @Test
    public void testMainWithMixed() throws IOException {
        createTestFile("[2, 4, 7, 9]");
        assertDoesNotThrow(() -> Main.main(new String[]{}));
    }
}