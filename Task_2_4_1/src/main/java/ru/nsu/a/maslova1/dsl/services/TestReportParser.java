package ru.nsu.a.maslova1.dsl.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.nsu.a.maslova1.dsl.model.TestStats;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class TestReportParser {

    public static TestStats parse(File taskDir) {
        TestStats stats = new TestStats();

        File reportsDir = new File(taskDir, "build/test-results/test");

        if (!reportsDir.exists() || !reportsDir.isDirectory()) {
            System.out.println("   [!] Отчеты о тестах не найдены. Возможно, тесты не запускались или их нет.");
            return stats; // Возвращаем нули
        }

        File[] xmlFiles = reportsDir.listFiles((dir, name) -> name.endsWith(".xml"));
        if (xmlFiles == null || xmlFiles.length == 0) {
            return stats;
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            for (File xml : xmlFiles) {
                Document doc = builder.parse(xml);
                doc.getDocumentElement().normalize();

                Element testsuite = (Element) doc.getElementsByTagName("testsuite").item(0);
                if (testsuite != null) {
                    stats.total += Integer.parseInt(testsuite.getAttribute("tests"));
                    stats.failed += Integer.parseInt(testsuite.getAttribute("failures"));
                    stats.skipped += Integer.parseInt(testsuite.getAttribute("skipped"));
                }
            }
            stats.passed = stats.total - stats.failed - stats.skipped;

        } catch (Exception e) {
            System.err.println("   [ОШИБКА] Не удалось распарсить отчет: " + e.getMessage());
        }

        return stats;
    }
}