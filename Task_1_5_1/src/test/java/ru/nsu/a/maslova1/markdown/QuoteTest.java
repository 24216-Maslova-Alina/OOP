package ru.nsu.a.maslova1.markdown;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import ru.nsu.a.maslova1.markdown.elements.Code;
import ru.nsu.a.maslova1.markdown.elements.Link;
import ru.nsu.a.maslova1.markdown.elements.Lists;
import ru.nsu.a.maslova1.markdown.elements.Quote;
import ru.nsu.a.maslova1.markdown.elements.Text;


class QuoteTest {

    @Test
    void testSingleLineQuote() {
        // Однострочная цитата
        Quote quote = new Quote(new Text("Simple quote"));
        assertEquals("> Simple quote", quote.toMarkdown());

        // Ссылка в цитате
        Quote withLink = new Quote(new Link(new Text("Link"), "https://example.com"));
        assertEquals("> [Link](https://example.com)", withLink.toMarkdown());
    }

    @Test
    void testMultiLineQuote() {
        // Многострочная цитата
        Quote multiLine = new Quote(new Text("Line one\nLine two\nLine three"));
        String expected = "> Line one\n> Line two\n> Line three";
        assertEquals(expected, multiLine.toMarkdown());

        // Многострочный код в цитате
        Quote codeQuote = new Quote(new Code("java", "public void test() {\n    // code\n}"));
        String codeExpected = "> ```java\n> public void test() {\n>     // code\n> }\n> ```";
        assertEquals(codeExpected, codeQuote.toMarkdown());
    }

    @Test
    void testComplexContentInQuote() {
        // Сложное содержимое с разными элементами
        Lists list = Lists.unorderedList(new Text("Item 1"), new Text("Item 2"));
        Quote listQuote = new Quote(list);
        assertEquals("> - Item 1\n> - Item 2", listQuote.toMarkdown());

        // Цитата внутри цитаты (косвенно через текст)
        Quote nested = new Quote(new Text("Outer > Inner"));
        assertEquals("> Outer > Inner", nested.toMarkdown());
    }

    @Test
    void testEqualsAndHashCode() {
        Element content1 = new Text("Same content");
        Element content2 = new Text("Same content");
        Element content3 = new Text("Different");

        Quote quote1 = new Quote(content1);
        Quote quote2 = new Quote(content2);
        Quote quote3 = new Quote(content3);

        // Равные цитаты
        assertEquals(quote1, quote2);
        assertEquals(quote1.hashCode(), quote2.hashCode());

        // Разное содержимое
        assertNotEquals(quote1, quote3);

        // Рефлексивность
        assertEquals(quote1, quote1);

        // Сравнение с null и другим классом
        assertNotEquals(quote1, null);
        assertNotEquals(quote1, "string");
    }

    @Test
    void testEdgeCases() {
        // Пустая цитата
        Quote empty = new Quote(new Text(""));
        assertEquals("> ", empty.toMarkdown());

        // Консистентность hashCode
        Quote quote = new Quote(new Text("Test"));
        assertEquals(quote.hashCode(), quote.hashCode());

        // Проверка на пробелы и табуляции
        Quote withSpaces = new Quote(new Text("  indented  "));
        assertEquals(">   indented  ", withSpaces.toMarkdown());
    }
}