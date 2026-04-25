package ru.nsu.a.maslova1.dsl.model;

public class Student {
    private String gitId, fullName, repoUrl;

    public Student(String gitId, String fullName, String repoUrl) {
        this.gitId = gitId;
        this.fullName = fullName;
        this.repoUrl = repoUrl;
    }

    public String getGitId() {
        return gitId;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return "Student{name='" + fullName + "'}";
    }
}