package ru.nsu.a.maslova1.markdown;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import ru.nsu.a.maslova1.markdown.elements.Code;
import ru.nsu.a.maslova1.markdown.elements.Heading;
import ru.nsu.a.maslova1.markdown.elements.Text;

class HeadingTest {

    @Test
    void testHeadingCreationAndFormatting() {
        // Тестируем разные уровни заголовков
        Element text = new Text("Hello World");

        Heading h1 = new Heading(1, text);
        assertEquals("# Hello World", h1.toMarkdown());

        Heading h3 = new Heading(3, text);
        assertEquals("### Hello World", h3.toMarkdown());

        Heading h6 = new Heading(6, text);
        assertEquals("###### Hello World", h6.toMarkdown());
    }

    @Test
    void testHeadingWithDifferentContent() {
        // Тестируем заголовки с разным содержимым
        Text text = new Text("Simple text");
        Code code = new Code("java", "System.out.println();");

        Heading h1Text = new Heading(1, text);
        Heading h2Code = new Heading(2, code);

        assertEquals("# Simple text", h1Text.toMarkdown());
        assertEquals("## ```java\nSystem.out.println();\n```", h2Code.toMarkdown());
    }

    @Test
    void testEqualsAndHashCode() {
        Element text1 = new Text("Title");
        Element text2 = new Text("Title");

        Heading h1 = new Heading(1, text1);
        Heading h1Same = new Heading(1, text2);
        Heading h2 = new Heading(2, text1);

        // Равные объекты
        assertEquals(h1, h1Same);
        assertEquals(h1.hashCode(), h1Same.hashCode());

        // Разные уровни
        assertNotEquals(h1, h2);

        // Разное содержимое
        Element text3 = new Text("Different");
        Heading h1DiffContent = new Heading(1, text3);
        assertNotEquals(h1, h1DiffContent);

        // Сравнение с собой
        assertEquals(h1, h1);

        // Сравнение с null и другим классом
        assertNotEquals(h1, null);
        assertNotEquals(h1, "string");
    }

    @Test
    void testEdgeCases() {
        // Минимальный уровень
        Heading h1 = new Heading(1, new Text("Min"));
        assertEquals("# Min", h1.toMarkdown());

        // Пустой текст
        Heading empty = new Heading(2, new Text(""));
        assertEquals("## ", empty.toMarkdown());

        // Проверка hashCode консистентности
        Heading heading = new Heading(3, new Text("Test"));
        assertEquals(heading.hashCode(), heading.hashCode());
    }

    @Test
    void testSpecialEqualsCases() {
        Heading heading = new Heading(2, new Text("Test"));

        // Разные классы
        assertNotEquals(heading, new Text("Test"));

        // Разные объекты с одинаковыми полями (но разными инстансами содержимого)
        Element content1 = new Text("Same");
        Element content2 = new Text("Same");
        Heading h1 = new Heading(1, content1);
        Heading h2 = new Heading(1, content2);

        // Должны быть равны, так как Text.equals() сравнивает содержимое
        assertEquals(h1, h2);
    }
}