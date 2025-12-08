package ru.nsu.a.maslova1.markdown;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class TextTest {

    @Test
    void testPlainText() {
        // Простой текст
        Text plain = new Text("Hello World");
        assertEquals("Hello World", plain.toMarkdown());

        // Текст со специальными символами
        Text special = new Text("Test & <test>");
        assertEquals("Test & <test>", special.toMarkdown());

        // Пустой текст
        Text empty = new Text("");
        assertEquals("", empty.toMarkdown());

        // Текст только с пробелами
        Text spaces = new Text("   ");
        assertEquals("   ", spaces.toMarkdown());
    }

    @Test
    void testStyledText() {
        // Полужирный текст
        Text bold = Text.bold("Important");
        assertEquals("**Important**", bold.toMarkdown());

        // Курсив
        Text italic = Text.italic("Emphasis");
        assertEquals("*Emphasis*", italic.toMarkdown());

        // Зачеркнутый
        Text strike = Text.strikethrough("Old price");
        assertEquals("~~Old price~~", strike.toMarkdown());

        // Код
        Text code = Text.code("variable");
        assertEquals("`variable`", code.toMarkdown());
    }

    @Test
    void testFactoryMethods() {
        // Проверяем все фабричные методы
        assertEquals("**Bold**", Text.bold("Bold").toMarkdown());
        assertEquals("*Italic*", Text.italic("Italic").toMarkdown());
        assertEquals("~~Strike~~", Text.strikethrough("Strike").toMarkdown());
        assertEquals("`Code`", Text.code("Code").toMarkdown());

        // Метод plain (но он не static)
        Text text = new Text("test");
        assertEquals("plain", text.plain("plain").toMarkdown());
    }

    @Test
    void testEqualsAndHashCode() {
        // Простые тексты
        Text text1 = new Text("Same");
        Text text2 = new Text("Same");
        Text text3 = new Text("Different");

        // Стилизованные тексты
        Text bold1 = Text.bold("Text");
        Text bold2 = Text.bold("Text");
        Text italic = Text.italic("Text");

        // Равные простые тексты
        assertEquals(text1, text2);
        assertEquals(text1.hashCode(), text2.hashCode());

        // Равные стилизованные тексты
        assertEquals(bold1, bold2);

        // Разное содержимое
        assertNotEquals(text1, text3);

        // Разные стили
        assertNotEquals(bold1, italic);

        // Рефлексивность
        assertEquals(text1, text1);

        // Сравнение с null и другим классом
        assertNotEquals(text1, null);
        assertNotEquals(text1, "string");

        // Проверка всех полей в equals
        Text full1 = new Text("test", true, false, true, false);
        Text full2 = new Text("test", true, false, true, false);
        Text full3 = new Text("test", false, true, false, false);

        assertEquals(full1, full2);
        assertNotEquals(full1, full3);
    }

    @Test
    void testEdgeCasesAndCombinations() {
        // Текст с экранируемыми символами Markdown
        Text withStars = new Text("**not bold**");
        assertEquals("**not bold**", withStars.toMarkdown());

        Text withTildes = new Text("~~not strike~~");
        assertEquals("~~not strike~~", withTildes.toMarkdown());

        Text withBackticks = new Text("`not code`");
        assertEquals("`not code`", withBackticks.toMarkdown());

        // Многострочный текст (должен сохранять переносы)
        Text multiline = new Text("Line1\nLine2\nLine3");
        assertEquals("Line1\nLine2\nLine3", multiline.toMarkdown());

        // Консистентность hashCode для сложного текста
        Text complex = new Text("Content", true, true, false, false);
        assertEquals(complex.hashCode(), complex.hashCode());
    }

    @Test
    void testStylePriority() {
        // Когда задано несколько стилей одновременно - проверяем приоритеты
        // В реализации приоритет: bold > italic > strikethrough > code > plain

        Text boldItalic = new Text("Test", true, true, false, false);
        assertEquals("**Test**", boldItalic.toMarkdown()); // bold имеет приоритет

        Text italicStrike = new Text("Test", false, true, true, false);
        assertEquals("*Test*", italicStrike.toMarkdown()); // italic имеет приоритет над strikethrough

        Text strikeCode = new Text("Test", false, false, true, true);
        assertEquals("~~Test~~", strikeCode.toMarkdown()); // strikethrough имеет приоритет над code

        Text allStyles = new Text("Test", true, true, true, true);
        assertEquals("**Test**", allStyles.toMarkdown()); // bold имеет высший приоритет
    }
}