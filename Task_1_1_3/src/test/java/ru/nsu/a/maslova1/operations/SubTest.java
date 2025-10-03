package ru.nsu.a.maslova1.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SubTest {

    @Test
    void derivativeSubWithVariableLeft() {
        // x - 2
        Expression expr = new Sub(new Variable("x"), new Number(2));
        Expression der = expr.derivative("x");

        // 1 - 0 = 1
        int result = der.eval("x");
        assertEquals(1, result);
    }

    @Test
    void derivativeSubWithVariableRight() {
        // 5 - x
        Expression expr = new Sub(new Number(5), new Variable("x"));
        Expression der = expr.derivative("x");

        // 0 - 1 = -1
        int result = der.eval("x");
        assertEquals(-1, result);
    }

    @Test
    void derivativeSubWithTwoVariables() {
        // x - y
        Expression expr = new Sub(new Variable("x"), new Variable("y"));

        // Производная по x: 1 - 0 = 1
        Expression derX = expr.derivative("x");
        int resultX = derX.eval("x");
        assertEquals(1, resultX);

        // Производная по y: 0 - 1 = -1
        Expression derY = expr.derivative("y");
        int resultY = derY.eval("y");
        assertEquals(-1, resultY);
    }

    @Test
    void evalSubWithNumbers() {
        Expression expr = new Sub(new Number(8), new Number(3));
        int result = expr.eval("x");
        assertEquals(5, result);
    }

    @Test
    void evalSubWithLargeNumbers() {
        Expression expr = new Sub(new Number(1000), new Number(500));
        int result = expr.eval("x");
        assertEquals(500, result);
    }

    @Test
    void evalSubWithNegativeResult() {
        Expression expr = new Sub(new Number(3), new Number(8));
        int result = expr.eval("x");
        assertEquals(-5, result);
    }
}