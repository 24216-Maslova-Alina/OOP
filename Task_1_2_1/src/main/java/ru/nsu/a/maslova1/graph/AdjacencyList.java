package ru.nsu.a.maslova1.graph;

import java.util.*;

/**
 * Реализация графа с использованием списка смежности.
 */
public class AdjacencyList implements Graph {
    private final Map<Integer, List<Integer>> adjList;

    /**
     * Конструктор по умолчанию.
     */
    public AdjacencyList() {
        this.adjList = new HashMap<>();
    }

    /**
     * Добавляет вершину в граф.
     *
     * @param vertex вершина
     */
    @Override
    public void addVertex(int vertex) {
        adjList.putIfAbsent(vertex, new ArrayList<>());
    }

    /**
     * Удаляет вершину и все ссылки на нее.
     *
     * @param vertex вершина
     */
    @Override
    public void removeVertex(int vertex) {
        adjList.remove(vertex);
        for (List<Integer> v: adjList.values()) {
            v.removeIf(neighbor -> (neighbor == vertex));
        }
    }

    /**
     * Добавляет ребро между вершинами.
     *
     * @param from идентификатор исходной вершины
     * @param to идентификатор конечной вершины
     */
    @Override
    public void addEdge(int from, int to) {
        if (!adjList.containsKey(from) | !adjList.containsKey(to)) {
            throw new IllegalArgumentException("Not all vertices must exist in the graph");
        }

        adjList.get(from).add(to);
        adjList.get(to).add(from);
    }

    /**
     * Удаляет ребро между вершинами.
     *
     * @param from идентификатор исходной вершины
     * @param to идентификатор конечной вершины
     */
    @Override
    public void removeEdge(int from, int to) {
        if (!adjList.containsKey(from) | !adjList.containsKey(to)) {
            throw new IllegalArgumentException("Not all vertices must exist in the graph");
        }

        adjList.get(from).remove(to);
        adjList.get(to).remove(from);
    }

    /**
     * Возвращает список соседей вершины.
     *
     * @param vertex вершина
     */
    @Override
    public List<Integer> getNeighbors(int vertex) {
        return adjList.getOrDefault(vertex, new ArrayList<>());
    }

    /**
     * Выводит граф в консоль.
     */
    @Override
    public void outputGraph() {
        System.out.println("Список смежности:");
        for (Map.Entry<Integer, List<Integer>> graph : adjList.entrySet()){
            System.out.println(graph.getKey() + ": " + graph.getValue());
        }
    }

    @Override
    public Set<Integer> getAllVertices() {
        return new HashSet<>(adjList.keySet());
    }
}