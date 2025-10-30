package ru.nsu.a.maslova1.hashtable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class NodeTest {

    @Test
    void testNodeCreation() {
        Node<String, Integer> node = new Node<>("key", 42);
        assertEquals("key", node.getKey());
        assertEquals(42, node.getValue());
        assertNull(node.getNext());
    }

    @Test
    void testSetValue() {
        Node<String, Integer> node = new Node<>("key", 10);
        node.setValue(20);
        assertEquals(20, node.getValue());
        assertEquals("key", node.getKey()); // Ключ не должен измениться
    }

    @Test
    void testSetNext() {
        Node<String, Integer> node1 = new Node<>("a", 1);
        Node<String, Integer> node2 = new Node<>("b", 2);

        node1.setNext(node2);
        assertEquals(node2, node1.getNext());
        assertEquals("b", node1.getNext().getKey());
    }

    @Test
    void testEqualsSameObject() {
        Node<String, Integer> node = new Node<>("key", 10);
        assertTrue(node.equals(node));
    }

    @Test
    void testEqualsSameValues() {
        Node<String, Integer> node1 = new Node<>("key", 10);
        Node<String, Integer> node2 = new Node<>("key", 10);

        assertTrue(node1.equals(node2));
        assertTrue(node2.equals(node1));
    }

    @Test
    void testEqualsDifferentKeys() {
        Node<String, Integer> node1 = new Node<>("key1", 10);
        Node<String, Integer> node2 = new Node<>("key2", 10);

        assertFalse(node1.equals(node2));
    }

    @Test
    void testEqualsDifferentValues() {
        Node<String, Integer> node1 = new Node<>("key", 10);
        Node<String, Integer> node2 = new Node<>("key", 20);

        assertFalse(node1.equals(node2));
    }

    @Test
    void testEqualsNull() {
        Node<String, Integer> node = new Node<>("key", 10);
        assertFalse(node.equals(null));
    }

    @Test
    void testEqualsDifferentClass() {
        Node<String, Integer> node = new Node<>("key", 10);
        assertFalse(node.equals("not a node"));
    }

    @Test
    void testHashCodeConsistency() {
        Node<String, Integer> node = new Node<>("key", 10);
        int hash1 = node.hashCode();
        int hash2 = node.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    void testHashCodeSameValues() {
        Node<String, Integer> node1 = new Node<>("key", 10);
        Node<String, Integer> node2 = new Node<>("key", 10);

        assertEquals(node1.hashCode(), node2.hashCode());
    }

    @Test
    void testHashCodeDifferentKeys() {
        Node<String, Integer> node1 = new Node<>("key1", 10);
        Node<String, Integer> node2 = new Node<>("key2", 10);

        // Хеш-коды могут быть разными (но не обязательно)
        // Главное - если equals возвращает false, hashCode может быть разным
        assertNotEquals(node1, node2);
    }

    @Test
    void testToString() {
        Node<String, Integer> node = new Node<>("test", 123);
        String result = node.toString();
        assertTrue(result.contains("test"));
        assertTrue(result.contains("123"));
        assertTrue(result.startsWith("{"));
        assertTrue(result.endsWith("}"));
    }

    @Test
    void testNodeChain() {
        Node<String, Integer> node1 = new Node<>("a", 1);
        Node<String, Integer> node2 = new Node<>("b", 2);
        Node<String, Integer> node3 = new Node<>("c", 3);

        node1.setNext(node2);
        node2.setNext(node3);

        assertEquals(node2, node1.getNext());
        assertEquals(node3, node2.getNext());
        assertNull(node3.getNext());
    }

    @Test
    void testWithNullValue() {
        Node<String, Integer> node = new Node<>("key", null);
        assertEquals("key", node.getKey());
        assertNull(node.getValue());

        // Проверяем что toString и equals работают с null значением
        assertNotNull(node.toString());

        Node<String, Integer> node2 = new Node<>("key", null);
        assertTrue(node.equals(node2));
    }
}