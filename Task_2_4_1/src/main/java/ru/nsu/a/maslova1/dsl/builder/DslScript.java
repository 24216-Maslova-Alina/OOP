package ru.nsu.a.maslova1.dsl.builder;

import groovy.lang.Script;

import java.io.File;
import java.util.Map;

public abstract class DslScript extends Script {
    private DslBuilder builder;

    public void setBuilder(DslBuilder builder) {
        this.builder = builder;
    }

    public void include(String filepath) throws Exception {
        File file = new File(filepath);
        if (!file.exists()) {
            throw new RuntimeException("Файл для импорта не найден: " + filepath);
        }

        // Переиспользуем текущий shell, чтобы распарсить файл
        // Нам нужно привести результат parse к DslScript и передать ему наш builder
        DslScript includedScript = (DslScript) getShell().parse(file);
        includedScript.setBuilder(this.builder);
        includedScript.run();
    }

    // Вспомогательный метод для получения текущего Shell
    private groovy.lang.GroovyShell getShell() {
        return (groovy.lang.GroovyShell) getBinding().getVariable("shell");
    }

    public void task(String id, String name, int points, String soft, String hard) {
        builder.task(id, name, points, soft, hard);
    }

    public void group(String name, groovy.lang.Closure closure) {
        builder.group(name, closure);
    }

    public void controlPoint(String name, String date) {
        builder.controlPoint(name, date);
    }

    public void systemSetting(String key, Object value) {
        builder.systemSetting(key, value);
    }

    public void target(Map<String, Object> params) {
        builder.target(params);
    }
}