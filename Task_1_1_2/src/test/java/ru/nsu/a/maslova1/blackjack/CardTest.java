package ru.nsu.a.maslova1.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CardTest {

    @Test
    void testGetValue() {
        Card card = new Card(Card.Suits.CLABS, Card.Rank.ACE);
        assertEquals(11, card.getValue());

        Card card2 = new Card(Card.Suits.DIAMONDS, Card.Rank.TWO);
        assertEquals(2, card2.getValue());
    }

    @Test
    void testToString() {
        Card card = new Card(Card.Suits.HERTS, Card.Rank.KING);
        String expected = "Король Червы(♥)(10)";
        assertEquals(expected, card.toString());

        Card card2 = new Card(Card.Suits.SPADES, Card.Rank.ACE);
        String expected2 = "Туз Пики(♠)(11)";
        assertEquals(expected2, card2.toString());
    }

    @Test
    void suit() {
        Card card = new Card(Card.Suits.DIAMONDS, Card.Rank.QUEEN);
        assertEquals(Card.Suits.DIAMONDS, card.suit());
        assertEquals("Бубны(♦)", card.suit().getRussianName());
    }

    @Test
    void rank() {
        Card card = new Card(Card.Suits.CLABS, Card.Rank.JACK);
        assertEquals(Card.Rank.JACK, card.rank());
        assertEquals("Валет", card.rank().getRussianName());
        assertEquals(10, card.rank().getValue());
    }

    @Test
    void recordComponents() {
        Card card = new Card(Card.Suits.SPADES, Card.Rank.FIVE);

        assertEquals(Card.Suits.SPADES, card.suit());
        assertEquals(Card.Rank.FIVE, card.rank());
        assertEquals(5, card.getValue());
        assertEquals("Пики(♠)", card.suit().getRussianName());
        assertEquals("Пятерка", card.rank().getRussianName());
    }
}