package ru.nsu.a.maslova1.operations;

/**
 * Парсер для разбора математических выражений из строкового представления.
 * Поддерживает операции: сложение, вычитание, умножение, деление.
 * Поддерживает числовые константы, переменные и унарный минус.
 * Выражения должны быть заключены в скобки: (a+b), (x*(-5))
 */
public class Parser {
    private String input;
    private int pos;

    /**
     * Разбирает строку с математическим выражением и возвращает дерево выражений.
     * Удаляет все пробельные символы из входной строки перед разбором.
     *
     * @param expression строка с математическим выражением
     * @return корневой узел дерева выражений
    */
    public Expression parse(String expression) {
        this.input = expression.replaceAll("\\s+", "");
        this.pos = 0;
        Expression result = parseExpression();
        if (pos != input.length()) {
            throw new IllegalArgumentException("Лишние символы в конце: "
                    + input.substring(pos));
        }
        return result;
    }

    /**
     * Разбирает выражение на текущей позиции.
     * Определяет тип выражения: скобочное, числовое или переменная.
     *
     * @return разобранное выражение
     */
    private Expression parseExpression() {
        if (pos >= input.length()) {
            throw new IllegalArgumentException("Неожиданный конец выражения");
        }

        char c = input.charAt(pos);
        if (c == '(') {
            return parseParenthesized();
        } else if (Character.isDigit(c) || (c == '-' && pos + 1 < input.length() && Character.isDigit(input.charAt(pos + 1)))) {
            return parseNumber();
        } else if (Character.isLetter(c) || c == '_') {
            return parseVariable();
        } else {
            throw new IllegalArgumentException("Неизвестный символ: " + c);
        }
    }

    /**
     * Разбирает скобочное выражение вида (операнд оператор операнд).
     * Также обрабатывает унарный минус вида (-выражение).
     * Если внутри скобок только одно выражение, возвращает его без обертки.
     *
     * @return выражение, соответствующее операции в скобках
     */
    private Expression parseParenthesized() {
        consume('(');

        // Проверяем на унарный минус
        if (pos < input.length() && input.charAt(pos) == '-') {
            pos++;
            Expression expr = parseExpression();
            consume(')');
            return new Mul(new Number(-1), expr); // Унарный минус как умножение на -1
        }

        Expression left = parseExpression();

        // Если после первого выражения сразу закрывающая скобка - возвращаем выражение без обертки
        if (pos < input.length() && input.charAt(pos) == ')') {
            consume(')');
            return left;
        }

        if (pos >= input.length()) {
            throw new IllegalArgumentException("Ожидался оператор");
        }
        char op = input.charAt(pos);
        pos++; // пропускаем оператор
        Expression right = parseExpression();
        consume(')');

        return switch (op) {
            case '+' -> new Add(left, right);
            case '-' -> new Sub(left, right);
            case '*' -> new Mul(left, right);
            case '/' -> new Div(left, right);
            default -> throw new IllegalArgumentException("Неизвестный оператор: "
                    + op);
        };
    }

    /**
     * Разбирает числовую константу.
     * Поддерживает отрицательные числа (знак минус перед числом).
     *
     * @return выражение Number с числовым значением
     */
    private Expression parseNumber() {
        int start = pos;
        boolean negative = false;

        // Обработка знака
        if (input.charAt(pos) == '-') {
            negative = true;
            pos++;
            start = pos;
        }

        while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
            pos++;
        }
        int value = Integer.parseInt(input.substring(start, pos));
        return new Number(negative ? -value : value);
    }

    /**
     * Разбирает имя переменной.
     * Имена переменных могут содержать буквы, цифры и символ подчеркивания.
     *
     * @return выражение Variable с именем переменной
     */
    private Expression parseVariable() {
        int start = pos;
        while (pos < input.length() &&
                (Character.isLetterOrDigit(input.charAt(pos)) || input.charAt(pos) == '_')) {
            pos++;
        }
        String name = input.substring(start, pos);
        return new Variable(name);
    }

    /**
     * Проверяет, что текущий символ соответствует ожидаемому, и перемещает позицию.
     *
     * @param expected ожидаемый символ
     */
    private void consume(char expected) {
        if (pos >= input.length() || input.charAt(pos) != expected) {
            throw new IllegalArgumentException("Ожидался символ '" + expected + "'");
        }
        pos++;
    }
}