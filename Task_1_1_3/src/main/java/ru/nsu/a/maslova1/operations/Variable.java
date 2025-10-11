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
            throw new RuntimeException("Переменная '" + variable + "' не определена");
        }

        // Удаляем все пробелы для упрощения парсинга
        String cleaned = var.replaceAll("\\s+", "");
        String[] assignments = cleaned.split(";");

        for (String assignment : assignments) {
            if (assignment.isEmpty()) continue; // Пропускаем пустые присваивания

            String[] parts = assignment.split("=");
            if (parts.length != 2) {
                throw new RuntimeException("Некорректный формат присваивания: " + assignment);
            }

            String varName = parts[0];
            String valueStr = parts[1];

            if (varName.isEmpty()) {
                throw new RuntimeException("Пустое имя переменной в присваивании: " + assignment);
            }

            if (valueStr.isEmpty()) {
                throw new RuntimeException("Пустое значение переменной в присваивании: " + assignment);
            }

            if (varName.equals(this.variable)) {
                try {
                    return Integer.parseInt(valueStr);
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Некорректное значение для переменной '" + variable + "': " + valueStr);
                }
            }
        }
        throw new RuntimeException("Переменная '" + variable + "' не определена в строке: " + var);
    }
}