package ru.nsu.a.maslova1.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RankTest {

    @Test
    void getRussianName() {
        assertEquals("Двойка", Rank.TWO.getRussianName());
        assertEquals("Тройка", Rank.THREE.getRussianName());
        assertEquals("Четверка", Rank.FOUR.getRussianName());
        assertEquals("Пятерка", Rank.FIVE.getRussianName());
        assertEquals("Шестерка", Rank.SIX.getRussianName());
        assertEquals("Семерка", Rank.SEVEN.getRussianName());
        assertEquals("Восьмерка", Rank.EIGHT.getRussianName());
        assertEquals("Девятка", Rank.NINE.getRussianName());
        assertEquals("Десятка", Rank.TEN.getRussianName());

        assertEquals("Валет", Rank.JACK.getRussianName());
        assertEquals("Дама", Rank.QUEEN.getRussianName());
        assertEquals("Король", Rank.KING.getRussianName());

        assertEquals("Туз", Rank.ACE.getRussianName());
    }

    @Test
    void getValue() {
        assertEquals(2, Rank.TWO.getValue());
        assertEquals(3, Rank.THREE.getValue());
        assertEquals(4, Rank.FOUR.getValue());
        assertEquals(5, Rank.FIVE.getValue());
        assertEquals(6, Rank.SIX.getValue());
        assertEquals(7, Rank.SEVEN.getValue());
        assertEquals(8, Rank.EIGHT.getValue());
        assertEquals(9, Rank.NINE.getValue());
        assertEquals(10, Rank.TEN.getValue());

        assertEquals(10, Rank.JACK.getValue());
        assertEquals(10, Rank.QUEEN.getValue());
        assertEquals(10, Rank.KING.getValue());

        assertEquals(11, Rank.ACE.getValue());
    }

    @Test
    void testToString() {
        assertEquals("TWO", Rank.TWO.toString());
        assertEquals("THREE", Rank.THREE.toString());
        assertEquals("JACK", Rank.JACK.toString());
        assertEquals("ACE", Rank.ACE.toString());
    }


}