package ru.nsu.a.maslova1.graph;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Главный класс для тестирования реализаций графа.
 */
public class Main {

    /**
     * Точка входа в программу.
     * Читает граф из файла и тестирует все реализации.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("test.txt"));

            Graph[] graphs = {
                new AdjacencyList(),
                new AdjacencyMatrix(),
                new IncidenceMatrix()
            };

            String[] graphNames = {
                "СПИСОК СМЕЖНОСТИ",
                "МАТРИЦА СМЕЖНОСТИ",
                "МАТРИЦА ИНЦИДЕНТНОСТИ"
            };

            Set<Integer> vertices = new HashSet<>();
            List<String> lines = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                lines.add(line);

                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    vertices.add(Integer.parseInt(parts[0]));
                    vertices.add(Integer.parseInt(parts[1]));
                }
            }
            scanner.close();

            for (int i = 0; i < graphs.length; i++) {
                Graph graph = graphs[i];
                String graphName = graphNames[i];

                System.out.println("=".repeat(50));
                System.out.println("РЕАЛИЗАЦИЯ: " + graphName);
                System.out.println("=".repeat(50));

                for (int vertex : vertices) {
                    graph.addVertex(vertex);
                }

                for (String line : lines) {
                    String[] parts = line.split(" ");
                    if (parts.length == 2) {
                        try {
                            int from = Integer.parseInt(parts[0]);
                            int to = Integer.parseInt(parts[1]);
                            graph.addEdge(from, to);
                        } catch (NumberFormatException e) {
                            System.out.println("Некорректные числа в строке: " + line);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Ошибка при добавлении ребра: "
                                    + e.getMessage());
                        }
                    }
                }

                // Сравнение с другими графами
                System.out.println("\nСравнение с другими реализациями:");
                for (int j = 0; j < i; j++) {
                    boolean areEqual = graph.equals(graphs[j]);
                    System.out.println(graphName + " == " + graphNames[j] + ": " + areEqual);
                }

                System.out.println("\nТопологическая сортировка:");
                try {
                    List<Integer> topologicalOrder = TopologicalSort.topologicalSort(graph);
                    System.out.println("Топологический порядок: " + topologicalOrder);
                } catch (IllegalArgumentException e) {
                    System.out.println("Топологическая сортировка невозможна: " + e.getMessage());
                }

                try {
                    List<Integer> topologicalOrderdfs = TopologicalSort.topologicalSort(graph);
                    System.out.println("Топологический порядок (DFS): " + topologicalOrderdfs);
                } catch (IllegalArgumentException e) {
                    System.out.println("Топологическая сортировка невозможна: " + e.getMessage());
                }
                System.out.println("\nСтруктура графа:");
                graph.outputGraph();

                System.out.println("\nСоседи вершин:");
                List<Integer> sortedVertices = new ArrayList<>(vertices);
                Collections.sort(sortedVertices);

                for (int vertex : sortedVertices) {
                    List<Integer> neighbors = graph.getNeighbors(vertex);
                    System.out.println("Вершина " + vertex + ": " + neighbors);
                }

                System.out.println();
            }

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Ошибка открытия файла: " + e.getMessage());
        }
    }
}