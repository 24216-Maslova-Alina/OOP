package ru.nsu.a.maslova1.operations;

/**
 * Представляет операцию вычитания двух выражений.
 */
public class Sub extends Expression{
    private final Expression left;
    private final Expression right;

    /**
     * Создает операцию вычитания двух выражений.
     */
    public Sub (Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Выводит выражение в формате (левый_операнд - правый_операнд).
     */
    @Override
    public void print () {
        System.out.print("(");
        left.print();
        System.out.print("-");
        right.print();
        System.out.print(")");
    }

    /**
     * Вычисляет производную вычитания.
     * Производная разности равна разности производных.
     */
    @Override
    public Expression derivative (String var) {
        return new Sub(left.derivative(var), right.derivative(var));
    }

    /**
     * Вычисляет значение вычитания.
     * Возвращает разность значений левого и правого выражений.
     */
    @Override
    public int eval (String var) {
        return left.eval(var) - right.eval(var);
    }
}