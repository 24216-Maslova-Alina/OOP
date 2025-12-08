package ru.nsu.a.maslova1.markdown;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class CodeTest {

    @Test
    void testConstructorsAndBasicMarkdown() {
        // Проверяем оба конструктора и базовое форматирование
        Code codeWithLang = new Code("python", "print('Hello')");
        assertEquals("```python\nprint('Hello')\n```", codeWithLang.toMarkdown());

        Code codeWithoutLang = new Code("simple text");
        assertEquals("```\nsimple text\n```", codeWithoutLang.toMarkdown());
    }

    @Test
    void testMarkdownEdgeCases() {
        // Пустое содержимое и многострочный контент
        Code emptyCode = new Code("java", "");
        assertEquals("```java\n\n```", emptyCode.toMarkdown());

        Code multilineCode = new Code("text", "line1\nline2\nline3");
        assertEquals("```text\nline1\nline2\nline3\n```", multilineCode.toMarkdown());
    }

    @Test
    void testEqualsBasicCases() {
        Code code1 = new Code("java", "code");
        Code code2 = new Code("java", "code");
        Code code3 = new Code("python", "code");
        Code code4 = new Code("java", "different");
        Code code5 = new Code("code");

        // Рефлексивность и равенство одинаковых объектов
        assertEquals(code1, code1);
        assertEquals(code1, code2);
        assertEquals(code1.hashCode(), code2.hashCode());

        // Разные языки
        assertNotEquals(code1, code3);

        // Разное содержимое
        assertNotEquals(code1, code4);

        // С null языком vs с языком
        assertNotEquals(code1, code5);
    }

    @Test
    void testEqualsSpecialCases() {
        Code code = new Code("java", "code");

        // Сравнение с null и другим классом
        assertNotEquals(code, null);
        assertNotEquals(code, "string object");

        // Оба с null языком
        Code nullLang1 = new Code("same");
        Code nullLang2 = new Code("same");
        Code nullLang3 = new Code("different");

        assertEquals(nullLang1, nullLang2);
        assertNotEquals(nullLang1, nullLang3);
    }

    @Test
    void testHashCodeConsistency() {
        Code code = new Code("java", "code");
        int hash1 = code.hashCode();
        int hash2 = code.hashCode();

        assertEquals(hash1, hash2);

        // Проверяем согласованность с equals
        Code equalCode = new Code("java", "code");
        if (code.equals(equalCode)) {
            assertEquals(code.hashCode(), equalCode.hashCode());
        }
    }
}