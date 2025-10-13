package ru.nsu.a.maslova1.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class NumberTest {

    @Test
    void evalPositiveNumber() {
        Expression number = new Number(5);
        int result = number.eval("x");
        assertEquals(5, result);
    }

    @Test
    void evalNegativeNumber() {
        Expression number = new Number(-3);
        int result = number.eval("x");
        assertEquals(-3, result);
    }

    @Test
    void evalZero() {
        Expression number = new Number(0);
        int result = number.eval("x");
        assertEquals(0, result);
    }

    @Test
    void evalLargeNumber() {
        Expression number = new Number(1000000);
        int result = number.eval("x");
        assertEquals(1000000, result);
    }

    @Test
    void evalWithDifferentVariables() {
        Expression number = new Number(42);

        // Должно возвращать одно и то же значение независимо от переменной
        int result1 = number.eval("x");
        int result2 = number.eval("y");
        int result3 = number.eval("any_variable");

        assertEquals(42, result1);
        assertEquals(42, result2);
        assertEquals(42, result3);
        assertEquals(result1, result2);
        assertEquals(result2, result3);
    }

    @Test
    void derivativeWithDifferentVariables() {
        Expression number = new Number(10);

        // Производная должна быть 0 для любой переменной
        Expression der1 = number.derivative("x");
        Expression der2 = number.derivative("y");
        Expression der3 = number.derivative("any_name");

        int result1 = der1.eval("x");
        int result2 = der2.eval("y");
        int result3 = der3.eval("z");

        assertEquals(0, result1);
        assertEquals(0, result2);
        assertEquals(0, result3);
    }

    @Test
    void printPositiveNumber() {
        Expression number = new Number(123);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        number.print();

        System.setOut(System.out);
        assertEquals("123", outContent.toString());
    }

    @Test
    void printNegativeNumber() {
        Expression number = new Number(-456);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        number.print();

        System.setOut(System.out);
        assertEquals("-456", outContent.toString());
    }

    @Test
    void printZero() {
        Expression number = new Number(0);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        number.print();

        System.setOut(System.out);
        assertEquals("0", outContent.toString());
    }
}