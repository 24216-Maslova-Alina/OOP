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

        bufferRead(input, substring);

        this.size = array.size();
        return array;
    }

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
        int CHUNK_SIZE = 8192;

        int overlapSize = Math.max(0, substring.length() - 1);
    
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(filename),
                        StandardCharsets.UTF_8
                ),
                CHUNK_SIZE * 2
        )) {
            char[] buffer = new char[CHUNK_SIZE + overlapSize];
            char[] overlap = new char[0];
            long globalPosition = 0;

            computeLPS(substring);

            while(true) {
                if (overlap.length > 0) {
                    System.arraycopy(overlap, 0, buffer, 0, overlapSize);

                }
                int charsRead = reader.read(buffer, overlap.length, CHUNK_SIZE);
                if (charsRead == -1) {
                    break;
                }
                int totalSize = overlap.length + charsRead;
                searchInChunk(buffer, totalSize, substring, globalPosition);

                overlap = new char[Math.max(0, substring.length() - 1)];

                if (totalSize >= overlapSize) {
                    System.arraycopy(buffer, totalSize - overlap.length,
                            overlap, 0, overlap.length);
                } else {
                    overlap = new char[totalSize];
                    System.arraycopy(buffer, 0, overlap, 0, totalSize);
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
    private void computeLPS(String substring) {
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
    private void searchInChunk(char[] buffer, int totalSize, String substring, long globalPosition) {
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
