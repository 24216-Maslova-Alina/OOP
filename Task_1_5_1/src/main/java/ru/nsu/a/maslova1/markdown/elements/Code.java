package ru.nsu.a.maslova1.markdown.elements;

import java.util.Objects;
import ru.nsu.a.maslova1.markdown.Element;

/**
 * Класс, представляющий блок кода в Markdown формате.
 */
public class Code extends Element {
    private final String language;
    private final String content;

    /**
     * Создает блок кода с указанным языком программирования и содержимым.
     *
     * @param language язык программирования (может быть null)
     * @param content содержимое блока кода
     */
    public Code(String language, String content) {
        this.language = language;
        this.content = content;
    }

    /**
     * Создает блок кода без указания языка программирования.
     *
     * @param content содержимое блока кода
     */
    public Code(String content) {
        this(null, content);
    }

    /**
     * Преобразует блок кода в строку в формате Markdown.
     *
     * @return строка в формате Markdown, представляющая блок кода
     */
    @Override
    public String toMarkdown() {
        if (language == null) {
            return "```\n" + content + "\n```";
        }
        return "```" + language + "\n" + content + "\n```";
    }

    /**
     * Сравнивает данный блок кода с другим объектом.
     *
     * @param obj объект для сравнения
     * @return true, если объекты равны (одинаковое содержимое и язык), иначе false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Code code = (Code) obj;
        return Objects.equals(content, code.content)
                && Objects.equals(language, code.language);
    }

    /**
     * Возвращает хэш-код блока кода.
     *
     * @return хэш-код, вычисленный на основе содержимого и языка
     */
    @Override
    public int hashCode() {
        return Objects.hash(content, language);
    }
}