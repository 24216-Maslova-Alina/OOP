package ru.nsu.a.maslova1.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("test.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Ошибка открытия файла");
        }
    }
}