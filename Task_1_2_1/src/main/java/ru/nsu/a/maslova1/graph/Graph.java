package ru.nsu.a.maslova1.graph;

import java.util.List;

public interface Graph {
    void addVertex(int vertex);
    void removeVertex(int vertex);
    void addEdge(int from, int to);
    void removeEdge(int from, int to);
    List<Integer> getNeighbors(int vertex);
    void outputGragh(int[][] graph);

}
