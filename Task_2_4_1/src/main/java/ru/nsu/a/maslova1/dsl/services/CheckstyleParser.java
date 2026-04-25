package ru.nsu.a.maslova1.dsl.services;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class CheckstyleParser {

    public static int countErrors(File taskDir) {
        File reportFile = new File(taskDir, "build/reports/checkstyle/main.xml");
        if (!reportFile.exists()) return 0;

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(reportFile);

            // Считаем все теги <error>
            NodeList errors = doc.getElementsByTagName("error");
            return errors.getLength();
        } catch (Exception e) {
            System.err.println("   [!] Ошибка парсинга Checkstyle: " + e.getMessage());
            return 0;
        }
    }
}