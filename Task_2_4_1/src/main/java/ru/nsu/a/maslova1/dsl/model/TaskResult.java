package ru.nsu.a.maslova1.dsl.model;

public class TaskResult {
    private String studentName;
    private String taskId;
    private double finalScore;
    private int maxScore;
    private int passedTests;
    private int totalTests;
    private String commitDate;

    public TaskResult(String studentName, String taskId, double finalScore, int maxScore, int passedTests, int totalTests, String commitDate) {
        this.studentName = studentName;
        this.taskId = taskId;
        this.finalScore = finalScore;
        this.maxScore = maxScore;
        this.passedTests = passedTests;
        this.totalTests = totalTests;
        this.commitDate = commitDate;
    }

    // Геттеры
    public String getStudentName() {
        return studentName;
    }

    public String getTaskId() {
        return taskId;
    }

    public double getFinalScore() {
        return finalScore;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public int getPassedTests() {
        return passedTests;
    }

    public int getTotalTests() {
        return totalTests;
    }

    public String getCommitDate() {
        return commitDate;
    }
}