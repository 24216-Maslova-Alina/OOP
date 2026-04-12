package ru.nsu.a.maslova1.hashtable;

import java.util.Objects;

/**
 * Узел хеш-таблицы, хранящий пару ключ-значение.
 * Используется для разрешения коллизий методом цепочек.
 *
 * @param <K> тип ключа
 * @param <V> тип значения
 */
public class Node<K, V> {
    private K key;
    private V value;
    private Node<K, V> next;

    /**
     * Создает новый узел с указанными ключом и значением.
     *
     * @param key ключ узла
     * @param value значение узла
     */
    public Node(K key, V value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }

    /**
     * Возвращает ключ узла.
     *
     * @return ключ узла
     */
    public K getKey() {
        return key;
    }

    /**
     * Возвращает значение узла.
     *
     * @return значение узла
     */
    public V getValue() {
        return value;
    }

    /**
     * Возвращает следующий узел в цепочке.
     *
     * @return следующий узел или null, если его нет
     */
    public Node<K, V> getNext() {
        return next;
    }

    /**
     * Устанавливает следующий узел в цепочке.
     *
     * @param next следующий узел
     */
    public void setNext(Node<K, V> next) {
        this.next = next;
    }

    /**
     * Устанавливает новое значение узла.
     *
     * @param value новое значение
     */
    public void setValue(V value) {
        this.value = value;
    }

    /**
     * Возвращает строковое представление узла в формате {ключ: значение}.
     *
     * @return строковое представление узла
     */
    @Override
    public String toString() {
        return "{" + key + ": " + value + "}";
    }

    /**
     * Сравнивает данный узел с другим объектом.
     * Два узла считаются равными, если их ключи и значения равны.
     *
     * @param obj объект для сравнения
     * @return true если узлы равны, иначе false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Node<?, ?> other = (Node<?, ?>) obj;

        return key.equals(other.key)
                && value.equals(other.value);
    }

    /**
     * Возвращает хеш-код узла на основе ключа и значения.
     *
     * @return хеш-код узла
     */
    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}