package ru.nsu.a.maslova1.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SuitTest {

    @Test
    void getRussianName() {
        assertEquals("Трефы(♣)", Suit.CLUBS.getRussianName());
        assertEquals("Бубны(♦)", Suit.DIAMONDS.getRussianName());
        assertEquals("Червы(♥)", Suit.HEARTS.getRussianName());
        assertEquals("Пики(♠)", Suit.SPADES.getRussianName());
    }

    @Test
    void values() {
        Suit[] suits = Suit.values();
        assertEquals(4, suits.length);
    }

    @Test
    void valueOf() {
        // Test 1: Проверяем преобразование строк в значения enum
        assertEquals(Suit.CLUBS, Suit.valueOf("CLUBS"));
        assertEquals(Suit.DIAMONDS, Suit.valueOf("DIAMONDS"));
        assertEquals(Suit.HEARTS, Suit.valueOf("HERTS"));
        assertEquals(Suit.SPADES, Suit.valueOf("SPADES"));
    }

}