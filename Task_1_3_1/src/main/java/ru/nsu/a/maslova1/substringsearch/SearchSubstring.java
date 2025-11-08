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
    /**
     * Запуск поиска.
     *
     * @param filename файл для чтения
     * @param substring подстрока
     * @return список индексов найденных подстрок
     */
    public List<Integer> find(String filename, String substring) {
        // Обработка пустой подстроки
        if (substring == null || substring.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> array = new ArrayList<>();
        int[] lps = computeLps(substring);
        int kmpState = 0;

        bufferRead(filename, substring, lps, array, kmpState);

        return array;
    }

    /**
     * Чтение файла по частям.
     *
     * @param filename файл для чтения
     * @param substring подстрока
     */
    private void bufferRead(String filename, String substring,
                            int[] lps, List<Integer> array, int kmpState) {
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
                    kmpState = searchInChunk(buffer, totalSize, substring, globalPosition - overlapLength, array, lps, kmpState);
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
    private int[] computeLps(String substring) {
        int m = substring.length();
        int[] lps = new int[m];

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
        return lps;
    }

    /**
     * Алгоритм Кнута-Морриса-Пратта.
     *
     * @param buffer массив прочитанных значений из файла
     * @param totalSize размер buffer
     * @param substring подстрока
     * @param globalPosition позиция во всем файле
     */
    private int searchInChunk(char[] buffer, int totalSize, String substring,
                              long globalPosition, List<Integer> array, int[] lps, int kmpState) {
        int m = substring.length();
        int state = kmpState;

        for (int i = 0; i < totalSize; i++) {
            while (state > 0 && buffer[i] != substring.charAt(state)) {
                state = lps[state - 1];
            }

            if (buffer[i] == substring.charAt(state)) {
                state++;
            }

            if (state == m) {
                long position = globalPosition + i - m + 1;
                array.add((int) position);
                state = lps[state - 1];
            }
        }
        return state;
    }
}