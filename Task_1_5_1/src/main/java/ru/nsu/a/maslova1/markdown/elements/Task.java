package ru.nsu.a.maslova1.markdown.elements;

import ru.nsu.a.maslova1.markdown.Element;

import java.util.Objects;

/**
 * Класс, представляющий задачу (чекбокс) в Markdown формате.
 */
public class Task extends Element {
    private final Element text;
    private final boolean status;

    /**
     * Создает задачу с элементом в качестве текста и статусом выполнения.
     *
     * @param text текст задачи в виде элемента
     * @param status статус выполнения (true - выполнено, false - не выполнено)
     */
    public Task(Element text, boolean status) {
        this.text = text;
        this.status = status;
    }

    /**
     * Создает задачу со строкой в качестве текста и статусом выполнения.
     *
     * @param text текст задачи в виде строки
     * @param status статус выполнения (true - выполнено, false - не выполнено)
     */
    public Task(String text, boolean status) {
        this(new Text(text), status);
    }

    /**
     * Преобразует задачу в строку в формате Markdown.
     * Выполненные задачи отображаются как "[x] текст",
     * невыполненные - как "[ ] текст".
     *
     * @return строка в формате Markdown, представляющая задачу
     */
    @Override
    public String toMarkdown() {
        if (status) {
            return "[x] " + text.toMarkdown();
        }
        return "[ ] " + text.toMarkdown();
    }

    /**
     * Сравнивает данную задачу с другим объектом.
     *
     * @param obj объект для сравнения
     * @return true, если объекты равны (одинаковый текст и статус), иначе false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Task task = (Task) obj;
        return Objects.equals(text, task.text)
                && status == task.status;
    }

    /**
     * Возвращает хэш-код задачи.
     *
     * @return хэш-код, вычисленный на основе текста и статуса
     */
    @Override
    public int hashCode() {
        return Objects.hash(text, status);
    }
}