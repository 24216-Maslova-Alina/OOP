package ru.nsu.a.maslova1.operations;

/**
 * Представляет операцию умножения двух выражений.
 */
public class Mul extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Создает операцию умножения двух выражений.
     */
    public Mul (Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Выводит выражение в формате (левый_операнд * правый_операнд).
     */
    @Override
    public void print () {
        System.out.print("(");
        left.print();
        System.out.print("*");
        right.print();
        System.out.print(")");
    }

    /**
     * Вычисляет производную умножения по правилу произведения.
     * Формула: l'r + lr'
     */
    @Override
    public Expression derivative (String var) {
        return new Add(new Mul(left.derivative(var), right), new Mul(left, right.derivative(var)));
    }

    /**
     * Вычисляет значение умножения.
     * Возвращает произведение значений левого и правого выражений.
     */
    @Override
    public int eval (String var) {
        return left.eval(var) * right.eval(var);
    }
}