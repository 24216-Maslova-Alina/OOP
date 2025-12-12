package ru.nsu.a.maslova1.markdown.elements;

import ru.nsu.a.maslova1.markdown.ContainerType;
import ru.nsu.a.maslova1.markdown.Element;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Класс, представляющий список в Markdown формате.
 */
public class Lists extends Element {
    private final ContainerType type;
    private final List<Element> content;

    /**
     * Конструктор.
     *
     * @param type тип списка (упорядоченный, неупорядоченный или группа)
     * @param content элементы списка
     */
    public Lists(ContainerType type, Element... content) {
        this.type = type;
        this.content = Arrays.asList(content);
    }

    /**
     * Преобразует список в строку в формате Markdown.
     *
     * @return строка в формате Markdown, представляющая список
     */
    @Override
    public String toMarkdown() {
        StringBuilder answer = new StringBuilder();
        if (type == ContainerType.UNORDERED_LIST) {
            for (Element element : content) {
                answer.append("- ").append(element.toMarkdown()).append("\n");
            }
        } else if (type == ContainerType.ORDERED_LIST) {
            int i = 1;
            for (Element element : content) {
                answer.append(i++).append(". ").append(element.toMarkdown()).append("\n");
            }
        } else {
            for (Element element : content) {
                answer.append(element.toMarkdown()).append("\n");
            }
        }
        return answer.toString().trim();
    }

    /**
     * Сравнивает данный список с другим объектом.
     *
     * @param obj объект для сравнения
     * @return true, если объекты равны (одинаковый тип и содержимое), иначе false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Lists list = (Lists) obj;
        return type == list.type
                && Objects.equals(content, list.content);
    }

    /**
     * Возвращает хэш-код списка.
     *
     * @return хэш-код, вычисленный на основе типа и содержимого
     */
    @Override
    public int hashCode() {
        return Objects.hash(type, content);
    }

    /**
     * Создает неупорядоченный (маркированный) список.
     *
     * @param content элементы списка
     * @return новый неупорядоченный список
     */
    public static Lists unorderedList(Element... content) {
        return new Lists(ContainerType.UNORDERED_LIST, content);
    }

    /**
     * Создает упорядоченный (нумерованный) список.
     *
     * @param content элементы списка
     * @return новый упорядоченный список
     */
    public static Lists orderedList(Element... content) {
        return new Lists(ContainerType.ORDERED_LIST, content);
    }
}