package ru.nsu.a.maslova1.graph.graphrepresentation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.nsu.a.maslova1.graph.sort.Sort;
/**
 * Реализация графа с использованием матрицы инцидентности.
 * Матрица инцидентности представляет собой двумерный массив, где строки соответствуют вершинам,
 * а столбцы - ребрам. Значения в матрице указывают на инцидентность вершины ребру:
 * 0 - не инцидентна, 1 - инцидентна, 2 - петля (вершина соединена сама с собой).
 */
public class IncidenceMatrix implements Graph {

    /**
     * Конструктор.
     */
    public IncidenceMatrix() {
        this.vertexCount = 0;
        this.edgeCount = 0;
    }

    private int[][] matrix;
    private int vertexCount;
    private int edgeCount;
    private final Set<Integer> vertexSet = new HashSet<>();

    /**
     * Добавляет вершину в граф.
     * Если вершина уже существует, метод не выполняет никаких действий.
     *
     * @param vertex идентификатор добавляемой вершины
     */
    @Override
    public void addVertex(int vertex) {
        if (vertexSet.contains(vertex)) {
            return;
        }

        vertexSet.add(vertex);
        int newVertexCount = vertexSet.size();
        int[][] newMatrix = new int[newVertexCount][edgeCount];

        if (matrix != null) {
            for (int i = 0; i < vertexCount; i++) {
                System.arraycopy(matrix[i], 0, newMatrix[i], 0, edgeCount);
            }
        }

        matrix = newMatrix;
        vertexCount = newVertexCount;
    }

    /**
     * Удаляет вершину из графа.
     * Если вершина не существует, метод не выполняет никаких действий.
     * При удалении вершины матрица инцидентности пересоздается с уменьшенным количеством строк
     * и удаляются все ребра, ставшие некорректными после удаления вершины.
     *
     * @param vertex идентификатор удаляемой вершины
     */
    @Override
    public void removeVertex(int vertex) {
        if (!vertexSet.contains(vertex)) {
            return;
        }

        int vertexIndex = getVertexIndex(vertex);
        vertexSet.remove(vertex);

        int newVertexCount = vertexSet.size();
        int[][] newMatrix = new int[newVertexCount][edgeCount];

        for (int i = 0, newI = 0; i < vertexCount; i++) {
            if (i != vertexIndex) {
                System.arraycopy(matrix[i], 0, newMatrix[newI], 0, edgeCount);
                newI++;
            }
        }

        matrix = newMatrix;
        vertexCount = newVertexCount;
        removeExcessEdge();
    }

