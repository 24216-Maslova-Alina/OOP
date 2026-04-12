package ru.nsu.a.maslova1.primenumbers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.a.maslova1.primenumbers.solutions.Consistent;

/**
 * Тесты для Consistent.
 */
public class ConsistentTest {

    @Test
    public void testEmptyArray() {
        Consistent consistent = new Consistent();
        assertFalse(consistent.hasNotPrimeNumber(new int[]{}, 0));
    }

    @Test
    public void testAllPrimes() {
        Consistent consistent = new Consistent();
        assertFalse(consistent.hasNotPrimeNumber(new int[]{2, 3, 5, 7, 11}, 5));
    }

    @Test
    public void testContainsNotPrime() {
        Consistent consistent = new Consistent();
        assertTrue(consistent.hasNotPrimeNumber(new int[]{2, 3, 4, 5, 7}, 5));
    }

    @Test
    public void testSingleNotPrime() {
        Consistent consistent = new Consistent();
        assertTrue(consistent.hasNotPrimeNumber(new int[]{9}, 1));
    }
}