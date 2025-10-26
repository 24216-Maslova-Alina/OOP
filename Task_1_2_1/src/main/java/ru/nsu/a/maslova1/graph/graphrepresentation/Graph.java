package ru.nsu.a.maslova1.graph.graphrepresentation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.nsu.a.maslova1.graph.sort.Sort;

/**
 * Интерфейс для представления графа.
 * Определяет основные операции для работы с графами.
 */
public interface Graph {

    /**
     * Добавляет вершину в граф.
     *
     * @param vertex идентификатор вершины
     */
    void addVertex(int vertex);

    /**
     * Удаляет вершину из графа.
     *
     * @param vertex идентификатор вершины
     */
    void removeVertex(int vertex);

    /**
     * Добавляет ребро между вершинами.
     *
     * @param from исходная вершина
     * @param to конечная вершина
     */
    void addEdge(int from, int to);

    /**
     * Удаляет ребро между вершинами.
     *
     * @param from исходная вершина
     * @param to конечная вершина
     */
    void removeEdge(int from, int to);

    /**
     * Возвращает список соседей вершины.
     *
     * @param vertex вершина, для которой запрашиваются соседи
     * @return список соседних вершин
     */
    List<Integer> getNeighbors(int vertex);

    /**
     * Выводит граф в консоль.
     */
    void outputGraph();

    /**
     * Возвращает множество всех вершин графа.
     *
     * @return множество всех вершин
     */
    Set<Integer> getAllVertices();

    /**
     * Возвращает множество исходящих соседей.
     *
     * @param vertex вершина
     * @return исходящие соседи
     */
    List<Integer> getOutgoingNeighbors(int vertex);

    void sorted(Sort sort);

    /**
     * Сравнивает текущий граф с другим графом.
     *
     * @param otherGraph другой граф для сравнения
     * @return true если графы идентичны, false в противном случае
     */
    default boolean equals(Graph otherGraph) {
        if (otherGraph == null) {
            return false;
        }

        Set<Integer> thisVertices = this.getAllVertices();
        Set<Integer> otherVertices = otherGraph.getAllVertices();

        if (!thisVertices.equals(otherVertices)) {
            return false;
        }

        for (int vertex : thisVertices) {
            List<Integer> thisOutgoing = this.getOutgoingNeighbors(vertex);
            List<Integer> otherOutgoing = otherGraph.getOutgoingNeighbors(vertex);

            if (!new HashSet<>(thisOutgoing).equals(new HashSet<>(otherOutgoing))) {
                return false;
            }
        }

        return true;
    }
}