package ru.nsu.a.maslova1.primenumbers.solutions;

/**
 * Класс для проверки чисел на простоту.
 * Реализует алгоритм проверки числа на простоту.
 */
public class PrimeNumbers {

    /**
     * Проверяет, является ли число простым.
     *
     * @param num число для проверки
     * @return true если число простое, иначе false
     */
    public boolean isPrime(long num) {
        if (num <= 1) {
            return false;
        }

        for (long i = 2; i * i <= num; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}