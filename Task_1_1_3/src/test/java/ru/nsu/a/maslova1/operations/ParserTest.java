package ru.nsu.a.maslova1.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.Test;

class ParserTest {
    private final Parser parser = new Parser();

    @Test
    void testParseNumber() {
        Expression expr = parser.parse("42");
        assertInstanceOf(Number.class, expr);
        assertEquals(42, ((Number) expr).eval(""));
    }

    @Test
    void testParseNegativeNumber() {
        Expression expr = parser.parse("-5");
        assertInstanceOf(Number.class, expr);
        assertEquals(-5, ((Number) expr).eval(""));
    }

    @Test
    void testParseVariable() {
        Expression expr = parser.parse("x");
        assertInstanceOf(Variable.class, expr);
        assertEquals(10, expr.eval("x=10"));
    }

    @Test
    void testParseVariableWithUnderscore() {
        Expression expr = parser.parse("var_name");
        assertInstanceOf(Variable.class, expr);
        assertEquals(5, expr.eval("var_name=5"));
    }

    @Test
    void testParseAddition() {
        Expression expr = parser.parse("(2+3)");
        assertInstanceOf(Add.class, expr);
        assertEquals(5, expr.eval(""));
    }

    @Test
    void testParseSubtraction() {
        Expression expr = parser.parse("(5-2)");
        assertInstanceOf(Sub.class, expr);
        assertEquals(3, expr.eval(""));
    }

    @Test
    void testParseMultiplication() {
        Expression expr = parser.parse("(3*4)");
        assertInstanceOf(Mul.class, expr);
        assertEquals(12, expr.eval(""));
    }

    @Test
    void testParseDivision() {
        Expression expr = parser.parse("(10/2)");
        assertInstanceOf(Div.class, expr);
        assertEquals(5, expr.eval(""));
    }

    @Test
    void testParseUnaryMinus() {
        Expression expr = parser.parse("(-5)");
        assertInstanceOf(Mul.class, expr);
        assertEquals(-5, expr.eval(""));
    }
}