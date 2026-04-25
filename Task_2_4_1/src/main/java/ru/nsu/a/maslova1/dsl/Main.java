package ru.nsu.a.maslova1.dsl;

import ru.nsu.a.maslova1.dsl.model.*;
import ru.nsu.a.maslova1.dsl.builder.*;
import ru.nsu.a.maslova1.dsl.services.*;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("--- Проверка окружения ---");
        // Берем любой URL из конфига для проверки
        if (!GitManager.checkGitAuth("https://github.com/google/guava.git")) {
            System.err.println("[ОШИБКА] Git требует аутентификацию или не настроен!");
            System.err.println("Убедитесь, что SSH-ключи настроены или доступ к репозиториям публичный.");
            return;
        }

        try {
            // Проверяем наличие конфигурационного файла
            File configFile = new File("config.groovy");
            if (!configFile.exists()) {
                System.err.println("Файл config.groovy не найден в корне проекта!");
                return;
            }

            // Инициализируем объекты конфигурации и строителя (Builder)
            Config config = new Config();
            DslBuilder builder = new DslBuilder(config);

            // Настраиваем GroovyShell для обработки нашего DSL
            CompilerConfiguration cc = new CompilerConfiguration();
            cc.setScriptBaseClass(DelegatingScript.class.getName());
            GroovyShell shell = new GroovyShell(Main.class.getClassLoader(), new Binding(), cc);

            // Читаем скрипт
            DelegatingScript script = (DelegatingScript) shell.parse(configFile);
            script.setDelegate(builder);
            script.run();

            System.out.println("--- Конфигурация успешно загружена ---");

            // Запускаем проверки
            runCheckPipeline(config);

        } catch (Exception e) {
            System.err.println("Ошибка выполнения: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void runCheckPipeline(Config config) {
        System.out.println("\n--- Запуск пайплайна проверок ---");

        // Читаем настройки перевода баллов в оценки (если не заданы, берем дефолтные)
        int excellentScore = (int) config.getSystemSettings().getOrDefault("gradeExcellent", 80);
        int goodScore = (int) config.getSystemSettings().getOrDefault("gradeGood", 60);
        int satScore = (int) config.getSystemSettings().getOrDefault("gradeSatisfactory", 40);

        List<StudentResult> allResults = new ArrayList<>();

        for (CheckTarget target : config.getCheckTargets()) {
            System.out.println("\n========================================");

            Student currentStudent = null;
            for (Group group : config.getGroups()) {
                for (Student s : group.getStudents()) {
                    if (s.getGitId().equals(target.getStudentId())) {
                        currentStudent = s;
                        break;
                    }
                }
                if (currentStudent != null) break;
            }

            if (currentStudent == null) {
                System.err.println("Студент с ID '" + target.getStudentId() + "' не найден в конфигурации!");
                continue;
            }

            StudentResult studentResult = new StudentResult(currentStudent.getFullName());

            System.out.println("Проверяем студента: " + studentResult.getStudentName());
            File studentRepoDir = GitManager.cloneRepository(currentStudent.getRepoUrl(), currentStudent.getGitId());

            if (studentRepoDir == null) continue;

            for (String taskId : target.getTaskIds()) {
                TestStats stats = BuildManager.runPipeline(studentRepoDir, taskId);

                Task currentTask = null;
                for (Task t : config.getTasks()) {
                    if (t.getId().equals(taskId)) {
                        currentTask = t;
                        break;
                    }
                }

                if (stats != null && currentTask != null) {
                    LocalDate commitDate = GitManager.getLastCommitDateForTask(studentRepoDir, taskId);

                    File taskFolder = new File(studentRepoDir, taskId);
                    File actualTaskDir = (taskFolder.exists() && taskFolder.isDirectory()) ? taskFolder : studentRepoDir;

                    double finalScore = GradeCalculator.calculate(currentTask, stats, commitDate, actualTaskDir);

                    System.out.println("ИТОГ по " + taskId + ": " + stats);
                    System.out.printf("НАЧИСЛЕНО БАЛЛОВ: %.2f из %d\n", finalScore, currentTask.getMaxPoints());

                    studentResult.addTaskResult(new TaskResult(
                            studentResult.getStudentName(), taskId, finalScore, currentTask.getMaxPoints(),
                            stats.passed, stats.total, commitDate.toString()
                    ));
                } else {
                    int maxPts = (currentTask != null) ? currentTask.getMaxPoints() : 0;
                    studentResult.addTaskResult(new TaskResult(
                            studentResult.getStudentName(), taskId, 0.0, maxPts, 0, 0, "Ошибка"
                    ));
                }
            }

            // Выставляем итоговую оценку на основе системных настроек
            double total = studentResult.getTotalScore();
            if (total >= excellentScore) studentResult.setFinalGrade("Отлично");
            else if (total >= goodScore) studentResult.setFinalGrade("Хорошо");
            else if (total >= satScore) studentResult.setFinalGrade("Удовлетворительно");
            else studentResult.setFinalGrade("Неудовлетворительно");

            allResults.add(studentResult);
        }

        System.out.println("\n========================================");
        HtmlReportGenerator.generate(allResults, "report.html");
    }
}