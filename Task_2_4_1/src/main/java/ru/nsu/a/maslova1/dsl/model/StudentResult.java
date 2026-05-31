package ru.nsu.a.maslova1.dsl.model;

import java.util.ArrayList;
import java.util.List;

public class StudentResult {
    private String studentName;
    private List<TaskResult> tasks = new ArrayList<>();
    private double totalScore = 0.0;
    private String finalGrade = "Не оценено";

    public StudentResult(String studentName) {
        this.studentName = studentName;
    }

    public void addTaskResult(TaskResult result) {
        tasks.add(result);
        totalScore += result.getFinalScore();
    }

    public String getStudentName() {
        return studentName;
    }

    public List<TaskResult> getTasks() {
        return tasks;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public String getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(String grade) {
        this.finalGrade = grade;
    }
}