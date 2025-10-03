package ru.nsu.a.maslova1.operations;

/**
 * Представляет математическую переменную в выражении.
 */
public class Variable extends Expression {
    private final String variable;

    /**
     * Создает переменную с указанным именем.
     */
    public Variable(String variable) {
        this.variable = variable;
    }

    /**
     * Выводит имя переменной.
     */
    @Override
    public void print() {
        System.out.print(variable);
    }

    /**
     * Вычисляет производную переменной.
     * Возвращает 1, если переменная совпадает с параметром, иначе 0.
     */
    @Override
    public Expression derivative(String var) {
        if (variable.equals(var))  {
            return new Number(1);
        } else {
            return new Number(0);
        }
    }

    /**
     * Вычисляет значение переменной из строки присваивания.
     * Формат: "имя=значение" или несколько через точку с запятой.
     */
    @Override
    public int eval(String var) {
        String[] allVar = var.split(";");
        for (String vars : allVar) {
            String[] parts = vars.split("=");
            if (parts.length == 2) {
                String varName = parts[0].trim();
                String valueStr = parts[1].trim();

                if (varName.equals(this.variable)) {
                    return Integer.parseInt(valueStr);
                }
            }
        }
        return 0;
    }
}