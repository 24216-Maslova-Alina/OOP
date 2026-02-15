package ru.nsu.a.maslova1.primenumbers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import ru.nsu.a.maslova1.primenumbers.solutions.Consistent;
import ru.nsu.a.maslova1.primenumbers.solutions.ParallelStream;
import ru.nsu.a.maslova1.primenumbers.solutions.ParallelThreads;

/**
 * Главный класс приложения для сравнения производительности
 * различных реализаций проверки наличия не простых чисел в массиве.
 */
public class Main {

    /**
     * Основной метод приложения.
     * Считывает массив чисел, проверяет его различными способами
     * и выводит результаты с временем выполнения.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("test.txt"));

        Timer timer = new Timer();

        String input = scanner.nextLine();

        input = input.substring(1, input.length() - 1);
        String[] num = input.split(", ");
        int[] arr = new int[num.length];

        for (int i = 0; i < num.length; i++) {
            arr[i] = Integer.parseInt(num[i]);
        }

        // Последовательное решение
        Consistent check1 = new Consistent();
        timer.start();
        if (check1.hasNotPrimeNumber(arr, arr.length)) {
            System.out.println("Последовательное решение: true");
        } else {
            System.out.println("Последовательное решение: false");
        }
        long time1 = timer.end();

        ParallelThreads check2 = new ParallelThreads(arr, 0, 0, arr.length);

        // 2 потока
        timer.start();
        if (check2.hasNotPrimeThreads(arr, arr.length, 2)) {
            System.out.println("Параллельное с потоками: true");
        } else {
            System.out.println("Параллельное с потоками: false");
        }
        long time22 = timer.end();

        // 4 потока
        timer.start();
        if (check2.hasNotPrimeThreads(arr, arr.length, 4)) {
            System.out.println("Параллельное с потоками: true");
        } else {
            System.out.println("Параллельное с потоками: false");
        }
        long time24 = timer.end();

        // 6 потоков
        timer.start();
        if (check2.hasNotPrimeThreads(arr, arr.length, 6)) {
            System.out.println("Параллельное с потоками: true");
        } else {
            System.out.println("Параллельное с потоками: false");
        }
        long time26 = timer.end();

        // 8 потоков
        timer.start();
        if (check2.hasNotPrimeThreads(arr, arr.length, 8)) {
            System.out.println("Параллельное с потоками: true");
        } else {
            System.out.println("Параллельное с потоками: false");
        }
        long time28 = timer.end();

        // Параллель стрим
        ParallelStream check3 = new ParallelStream();
        timer.start();
        if (check3.hasNotPrimeParallelStream(arr)) {
            System.out.println("Parallel strim: true");
        } else {
            System.out.println("Parallel strim: false");
        }
        long time3 = timer.end();

        System.out.print("|--------------------------------------|\n");
        System.out.printf("  Время последовательного решения: %d\n", time1);
        System.out.print("|--------------------------------------|\n");
        System.out.printf("    Время решения с 2 потоками: %d\n", time22);
        System.out.print("|--------------------------------------|\n");
        System.out.printf("    Время решения с 4 потоками: %d\n", time24);
        System.out.print("|--------------------------------------|\n");
        System.out.printf("    Время решения с 6 потоками: %d\n", time26);
        System.out.print("|--------------------------------------|\n");
        System.out.printf("    Время решения с 8 потоками: %d\n", time28);
        System.out.print("|--------------------------------------|\n");
        System.out.printf("        Параллельный стрим: %d\n", time3);
        System.out.print("|--------------------------------------|\n");
    }
}