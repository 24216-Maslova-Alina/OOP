package ru.nsu.a.maslova1.dsl.model;

import java.util.List;

public class CheckTarget {
    private String studentId;
    private List<String> taskIds;

    public CheckTarget(String studentId, List<String> taskIds) {
        this.studentId = studentId;
        this.taskIds = taskIds;
    }

    public String getStudentId() {
        return studentId;
    }

    public List<String> getTaskIds() {
        return taskIds;
    }

    @Override
    public String toString() {
        return "CheckTarget{studentId='" + studentId + "', tasks=" + taskIds + "}";
    }
}