package ru.nsu.a.maslova1.dsl.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.time.LocalDate;

public class GitManager {
    private static final String CLONE_DIR = "student_repos"; // Папка, куда всё скачается

    // ... все импорты оставляем как были ...

    public static File cloneRepository(String repoUrl, String studentId) {
        File baseDir = new File(CLONE_DIR);
        if (!baseDir.exists()) {
            baseDir.mkdir();
        }

        File studentDir = new File(baseDir, studentId);

        // Если репозиторий уже есть, обновляем его
        if (studentDir.exists()) {
            System.out.println("Репозиторий " + studentId + " уже существует. Обновляем...");
            try {
                // Переключаемся на main/master и делаем pull
                try {
                    CommandRunner.run(studentDir, "git", "checkout", "main");
                } catch (Exception e) {
                    CommandRunner.run(studentDir, "git", "checkout", "master");
                }
                CommandRunner.run(studentDir, "git", "pull");
                System.out.println("   [УСПЕХ] Репозиторий обновлен.");
            } catch (Exception e) {
                System.out.println("   [ВНИМАНИЕ] Не удалось обновить репозиторий. Проверяем локальную версию.");
            }
            return studentDir;
        }

        System.out.println("Клонирование репозитория для " + studentId + "...");
        try {
            CommandRunner.run(baseDir, "git", "clone", repoUrl, studentId);
            return studentDir;
        } catch (Exception e) {
            System.err.println("Ошибка при клонировании репозитория " + studentId + ": " + e.getMessage());
            return null;
        }
    }

    public static LocalDate getLastCommitDateForTask(File repoDir, String taskFolderName) {
        try {
            ProcessBuilder pb = new ProcessBuilder("git", "log", "-1", "--format=%as", "--", taskFolderName);            pb.directory(repoDir);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String dateStr = reader.readLine();
            process.waitFor();

            if (dateStr != null && !dateStr.trim().isEmpty()) {
                return LocalDate.parse(dateStr.trim());
            }
        } catch (Exception e) {
            System.err.println("   [ОШИБКА] Не удалось получить дату коммита: " + e.getMessage());
        }

        System.out.println("   [ВНИМАНИЕ] Не смогли найти дату в Git, берем сегодняшнюю.");
        return LocalDate.now();
    }

    public static boolean checkGitAuth(String sampleRepoUrl) {
        try {
            // Команда ls-remote проверяет доступ к репозиторию без скачивания
            // Устанавливаем переменные окружения, чтобы git не открывал окна запроса пароля
            ProcessBuilder pb = new ProcessBuilder("git", "ls-remote", sampleRepoUrl, "HEAD");
            pb.environment().put("GIT_TERMINAL_PROMPT", "0");
            Process p = pb.start();
            return p.waitFor() == 0;
        } catch (Exception e) {
            return false;
        }
    }
}