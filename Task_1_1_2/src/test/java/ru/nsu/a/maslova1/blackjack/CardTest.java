package ru.nsu.a.maslova1.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class CardTest {

    @Test
    void testCardConstructor() {
        Card card = new Card(Suit.HERTS, Rank.ACE);

        assertNotNull(card);
        assertEquals(Suit.HERTS, card.getSuit());
        assertEquals(Rank.ACE, card.getRank());
    }

    @Test
    void getRank() {
        Card card1 = new Card(Suit.CLUBS, Rank.SEVEN);
        assertEquals(Rank.SEVEN, card1.getRank());

        Card card2 = new Card(Suit.DIAMONDS, Rank.KING);
        assertEquals(Rank.KING, card2.getRank());

        Card card3 = new Card(Suit.HERTS, Rank.ACE);
        assertEquals(Rank.ACE, card3.getRank());
    }

    @Test
    void testToString() {
        Card card2 = new Card(Suit.DIAMONDS, Rank.QUEEN);
        String expected2 = "Дама Бубны(♦)(10)"; // или "Королева Бубны(10)"
        assertEquals(expected2, card2.toString());

        Card card3 = new Card(Suit.HERTS, Rank.ACE);
        String expected3 = "Туз Червы(♥)(11)"; // или "Туз Червы(11)"
        assertEquals(expected3, card3.toString());

        Card card4 = new Card(Suit.SPADES, Rank.TWO);
        String expected4 = "Двойка Пики(♠)(2)";
        assertEquals(expected4, card4.toString());
    }

    @Test
    void getValue() {
        Card two = new Card(Suit.CLUBS, Rank.TWO);
        assertEquals(2, two.getValue());

        Card seven = new Card(Suit.DIAMONDS, Rank.SEVEN);
        assertEquals(7, seven.getValue());

        Card ten = new Card(Suit.HERTS, Rank.TEN);
        assertEquals(10, ten.getValue());

        Card jack = new Card(Suit.SPADES, Rank.JACK);
        assertEquals(10, jack.getValue());

        Card queen = new Card(Suit.CLUBS, Rank.QUEEN);
        assertEquals(10, queen.getValue());

        Card king = new Card(Suit.DIAMONDS, Rank.KING);
        assertEquals(10, king.getValue());

        Card ace = new Card(Suit.HERTS, Rank.ACE);
        assertEquals(11, ace.getValue());
    }

    @Test
    void testDifferentSuits() {
        Card clubs = new Card(Suit.CLUBS, Rank.ACE);
        Card diamonds = new Card(Suit.DIAMONDS, Rank.ACE);
        Card hearts = new Card(Suit.HERTS, Rank.ACE);
        Card spades = new Card(Suit.SPADES, Rank.ACE);

        assertEquals(Rank.ACE, clubs.getRank());
        assertEquals(Rank.ACE, diamonds.getRank());
        assertEquals(Rank.ACE, hearts.getRank());
        assertEquals(Rank.ACE, spades.getRank());

        assertNotEquals(clubs.toString(), diamonds.toString());
        assertNotEquals(hearts.toString(), spades.toString());
    }

    @Test
    void testAllRanks() {
        for (Rank rank : Rank.values()) {
            Card card = new Card(Suit.HERTS, rank);
            assertEquals(rank, card.getRank());
            assertEquals(rank.getValue(), card.getValue());
            assertNotNull(card.toString());
        }
    }
}