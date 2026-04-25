package ru.nsu.a.maslova1.dsl.model;

public class TestStats {
    public int total = 0;
    public int passed = 0;
    public int failed = 0;
    public int skipped = 0;

    @Override
    public String toString() {
        return String.format("Всего=%d, Пройдено=%d, Упало=%d, Пропущено=%d", total, passed, failed, skipped);
    }
}