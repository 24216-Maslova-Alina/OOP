package ru.nsu.a.maslova1.dsl.builder;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import org.codehaus.groovy.control.CompilerConfiguration;

import groovy.lang.Closure;
import ru.nsu.a.maslova1.dsl.model.CheckTarget;
import ru.nsu.a.maslova1.dsl.model.Group;
import ru.nsu.a.maslova1.dsl.model.Student;
import ru.nsu.a.maslova1.dsl.model.Task;
import ru.nsu.a.maslova1.dsl.model.Config;

import java.io.File;
import java.util.List;
import java.util.Map;

public class DslBuilder {
    private final Config config;
    private Group currentGroup;

    public DslBuilder(Config config) { this.config = config; }

    public void include(String filename) {
        try {
            File includedFile = new File(filename);
            if (!includedFile.exists()) {
                throw new RuntimeException("Файл для include не найден: " + filename);
            }

            // Настраиваем GroovyShell для чтения вложенного файла
            CompilerConfiguration cc = new CompilerConfiguration();
            cc.setScriptBaseClass(DelegatingScript.class.getName());
            GroovyShell shell = new GroovyShell(this.getClass().getClassLoader(), new Binding(), cc);

            // Парсим файл и передаем ему ТОТ ЖЕ самый builder
            DelegatingScript script = (DelegatingScript) shell.parse(includedFile);
            script.setDelegate(this);
            script.run();

        } catch (Exception e) {
            throw new RuntimeException("Ошибка при выполнении include('" + filename + "'): " + e.getMessage(), e);
        }
    }

    public void task(String id, String name, int points, String soft, String hard) {
        config.addTask(new Task(id, name, points, soft, hard));
    }

    public void group(String name, Closure closure) {
        Group group = new Group(name);
        config.addGroup(group);
        this.currentGroup = group;
        closure.setDelegate(this);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
        this.currentGroup = null;
    }

    public void student(String id, String name, String url) {
        if (currentGroup != null) {
            currentGroup.addStudent(new Student(id, name, url));
        }
    }

    public void controlPoint(String name, String date) {
        config.addControlPoint(name, date);
    }

    public void systemSetting(String key, Object value) {
        config.addSystemSetting(key, value);
    }

    public void target(Map<String, Object> params) {
        String studentId = (String) params.get("student");
        @SuppressWarnings("unchecked")
        List<String> tasks = (List<String>) params.get("tasks");
        if (studentId != null && tasks != null) {
            config.addCheckTarget(new CheckTarget(studentId, tasks));
        }
    }
}