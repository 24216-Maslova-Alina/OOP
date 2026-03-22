package ru.nsu.a.maslova1.pizzeria;

import ru.nsu.a.maslova1.pizzeria.input.PizzeriaConfig;
import ru.nsu.a.maslova1.pizzeria.init.PizzeriaComponents;
import ru.nsu.a.maslova1.pizzeria.init.PizzeriaInitializer;
import ru.nsu.a.maslova1.pizzeria.input.ReadFile;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            System.err.println("Ошибка в потоке: " + thread.getName());
            throwable.printStackTrace();
        });

        try {
            PizzeriaConfig config = ReadFile.read("information.json");
            PizzeriaComponents components = PizzeriaInitializer.init(config);

            new Pizzeria(components).start();

        } catch (IOException e) {
            System.err.println("Ошибка чтения конфигурации:");
            e.printStackTrace();
        }
    }
}