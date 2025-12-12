package ru.nsu.a.maslova1.markdown.elements;

import ru.nsu.a.maslova1.markdown.Element;

import java.util.Objects;

/**
 * Класс, представляющий цитату в Markdown формате.
 */
public class Quote extends Element {
    private final Element content;

    /**
     * Конструктор.
     *
     * @param content содержимое цитаты
     */
    public Quote(Element content) {
        this.content = content;
    }

    /**
     * Преобразует цитату в строку в формате Markdown.
     * Для однострочных цитат добавляет префикс "> ".
     * Для многострочных цитат добавляет префикс "> " к каждой строке.
     *
     * @return строка в формате Markdown, представляющая цитату
     */
    @Override
    public String toMarkdown() {
        String textMarkdown = content.toMarkdown();
        if (!textMarkdown.contains("\n")) {
            return "> " + textMarkdown;
        }

        String[] str = textMarkdown.split("\n");
        StringBuilder res = new StringBuilder();
        for (String line : str) {
            res.append("> ").append(line).append("\n");
        }
        return res.toString().trim();
    }

    /**
     * Сравнивает данную цитату с другим объектом.
     *
     * @param obj объект для сравнения
     * @return true, если объекты равны (одинаковое содержимое), иначе false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Quote quote = (Quote) obj;
        return Objects.equals(content, quote.content);
    }

    /**
     * Возвращает хэш-код цитаты.
     *
     * @return хэш-код, вычисленный на основе содержимого
     */
    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}