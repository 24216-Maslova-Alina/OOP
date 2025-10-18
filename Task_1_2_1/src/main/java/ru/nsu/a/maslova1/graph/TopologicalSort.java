package ru.nsu.a.maslova1.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Класс для топологической сортировки графов.
 * Реализует алгоритмы Кана и DFS для топологической сортировки.
 */
public class TopologicalSort {

    /**
     * Выполняет топологическую сортировку графа с помощью алгоритма Кана.
     *
     * @param graph граф для сортировки
     * @return список вершин в топологическом порядке
     */
    public static List<Integer> topologicalSort(Graph graph) {
        Map<Integer, Integer> inDegree = new HashMap<>();
        Queue<Integer> queue = new LinkedList<>();
        List<Integer> result = new ArrayList<>();

        calculateInDegree(graph, inDegree);

        for(Map.Entry<Integer, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.offer(entry.getKey());
            }
        }

        while (!queue.isEmpty()) {
            int current = queue.poll();
            result.add(current);

            for(int neighbor : graph.getNeighbors(current)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        if (result.size() != inDegree.size()) {
            throw new IllegalArgumentException("Граф содержит цикл! " +
                    "Топологическая сортировка невозможна.");
        }

        return result;
    }

    /**
     * Вычисляет полустепень захода для каждой вершины.
     *
     * @param graph граф для расчета
     * @param inDegree карта для хранения полустепеней захода
     */
    private static void calculateInDegree(Graph graph, Map<Integer, Integer> inDegree) {
        for(int vertex : graph.getAllVertices()) {
            inDegree.put(vertex, 0);
        }

        for(int vertex : graph.getAllVertices()) {
            for(int neighbor : graph.getNeighbors(vertex)) {
                inDegree.put(neighbor, inDegree.get(neighbor) + 1);
            }
        }
    }
}