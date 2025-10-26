package ru.nsu.a.maslova1.graph.graphRepresentation;


import ru.nsu.a.maslova1.graph.sort.Sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Реализация графа с использованием списка смежности.
 */
public class AdjacencyList implements Graph {
    private final Map<Integer, List<Integer>> adjMap;

    /**
     * Конструктор по умолчанию.
     */
    public AdjacencyList() {
        this.adjMap = new HashMap<>();
    }

    /**
     * Добавляет вершину в граф.
     *
     * @param vertex вершина
     */
    @Override
    public void addVertex(int vertex) {
        adjMap.putIfAbsent(vertex, new ArrayList<>());
    }

    /**
     * Удаляет вершину и все ссылки на нее.
     *
     * @param vertex вершина
     */
    @Override
    public void removeVertex(int vertex) {
        adjMap.remove(vertex);
        for (List<Integer> v : adjMap.values()) {
            v.removeIf(neighbor -> (neighbor == vertex));
        }
    }

    /**
     * Добавляет ребро между вершинами.
     *
     * @param from идентификатор исходной вершины
     * @param to   идентификатор конечной вершины
     */
    @Override
    public void addEdge(int from, int to) {
        if (!adjMap.containsKey(from) || !adjMap.containsKey(to)) {
            throw new IllegalArgumentException("Not all vertices must exist in the graph");
        }

        adjMap.get(from).add(to);
    }

    /**
     * Удаляет ребро между вершинами.
     *
     * @param from идентификатор исходной вершины
     * @param to   идентификатор конечной вершины
     */
    @Override
    public void removeEdge(int from, int to) {
        if (!adjMap.containsKey(from) || !adjMap.containsKey(to)) {
            throw new IllegalArgumentException("Not all vertices must exist in the graph");
        }

        adjMap.get(from).remove(Integer.valueOf(to));
    }

    /**
     * Возвращает список соседей вершины.
     *
     * @param vertex вершина
     */
    @Override
    public List<Integer> getNeighbors(int vertex) {
        return adjMap.getOrDefault(vertex, new ArrayList<>());
    }

    /**
     * Выводит граф в консоль.
     */
    @Override
    public void outputGraph() {
        System.out.println("Список смежности:");
        for (Map.Entry<Integer, List<Integer>> graph : adjMap.entrySet()) {
            System.out.println(graph.getKey() + ": " + graph.getValue());
        }
    }

    /**
     * Возвращает множество всех вершин графа.
     *
     * @return все вершины графа
     */
    @Override
    public Set<Integer> getAllVertices() {
        return new HashSet<>(adjMap.keySet());
    }

    /**
     * Возвращает множество исходящих соседей.
     *
     * @param vertex вершина
     * @return исходящие соседи
     */
    @Override
    public List<Integer> getOutgoingNeighbors(int vertex) {
        return adjMap.getOrDefault(vertex, new ArrayList<>());
    }

    /**
     * Метод сортировки графа.
     */
    @Override
    public void sorted(Sort sort) {
        sort.sorted(this);
    }
}