package ru.nsu.a.maslova1.markdown.elements;

import ru.nsu.a.maslova1.markdown.Element;

import java.util.Objects;

/**
 * Класс, представляющий текстовый элемент с различными стилями форматирования в Markdown.
 */
public class Text extends Element {
    private final String content;
    private final boolean bold;
    private final boolean italic;
    private final boolean strikethrough;
    private final boolean code;

    /**
     * Создает простой текстовый элемент без стилей форматирования.
     *
     * @param content текстовое содержимое
     */
    public Text(String content) {
        this(content, false, false, false, false);
    }

    /**
     * Создает текстовый элемент с указанными стилями форматирования.
     *
     * @param content текстовое содержимое
     * @param bold true для полужирного текста
     * @param italic true для курсивного текста
     * @param strikethrough true для зачеркнутого текста
     * @param code true для текста в формате кода
     */
    public Text(String content, boolean bold, boolean italic, boolean strikethrough, boolean code) {
        this.content = content;
        this.bold = bold;
        this.italic = italic;
        this.strikethrough = strikethrough;
        this.code = code;
    }

    /**
     * Создает простой текстовый элемент без стилей форматирования.
     * Нестатический метод-обертка над конструктором.
     *
     * @param content текстовое содержимое
     * @return новый текстовый элемент без стилей
     */
    public Text plain(String content) {
        return new Text(content, false, false, false, false);
    }

    /**
     * Создает полужирный текстовый элемент.
     *
     * @param content текстовое содержимое
     * @return новый полужирный текстовый элемент
     */
    public static Text bold(String content) {
        return new Text(content, true, false, false, false);
    }

    /**
     * Создает курсивный текстовый элемент.
     *
     * @param content текстовое содержимое
     * @return новый курсивный текстовый элемент
     */
    public static Text italic(String content) {
        return new Text(content, false, true, false, false);
    }

    /**
     * Создает зачеркнутый текстовый элемент.
     *
     * @param content текстовое содержимое
     * @return новый зачеркнутый текстовый элемент
     */
    public static Text strikethrough(String content) {
        return new Text(content, false, false, true, false);
    }

    /**
     * Создает текстовый элемент в формате кода (инлайновый код).
     *
     * @param content текстовое содержимое
     * @return новый текстовый элемент в формате кода
     */
    public static Text code(String content) {
        return new Text(content, false, false, false, true);
    }

    /**
     * Преобразует текстовый элемент в строку в формате Markdown.
     * Применяет соответствующее форматирование в зависимости от установленных стилей.
     *
     * @return строка в формате Markdown, представляющая текстовый элемент
     */
    @Override
    public String toMarkdown() {
        if (bold) {
            return "**" + content + "**";
        }

        if (italic) {
            return "*" + content + "*";
        }

        if (strikethrough) {
            return "~~" + content + "~~";
        }

        if (code) {
            return "`" + content + "`";
        }
        return content;
    }

    /**
     * Сравнивает данный текстовый элемент с другим объектом.
     *
     * @param obj объект для сравнения
     * @return true, если объекты равны (одинаковое содержимое и стили), иначе false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Text text = (Text) obj;
        return Objects.equals(content, text.content)
                && bold == text.bold
                && italic == text.italic
                && strikethrough == text.strikethrough
                && code == text.code;
    }

    /**
     * Возвращает хэш-код текстового элемента.
     *
     * @return хэш-код, вычисленный на основе содержимого и стилей
     */
    @Override
    public int hashCode() {
        return Objects.hash(content, bold, italic, strikethrough, code);
    }
}