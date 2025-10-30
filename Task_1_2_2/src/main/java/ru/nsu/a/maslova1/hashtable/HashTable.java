package ru.nsu.a.maslova1.hashtable;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Objects;

/**
 * Параметризованная хеш-таблица, реализующая ассоциативный массив.
 * Поддерживает добавление, поиск и удаление объектов за константное время в среднем.
 * Для разрешения коллизий используется метод цепочек.
 *
 * @param <K> тип ключей
 * @param <V> тип значений
 */
public class HashTable<K, V> implements Iterable<Node<K, V>> {
    private Node<K, V>[] tab;
    private int size;
    private int capacity;
    private int modCount;

    /**
     * Создает новую пустую хеш-таблицу с начальной вместимостью 16.
     */
    public HashTable() {
        this.capacity = 16;
        this.size = 0;
        this.tab =  (Node<K, V>[]) new Node[this.capacity];
        this.modCount = 0;
    }

    /**
     * Вычисляет индекс корзины для заданного ключа.
     *
     * @param key ключ для вычисления индекса
     * @return индекс в диапазоне [0, capacity-1]
     */
    private int getIndex(K key) {
        if (key == null) {
            return 0;
        }
        int hash = key.hashCode();
        hash = hash ^ (hash >>> 16);

        return Math.abs(hash % capacity);
    }

    /**
     * Добавляет пару ключ-значение в таблицу.
     * Если ключ уже существует, обновляет значение.
     *
     * @param key ключ для добавления
     * @param value значение для добавления
     */
    public void put(K key, V value) {
        if (size >= capacity) {
            resize();
        }
        int index = getIndex(key);

        Node<K, V> current = tab[index];
        while (current != null) {
            if (Objects.equals(current.getKey(), key)) {
                current.setValue(value);
                return;
            }
            current = current.getNext();

        }

        Node<K, V> newNode = new Node<>(key, value);
        newNode.setNext(tab[index]);
        tab[index] = newNode;
        size++;
        modCount++;
    }

    /**
     * Увеличивает вместимость таблицы в 2 раза и перераспределяет все элементы.
     */
    private void resize() {
        int newCapacity = capacity * 2;
        Node<K, V>[] newTab = (Node<K, V>[]) new Node[newCapacity];
        tab = newTab;
        capacity = newCapacity;
        size = 0;

        Node<K, V>[] oldTab = tab;
        for (Node<K, V> kvNode : oldTab) {
            Node<K, V> current = kvNode;
            while (current != null) {
                int index = getIndex(current.getKey());
                Node<K, V> newNode = new Node<>(current.getKey(), current.getValue());
                newNode.setNext(tab[index]);
                tab[index] = newNode;
                size++;

                current = current.getNext();
            }
        }
        modCount++;
    }

    /**
     * Удаляет пару ключ-значение из таблицы по ключу.
     * Если ключ не найден, ничего не делает.
     *
     * @param key ключ для удаления
     */
    public void remove(K key) {
        int index = getIndex(key);

        Node<K, V> current = tab[index];
        Node<K, V> prev = null;
        while (current != null) {
            if (Objects.equals(current.getKey(), key)) {
                if (prev == null) {
                    tab[index] = current.getNext();
                } else {
                    prev.setNext(current.getNext());
                }

                size--;
                return;
            }

            prev = current;
            current = current.getNext();
        }
        modCount++;
    }

    /**
     * Возвращает значение по ключу.
     *
     * @param key ключ для поиска
     * @return значение или null, если ключ не найден
     */
    public V get(K key) {
        int index = getIndex(key);

        Node<K, V> current = tab[index];
        while (current != null) {
            if (Objects.equals(current.getKey(), key)) {
                return current.getValue();
            }
            current = current.getNext();

        }
        return null;
    }

    /**
     * Обновляет значение по ключу.
     * Если ключ не найден, ничего не делает.
     *
     * @param key ключ для обновления
     * @param valueNew новое значение
     */
    public void update(K key, V valueNew) {
        int index = getIndex(key);

        Node<K, V> current = tab[index];
        while (current != null) {
            if (Objects.equals(current.getKey(), key)) {
                current.setValue(valueNew);
                return;
            }
            current = current.getNext();

        }
    }

    /**
     * Проверяет наличие не-null значения по ключу.
     *
     * @param key ключ для проверки
     * @return true если ключ существует и значение не null, иначе false
     */
    public boolean hasValueForKey(K key) {
        int index = getIndex(key);
        Node<K, V> current = tab[index];

        while (current != null) {
            if (Objects.equals(current.getKey(), key)) {
                return current.getValue() != null;
            }
            current = current.getNext();
        }

        return false;
    }

    /**
     * Возвращает количество элементов в таблице.
     *
     * @return количество элементов
     */
    public int getSize() {
        return size;
    }

    /**
     * Возвращает текущую вместимость таблицы.
     *
     * @return вместимость таблицы
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Сравнивает данную таблицу с другим объектом.
     * Две таблицы считаются равными, если содержат одинаковые пары ключ-значение.
     *
     * @param obj объект для сравнения
     * @return true если таблицы равны, иначе false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        HashTable<K, ?> other = (HashTable<K, ?>) obj;
        if (this.size != other.size) {
            return false;
        }

        for (int i = 0; i < this.capacity; i++) {
            Node<K, V> current = this.tab[i];
            while (current != null) {
                V thisValue = current.getValue();
                K key = current.getKey();
                Object otherValue = other.get(key);

                if (!Objects.equals(thisValue, otherValue)) {
                    return false;
                }

                current = current.getNext();
            }
        }

        return true;
    }

    /**
     * Возвращает строковое представление таблицы в формате {ключ: значение, ...}.
     *
     * @return строковое представление таблицы
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("{");

        boolean first = true;
        for (int i = 0; i < capacity; i++) {
            Node<K, V> current = tab[i];
            while (current != null) {
                if (!first) {
                    result.append(", ");
                }
                result.append(current.toString());
                first = false;
                current = current.getNext();
            }
        }

        result.append("}");
        return result.toString();
    }

    /**
     * Возвращает итератор для обхода элементов таблицы.
     * Итератор поддерживает проверку на одновременную модификацию.
     *
     * @return итератор по узлам таблицы
     */
    @Override
    public Iterator<Node<K, V>> iterator() {
        return new Iterator<Node<K, V>>() {
            private int currentBucket = 0;
            private Node<K, V> currentNode = null;
            private int expectedModCount = modCount;

            {
                findNextBucket();
            }

            private void findNextBucket() {
                while (currentBucket < capacity && tab[currentBucket] == null) {
                    currentBucket++;
                }
                if (currentBucket < capacity) {
                    currentNode = tab[currentBucket];
                }
            }

            @Override
            public boolean hasNext() {
                checkForModification();
                return currentNode != null;
            }

            @Override
            public Node<K, V> next() {
                checkForModification();

                Node<K, V> resultNode = currentNode;
                currentNode = currentNode.getNext();

                if (currentNode == null) {
                    currentBucket++;
                    findNextBucket();
                }

                return resultNode;
            }

            private void checkForModification() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }
}