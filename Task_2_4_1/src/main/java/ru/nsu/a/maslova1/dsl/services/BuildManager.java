package ru.nsu.a.maslova1.dsl.services;

import java.io.File;
import ru.nsu.a.maslova1.dsl.model.TestStats;

public class BuildManager {

    public static TestStats runPipeline(File repoDir, String taskId) {
        System.out.println("\n>>> Начинаем проверку задачи: " + taskId);

        File workingDir = repoDir;
        File taskFolder = new File(repoDir, taskId);

        if (taskFolder.exists() && taskFolder.isDirectory()) {
            workingDir = taskFolder;
        }

        String gradlewName = isWindows() ? "gradlew.bat" : "gradlew";
        File gradlewFile = new File(workingDir, gradlewName);

        if (!gradlewFile.exists()) {
            System.err.println("[ОШИБКА] Файл " + gradlewName + " не найден.");
            return null;
        }

        String gradlewCmd = isWindows() ? "gradlew.bat" : "./gradlew";

        if (!isWindows()) {
            try { CommandRunner.run(workingDir, "chmod", "+x", "gradlew"); } catch (Exception ignored) {}
        }

        // Компиляция
        System.out.println("1. Запуск компиляции...");
        try {
            CommandRunner.run(workingDir, gradlewCmd, "classes", "testClasses");
            System.out.println("   [УСПЕХ] Компиляция прошла успешно.");
        } catch (Exception e) {
            System.err.println("   [ОШИБКА] Компиляция провалена.");
            return null;
        }

        // Javadoc
        System.out.println("2. Генерация документации (Javadoc)...");
        try {
            CommandRunner.run(workingDir, gradlewCmd, "javadoc");
            System.out.println("   [УСПЕХ] Документация сгенерирована.");
        } catch (Exception e) {
            System.out.println("   [ПРОПУСК] Не удалось сгенерировать Javadoc (возможно, нет плагина). Идем дальше.");
        }

        // Checkstyle
        System.out.println("3. Проверка стиля кода (Google Java Style)...");
        try {
            File initScript = new File("checkstyle-init.gradle");
            File configFile = new File("google_checks.xml"); // Твой конфиг в корне

            if (!initScript.exists() || !configFile.exists()) {
                System.err.println("   [!] Файлы Checkstyle не найдены в корне проекта!");
            }

            CommandRunner.run(workingDir, gradlewCmd,
                    "checkstyleMain",
                    "--init-script", initScript.getAbsolutePath(),
                    "-PcheckstyleConfigPath=" + configFile.getAbsolutePath() // Передаем абсолютный путь
            );
            System.out.println("   [УСПЕХ] Проверка стиля завершена.");
        } catch (Exception e) {
            System.out.println("   [ВНИМАНИЕ] Checkstyle завершился с ошибками (это нормально, если в коде есть нарушения).");
        }

        // Тесты
        System.out.println("4. Запуск тестов...");
        try {
            CommandRunner.run(workingDir, gradlewCmd, "test");
            System.out.println("   [УСПЕХ] Тесты отработали.");
        } catch (Exception e) {
            System.out.println("   [ВНИМАНИЕ] Сборка тестов завершилась с ошибками.");
        }

        // Парсинг результатов
        System.out.println("5. Анализ результатов тестирования...");
        TestStats stats = TestReportParser.parse(workingDir);
        System.out.println("   -> " + stats.toString());

        return stats;
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }
}