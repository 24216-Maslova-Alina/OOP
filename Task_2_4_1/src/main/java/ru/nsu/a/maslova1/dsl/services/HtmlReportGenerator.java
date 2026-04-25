package ru.nsu.a.maslova1.dsl.services;

import ru.nsu.a.maslova1.dsl.model.StudentResult;
import ru.nsu.a.maslova1.dsl.model.TaskResult;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class HtmlReportGenerator {

    public static void generate(List<StudentResult> results, String outputPath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath))) {
            writer.println("<!DOCTYPE html>");
            writer.println("<html lang='ru'><head><meta charset='UTF-8'><title>Отчет по ООП</title>");
            writer.println("<style>");
            writer.println("body { font-family: Arial, sans-serif; margin: 40px; background-color: #f4f4f9; }");
            writer.println("h1, h2 { color: #333; }");
            writer.println(".student-card { background: #fff; padding: 20px; margin-bottom: 20px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }");
            writer.println("table { width: 100%; border-collapse: collapse; margin-top: 10px; }");
            writer.println("th, td { padding: 10px; border: 1px solid #ddd; text-align: center; }");
            writer.println("th { background-color: #007BFF; color: white; }");
            writer.println(".perfect { color: green; font-weight: bold; }");
            writer.println(".zero { color: red; font-weight: bold; }");
            writer.println(".total-score { font-size: 18px; margin-top: 15px; font-weight: bold; }");
            writer.println("</style></head><body>");

            writer.println("<h1>Результаты автоматической проверки</h1>");

            for (StudentResult sr : results) {
                writer.println("<div class='student-card'>");
                writer.println("<h2>Студент: " + sr.getStudentName() + "</h2>");
                writer.println("<table>");
                writer.println("<tr><th>Задача</th><th>Тесты</th><th>Дата коммита</th><th>Балл</th></tr>");

                // Перебираем задачи внутри конкретного студента
                for (TaskResult r : sr.getTasks()) {
                    writer.println("<tr>");
                    writer.println("<td>" + r.getTaskId() + "</td>");
                    writer.println("<td>" + r.getPassedTests() + " / " + r.getTotalTests() + "</td>");
                    writer.println("<td>" + r.getCommitDate() + "</td>");

                    String scoreClass = "";
                    if (r.getFinalScore() == r.getMaxScore()) scoreClass = "perfect";
                    else if (r.getFinalScore() == 0) scoreClass = "zero";

                    writer.println("<td class='" + scoreClass + "'>" + String.format("%.2f", r.getFinalScore()) + " / " + r.getMaxScore() + "</td>");
                    writer.println("</tr>");
                }
                writer.println("</table>");

                writer.println("<div class='total-score'>Итого баллов: " + String.format("%.2f", sr.getTotalScore()) + " </div>");
                writer.println("<div class='total-score'>Оценка: " + sr.getFinalGrade() + " </div>");
                writer.println("</div>");
            }

            writer.println("</body></html>");
            System.out.println("\n[ОТЧЕТ] HTML-отчет успешно сгенерирован: " + outputPath);
        } catch (IOException e) {
            System.err.println("[ОШИБКА] Не удалось создать отчет: " + e.getMessage());
        }
    }
}