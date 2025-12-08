package ru.nsu.a.maslova1.markdown;

import java.util.Objects;

/**
 * Класс, представляющий заголовок в Markdown формате.
 */
public class Heading extends Element {
    private final int level;
    private final Element content;

    /**
     * Создает заголовок указанного уровня с заданным содержимым.
     *
     * @param level уровень заголовка (от 1 до 6)
     * @param content содержимое заголовка
     */
    public Heading(int level, Element content) {
        this.level = level;
        this.content = content;
    }

    /**
     * Преобразует заголовок в строку в формате Markdown.
     *
     * @return строка в формате Markdown, представляющая заголовок
     */
    @Override
    public String toMarkdown() {
        return "#".repeat(level) + " " + content.toMarkdown();
    }

    /**
     * Сравнивает данный заголовок с другим объектом.
     *
     * @param obj объект для сравнения
     * @return true, если объекты равны (одинаковый уровень и содержимое), иначе false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Heading heading = (Heading) obj;
        return Objects.equals(content, heading.content)
                && level == heading.level;
    }

    /**
     * Возвращает хэш-код заголовка.
     *
     * @return хэш-код, вычисленный на основе уровня и содержимого
     */
    @Override
    public int hashCode() {
        return Objects.hash(level, content);
    }
}