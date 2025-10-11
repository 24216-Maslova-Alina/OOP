package ru.nsu.a.maslova1.operations;

import java.util.Scanner;

/**
 * Метод запускающий программы.
 */
public class Main {
    /**
     * Запуск.
     *
     * @param args не вводятся
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите выражение: ");
        String expressionStr = scanner.nextLine();
        Parser parser = new Parser();
        Expression expression = parser.parse(expressionStr);

        System.out.print("Исходное выражение: ");
        expression.print();
        System.out.println();

        System.out.print("Введите переменную для дифференцирования: ");
        String diffVar = scanner.nextLine();
        Expression derivative = expression.derivative(diffVar);

        System.out.print("Производная: ");
        derivative.print();
        System.out.println();

        System.out.println("Введите значения переменных в формате: x=5; y=10 (или оставьте пустым):");
        String variables = scanner.nextLine();

        try {
            int result = expression.eval(variables.trim().isEmpty() ? null : variables);
            System.out.println("Результат: " + result);
        } catch (RuntimeException e) {
            System.out.println("Ошибка вычисления: " + e.getMessage());
        }

        scanner.close();
    }
}