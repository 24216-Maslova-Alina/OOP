package ru.nsu.a.maslova1.markdown;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Класс, представляющий таблицу в Markdown формате.
 */
public class Table extends Element {
    private final List<List<Element>> rows;
    private final int[] alignments;
    private final int rowLimit;

    public static int ALIGN_RIGHT = 0;
    public static int ALIGN_LEFT = 1;
    public static int ALIGN_CENTER = 2;

    /**
     * Конструктор.
     *
     * @param rows строки таблицы (список списков элементов)
     * @param alignment массив выравниваний для каждого столбца
     * @param rowsLimit максимальное количество отображаемых строк
     */
    public Table(List<List<Element>> rows, int[] alignment, int rowsLimit) {
        this.rows = rows;
        this.alignments = alignment;
        this.rowLimit = rowsLimit;
    }

    /**
     * Преобразует таблицу в строку в формате Markdown.
     * Если alignments не заданы, используются выравнивания по левому краю по умолчанию.
     *
     * @return строка в формате Markdown, представляющая таблицу
     */
    @Override
    public String toMarkdown() {
        if (rows == null || rows.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        int columnCount = rows.get(0).size();
        int rowsCount = Math.min(rowLimit, rows.size());
        int[] cellSize = alignCell(columnCount, rowsCount, rows);

        // Инициализируем alignments по умолчанию, если не заданы
        int[] effectiveAlignments = alignments;
        if (effectiveAlignments == null || effectiveAlignments.length == 0) {
            effectiveAlignments = new int[columnCount];
            for (int j = 0; j < columnCount; j++) {
                effectiveAlignments[j] = ALIGN_LEFT;
            }
        }

        // Создаем строку заголовков
        result.append("| ");
        int i = 0;
        for (Element cell : rows.get(0)) {
            result.append(cell.toMarkdown());
            if (cell.toMarkdown().length() < cellSize[i]) {
                String str = " ".repeat(cellSize[i] - cell.toMarkdown().length());
                result.append(str);
            }
            result.append(" |");
            i++;
            if (i < columnCount) {
                result.append(" ");
            }
        }
        result.append("\n");

        // Создаем строку выравнивания
        result.append("| ");
        for (int j = 0; j < columnCount; j++) {
            int sizeWidth = cellSize[j];
            result.append(getAlignment(sizeWidth, effectiveAlignments[j])).append(" |");
            if (j < columnCount - 1) {
                result.append(" ");
            }
        }
        result.append("\n");

        // Создание остальных ячеек таблицы
        for (int row = 1; row < rowsCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                Element cell = rows.get(row).get(column);
                if (column >= 1) {
                    result.append(" ");
                }
                result.append("| ").append(cell.toMarkdown());
                if (cell.toMarkdown().length() < cellSize[column]) {
                    String str = " ".repeat(cellSize[column] - cell.toMarkdown().length());
                    result.append(str);
                }
            }
            result.append(" |\n");
        }
        return result.toString().trim();
    }

    /**
     * Сравнивает данную таблицу с другим объектом.
     *
     * @param obj объект для сравнения
     * @return true, если объекты равны (одинаковые строки, выравнивания и ограничение строк), иначе false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Table table = (Table) obj;
        return rowLimit == table.rowLimit
                && Objects.equals(rows, table.rows)
                && Arrays.equals(alignments, table.alignments);
    }

    /**
     * Возвращает хэш-код таблицы.
     *
     * @return хэш-код, вычисленный на основе строк, выравниваний и ограничения строк
     */
    @Override
    public int hashCode() {
        return Objects.hash(rows, Arrays.hashCode(alignments), rowLimit);
    }

    /**
     * Генерирует строку выравнивания для столбца таблицы.
     *
     * @param width ширина столбца
     * @param alignment тип выравнивания (ALIGN_RIGHT, ALIGN_LEFT, ALIGN_CENTER)
     * @return строка выравнивания в формате Markdown
     */
    private String getAlignment(int width, int alignment) {
        String line = "-".repeat(width);
        if (alignment == ALIGN_CENTER) {
            return ":" + line.substring(2) + ":";
        } else if (alignment == ALIGN_RIGHT) {
            return line.substring(1) + ":";
        } else if (alignment == ALIGN_LEFT) {
            return ":" + line.substring(1);
        }
        return line;
    }

    /**
     * Вычисляет максимальную ширину для каждого столбца таблицы.
     *
     * @param columnCount количество столбцов
     * @param rowsCount количество строк
     * @param rows строки таблицы
     * @return массив максимальных ширин для каждого столбца
     */
    private int[] alignCell(int columnCount, int rowsCount, List<List<Element>> rows) {
        int[] answer = new int[columnCount];
        for(int i = 0; i < rowsCount; i++) {
            List<Element> row = rows.get(i);
            for (int j = 0; j < columnCount; j++) {
                String cell = row.get(j).toMarkdown();
                if (answer[j] < cell.length()) {
                    answer[j] = cell.length();
                }
            }
        }
        return answer;
    }

    /**
     * Строитель для создания таблиц с использованием паттерна Builder.
     */
    public static class Builder {
        private final List<List<Element>> rows = new ArrayList<>();
        private int[] alignments = new int[0];
        private int rowLimit = Integer.MAX_VALUE;

        /**
         * Устанавливает выравнивания для столбцов таблицы.
         *
         * @param aligns массив выравниваний для каждого столбца
         * @return текущий строитель
         */
        public Builder withAlignments(int... aligns) {
            this.alignments = aligns.clone();
            return this;
        }

        /**
         * Устанавливает ограничение на количество строк в таблице.
         *
         * @param limit максимальное количество строк
         * @return текущий строитель
         */
        public Builder withRowLimit(int limit) {
            rowLimit = limit;
            return this;
        }

        /**
         * Добавляет строку в таблицу.
         * Поддерживает преобразование различных типов данных в элементы таблицы.
         *
         * @param cells ячейки строки
         * @return текущий строитель
         */
        public Builder addRow(Object... cells) {
            if (rows.size() >= rowLimit) {
                return this;
            }

            List<Element> row = new ArrayList<>();
            for (Object cell : cells) {
                switch (cell) {
                    case null -> row.add(new Text(""));
                    case String _ -> row.add(new Text((String) cell));
                    case Integer _ -> row.add(new Text(String.valueOf(cell)));
                    case Element element -> row.add(element);
                    default -> row.add(new Text(cell.toString()));
                }
            }
            rows.add(row);
            return this;
        }

        /**
         * Создает таблицу на основе текущих параметров строителя.
         *
         * @return новая таблица
         */
        public Table build() {
            return new Table(rows, alignments, rowLimit);
        }
    }
}