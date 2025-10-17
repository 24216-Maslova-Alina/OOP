package ru.nsu.a.maslova1.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Реализация графа с использованием матрицы смежности.
 * Матрица смежности представляет собой двумерный массив, где matrix[i][j] = 1
 * указывает на наличие ребра между вершинами i и j.
 */
public class AdjacencyMatrix implements Graph {
    private int[][] matrix;
    private int vertexCount;
    private Set<Integer> vertexSet = new HashSet<>();

    /**
     * Добавляет вершину в граф.
     * Если вершина уже существует, метод не выполняет никаких действий.
     * При добавлении новой вершины матрица смежности пересоздается с увеличенным размером.
     *
     * @param vertex идентификатор добавляемой вершины
     */
    @Override
    public void addVertex(int vertex) {
        if (vertexSet.contains(vertex)) {
            return;
        }

        vertexSet.add(vertex);
        int[][] newMatrix = new int[vertexCount + 1][vertexCount + 1];

        if (matrix != null) {
            for (int i = 0; i < vertexCount; i++) {
                System.arraycopy(matrix[i], 0, newMatrix[i], 0, vertexCount);
            }
        }

        matrix = newMatrix;
        vertexCount++;
    }

    /**
     * Удаляет вершину из графа.
     * Если вершина не существует, метод не выполняет никаких действий.
     * При удалении вершины матрица смежности пересоздается с уменьшенным размером.
     *
     * @param vertex идентификатор удаляемой вершины
     */
    @Override
    public void removeVertex(int vertex) {
        if (!vertexSet.contains(vertex)) {
            return;
        }

        vertexSet.remove(vertex);
        int vertexIndex = getVertexIndex(vertex);
        int[][] newMatrix = new int[vertexCount - 1][vertexCount - 1];

        for (int i = 0, newI = 0; i < vertexCount; i++) {
            if (i == vertexIndex) {
                continue;
            }
            for (int j = 0, newJ = 0; j < vertexCount; j++) {
                if (j == vertexIndex) {
                    continue;
                }

                newMatrix[newI][newJ] = matrix[i][j];
                newJ++;
            }
            newI++;
        }

        matrix = newMatrix;
        vertexCount--;
    }

    /**
     * Добавляет ребро между двумя вершинами.
     * Если одна или обе вершины не существуют в графе, выбрасывается исключение.
     * Для неориентированного графа ребро добавляется в обоих направлениях.
     *
     * @param from идентификатор исходной вершины
     * @param to идентификатор конечной вершины
     */
    @Override
    public void addEdge(int from, int to) {
        if (!vertexSet.contains(from) || !vertexSet.contains(to)) {
            throw new IllegalArgumentException("Not all vertices must exist in the graph");
        }

        int fromIndex = getVertexIndex(from);
        int toIndex = getVertexIndex(to);

        matrix[fromIndex][toIndex] = 1;
        matrix[toIndex][fromIndex] = 1;
    }

    /**
     * Удаляет ребро между двумя вершинами.
     * Если одна или обе вершины не существуют в графе, выбрасывается исключение.
     * Для неориентированного графа ребро удаляется в обоих направлениях.
     *
     * @param from идентификатор исходной вершины
     * @param to идентификатор конечной вершины
     */
    @Override
    public void removeEdge(int from, int to) {
        if (!vertexSet.contains(from) || !vertexSet.contains(to)) {
            throw new IllegalArgumentException("Not all vertices must exist in the graph");
        }

        int fromIndex = getVertexIndex(from);
        int toIndex = getVertexIndex(to);

        matrix[fromIndex][toIndex] = 0;
        matrix[toIndex][fromIndex] = 0;
    }

    /**
     * Возвращает список соседей указанной вершины.
     * Соседями считаются все вершины, соединенные ребром с данной вершиной.
     *
     * @param vertex идентификатор вершины, для которой запрашиваются соседи
     * @return список идентификаторов соседних вершин; пустой список, если вершина не существует
     */
    @Override
    public List<Integer> getNeighbors(int vertex) {
        if (!vertexSet.contains(vertex)) {
            return List.of();
        }

        List<Integer> neighbors = new ArrayList<>();
        int vertexIndex = getVertexIndex(vertex);

        for (int i = 0; i < vertexCount; i++) {
            if (matrix[vertexIndex][i] == 1) {
                int neighbor = getVertexByIndex(i);
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }

    /**
     * Выводит граф в заданном формате.
     * Реализация метода зависит от конкретных требований к выводу.
     *
     * @param graph матрица представления графа для вывода
     */
    @Override
    public void outputGragh(int[][] graph) {

    }

    /**
     * Возвращает идентификатор вершины по ее индексу в матрице смежности.
     *
     * @param index индекс вершины в матрице смежности
     * @return идентификатор вершины
     */
    private int getVertexByIndex(int index) {
        int current = 0;
        for (int v : vertexSet) {
            if (current == index) {
                return v;
            }
            current++;
        }
        throw new IllegalArgumentException("Invalid vertex index");
    }

    /**
     * Возвращает индекс вершины в матрице смежности по ее идентификатору.
     *
     * @param vertex идентификатор вершины
     * @return индекс вершины в матрице смежности или -1, если вершина не найдена
     */
    private int getVertexIndex(int vertex) {
        int index = 0;
        for (int v : vertexSet) {
            if (v == vertex) {
                return index;
            }
            index++;
        }
        return -1;
    }
}