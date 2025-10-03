package ru.nsu.a.maslova1.operations;

/**
 * Представляет операцию сложения двух выражений.
 */
public class Add extends Expression {
    public final Expression left;
    public final Expression right;

    /**
     * Создает операцию сложения двух выражений.
     */
    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Выводит выражение в формате (левый_операнд + правый_операнд).
     */
    @Override
    public void print() {
        System.out.print("(");
        left.print();
        System.out.print("+");
        right.print();
        System.out.print(")");
    }

    /**
     * Вычисляет производную сложения.
     * Производная суммы равна сумме производных.
     */
    @Override
    public Expression derivative(String var) {
        return new Add(left.derivative(var), right.derivative(var));
    }

    /**
     * Вычисляет значение сложения.
     * Возвращает сумму значений левого и правого выражений.
     */
    @Override
    public int eval(String var) {
        return left.eval(var) + right.eval(var);
    }
}