package ru.nsu.a.maslova1.dsl.services;

import ru.nsu.a.maslova1.dsl.model.Task;
import ru.nsu.a.maslova1.dsl.model.TestStats;
import java.time.LocalDate;
import java.io.File;

public class GradeCalculator {

    public static double calculate(Task task, TestStats stats, LocalDate submissionDate, File taskDir) {
        if (stats == null || stats.total == 0) return 0.0;

        double passRatio = (double) stats.passed / stats.total;
        double baseScore = task.getMaxPoints() * passRatio;

        // Проверка дедлайнов
        LocalDate softDeadline = LocalDate.parse(task.getSoftDeadline());
        LocalDate hardDeadline = LocalDate.parse(task.getHardDeadline());

        if (submissionDate.isAfter(hardDeadline)) {
            return 0.0;
        } else if (submissionDate.isAfter(softDeadline)) {
            baseScore /= 2.0;
        }

        // Штраф за Checkstyle
        int styleErrors = CheckstyleParser.countErrors(taskDir);
        if (styleErrors > 0) {
            double penalty = styleErrors * 0.1; // 0.1 балла за ошибку
            double maxPenalty = task.getMaxPoints() * 0.2; // Макс штраф 20%
            if (penalty > maxPenalty) penalty = maxPenalty;

            System.out.printf("   [ШТРАФ] Найдено ошибок стиля: %d. Вычтено: %.2f балла.\n", styleErrors, penalty);
            baseScore -= penalty;
        }

        return Math.max(0, baseScore);
    }
}