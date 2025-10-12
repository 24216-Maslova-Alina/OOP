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
        if (variable.equals(var)) {
            return new Number(1);
        } else {
            return new Number(0);
        }
    }

    /**
     * Вычисляет значение переменной из строки присваивания.
     * Формат: "x=5; y=10" (пробелы игнорируются)
     */
    @Override
    public int eval(String var) {
        if (var == null || var.trim().isEmpty()) {
            throw new IllegalArgumentException("Переменная '" + variable
                    + "' не определена");
        }

        // Убираем все пробелы
        String cleaned = var.replaceAll("\\s+", "");
        String[] assignments = cleaned.split(";");

        for (String assignment : assignments) {
            if (assignment.isEmpty()) {
                continue; // Пропускаем пустые присваивания (;;)
            }

            String[] parts = assignment.split("=");
            if (parts.length != 2) {
                // Если есть запятая или другие нестандартные разделители - бросаем ошибку
                if (assignment.contains(",")) {
                    throw new IllegalArgumentException("Некорректный формат присваивания: используйте "
                            + "';' как разделитель");
                }
                throw new IllegalArgumentException("Некорректный формат присваивания: "
                        + assignment);
            }

            String varName = parts[0];
            String valueStr = parts[1];

            if (varName.isEmpty()) {
                throw new IllegalArgumentException("Пустое имя переменной в присваивании: "
                        + assignment);
            }

            if (valueStr.isEmpty()) {
                throw new IllegalArgumentException("Пустое значение переменной в присваивании: "
                        + assignment);
            }

            if (varName.equals(this.variable)) {
                try {
                    return Integer.parseInt(valueStr);
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Некорректное значение для переменной '"
                            + variable + "': " + valueStr);
                }
            }
        }
        throw new IllegalArgumentException("Переменная '" + variable
                + "' не определена в строке: " + var);
    }
}