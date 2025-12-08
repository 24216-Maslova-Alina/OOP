package ru.nsu.a.maslova1.markdown;

import java.util.Objects;

/**
 * Класс, представляющий изображение в Markdown формате.
 */
public class Images extends Element {
    private final Element text;
    private final String url;

    /**
     * Конструктор.
     *
     * @param text альтернативный текст изображения
     * @param url URL-адрес изображения
     */
    public Images(Element text, String url) {
        this.text = text;
        this.url = url;
    }

    /**
     * Преобразует изображение в строку в формате Markdown.
     *
     * @return строка в формате Markdown, представляющая изображение
     */
    @Override
    public String toMarkdown() {
        return "!" + "[" + text.toMarkdown() + "]" + "(" + url + ")";
    }

    /**
     * Сравнивает данное изображение с другим объектом.
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
        Images images = (Images) obj;
        return Objects.equals(text, images.text)
                && Objects.equals(url, images.url);
    }

    /**
     * Возвращает хэш-код изображения.
     *
     * @return хэш-код, вычисленный на основе текста и URL
     */
    @Override
    public int hashCode() {
        return Objects.hash(text, url);
    }
}