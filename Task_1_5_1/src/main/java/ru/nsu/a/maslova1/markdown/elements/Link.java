package ru.nsu.a.maslova1.markdown.elements;

import ru.nsu.a.maslova1.markdown.Element;

import java.util.Objects;

/**
 * Класс, представляющий гиперссылки в Markdown формате.
 */
public class Link extends Element {
    private final Element text;
    private final String url;

    /**
     * Конструктор.
     *
     * @param text текст ссылки
     * @param url URL-адрес, на который ведет ссылка
     */
    public Link(Element text, String url) {
        this.text = text;
        this.url = url;
    }

    /**
     * Преобразует ссылку в строку в формате Markdown.
     *
     * @return строка в формате Markdown, представляющая гиперссылку
     */
    @Override
    public String toMarkdown() {
        return "[" + text.toMarkdown() + "]" + "(" + url + ")";
    }

    /**
     * Сравнивает данную ссылку с другим объектом.
     *
     * @param obj объект для сравнения
     * @return true, если объекты равны (одинаковый текст и URL), иначе false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Link link = (Link) obj;
        return Objects.equals(text, link.text)
                && Objects.equals(url, link.url);
    }

    /**
     * Возвращает хэш-код ссылки.
     *
     * @return хэш-код, вычисленный на основе текста и URL
     */
    @Override
    public int hashCode() {
        return Objects.hash(text, url);
    }
}