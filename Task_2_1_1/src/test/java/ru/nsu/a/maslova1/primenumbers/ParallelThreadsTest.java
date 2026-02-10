package ru.nsu.a.maslova1.primenumbers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.a.maslova1.primenumbers.solutions.ParallelThreads;

public class ParallelThreadsTest {

    @Test
    public void testEmptyArrayTwoThreads() {
        ParallelThreads parallelThreads = new ParallelThreads();
        assertFalse(parallelThreads.hasNotPrimeThreads(new int[]{}, 0, 2));
    }

    @Test
    public void testAllPrimesFourThreads() {
        ParallelThreads parallelThreads = new ParallelThreads();
        assertFalse(parallelThreads.hasNotPrimeThreads(new int[]{2, 3, 5, 7, 11}, 5, 4));
    }

    @Test
    public void testContainsNotPrimeTwoThreads() {
        ParallelThreads parallelThreads = new ParallelThreads();
        assertTrue(parallelThreads.hasNotPrimeThreads(new int[]{2, 3, 4, 5, 7}, 5, 2));
    }

    @Test
    public void testSameResultDifferentThreadCounts() {
        ParallelThreads parallelThreads = new ParallelThreads();
        int[] numbers = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11};

        boolean result1 = parallelThreads.hasNotPrimeThreads(numbers, numbers.length, 1);
        boolean result2 = parallelThreads.hasNotPrimeThreads(numbers, numbers.length, 2);
        boolean result4 = parallelThreads.hasNotPrimeThreads(numbers, numbers.length, 4);

        assertEquals(result1, result2);
        assertEquals(result1, result4);
    }
}