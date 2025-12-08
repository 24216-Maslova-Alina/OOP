package ru.nsu.a.maslova1.markdown;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TableTest {

    @Test
    void testSimpleTable() {
        // Простая таблица с текстом
        Table table = new Table.Builder()
                .addRow("Header1", "Header2")
                .addRow("Cell1", "Cell2")
                .build();

        // Проверяем базовую структуру
        String result = table.toMarkdown();
        assertTrue(result.contains("Header1"));
        assertTrue(result.contains("Header2"));
        assertTrue(result.contains("Cell1"));
        assertTrue(result.contains("Cell2"));
        assertTrue(result.contains("|"));
        assertTrue(result.contains("-"));
    }

    @Test
    void testTableWithAlignments() {
        // Таблица с разными выравниваниями
        Table table = new Table.Builder()
                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_CENTER, Table.ALIGN_RIGHT)
                .addRow("Left", "Center", "Right")
                .addRow("A", "B", "C")
                .build();

        String result = table.toMarkdown();
        assertTrue(result.contains("Left"));
        assertTrue(result.contains("Center"));
        assertTrue(result.contains("Right"));
        assertTrue(result.contains("A"));
        assertTrue(result.contains("B"));
        assertTrue(result.contains("C"));
        // Проверяем наличие маркеров выравнивания
        assertTrue(result.contains(":"));
    }

    @Test
    void testTableWithDifferentContentTypes() {
        // Таблица с разными типами элементов
        Table table = new Table.Builder()
                .addRow("Text", new Code("code"), new Link(new Text("Link"), "url"))
                .addRow(new Text("Normal"), new Code("java", "System.out.println();"), "Simple")
                .build();

        String result = table.toMarkdown();
        assertTrue(result.contains("Text"));
        assertTrue(result.contains("code"));
        assertTrue(result.contains("Link"));
        assertTrue(result.contains("Normal"));
        assertTrue(result.contains("System.out.println();"));
        assertTrue(result.contains("Simple"));
    }

    @Test
    void testTableWithRowLimit() {
        // Таблица с ограничением строк
        Table table = new Table.Builder()
                .withRowLimit(2)
                .addRow("H1", "H2")
                .addRow("R1", "R2")
                .addRow("R3", "R4")  // Эта строка не должна попасть
                .build();

        String result = table.toMarkdown();
        assertTrue(result.contains("H1") && result.contains("H2"));
        assertTrue(result.contains("R1") && result.contains("R2"));
        assertFalse(result.contains("R3") || result.contains("R4"));
    }

    @Test
    void testBuilderWithDifferentCellTypes() {
        // Проверяем преобразование разных типов в ячейках
        Table table = new Table.Builder()
                .addRow("String", 123, new Text("Element")) // убрали null
                .addRow(new Code("test"), true, 45.67)
                .build();

        String result = table.toMarkdown();
        assertTrue(result.contains("String"));
        assertTrue(result.contains("123"));
        assertTrue(result.contains("Element"));
        assertTrue(result.contains("test"));
        assertTrue(result.contains("true"));
        assertTrue(result.contains("45.67"));
    }

    @Test
    void testEqualsAndHashCode() {
        Table.Builder builder1 = new Table.Builder()
                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_RIGHT)
                .addRow("A", "B")
                .addRow("C", "D");

        Table.Builder builder2 = new Table.Builder()
                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_RIGHT)
                .addRow("A", "B")
                .addRow("C", "D");

        Table table1 = builder1.build();
        Table table2 = builder2.build();
        Table table3 = new Table.Builder()
                .addRow("X", "Y")
                .build();

        // Равные таблицы
        assertEquals(table1, table2);
        assertEquals(table1.hashCode(), table2.hashCode());

        // Разные таблицы
        assertNotEquals(table1, table3);

        // Рефлексивность
        assertEquals(table1, table1);

        // Сравнение с null и другим классом
        assertNotEquals(table1, null);
        assertNotEquals(table1, "string");
    }

    @Test
    void testEdgeCases() {
        // Пустая таблица
        Table empty = new Table.Builder().build();
        assertEquals("", empty.toMarkdown());

        // Таблица с одним столбцом и одной строкой
        Table singleCell = new Table.Builder()
                .addRow("Single")
                .build();
        String singleResult = singleCell.toMarkdown();
        assertTrue(singleResult.contains("Single"));

        // Консистентность hashCode
        Table table = new Table.Builder()
                .addRow("Test")
                .build();
        assertEquals(table.hashCode(), table.hashCode());
    }

    @Test
    void testVariableColumnWidths() {
        // Таблица с разной шириной столбцов
        Table table = new Table.Builder()
                .addRow("Short", "Very Long Header", "Medium")
                .addRow("Longer cell here", "Short", "A")
                .build();

        String result = table.toMarkdown();
        assertTrue(result.contains("Very Long Header"));
        assertTrue(result.contains("Longer cell here"));
        // Проверяем форматирование
        String[] lines = result.split("\n");
        assertEquals(3, lines.length); // Заголовок + разделитель + строка
    }
}