    /**
     * Добавляет ребро между двумя вершинами.
     * Если одна или обе вершины не существуют в графе, выбрасывается исключение.
     * Для петель (from == to) используется значение 2 в матрице инцидентности.
     * Для обычных ребер используется значение 1 для обеих инцидентных вершин.
     *
     * @param from идентификатор исходной вершины
     * @param to идентификатор конечной вершины
     */
    @Override
    public void addEdge(int from, int to) {
        if (!vertexSet.contains(from) || !vertexSet.contains(to)) {
            throw new IllegalArgumentException("Not all vertices must "
                    + "exist in the graph");
        }

        int[][] newMatrix = new int[vertexCount][edgeCount + 1];

        for (int i = 0; i < vertexCount; i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i],
                    0, edgeCount);
        }

        int fromIndex = getVertexIndex(from);
        int toIndex = getVertexIndex(to);

        // петля - 2; начальная вершина - 1; конечная - -1
        if (from == to) {
            newMatrix[fromIndex][edgeCount] = 2;
        } else {
            newMatrix[fromIndex][edgeCount] = 1;
            newMatrix[toIndex][edgeCount] = -1;
        }

        matrix = newMatrix;
        edgeCount++;
    }

    /**
     * Удаляет ребро между двумя вершинами.
     * Если одна или обе вершины не существуют в графе, выбрасывается исключение.
     * Метод ищет ребро, соответствующее заданным вершинам, и удаляет его из матрицы инцидентности.
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

        for (int edgeIndex = 0; edgeIndex < edgeCount; edgeIndex++) {
            boolean isEdge = false;
            if (from == to && matrix[fromIndex][edgeIndex] == 2) {
                isEdge = true;
            } else {
                if (matrix[fromIndex][edgeIndex] == 1 && matrix[toIndex][edgeIndex] == -1) {
                    isEdge = true;
                }
            }

            if (isEdge) {
                int[][] newMatrix = new int[vertexCount][edgeCount - 1];

                for (int i = 0; i < vertexCount; i++) {

                    for (int j = 0, newJ = 0; j < edgeCount; j++) {
                        if (j != edgeIndex) {
                            newMatrix[i][newJ] = matrix[i][j];
                            newJ++;
                        }
                    }
                }
                matrix = newMatrix;
                edgeCount--;
                return;
            }
        }
    }

    /**
     * Возвращает список соседей указанной вершины.
     * Соседями считаются все вершины, соединенные ребром с данной вершиной.
     * Для петель вершина считается соседом самой себя.
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

        for (int j = 0; j < edgeCount; j++) {
            if (matrix[vertexIndex][j] != 0) {
                for (int i = 0; i < vertexCount; i++) {
                    if (i != vertexIndex && matrix[i][j] != 0) {
                        int neighbor = getVertexByIndex(i);
                        neighbors.add(neighbor);
                    }
                }

                if (matrix[vertexIndex][j] == 2) {
                    neighbors.add(vertex);
                }
            }
        }

        return neighbors;
    }

    /**
     * Выводит граф в заданном формате.
     * Реализация метода зависит от конкретных требований к выводу.
     */
    public void outputGraph() {
        System.out.println("Матрица инцидентности:");
        System.out.print("   ");

        for (int j = 0; j < edgeCount; j++) {
            System.out.print("e" + j + " ");  // e0, e1, e2, ...
        }
        System.out.println();

        for (int i = 0; i < vertexCount; i++) {
            int vertex = getVertexByIndex(i);
            System.out.print(vertex + ": ");

            for (int j = 0; j < edgeCount; j++) {
                System.out.print(matrix[i][j] + "  ");
            }
            System.out.println();
        }
    }

    /**
     * Возвращает множество всех вершин графа.
     *
     * @return все вершины графа
     */
    @Override
    public Set<Integer> getAllVertices() {
        return new HashSet<>(vertexSet);
    }

    /**
     * Возвращает множество исходящих соседей.
     *
     * @param vertex вершина
     * @return исходящие соседи
     */
    @Override
    public List<Integer> getOutgoingNeighbors(int vertex) {
        if (!vertexSet.contains(vertex)) {
            return List.of();
        }

        int vertexIndex = getVertexIndex(vertex);

        List<Integer> neighborsOut = new ArrayList<>();
        for (int j = 0; j < edgeCount; j++) {
            if (matrix[vertexIndex][j] == 1 || matrix[vertexIndex][j] == 2) {

                for (int i = 0; i < vertexCount; i++) {
                    if (matrix[vertexIndex][j] == 1 && i != vertexIndex && matrix[i][j] == -1) {
                        int neighbor = getVertexByIndex(i);
                        neighborsOut.add(neighbor);
                    }
                }

                if (matrix[vertexIndex][j] == 2) {
                    neighborsOut.add(vertex);
                }
            }
        }
        return neighborsOut;
    }

    /**
     * Метод сортировки графа.
     */
    @Override
    public void sorted(Sort sort) {
        sort.sorted(this);
    }

    /**
     * Возвращает индекс вершины в матрице инцидентности по ее идентификатору.
     *
     * @param vertex идентификатор вершины
     * @return индекс вершины в матрице инцидентности или -1, если вершина не найдена
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

    /**
     * Возвращает идентификатор вершины по ее индексу в матрице инцидентности.
     *
     * @param index индекс вершины в матрице инцидентности
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
     * Удаляет избыточные ребра из графа.
     * Ребро считается избыточным, если оно инцидентно менее чем двум вершинам
     * (что может произойти после удаления вершины).
     * Метод проходит по всем ребрам и удаляет те, которые стали некорректными.
     */
    private void removeExcessEdge() {
        for (int edgeIndex = edgeCount - 1; edgeIndex >= 0; edgeIndex--) {
            int startCount = 0;
            int endCount = 0;

            for (int i = 0; i < vertexCount; i++) {
                if (matrix[i][edgeIndex] == 2) {
                    startCount++;
                    endCount++;
                } else if (matrix[i][edgeIndex] == 1) {
                    startCount++;
                } else if (matrix[i][edgeIndex] == -1) {
                    endCount++;
                }
            }

            if (startCount == 0 || endCount == 0) {
                int[][] newMatrix = new int[vertexCount][edgeCount - 1];

                for (int i = 0; i < vertexCount; i++) {
                    for (int j = 0, newJ = 0; j < edgeCount; j++) {
                        if (j != edgeIndex) {
                            newMatrix[i][newJ] = matrix[i][j];
                            newJ++;
                        }
                    }
                }

                matrix = newMatrix;
                edgeCount--;
            }
        }
    }
}