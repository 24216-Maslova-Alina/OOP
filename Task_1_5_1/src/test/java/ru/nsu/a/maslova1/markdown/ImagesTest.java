package ru.nsu.a.maslova1.markdown;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import ru.nsu.a.maslova1.markdown.elements.Code;
import ru.nsu.a.maslova1.markdown.elements.Heading;
import ru.nsu.a.maslova1.markdown.elements.Images;
import ru.nsu.a.maslova1.markdown.elements.Text;

class ImagesTest {

    @Test
    void testImageCreationAndFormatting() {
        // Базовое создание и форматирование изображений
        Element text = new Text("Alt text");
        Images image = new Images(text, "https://example.com/image.png");

        assertEquals("![Alt text](https://example.com/image.png)", image.toMarkdown());

        // С кодом в качестве текста
        Element code = new Code("java", "System.out.println();");
        Images imageWithCode = new Images(code, "https://example.com/code.png");
        assertEquals("![```java\nSystem.out.println();\n```](https://example.com/code.png)",
                imageWithCode.toMarkdown());
    }

    @Test
    void testImageWithDifferentUrls() {
        Element text = new Text("Image");

        Images httpImage = new Images(text, "http://site.com/img.jpg");
        assertEquals("![Image](http://site.com/img.jpg)", httpImage.toMarkdown());

        Images httpsImage = new Images(text, "https://secure.com/photo.png");
        assertEquals("![Image](https://secure.com/photo.png)", httpsImage.toMarkdown());

        Images localImage = new Images(text, "/local/path/image.gif");
        assertEquals("![Image](/local/path/image.gif)", localImage.toMarkdown());
    }

    @Test
    void testEqualsAndHashCode() {
        Element text1 = new Text("Alt");
        Element text2 = new Text("Alt");

        Images image1 = new Images(text1, "https://example.com/1.png");
        Images image2 = new Images(text2, "https://example.com/1.png");
        Images image3 = new Images(text1, "https://example.com/2.png");

        // Равные объекты
        assertEquals(image1, image2);
        assertEquals(image1.hashCode(), image2.hashCode());

        // Разные URL
        assertNotEquals(image1, image3);

        // Разный текст
        Element text3 = new Text("Different");
        Images image4 = new Images(text3, "https://example.com/1.png");
        assertNotEquals(image1, image4);

        // Рефлексивность
        assertEquals(image1, image1);

        // Сравнение с null и другим классом
        assertNotEquals(image1, null);
        assertNotEquals(image1, "string");
    }

    @Test
    void testEdgeCases() {
        // Пустой альтернативный текст
        Images emptyText = new Images(new Text(""), "https://example.com/img.jpg");
        assertEquals("![](https://example.com/img.jpg)", emptyText.toMarkdown());

        // Специальные символы в URL
        Images specialUrl = new Images(new Text("Test"), "https://ex.com/img?id=123&size=large");
        assertEquals("![Test](https://ex.com/img?id=123&size=large)", specialUrl.toMarkdown());

        // Консистентность hashCode
        Images image = new Images(new Text("Alt"), "url");
        assertEquals(image.hashCode(), image.hashCode());
    }

    @Test
    void testComplexContent() {
        // Вложенные элементы в качестве текста (если бы такие были)
        // Пока проверяем разные типы Element

        // С заголовком в качестве текста (необычный случай, но возможный)
        Heading heading = new Heading(2, new Text("Title"));
        Images imageWithHeading = new Images(heading, "https://example.com/title.png");
        assertEquals("![## Title](https://example.com/title.png)", imageWithHeading.toMarkdown());

        // Проверка equals с разными типами содержимого
        Images img1 = new Images(new Text("same"), "url");
        Images img2 = new Images(new Text("same"), "url");
        Images img3 = new Images(new Code("same"), "url");

        assertEquals(img1, img2);
        assertNotEquals(img1, img3); // Разные типы Element
    }
}