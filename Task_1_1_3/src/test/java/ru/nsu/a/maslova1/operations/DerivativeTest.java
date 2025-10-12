package ru.nsu.a.maslova1.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

class DerivativeTest {

    @Test
    void testMultipleDifferentiation() {
        // Многократное дифференцирование
        Expression e = new Variable("x");
        Expression de1 = e.derivative("x"); // 1
        Expression de2 = de1.derivative("x"); // 0

        assertInstanceOf(Number.class, de1);
        assertEquals(1, de1.eval(""));

        assertInstanceOf(Number.class, de2);
        assertEquals(0, de2.eval(""));
    }

    @Test
    void testComplexFunctionDerivative() {
        // f(x) = (x² + 3x)³
        Expression inner = new Add(
                new Mul(new Variable("x"), new Variable("x")),
                new Mul(new Number(3), new Variable("x"))
        );
        Expression f = new Mul(inner, new Mul(inner, inner));

        // Вычисляем производную f'(x)
        Expression derivative = f.derivative("x");

        // Проверяем, что производная вычисляется без ошибок
        assertNotNull(derivative);

        // Можно проверить конкретные значения
        // f(1) = (1 + 3)³ = 64
        // f'(1) = 3*(1 + 3)² * (2*1 + 3) = 3*16*5 = 240
        try {
            int fValue = f.eval("x=1");
            int derivativeValue = derivative.eval("x=1");

            assertEquals(64, fValue);
            // Производная в точке x=1 должна быть 240
            assertEquals(240, derivativeValue);
        } catch (Exception e) {
            fail("Вычисление не должно бросать исключение: " + e.getMessage());
        }
    }

    @Test
    void testMultiVariableExpressionEval() {
        // e = x*y + z/2
        Expression e = new Add(
                new Mul(new Variable("x"), new Variable("y")),
                new Div(new Variable("z"), new Number(2))
        );

        // Тест 1: x=3; y=4; z=10 → 3*4 + 10/2 = 12 + 5 = 17
        assertEquals(17, e.eval("x=3; y=4; z=10"));

        // Тест 2: x=0; y=100; z=8 → 0*100 + 8/2 = 0 + 4 = 4
        assertEquals(4, e.eval("x=0; y=100; z=8"));
    }

    @Test
    void testMultiVariableExpressionWithDifferentOrder() {
        Expression e = new Add(
                new Mul(new Variable("x"), new Variable("y")),
                new Div(new Variable("z"), new Number(2))
        );

        // Переменные в разном порядке
        assertEquals(17, e.eval("z=10; x=3; y=4"));

        // Лишние переменные
        assertEquals(17, e.eval("x=3; y=4; z=10; w=100"));
    }

    @Test
    void testMultiVariableExpressionMissingVariable() {
        Expression e = new Add(
                new Mul(new Variable("x"), new Variable("y")),
                new Div(new Variable("z"), new Number(2))
        );

        // z не задана → ошибка
        Exception exception = assertThrows(RuntimeException.class, () -> {
            e.eval("x=3; y=4");
        });
        assertTrue(exception.getMessage().contains("не определена"));
    }

    @Test
    void testDerivativeWithMultipleVariables() {
        // e = x*y + z/2
        Expression e = new Add(
                new Mul(new Variable("x"), new Variable("y")),
                new Div(new Variable("z"), new Number(2))
        );

        // Производная по x: y + 0
        Expression dx = e.derivative("x");
        assertEquals(4, dx.eval("x=3; y=4; z=10")); // y=4

        // Производная по y: x + 0
        Expression dy = e.derivative("y");
        assertEquals(3, dy.eval("x=3; y=4; z=10")); // x=3

        // Производная по z: 0 + 1/2
        Expression dz = e.derivative("z");
        assertEquals(0, dz.eval("x=3; y=4; z=10")); // 1/2 в целых числах = 0
    }

    @Test
    void testComplexDerivativeStructure() {
        // Проверяем структуру производной для f(x) = x²
        Expression f = new Mul(new Variable("x"), new Variable("x"));
        Expression derivative = f.derivative("x");

        // Производная x² = 2x = x*1 + x*1
        // Должна быть структура: Add(Mul(x,1), Mul(x,1))
        assertInstanceOf(Add.class, derivative);

        Add add = (Add) derivative;
        assertInstanceOf(Mul.class, add.left);
        assertInstanceOf(Mul.class, add.right);

        assertEquals(0, derivative.eval("x=0")); // 2*0 = 0
        assertEquals(2, derivative.eval("x=1")); // 2*1 = 2
        assertEquals(4, derivative.eval("x=2")); // 2*2 = 4
    }
}