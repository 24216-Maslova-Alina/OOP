package ru.nsu.a.maslova1.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.a.maslova1.graph.graphrepresentation.AdjacencyList;
import ru.nsu.a.maslova1.graph.graphrepresentation.AdjacencyMatrix;
import ru.nsu.a.maslova1.graph.graphrepresentation.Graph;

class GraphTest {

    @Test
    void testEqualsSameGraph() {
        Graph graph1 = new AdjacencyList();

        graph1.addVertex(1);
        graph1.addVertex(2);
        graph1.addEdge(1, 2);

        Graph graph2 = new AdjacencyList();

        graph2.addVertex(1);
        graph2.addVertex(2);
        graph2.addEdge(1, 2);

        assertTrue(graph1.equals(graph2));
        assertTrue(graph2.equals(graph1));
    }

    @Test
    void testEqualsDifferentGraphStructures() {
        Graph graph1 = new AdjacencyList();

        // Граф 1: 1 -> 2 -> 3
        graph1.addVertex(1);
        graph1.addVertex(2);
        graph1.addVertex(3);
        graph1.addEdge(1, 2);
        graph1.addEdge(2, 3);

        Graph graph2 = new AdjacencyList();

        // Граф 2: 1 -> 3, 2 -> 3
        graph2.addVertex(1);
        graph2.addVertex(2);
        graph2.addVertex(3);
        graph2.addEdge(1, 3);
        graph2.addEdge(2, 3);

        assertFalse(graph1.equals(graph2));
        assertFalse(graph2.equals(graph1));
    }

    @Test
    void testEqualsDifferentVertices() {
        Graph graph1 = new AdjacencyList();

        graph1.addVertex(1);
        graph1.addVertex(2);
        graph1.addEdge(1, 2);

        Graph graph2 = new AdjacencyList();

        graph2.addVertex(1);
        graph2.addVertex(3); // Разные вершины
        graph2.addEdge(1, 3);

        assertFalse(graph1.equals(graph2));
        assertFalse(graph2.equals(graph1));
    }

    @Test
    void testEqualsDifferentEdgeDirections() {
        Graph graph1 = new AdjacencyList();

        // Граф 1: 1 -> 2
        graph1.addVertex(1);
        graph1.addVertex(2);
        graph1.addEdge(1, 2);

        Graph graph2 = new AdjacencyList();

        // Граф 2: 2 -> 1
        graph2.addVertex(1);
        graph2.addVertex(2);
        graph2.addEdge(2, 1);

        assertFalse(graph1.equals(graph2));
        assertFalse(graph2.equals(graph1));
    }

    @Test
    void testEqualsWithSelfLoops() {
        Graph graph1 = new AdjacencyList();

        // Граф 1: с петлей
        graph1.addVertex(1);
        graph1.addEdge(1, 1);

        Graph graph2 = new AdjacencyList();

        // Граф 2: без петли
        graph2.addVertex(1);

        assertFalse(graph1.equals(graph2));
        assertFalse(graph2.equals(graph1));
    }

    @Test
    void testEqualsEmptyGraphs() {
        Graph graph1 = new AdjacencyList();
        Graph graph2 = new AdjacencyList();

        assertTrue(graph1.equals(graph2));
        assertTrue(graph2.equals(graph1));
    }

    @Test
    void testEqualsWithNull() {
        Graph graph = new AdjacencyList();
        graph.addVertex(1);

        assertFalse(graph.equals(null));
    }

    @Test
    void testEqualsSameGraphDifferentImplementations() {
        Graph listGraph = new AdjacencyList();

        listGraph.addVertex(1);
        listGraph.addVertex(2);
        listGraph.addVertex(3);
        listGraph.addEdge(1, 2);
        listGraph.addEdge(2, 3);

        Graph matrixGraph = new AdjacencyMatrix();

        matrixGraph.addVertex(1);
        matrixGraph.addVertex(2);
        matrixGraph.addVertex(3);
        matrixGraph.addEdge(1, 2);
        matrixGraph.addEdge(2, 3);

        assertTrue(listGraph.equals(matrixGraph));
        assertTrue(matrixGraph.equals(listGraph));
    }

    @Test
    void testEqualsMultipleEdgesSameVertex() {
        Graph graph1 = new AdjacencyList();

        // Граф 1: 1 -> 2, 1 -> 3
        graph1.addVertex(1);
        graph1.addVertex(2);
        graph1.addVertex(3);
        graph1.addEdge(1, 2);
        graph1.addEdge(1, 3);

        Graph graph2 = new AdjacencyList();

        // Граф 2: 1 -> 3, 1 -> 2 (тот же набор рёбер)
        graph2.addVertex(1);
        graph2.addVertex(2);
        graph2.addVertex(3);
        graph2.addEdge(1, 3);
        graph2.addEdge(1, 2);

        assertTrue(graph1.equals(graph2)); // Порядок не важен
        assertTrue(graph2.equals(graph1));
    }

    @Test
    void testEqualsIsolatedVertices() {
        Graph graph1 = new AdjacencyList();

        graph1.addVertex(1);
        graph1.addVertex(2);
        graph1.addVertex(3);

        Graph graph2 = new AdjacencyList();

        graph2.addVertex(1);
        graph2.addVertex(2);
        graph2.addVertex(3);

        assertTrue(graph1.equals(graph2));
        assertTrue(graph2.equals(graph1));
    }

    @Test
    void testEqualsReflexivity() {
        Graph graph = new AdjacencyList();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);

        assertTrue(graph.equals(graph));
    }

    @Test
    void testEqualsSymmetry() {
        Graph graph1 = new AdjacencyList();

        graph1.addVertex(1);
        graph1.addVertex(2);
        graph1.addEdge(1, 2);

        Graph graph2 = new AdjacencyList();

        graph2.addVertex(1);
        graph2.addVertex(2);
        graph2.addEdge(1, 2);

        // Проверяем симметричность: a.equals(b) == b.equals(a)
        assertEquals(graph1.equals(graph2), graph2.equals(graph1));
    }
}