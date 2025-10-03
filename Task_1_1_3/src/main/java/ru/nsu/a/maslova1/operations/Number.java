package ru.nsu.a.maslova1.operations;

/**
 * Представляет числовую константу в выражении.
 */
public class Number extends Expression {
    private final int value;

    /**
     * Создает числовую константу с заданным значением.
     */
    public Number (int value) {
        this.value = value;
    }

    /**
     * Выводит числовое значение.
     */
    @Override
    public void print () {
        System.out.print(value);
    }

    /**
     * Вычисляет производную константы (всегда 0).
     */
    @Override
    public Expression derivative (String var) {
        return new Number(0);
    }

    /**
     * Возвращает значение константы.
     */
    @Override
    public int eval (String var) {
        return value;
    }
}