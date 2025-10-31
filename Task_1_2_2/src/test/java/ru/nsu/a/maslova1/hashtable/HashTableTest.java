package ru.nsu.a.maslova1.hashtable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import org.junit.jupiter.api.Test;

class HashTableTest {

    @Test
    void testEmptyTable() {
        HashTable<String, Integer> table = new HashTable<>();
        assertEquals(0, table.getSize());
        assertEquals(16, table.getCapacity());
        assertNull(table.get("key"));
        assertFalse(table.containsKey("key"));
    }

    @Test
    void testPutAndGet() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("one", 1);
        table.put("two", 2);

        assertEquals(2, table.getSize());
        assertEquals(1, table.get("one"));
        assertEquals(2, table.get("two"));
        assertNull(table.get("three"));
    }

    @Test
    void testUpdate() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("key", 10);
        table.update("key", 20);

        assertEquals(20, table.get("key"));
        assertEquals(1, table.getSize()); // Размер не должен измениться
    }

    @Test
    void testRemove() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("one", 1);
        table.put("two", 2);

        table.remove("one");
        assertEquals(1, table.getSize());
        assertNull(table.get("one"));
        assertEquals(2, table.get("two"));
    }

    @Test
    void testHasValueForKey() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("key", 10);
        table.put("nullKey", null);

        assertTrue(table.containsKey("key"));
        assertFalse(table.containsKey("nullKey")); // значение null
        assertFalse(table.containsKey("unknown"));
    }

    @Test
    void testIterator() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("a", 1);
        table.put("b", 2);
        table.put("c", 3);

        int count = 0;
        for (Node<String, Integer> node : table) {
            assertNotNull(node.getKey());
            assertNotNull(node.getValue());
            count++;
        }
        assertEquals(3, count);
    }

    @Test
    void testIteratorEmptyTable() {
        HashTable<String, Integer> table = new HashTable<>();
        Iterator<Node<String, Integer>> it = table.iterator();
        assertFalse(it.hasNext());
    }

    @Test
    void testConcurrentModification() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("a", 1);
        table.put("b", 2);

        Iterator<Node<String, Integer>> it = table.iterator();
        table.put("c", 3); // Модифицируем таблицу во время итерации

        assertThrows(ConcurrentModificationException.class, it::next);
    }

    @Test
    void testEquals() {
        HashTable<String, Integer> table1 = new HashTable<>();
        HashTable<String, Integer> table2 = new HashTable<>();

        table1.put("a", 1);
        table1.put("b", 2);

        table2.put("a", 1);
        table2.put("b", 2);

        assertTrue(table1.equals(table2));

        table2.put("c", 3);
        assertFalse(table1.equals(table2));
    }

    @Test
    void testToString() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("a", 1);
        table.put("b", 2);

        String result = table.toString();
        assertTrue(result.contains("a"));
        assertTrue(result.contains("b"));
        assertTrue(result.contains("1"));
        assertTrue(result.contains("2"));
    }

    @Test
    void testCollisions() {
        HashTable<String, Integer> table = new HashTable<>();
        // Добавляем элементы которые могут создать коллизии
        table.put("abc", 1);
        table.put("cba", 2);
        table.put("bac", 3);

        assertEquals(3, table.getSize());
        assertEquals(1, table.get("abc"));
        assertEquals(2, table.get("cba"));
        assertEquals(3, table.get("bac"));
    }

    @Test
    void testNullKey() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put(null, 42);

        assertEquals(42, table.get(null));
        assertTrue(table.containsKey(null));
    }
}