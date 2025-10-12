package ru.nsu.a.maslova1.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class DivTest {

    @Test
    void derivativeDivWithConstants() {
        Expression expr = new Div(new Number(6), new Number(2));
        Expression der = expr.derivative("x");

        // Производная от константы/константа = 0
        int result = der.eval("x");
        assertEquals(0, result); // (0*2 - 6*0)/(2*2) = 0/4 = 0
    }

    @Test
    void evalDivWithNumbers() {
        Expression expr = new Div(new Number(8), new Number(2));
        int result = expr.eval("x");
        assertEquals(4, result);
    }

    @Test
    void evalDivWithLargeNumbers() {
        Expression expr = new Div(new Number(1000), new Number(100));
        int result = expr.eval("x");
        assertEquals(10, result);
    }

    @Test
    void evalDivWithTruncation() {
        Expression expr = new Div(new Number(7), new Number(2));
        int result = expr.eval("x");
        assertEquals(3, result); // 7/2 = 3.5 -> 3 (целочисленное деление)
    }

    @Test
    void evalDivSameNumber() {
        Expression expr = new Div(new Number(7), new Number(7));
        int result = expr.eval("x");
        assertEquals(1, result);
    }

    @Test
    void printDivTest() {
        Expression expr = new Div(new Number(8), new Number(2));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        expr.print();

        System.setOut(System.out);
        assertEquals("(8/2)", outContent.toString());
    }

    @Test
    void printDivWithVariablesTest() {
        Expression expr = new Div(new Variable("x"), new Variable("y"));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        expr.print();

        System.setOut(System.out);
        assertEquals("(x/y)", outContent.toString());
    }

    @Test
    void printDivWithMixedTest() {
        Expression expr = new Div(new Number(10), new Variable("z"));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        expr.print();

        System.setOut(System.out);
        assertEquals("(10/z)", outContent.toString());
    }

    @Test
    void complexDivExpressionEval() {
        // (8/2) / (4/2)
        Expression expr = new Div(
                new Div(new Number(8), new Number(2)),
                new Div(new Number(4), new Number(2))
        );

        int result = expr.eval("x"); // (8/2)/(4/2) = 4/2 = 2
        assertEquals(2, result);
    }

    @Test
    void complexDivExpressionPrint() {
        // ((8/2)/(4/2))
        Expression expr = new Div(
                new Div(new Number(8), new Number(2)),
                new Div(new Number(4), new Number(2))
        );

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        expr.print();

        System.setOut(System.out);
        assertEquals("((8/2)/(4/2))", outContent.toString());
    }
}