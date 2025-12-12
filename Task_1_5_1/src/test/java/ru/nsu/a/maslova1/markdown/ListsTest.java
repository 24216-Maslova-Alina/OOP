package ru.nsu.a.maslova1.markdown;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import ru.nsu.a.maslova1.markdown.elements.Code;
import ru.nsu.a.maslova1.markdown.elements.Link;
import ru.nsu.a.maslova1.markdown.elements.Lists;
import ru.nsu.a.maslova1.markdown.elements.Text;

class ListsTest {

    @Test
    void testUnorderedListCreationAndFormatting() {
        // Тестируем неупорядоченный список
        Element item1 = new Text("First item");
        Element item2 = new Text("Second item");

        Lists unordered = Lists.unorderedList(item1, item2);
        String expected = "- First item\n- Second item";
        assertEquals(expected, unordered.toMarkdown());

        // С одним элементом
        Lists singleItem = Lists.unorderedList(new Text("Only item"));
        assertEquals("- Only item", singleItem.toMarkdown());
    }

    @Test
    void testOrderedListCreationAndFormatting() {
        // Тестируем упорядоченный список
        Element item1 = new Text("Step one");
        Element item2 = new Text("Step two");
        Element item3 = new Text("Step three");

        Lists ordered = Lists.orderedList(item1, item2, item3);
        String expected = "1. Step one\n2. Step two\n3. Step three";
        assertEquals(expected, ordered.toMarkdown());

        // Проверяем нумерацию
        Lists twoItems = Lists.orderedList(new Text("A"), new Text("B"));
        assertEquals("1. A\n2. B", twoItems.toMarkdown());
    }

    @Test
    void testListsWithComplexElements() {
        // Списки с разными типами элементов
        Element text = new Text("Text item");
        Element code = new Code("java", "code here");
        Element link = new Link(new Text("Link"), "https://example.com");

        Lists unordered = Lists.unorderedList(text, code, link);
        String expected = "- Text item\n- ```java\ncode here\n```\n- [Link](https://example.com)";
        assertEquals(expected, unordered.toMarkdown());

        Lists ordered = Lists.orderedList(text, code);
        String orderedExpected = "1. Text item\n2. ```java\ncode here\n```";
        assertEquals(orderedExpected, ordered.toMarkdown());
    }

    @Test
    void testEqualsAndHashCode() {
        Element item1 = new Text("Item");
        Element item2 = new Text("Item");

        // Неупорядоченные списки
        Lists unordered1 = Lists.unorderedList(item1, item2);
        Lists unordered2 = Lists.unorderedList(item1, item2);


        // Упорядоченные списки
        Lists ordered1 = Lists.orderedList(item1, item2);
        Lists ordered2 = Lists.orderedList(item1, item2);

        // Равные списки одного типа
        assertEquals(unordered1, unordered2);
        assertEquals(unordered1.hashCode(), unordered2.hashCode());
        assertEquals(ordered1, ordered2);

        // Разные типы списков
        assertNotEquals(unordered1, ordered1);

        // Разное содержимое
        Element item3 = new Text("Different");
        Lists unorderedDiff = Lists.unorderedList(item3);
        assertNotEquals(unordered1, unorderedDiff);

        // Рефлексивность
        assertEquals(unordered1, unordered1);

        // Сравнение с null и другим классом
        assertNotEquals(unordered1, null);
        assertNotEquals(unordered1, "string");
    }

    @Test
    void testEdgeCasesAndFactoryMethods() {
        // Пустой список (через конструктор)
        Lists emptyUnordered = new Lists(ContainerType.UNORDERED_LIST);
        assertEquals("", emptyUnordered.toMarkdown());

        Lists emptyOrdered = new Lists(ContainerType.ORDERED_LIST);
        assertEquals("", emptyOrdered.toMarkdown());

        // Проверка фабричных методов
        Lists fromFactoryUnordered = Lists.unorderedList(new Text("Test"));
        assertEquals("- Test", fromFactoryUnordered.toMarkdown());

        Lists fromFactoryOrdered = Lists.orderedList(new Text("Test"));
        assertEquals("1. Test", fromFactoryOrdered.toMarkdown());

        // Консистентность hashCode
        Lists list = Lists.unorderedList(new Text("Item"));
        assertEquals(list.hashCode(), list.hashCode());
    }
}