package ru.nsu.a.maslova1.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class TopologicalSortTest {

    @Test
    void testTopologicalSortSimpleGraph() {
        Graph graph = new AdjacencyList();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        List<Integer> result = TopologicalSort.topologicalSort(graph);
        assertEquals(3, result.size());
        assertEquals(1, result.get(0));
        assertEquals(2, result.get(1));
        assertEquals(3, result.get(2));
    }

    @Test
    void testTopologicalSortMultipleSources() {
        Graph graph = new AdjacencyList();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);

        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);

        List<Integer> result = TopologicalSort.topologicalSort(graph);
        assertEquals(4, result.size());

        // 1 и 2 должны быть перед 3
        assertTrue(result.indexOf(1) < result.indexOf(3));
        assertTrue(result.indexOf(2) < result.indexOf(3));
        assertTrue(result.indexOf(3) < result.indexOf(4));
    }

    @Test
    void testTopologicalSortThrowsOnCycle() {
        Graph graph = new AdjacencyList();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1); // Цикл

        assertThrows(IllegalArgumentException.class, () -> {
            TopologicalSort.topologicalSort(graph);
        });
    }

    @Test
    void testTopologicalSortSingleVertex() {
        Graph graph = new AdjacencyList();
        graph.addVertex(1);

        List<Integer> result = TopologicalSort.topologicalSort(graph);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0));
    }

    @Test
    void testTopologicalSortIsolatedVertices() {
        Graph graph = new AdjacencyList();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);

        // Нет рёбер - все вершины изолированы

        List<Integer> result = TopologicalSort.topologicalSort(graph);
        assertEquals(3, result.size());
        // Любой порядок допустим для изолированных вершин
    }

    @Test
    void testTopologicalSortComplexGraph() {
        Graph graph = new AdjacencyList();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);

        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);

        List<Integer> result = TopologicalSort.topologicalSort(graph);
        assertEquals(5, result.size());

        // Проверяем зависимости
        assertTrue(result.indexOf(1) < result.indexOf(2));
        assertTrue(result.indexOf(1) < result.indexOf(3));
        assertTrue(result.indexOf(2) < result.indexOf(4));
        assertTrue(result.indexOf(3) < result.indexOf(4));
        assertTrue(result.indexOf(4) < result.indexOf(5));
    }

    @Test
    void testTopologicalSortWithDifferentGraphImplementations() {
        // Проверяем, что алгоритм работает с разными реализациями графа
        Graph[] graphs = {
            new AdjacencyList(),
            new AdjacencyMatrix(),
            new IncidenceMatrix()
        };

        for (Graph graph : graphs) {
            graph.addVertex(1);
            graph.addVertex(2);
            graph.addVertex(3);

            graph.addEdge(1, 2);
            graph.addEdge(2, 3);

            List<Integer> result = TopologicalSort.topologicalSort(graph);
            assertEquals(3, result.size());
            assertEquals(1, result.get(0));
            assertEquals(2, result.get(1));
            assertEquals(3, result.get(2));
        }
    }
}