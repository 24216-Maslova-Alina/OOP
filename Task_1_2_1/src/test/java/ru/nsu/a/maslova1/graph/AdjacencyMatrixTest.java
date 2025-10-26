package ru.nsu.a.maslova1.graph;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.a.maslova1.graph.graphRepresentation.AdjacencyMatrix;
import ru.nsu.a.maslova1.graph.graphRepresentation.Graph;

class AdjacencyMatrixTest {
    private Graph graph;

    @BeforeEach
    void setUp() {
        graph = new AdjacencyMatrix();
    }

    @Test
    void testAddAndRemoveVertex() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        Set<Integer> vertices = graph.getAllVertices();
        assertEquals(3, vertices.size());

        graph.removeVertex(2);
        vertices = graph.getAllVertices();
        assertEquals(2, vertices.size());
        assertTrue(vertices.contains(1));
        assertTrue(vertices.contains(3));
    }

    @Test
    void testAddAndRemoveEdge() {
        graph.addVertex(1);
        graph.addVertex(2);

        graph.addEdge(1, 2);

        List<Integer> neighbors = graph.getNeighbors(1);
        assertEquals(1, neighbors.size());
        assertTrue(neighbors.contains(2));

        List<Integer> outgoing = graph.getOutgoingNeighbors(1);
        assertEquals(1, outgoing.size());
        assertTrue(outgoing.contains(2));

        graph.removeEdge(1, 2);
        neighbors = graph.getNeighbors(1);
        assertFalse(neighbors.contains(2));
    }

    @Test
    void testAddEdgeThrowsExceptionWhenVertexMissing() {
        graph.addVertex(1);
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(1, 2));
    }

    @Test
    void testGetNeighborsVsOutgoingNeighbors() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        // Вершина 2: входящее от 1, исходящее к 3
        List<Integer> neighbors2 = graph.getNeighbors(2);
        List<Integer> outgoing2 = graph.getOutgoingNeighbors(2);

        assertEquals(2, neighbors2.size()); // и входящие и исходящие
        assertEquals(1, outgoing2.size());  // только исходящие
        assertTrue(neighbors2.contains(1));
        assertTrue(neighbors2.contains(3));
        assertTrue(outgoing2.contains(3));
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

        // Проверяем исходящих соседей
        List<Integer> outgoing1 = graph.getOutgoingNeighbors(1);
        assertEquals(2, outgoing1.size());
        assertTrue(outgoing1.contains(2));
        assertTrue(outgoing1.contains(4));

        List<Integer> outgoing2 = graph.getOutgoingNeighbors(2);
        assertEquals(1, outgoing2.size());
        assertTrue(outgoing2.contains(3));

        List<Integer> outgoing3 = graph.getOutgoingNeighbors(3);
        assertTrue(outgoing3.isEmpty());
    }

    @Test
    void testRemoveVertexWithEdges() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        graph.removeVertex(2);

        Set<Integer> vertices = graph.getAllVertices();
        assertEquals(2, vertices.size());

        // Проверяем, что рёбра удалены
        List<Integer> neighbors1 = graph.getNeighbors(1);
        assertFalse(neighbors1.contains(2));
    }

    @Test
    void testMultipleEdgesFromSameVertex() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        graph.addEdge(1, 2);
        graph.addEdge(1, 3);

        List<Integer> outgoing1 = graph.getOutgoingNeighbors(1);
        assertEquals(2, outgoing1.size());
        assertTrue(outgoing1.contains(2));
        assertTrue(outgoing1.contains(3));
    }

    @Test
    void testOutputGraph() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);

        assertDoesNotThrow(() -> graph.outputGraph());
    }
}