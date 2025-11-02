package ru.nsu.a.maslova1.substringsearch;

import java.util.List;
import java.util.Scanner;

/**
 * Класс для тестирования реализации.
 */
public class Main {
    /**
     * Точка входа в программу.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        SearchSubstring search = new SearchSubstring();

        Scanner scanner = new Scanner(System.in);
        String file = scanner.nextLine();
        String substring = scanner.nextLine();

        List<Integer> answer = search.find(file, substring);
        int arraySize = search.getSize();

        System.out.print("[");
        for (int  i = 0; i < arraySize; i++) {
            System.out.print(answer.get(i));
            if (i < arraySize - 1) {
                System.out.print(", ");
            }
        }
        System.out.print("]\n");
    }
}