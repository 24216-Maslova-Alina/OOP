package ru.nsu.a.maslova1.dsl.model;

public class Task {
    private String id, name, softDeadline, hardDeadline;
    private int points;

    public Task(String id, String name, int points, String soft, String hard) {
        this.id = id;
        this.name = name;
        this.points = points;
        this.softDeadline = soft;
        this.hardDeadline = hard;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxPoints() {
        return points;
    }

    public String getSoftDeadline() {
        return softDeadline;
    }

    public String getHardDeadline() {
        return hardDeadline;
    }

    @Override
    public String toString() {
        return "Task{id='" + id + "', name='" + name + "'}";
    }
}