package ru.nsu.a.maslova1.dsl.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {
    private List<Task> tasks = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();
    private Map<String, String> controlPoints = new HashMap<>();
    private Map<String, Object> systemSettings = new HashMap<>();
    private List<CheckTarget> checkTargets = new ArrayList<>(); // Новое поле

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void addGroup(Group group) {
        this.groups.add(group);
    }

    public void addControlPoint(String name, String date) {
        controlPoints.put(name, date);
    }

    public void addSystemSetting(String key, Object value) {
        systemSettings.put(key, value);
    }

    public void addCheckTarget(CheckTarget target) {
        this.checkTargets.add(target);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public Map<String, String> getControlPoints() {
        return controlPoints;
    }

    public Map<String, Object> getSystemSettings() {
        return systemSettings;
    }

    public List<CheckTarget> getCheckTargets() {
        return checkTargets;
    }
}