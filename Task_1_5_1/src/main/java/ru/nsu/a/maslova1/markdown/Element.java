package ru.nsu.a.maslova1.markdown;

/**
 * Абстрактный базовый класс для всех элементов Markdown.
 * Определяет общий интерфейс для преобразования элементов в формат Markdown.
 */
public abstract class Element {

    /**
     * Преобразует элемент в строку в формате Markdown.
     *
     * @return строка, представляющая элемент в формате Markdown
     */
    public abstract String toMarkdown();

    /**
     * Сравнивает данный элемент с другим объектом.
     * Должен быть реализован в каждом конкретном классе-наследнике.
     *
     * @param obj объект для сравнения
     * @return true, если объекты равны, иначе false
     */
    @Override
    public abstract boolean equals(Object obj);

    /**
     * Возвращает хэш-код элемента.
     * Должен быть реализован в каждом конкретном классе-наследнике.
     *
     * @return хэш-код элемента
     */
    @Override
    public abstract int hashCode();
}