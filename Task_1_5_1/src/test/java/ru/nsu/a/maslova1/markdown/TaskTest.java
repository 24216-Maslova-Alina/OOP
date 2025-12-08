package ru.nsu.a.maslova1.markdown;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TaskTest {

    @Test
    void testTaskCreationAndFormatting() {
        // Тестируем оба конструктора и форматирование
        Task uncheckedTask = new Task("Buy milk", false);
        assertEquals("[ ] Buy milk", uncheckedTask.toMarkdown());

        Task checkedTask = new Task("Read book", true);
        assertEquals("[x] Read book", checkedTask.toMarkdown());

        // С Element в конструкторе
        Task taskWithElement = new Task(new Code("java", "Task"), false);
        assertEquals("[ ] ```java\nTask\n```", taskWithElement.toMarkdown());
    }

    @Test
    void testTaskWithComplexContent() {
        // Задачи с разными типами содержимого
        Task withLink = new Task(new Link(new Text("Link"), "https://example.com"), true);
        assertEquals("[x] [Link](https://example.com)", withLink.toMarkdown());

        Task withHeading = new Task(new Heading(2, new Text("Title")), false);
        assertEquals("[ ] ## Title", withHeading.toMarkdown());

        Task withList = new Task(Lists.unorderedList(new Text("Item1"), new Text("Item2")), true);
        assertEquals("[x] - Item1\n- Item2", withList.toMarkdown());
    }

    @Test
    void testEqualsAndHashCode() {
        // Строковые конструкторы
        Task task1 = new Task("Same task", false);
        Task task2 = new Task("Same task", false);

        // Element конструкторы
        Task task5 = new Task(new Text("Task"), false);
        Task task6 = new Task(new Text("Task"), false);

        // Равные задачи
        assertEquals(task1, task2);
        assertEquals(task1.hashCode(), task2.hashCode());
        assertEquals(task5, task6);

        // Разный статус
        Task task3 = new Task("Same task", true);  // другой статус
        assertNotEquals(task1, task3);

        // Разный текст
        Task task4 = new Task("Different", false); // другой текст
        assertNotEquals(task1, task4);

        // Рефлексивность
        assertEquals(task1, task1);

        // Сравнение с null и другим классом
        assertNotEquals(task1, null);
        assertNotEquals(task1, "string");

        // Сравнение разных конструкторов с одинаковым содержимым
        Task strTask = new Task("Text", false);
        Task elemTask = new Task(new Text("Text"), false);
        assertEquals(strTask, elemTask);
    }

    @Test
    void testEdgeCases() {
        // Пустая задача
        Task emptyTask = new Task("", false);
        assertEquals("[ ] ", emptyTask.toMarkdown());

        Task emptyChecked = new Task("", true);
        assertEquals("[x] ", emptyChecked.toMarkdown());

        // Задача с пробелами
        Task spacedTask = new Task("  spaced  ", false);
        assertEquals("[ ]   spaced  ", spacedTask.toMarkdown());

        // Консистентность hashCode
        Task task = new Task("Test", true);
        assertEquals(task.hashCode(), task.hashCode());

        // Задача с многострочным текстом
        Task multiLine = new Task("Line1\nLine2", false);
        assertEquals("[ ] Line1\nLine2", multiLine.toMarkdown());
    }

    @Test
    void testStatusSwitching() {
        // Проверяем, что статус правильно влияет на форматирование
        Task task = new Task("Task", false);
        String unchecked = task.toMarkdown();
        assertTrue(unchecked.contains("[ ]"));
        assertFalse(unchecked.contains("[x]"));

        // Создаем другую задачу с тем же текстом, но другим статусом
        Task sameTaskChecked = new Task("Task", true);
        String checked = sameTaskChecked.toMarkdown();
        assertTrue(checked.contains("[x]"));
        assertFalse(checked.contains("[ ]"));

        // Они не равны
        assertNotEquals(task, sameTaskChecked);
    }
}