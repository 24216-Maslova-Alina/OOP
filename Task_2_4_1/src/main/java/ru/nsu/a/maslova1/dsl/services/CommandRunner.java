package ru.nsu.a.maslova1.dsl.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class CommandRunner {
    // Метод выполняет команду в указанной директории и возвращает вывод консоли
    public static String run(File workingDir, String... command) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.directory(workingDir);
        pb.redirectErrorStream(true); // Объединяем вывод ошибок с обычным выводом

        Process process = pb.start();

        // Читаем, что ответила консоль
        String output;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            output = reader.lines().collect(Collectors.joining("\n"));
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Команда завершилась с ошибкой " + exitCode + ":\n" + output);
        }

        return output;
    }
}