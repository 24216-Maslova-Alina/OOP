package ru.nsu.a.maslova1.substringsearch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;


class SearchSubstringTest {

    @TempDir
    Path tempDir;

    @Test
    void testEmptySubstring() throws IOException {
        File testFile = createTestFile("empty_substring.txt", "hello world");
        SearchSubstring search = new SearchSubstring();
        List<Integer> result = search.find(testFile.getPath(), "");
        assertEquals(0, result.size());
    }

    @Test
    void testSubstringLongerThanFile() throws IOException {
        File testFile = createTestFile("short.txt", "abc");
        SearchSubstring search = new SearchSubstring();
        List<Integer> result = search.find(testFile.getPath(), "abcdef");
        assertEquals(0, result.size());
    }

    @Test
    void testSingleMatch() throws IOException {
        File testFile = createTestFile("single.txt", "hello world");
        SearchSubstring search = new SearchSubstring();
        List<Integer> result = search.find(testFile.getPath(), "world");

        assertEquals(1, result.size());
        assertEquals(6, result.get(0));
    }

    @Test
    void testNoMatches() throws IOException {
        File testFile = createTestFile("none.txt", "hello world");
        SearchSubstring search = new SearchSubstring();
        List<Integer> result = search.find(testFile.getPath(), "test");
        assertEquals(0, result.size());
    }

    @Test
    void testOverlappingMatches() throws IOException {
        File testFile = createTestFile("overlap.txt", "aaa");
        SearchSubstring search = new SearchSubstring();
        List<Integer> result = search.find(testFile.getPath(), "aa");

        assertEquals(2, result.size());
        assertEquals(List.of(0, 1), result);
    }

    @Test
    void testRussianText() throws IOException {
        File testFile = createTestFile("russian.txt", "привет мир привет");
        SearchSubstring search = new SearchSubstring();
        List<Integer> result = search.find(testFile.getPath(), "привет");

        assertEquals(2, result.size());
        assertEquals(List.of(0, 11), result);
    }

    @Test
    void testCaseSensitivity() throws IOException {
        File testFile = createTestFile("case.txt", "Hello hello HELLO");
        SearchSubstring search = new SearchSubstring();
        List<Integer> result = search.find(testFile.getPath(), "hello");

        assertEquals(1, result.size());
        assertEquals(6, result.get(0));
    }

    @Test
    void testFileNotFound() {
        SearchSubstring search = new SearchSubstring();
        assertThrows(RuntimeException.class, () -> {
            search.find("nonexistent_file.txt", "test");
        });
    }

    @Test
    void testRepeatedUse() throws IOException {
        File testFile = createTestFile("reuse.txt", "test test test");
        SearchSubstring search = new SearchSubstring();

        List<Integer> result1 = search.find(testFile.getPath(), "test");
        assertEquals(3, result1.size());

        List<Integer> result2 = search.find(testFile.getPath(), "test");
        assertEquals(3, result2.size());
    }

    @Test
    void testSingleCharacterSearch() throws IOException {
        File testFile = createTestFile("single_char.txt", "abcabc");
        SearchSubstring search = new SearchSubstring();
        List<Integer> result = search.find(testFile.getPath(), "a");

        assertEquals(2, result.size());
        assertEquals(List.of(0, 3), result);
    }

    @Test
    void testExactMatch() throws IOException {
        File testFile = createTestFile("exact.txt", "exact");
        SearchSubstring search = new SearchSubstring();
        List<Integer> result = search.find(testFile.getPath(), "exact");

        assertEquals(1, result.size());
        assertEquals(0, result.get(0));
    }

    // Вспомогательный метод для создания временных файлов
    private File createTestFile(String filename, String content) throws IOException {
        File testFile = tempDir.resolve(filename).toFile();
        try (FileWriter writer = new FileWriter(testFile, StandardCharsets.UTF_8)) {
            writer.write(content);
        }
        return testFile;
    }
}