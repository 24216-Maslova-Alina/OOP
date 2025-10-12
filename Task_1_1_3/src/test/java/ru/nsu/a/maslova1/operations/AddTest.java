package ru.nsu.a.maslova1.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class AddTest {

    @Test
    void derivativeAddTest() {
        Expression expr = new Add(new Number(3), new Variable("x"));
        Expression der = expr.derivative("x");

        // Проверяем через eval
        int result = der.eval("x");
        assertEquals(1, result); // 0 + 1 = 1
    }

    @Test
    void derivativeAddWithConstants() {
        Expression expr = new Add(new Number(5), new Number(7));
        Expression der = expr.derivative("x");

        int result = der.eval("x");
        assertEquals(0, result); // 0 + 0 = 0
    }

    @Test
    void derivativeAddWithMultipleVariables() {
        Expression expr = new Add(new Variable("x"), new Variable("y"));

        Expression derX = expr.derivative("x");
        int resultX = derX.eval("x");
        assertEquals(1, resultX); // 1 + 0 = 1

        Expression derY = expr.derivative("y");
        int resultY = derY.eval("y");
        assertEquals(1, resultY); // 0 + 1 = 1
    }

    @Test
    void evalAddWithNumbers() {
        Expression expr = new Add(new Number(3), new Number(4));
        int result = expr.eval("x");
        assertEquals(7, result);
    }

    @Test
    void evalAddWithNegativeNumbers() {
        Expression expr = new Add(new Number(-5), new Number(10));
        int result = expr.eval("x");
        assertEquals(5, result);
    }

    @Test
    void printAddTest() {
        Expression expr = new Add(new Number(2), new Number(3));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        expr.print();

        System.setOut(System.out);
        assertEquals("(2+3)", outContent.toString());
    }

    @Test
    void printAddWithVariablesTest() {
        Expression expr = new Add(new Variable("x"), new Variable("y"));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        expr.print();

        System.setOut(System.out);
        assertEquals("(x+y)", outContent.toString());
    }

    @Test
    void printAddWithMixedTest() {
        Expression expr = new Add(new Number(5), new Variable("z"));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        expr.print();

        System.setOut(System.out);
        assertEquals("(5+z)", outContent.toString());
    }

    @Test
    void complexExpressionEval() {
        // (1 + 2) + (3 + 4)
        Expression expr = new Add(
                new Add(new Number(1), new Number(2)),
                new Add(new Number(3), new Number(4))
        );

        int result = expr.eval("x");
        assertEquals(10, result); // (1+2) + (3+4) = 3 + 7 = 10
    }

    @Test
    void nestedAddExpressions() {
        Expression expr = new Add(
                new Add(new Number(1), new Number(2)),
                new Add(new Number(3), new Number(4))
        );

        int result = expr.eval("x");
        assertEquals(10, result);
    }
}