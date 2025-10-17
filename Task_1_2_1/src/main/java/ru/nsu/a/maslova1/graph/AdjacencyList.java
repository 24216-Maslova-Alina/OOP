package ru.nsu.a.maslova1.graph;

import java.util.ArrayList;
import java.util.List;

public class AdjacencyList implements Graph {
    private List<List<Integer>> adjList = new ArrayList<>();
    private int vertexCount;
    private int edgeCount;

    @Override
    public void addVertex(int vertex) {
        // Находим позицию для вставки (список отсортирован по ID вершин)
        int index = findInsertIndex(vertex);

        if (index < adjList.size() && getVertexId(index) == vertex) {
            return; // Вершина уже существует
        }

        // Вставляем новую вершину с пустым списком соседей
        adjList.add(index, new ArrayList<>());
        setVertexId(index, vertex);
        vertexCount++;
    }

    @Override
    public void removeVertex(int vertex) {
        int index = findVertexIndex(vertex);
        if (index == -1) {
            return;
        }

        // Удаляем все рёбра, связанные с этой вершиной
        List<Integer> neighbors = getNeighbors(vertex);
        for (int neighbor : neighbors) {
            removeEdge(vertex, neighbor);
        }

        // Удаляем саму вершину
        adjList.remove(index);
        vertexCount--;
    }

    @Override
    public void addEdge(int from, int to) {
        int fromIndex = findVertexIndex(from);
        int toIndex = findVertexIndex(to);

        if (fromIndex == -1 || toIndex == -1) {
            throw new IllegalArgumentException("Both vertices must exist in the graph");
        }

        // Добавляем to в список соседей from
        List<Integer> fromNeighbors = adjList.get(fromIndex);
        if (!fromNeighbors.contains(to)) {
            fromNeighbors.add(to);
            edgeCount++;
        }

        // Для неориентированного графа добавляем from в список соседей to
        List<Integer> toNeighbors = adjList.get(toIndex);
        if (!toNeighbors.contains(from)) {
            toNeighbors.add(from);
            edgeCount++;
        }
    }

    @Override
    public void removeEdge(int from, int to) {
        int fromIndex = findVertexIndex(from);
        int toIndex = findVertexIndex(to);

        if (fromIndex == -1 || toIndex == -1) {
            return;
        }

        // Удаляем to из списка соседей from
        List<Integer> fromNeighbors = adjList.get(fromIndex);
        if (fromNeighbors.remove((Integer) to)) {
            edgeCount--;
        }

        // Для неориентированного графа удаляем from из списка соседей to
        List<Integer> toNeighbors = adjList.get(toIndex);
        if (toNeighbors.remove((Integer) from)) {
            edgeCount--;
        }
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        int index = findVertexIndex(vertex);
        if (index == -1) {
            return List.of();
        }

        // Возвращаем копию списка соседей
        return new ArrayList<>(adjList.get(index));
    }

    @Override
    public void outputGragh(int[][] graph) {
        // Конвертируем в матрицу смежности для вывода
        int size = getMaxVertexId() + 1;
        int[][] adjMatrix = new int[size][size];

        for (int i = 0; i < adjList.size(); i++) {
            int from = getVertexId(i);
            List<Integer> neighbors = adjList.get(i);
            for (int to : neighbors) {
                adjMatrix[from][to] = 1;
            }
        }

        // Вывод матрицы
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(adjMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Вспомогательные методы

    /**
     * Найти индекс вершины в списке
     */
    private int findVertexIndex(int vertex) {
        for (int i = 0; i < adjList.size(); i++) {
            if (getVertexId(i) == vertex) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Найти позицию для вставки новой вершины (бинарный поиск)
     */
    private int findInsertIndex(int vertex) {
        int left = 0;
        int right = adjList.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midVertex = getVertexId(mid);

            if (midVertex == vertex) {
                return mid;
            } else if (midVertex < vertex) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return left;
    }

    /**
     * Получить ID вершины по индексу в списке
     * В этой реализации мы храним ID как первый элемент списка
     */
    private int getVertexId(int index) {
        List<Integer> vertexData = adjList.get(index);
        return vertexData.get(0); // Первый элемент - ID вершины
    }

    /**
     * Установить ID вершины по индексу
     */
    private void setVertexId(int index, int vertexId) {
        List<Integer> vertexData = adjList.get(index);
        if (vertexData.isEmpty()) {
            vertexData.add(vertexId); // Первый элемент - ID
        } else {
            vertexData.set(0, vertexId);
        }
    }

    /**
     * Получить максимальный ID вершины
     */
    private int getMaxVertexId() {
        if (adjList.isEmpty()) {
            return 0;
        }
        return getVertexId(adjList.size() - 1);
    }
}