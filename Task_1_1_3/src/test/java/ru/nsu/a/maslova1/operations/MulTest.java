package ru.nsu.a.maslova1.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MulTest {

    @Test
    void derivativeMulWithConstants() {
        Expression expr = new Mul(new Number(3), new Number(4));
        Expression der = expr.derivative("x");

        // Производная от константа*константа = 0
        int result = der.eval("x");
        assertEquals(0, result); // (0*4 + 3*0) = 0 + 0 = 0
    }

    @Test
    void evalMulWithNumbers() {
        Expression expr = new Mul(new Number(3), new Number(4));
        int result = expr.eval("x");
        assertEquals(12, result);
    }

    @Test
    void evalMulWithLargeNumbers() {
        Expression expr = new Mul(new Number(100), new Number(200));
        int result = expr.eval("x");
        assertEquals(20000, result);
    }

    @Test
    void evalMulWithOne() {
        Expression expr = new Mul(new Number(7), new Number(1));
        int result = expr.eval("x");
        assertEquals(7, result);

        expr = new Mul(new Number(1), new Number(7));
        result = expr.eval("x");
        assertEquals(7, result);
    }

    @Test
    void evalMulWithZero() {
        Expression expr = new Mul(new Number(0), new Number(5));
        int result = expr.eval("x");
        assertEquals(0, result);

        expr = new Mul(new Number(5), new Number(0));
        result = expr.eval("x");
        assertEquals(0, result);
    }

    @Test
    void evalMulCommutativeProperty() {
        Expression expr1 = new Mul(new Number(3), new Number(4));
        Expression expr2 = new Mul(new Number(4), new Number(3));

        int result1 = expr1.eval("x");
        int result2 = expr2.eval("x");

        assertEquals(result1, result2); // 3*4 = 4*3
    }

    @Test
    void printMulWithVariablesTest() {
        Expression expr = new Mul(new Variable("x"), new Variable("y"));

        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        expr.print();

        System.setOut(System.out);
        assertEquals("(x*y)", outContent.toString());
    }

    @Test
    void printMulWithMixedTest() {
        Expression expr = new Mul(new Number(5), new Variable("z"));

        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        expr.print();

        System.setOut(System.out);
        assertEquals("(5*z)", outContent.toString());
    }

    @Test
    void complexMulExpressionPrint() {
        // ((2*3)*(4*5))
        Expression expr = new Mul(
                new Mul(new Number(2), new Number(3)),
                new Mul(new Number(4), new Number(5))
        );

        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        expr.print();

        System.setOut(System.out);
        assertEquals("((2*3)*(4*5))", outContent.toString());
    }

    @Test
    void mulWithAddInLeft() {
        // (x+1)*2
        Expression expr = new Mul(
                new Add(new Variable("x"), new Number(1)),
                new Number(2)
        );

        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        expr.print();

        System.setOut(System.out);
        String output = outContent.toString();
        assertTrue(output.contains("+"));
        assertTrue(output.contains("*"));
    }
}