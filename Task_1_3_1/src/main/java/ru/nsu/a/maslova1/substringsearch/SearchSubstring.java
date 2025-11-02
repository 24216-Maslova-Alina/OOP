package ru.nsu.a.maslova1.substringsearch;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для реализации поиска подстроки.
 */
public class SearchSubstring {
    private int size;
    private int kmpState = 0;
    private int[] lps;
    List<Integer> array;

    /**
     * Запуск поиска.
     *
     * @param input файл для чтения
     * @param substring подстрока
     * @return список индексов найденных подстрок
     */
    public List<Integer> find(String input, String substring) {
        array = new ArrayList<>();
        kmpState = 0;

        // Обработка пустой подстроки
        if (substring == null || substring.isEmpty()) {
            this.size = 0;
            return array;
        }

        bufferRead(input, substring);

        this.size = array.size();
        return array;
    }

    /**
     * Получение размера массива.
     *
     * @return размер массива
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Чтение файла по частям.
     *
     * @param filename файл для чтения
     * @param substring подстрока
     */
    private void bufferRead(String filename, String substring) {
        int chunkSize = 8192;

        int overlapSize = Math.max(0, substring.length() - 1);

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(filename),
                        StandardCharsets.UTF_8
                ),
                chunkSize * 2
        )) {
            char[] buffer = new char[chunkSize + overlapSize];
            char[] overlap = new char[overlapSize]; // Фиксированный размер для перекрытия
            int overlapLength = 0;
            long globalPosition = 0;

            computeLps(substring);

            while (true) {
                // Копируем перекрытие из предыдущего чанка
                if (overlapLength > 0) {
                    System.arraycopy(overlap, 0, buffer, 0, overlapLength);
                }

                int charsRead = reader.read(buffer, overlapLength, chunkSize);
                if (charsRead == -1) {
                    break;
                }

                int totalSize = overlapLength + charsRead;

                // Проверяем, что буфер достаточно большой для поиска
                if (totalSize >= substring.length()) {
                    searchInChunk(buffer, totalSize, substring, globalPosition - overlapLength);
                }

                // Обновляем перекрытие для следующего чанка
                if (totalSize >= overlapSize) {
                    System.arraycopy(buffer, totalSize - overlapSize, overlap, 0, overlapSize);
                    overlapLength = overlapSize;
                } else {
                    // Если прочитано меньше чем нужно для перекрытия
                    System.arraycopy(buffer, 0, overlap, 0, totalSize);
                    overlapLength = totalSize;
                }

                globalPosition += charsRead;
            }

        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + filename, e);
        }
    }

    /**
     * Вычисление массива для хранения префиксов.
     *
     * @param substring подстрока
     */
    private void computeLps(String substring) {
        int m = substring.length();
        lps = new int[m];

        int len = 0;
        int i = 1;
        while (i < m) {
            if (substring.charAt(i) == substring.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
    }

    /**
     * Алгоритм Кнута-Морриса-Пратта.
     *
     * @param buffer массив прочитанных значений из файла
     * @param totalSize размер buffer
     * @param substring подстрока
     * @param globalPosition позиция во всем файле
     */
    private void searchInChunk(char[] buffer, int totalSize,
                               String substring, long globalPosition) {
        int m = substring.length();

        for (int i = 0; i < totalSize; i++) {
            while (kmpState > 0 && buffer[i] != substring.charAt(kmpState)) {
                kmpState = lps[kmpState - 1];
            }

            if (buffer[i] == substring.charAt(kmpState)) {
                kmpState++;
            }

            if (kmpState == m) {
                long position = globalPosition + i - m + 1;
                array.add((int) position);
                kmpState = lps[kmpState - 1];
            }
        }
    }
}