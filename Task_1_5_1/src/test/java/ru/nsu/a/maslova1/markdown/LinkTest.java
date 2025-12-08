package ru.nsu.a.maslova1.markdown;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class LinkTest {

    @Test
    void testLinkCreationAndFormatting() {
        // Базовое создание и форматирование ссылок
        Element text = new Text("Click here");
        Link link = new Link(text, "https://example.com");

        assertEquals("[Click here](https://example.com)", link.toMarkdown());

        // С кодом в качестве текста ссылки
        Element code = new Code("java", "System.out.println();");
        Link linkWithCode = new Link(code, "https://docs.oracle.com");
        assertEquals("[```java\nSystem.out.println();\n```](https://docs.oracle.com)",
                linkWithCode.toMarkdown());
    }

    @Test
    void testLinkWithDifferentUrls() {
        Element text = new Text("Link");

        Link httpLink = new Link(text, "http://site.com");
        assertEquals("[Link](http://site.com)", httpLink.toMarkdown());

        Link httpsLink = new Link(text, "https://secure.com");
        assertEquals("[Link](https://secure.com)", httpsLink.toMarkdown());

        Link anchorLink = new Link(text, "#section");
        assertEquals("[Link](#section)", anchorLink.toMarkdown());

        Link relativeLink = new Link(text, "/about");
        assertEquals("[Link](/about)", relativeLink.toMarkdown());
    }

    @Test
    void testEqualsAndHashCode() {
        Element text1 = new Text("Link text");
        Element text2 = new Text("Link text");
        Element text3 = new Text("Different");

        Link link1 = new Link(text1, "https://example.com");
        Link link2 = new Link(text2, "https://example.com");
        Link link3 = new Link(text1, "https://different.com");

        // Равные объекты
        assertEquals(link1, link2);
        assertEquals(link1.hashCode(), link2.hashCode());

        // Разные URL
        assertNotEquals(link1, link3);

        // Разный текст
        Link link4 = new Link(text3, "https://example.com");
        assertNotEquals(link1, link4);

        // Рефлексивность
        assertEquals(link1, link1);

        // Сравнение с null и другим классом
        assertNotEquals(link1, null);
        assertNotEquals(link1, "string");
    }

    @Test
    void testEdgeCases() {
        // Пустой текст ссылки
        Link emptyText = new Link(new Text(""), "https://example.com");
        assertEquals("[](https://example.com)", emptyText.toMarkdown());

        // Специальные символы в URL
        Link complexUrl = new Link(new Text("Search"), "https://site.com/search?q=test&sort=asc");
        assertEquals("[Search](https://site.com/search?q=test&sort=asc)", complexUrl.toMarkdown());

        // Консистентность hashCode
        Link link = new Link(new Text("Test"), "url");
        assertEquals(link.hashCode(), link.hashCode());
    }

    @Test
    void testComplexContentAndComparison() {
        // Сложное содержимое
        Heading heading = new Heading(2, new Text("Documentation"));
        Link linkWithHeading = new Link(heading, "https://docs.com");
        assertEquals("[## Documentation](https://docs.com)", linkWithHeading.toMarkdown());

        // Изображение как текст ссылки (возможно, но странно)
        Images image = new Images(new Text("Logo"), "logo.png");
        Link linkWithImage = new Link(image, "https://homepage.com");
        assertEquals("[![Logo](logo.png)](https://homepage.com)", linkWithImage.toMarkdown());

        // Сравнение ссылок с разными типами содержимого
        Link textLink = new Link(new Text("click"), "url");
        Link codeLink = new Link(new Code("click"), "url");

        assertNotEquals(textLink, codeLink); // Разные типы Element

        // Две ссылки с одинаковым текстом, но разными типами элементов
        Link link1 = new Link(new Text("same"), "same-url");
        Link link2 = new Link(new Text("same"), "same-url");
        assertEquals(link1, link2); // Text.equals() сравнивает содержимое
    }
}