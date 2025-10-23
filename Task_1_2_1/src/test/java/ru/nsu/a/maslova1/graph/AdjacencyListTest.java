package ru.nsu.a.maslova1.graph;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AdjacencyListTest {
    private Graph graph;

    @BeforeEach
    void setUp() {
        graph = new AdjacencyList();
    }

    @Test
    void testAddVertex() {
        graph.addVertex(1);
        graph.addVertex(2);

        Set<Integer> vertices = graph.getAllVertices();
        assertEquals(2, vertices.size());
        assertTrue(vertices.contains(1));
        assertTrue(vertices.contains(2));
    }

    @Test
    void testAddDuplicateVertex() {
        graph.addVertex(1);
        graph.addVertex(1); // Дубликат

        Set<Integer> vertices = graph.getAllVertices();
        assertEquals(1, vertices.size());
        assertTrue(vertices.contains(1));
    }

    @Test
    void testRemoveVertex() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        graph.removeVertex(2);

        Set<Integer> vertices = graph.getAllVertices();
        assertEquals(2, vertices.size());
        assertTrue(vertices.contains(1));
        assertTrue(vertices.contains(3));
        assertFalse(vertices.contains(2));
    }

    @Test
    void testRemoveNonExistentVertex() {
        graph.addVertex(1);

        // Не должно быть исключения
        assertDoesNotThrow(() -> graph.removeVertex(999));

        Set<Integer> vertices = graph.getAllVertices();
        assertEquals(1, vertices.size());
        assertTrue(vertices.contains(1));
    }

    @Test
    void testAddEdge() {
        graph.addVertex(1);
        graph.addVertex(2);

        graph.addEdge(1, 2);

        List<Integer> neighbors = graph.getNeighbors(1);
        assertEquals(1, neighbors.size());
        assertTrue(neighbors.contains(2));

        List<Integer> outgoing = graph.getOutgoingNeighbors(1);
        assertEquals(1, outgoing.size());
        assertTrue(outgoing.contains(2));
    }

    @Test
    void testAddEdgeThrowsExceptionWhenVertexMissing() {
        graph.addVertex(1);
        // Вершина 2 не добавлена

        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(1, 2));
    }

    @Test
    void testRemoveEdge() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);

        graph.removeEdge(1, 2);

        List<Integer> neighbors = graph.getNeighbors(1);
        assertTrue(neighbors.isEmpty());
    }

    @Test
    void testRemoveNonExistentEdge() {
        graph.addVertex(1);
        graph.addVertex(2);

        // Не должно быть исключения
        assertDoesNotThrow(() -> graph.removeEdge(1, 2));
    }

    @Test
    void testGetNeighborsForNonExistentVertex() {
        List<Integer> neighbors = graph.getNeighbors(999);
        assertTrue(neighbors.isEmpty());
    }

    @Test
    void testGetOutgoingNeighborsForNonExistentVertex() {
        List<Integer> outgoing = graph.getOutgoingNeighbors(999);
        assertTrue(outgoing.isEmpty());
    }

    @Test
    void testGetAllVerticesEmptyGraph() {
        Set<Integer> vertices = graph.getAllVertices();
        assertTrue(vertices.isEmpty());
    }

    @Test
    void testComplexGraphStructure() {
        // Создаем граф: 1 -> 2 -> 3
        //               \-> 4
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);

        graph.addEdge(1, 2);
        graph.addEdge(1, 4);
        graph.addEdge(2, 3);

        // Проверяем соседей вершины 1
        List<Integer> neighbors1 = graph.getNeighbors(1);
        assertEquals(2, neighbors1.size());
        assertTrue(neighbors1.contains(2));
        assertTrue(neighbors1.contains(4));

        // Проверяем исходящих соседей вершины 1
        List<Integer> outgoing1 = graph.getOutgoingNeighbors(1);
        assertEquals(2, outgoing1.size());
        assertTrue(outgoing1.contains(2));
        assertTrue(outgoing1.contains(4));

        // Проверяем вершину 3 (нет исходящих рёбер)
        List<Integer> neighbors3 = graph.getNeighbors(3);
        assertTrue(neighbors3.isEmpty());

        List<Integer> outgoing3 = graph.getOutgoingNeighbors(3);
        assertTrue(outgoing3.isEmpty());

        // Проверяем все вершины
        Set<Integer> vertices = graph.getAllVertices();
        assertEquals(4, vertices.size());
        assertTrue(vertices.containsAll(List.of(1, 2, 3, 4)));
    }

    @Test
    void testRemoveVertexAlsoRemovesReferences() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1);

        // Удаляем вершину 2
        graph.removeVertex(2);

        // Проверяем, что ссылки на вершину 2 удалены
        List<Integer> neighbors1 = graph.getNeighbors(1);
        assertFalse(neighbors1.contains(2));

        List<Integer> neighbors3 = graph.getNeighbors(3);
        assertFalse(neighbors3.contains(2));

        // Проверяем оставшиеся вершины
        Set<Integer> vertices = graph.getAllVertices();
        assertEquals(2, vertices.size());
        assertTrue(vertices.contains(1));
        assertTrue(vertices.contains(3));
    }

    @Test
    void testOutputGraphDoesNotThrowException() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);

        // Проверяем, что метод не бросает исключение
        assertDoesNotThrow(() -> graph.outputGraph());
    }
}