package ru.nsu.a.maslova1.operations;

/**
 * Абстрактный класс для математических выражений.
 * Определяет основные операции для работы с выражениями.
 */
public abstract class Expression {
    /**
     * Выводит выражение в текстовом виде.
     */
    public abstract void print ();

    /**
     * Вычисляет производную выражения по переменной.
     */
    public abstract Expression derivative (String var);

    /**
     * Вычисляет значение выражения для заданных переменных.
     */
    public abstract int eval (String var);
}