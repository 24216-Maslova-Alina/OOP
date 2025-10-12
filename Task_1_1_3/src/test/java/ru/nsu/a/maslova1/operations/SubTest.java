package ru.nsu.a.maslova1.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class SubTest {

    @Test
    void printSubTest() {
        // Тестируем вывод выражения (5-3)
        Expression expr = new Sub(new Number(5), new Number(3));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            expr.print();
            assertEquals("(5-3)", outContent.toString());
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void evalSubWithVariables() {
        // x - y при x=10, y=4
        Expression expr = new Sub(new Variable("x"), new Variable("y"));
        int result = expr.eval("x=10; y=4");
        assertEquals(6, result);
    }

    @Test
    void evalSubComplexExpression() {
        // (x-2) - (y-1) при x=8, y=3
        Expression inner1 = new Sub(new Variable("x"), new Number(2));
        Expression inner2 = new Sub(new Variable("y"), new Number(1));
        Expression expr = new Sub(inner1, inner2);

        int result = expr.eval("x=8; y=3");
        // (8-2) - (3-1) = 6 - 2 = 4
        assertEquals(4, result);
    }

    @Test
    void derivativeSubComplexExpression() {
        // (x-2) - x
        Expression left = new Sub(new Variable("x"), new Number(2));
        Expression expr = new Sub(left, new Variable("x"));

        // Производная: (1-0) - 1 = 1 - 1 = 0
        Expression der = expr.derivative("x");
        int result = der.eval("x=5"); // Любое значение x даст 0
        assertEquals(0, result);
    }

    @Test
    void derivativeSubWithMixedExpressions() {
        // (x*2) - (x+1)
        Expression left = new Mul(new Variable("x"), new Number(2));
        Expression right = new Add(new Variable("x"), new Number(1));
        Expression expr = new Sub(left, right);

        // Производная: ((1*2 + x*0) - (1+0)) = (2 - 1) = 1
        Expression der = expr.derivative("x");
        int result = der.eval("x=3"); // Для любого x результат 1
        assertEquals(1, result);
    }

    @Test
    void evalSubWithZero() {
        // x - 0 = x
        Expression expr = new Sub(new Variable("x"), new Number(0));
        int result = expr.eval("x=7");
        assertEquals(7, result);
    }

    @Test
    void evalSubSameValues() {
        // x - x = 0
        Expression expr = new Sub(new Variable("x"), new Variable("x"));
        int result = expr.eval("x=5");
        assertEquals(0, result);
    }

    @Test
    void printSubWithVariablesTest() {
        // Тестируем вывод выражения (x-y)
        Expression expr = new Sub(new Variable("x"), new Variable("y"));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            expr.print();
            assertEquals("(x-y)", outContent.toString());
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void derivativeSubNestedOperations() {
        // (x - (y - 2))
        Expression inner = new Sub(new Variable("y"), new Number(2));
        Expression expr = new Sub(new Variable("x"), inner);

        // Производная по x: 1 - 0 = 1
        Expression derX = expr.derivative("x");
        assertEquals(1, derX.eval("x=1;y=1"));

        // Производная по y: 0 - (1 - 0) = -1
        Expression derY = expr.derivative("y");
        assertEquals(-1, derY.eval("x=1;y=1"));
    }

    @Test
    void evalSubWithNegativeNumbers() {
        // (-5) - (-3) = -2
        Expression expr = new Sub(new Number(-5), new Number(-3));
        int result = expr.eval("");
        assertEquals(-2, result);
    }

    @Test
    void evalSubWithMultipleVariables() {
        // x - y - z при x=10, y=3, z=2
        Expression firstSub = new Sub(new Variable("x"), new Variable("y"));
        Expression expr = new Sub(firstSub, new Variable("z"));

        int result = expr.eval("x=10; y=3; z=2");
        // (10-3) - 2 = 7 - 2 = 5
        assertEquals(5, result);
    }

    @Test
    void derivativeSubChain() {
        // Многократное дифференцирование: x - 1
        Expression expr = new Sub(new Variable("x"), new Number(1));

        // Первая производная: 1 - 0 = 1
        Expression firstDer = expr.derivative("x");
        assertEquals(1, firstDer.eval("x=5"));

        // Вторая производная: 0 - 0 = 0
        Expression secondDer = firstDer.derivative("x");
        assertEquals(0, secondDer.eval("x=5"));
    }
}