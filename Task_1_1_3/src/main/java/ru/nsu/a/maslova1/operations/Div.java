package ru.nsu.a.maslova1.operations;

/**
 * Представляет операцию деления двух выражений.
 */
public class Div extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Создает операцию деления двух выражений.
     */
    public Div(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Выводит выражение в формате (левый_операнд / правый_операнд).
     */
    @Override
    public void print() {
        System.out.print("(");
        left.print();
        System.out.print("/");
        right.print();
        System.out.print(")");
    }

    /**
     * Вычисляет производную деления по правилу частного.
     * Формула: (l'r - lr')/r²
     */
    @Override
    public Expression derivative(String var) {
        //(l'r - lr')/r^2
        Expression numerator = new Sub(new Mul(left.derivative(var), right),
                new Mul(left, right.derivative(var)));
        Expression denominator = new Mul(right, right);
        return new Div(numerator, denominator);
    }

    /**
     * Вычисляет значение деления.
     * Возвращает результат целочисленного деления.
     */
    @Override
    public int eval(String var) {
        return left.eval(var) / right.eval(var);
    }
}