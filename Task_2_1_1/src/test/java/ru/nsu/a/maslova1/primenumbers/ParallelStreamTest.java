package ru.nsu.a.maslova1.primenumbers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.a.maslova1.primenumbers.solutions.Consistent;
import ru.nsu.a.maslova1.primenumbers.solutions.ParallelStream;

/**
 * Тесты для ParallelStream
 */
public class ParallelStreamTest {

    @Test
    public void testEmptyArray() {
        ParallelStream parallelStream = new ParallelStream();
        assertFalse(parallelStream.hasNotPrimeParallelStream(new int[]{}));
    }

    @Test
    public void testAllPrimes() {
        ParallelStream parallelStream = new ParallelStream();
        assertFalse(parallelStream.hasNotPrimeParallelStream(new int[]{2, 3, 5, 7, 11}));
    }

    @Test
    public void testContainsNotPrime() {
        ParallelStream parallelStream = new ParallelStream();
        assertTrue(parallelStream.hasNotPrimeParallelStream(new int[]{2, 3, 4, 5, 7}));
    }

    @Test
    public void testSameResultAsConsistent() {
        ParallelStream parallelStream = new ParallelStream();
        Consistent consistent = new Consistent();

        int[] numbers = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11};

        boolean streamResult = parallelStream.hasNotPrimeParallelStream(numbers);
        boolean consistentResult = consistent.hasNotPrimeNumber(numbers, numbers.length);

        assertEquals(consistentResult, streamResult);
    }
}