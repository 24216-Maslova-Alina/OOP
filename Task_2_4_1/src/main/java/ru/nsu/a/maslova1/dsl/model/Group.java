package ru.nsu.a.maslova1.dsl.model;
import java.util.ArrayList;
import java.util.List;

public class Group {
    private String name;
    private List<Student> students = new ArrayList<>();

    public Group(String name) {
        this.name = name;
    }

    public void addStudent(Student s) {
        students.add(s);
    }

    public List<Student> getStudents() {
        return students;
    }

    @Override
    public String toString() { return "Group{name='" + name + "', students=" + students + "}"; }
}