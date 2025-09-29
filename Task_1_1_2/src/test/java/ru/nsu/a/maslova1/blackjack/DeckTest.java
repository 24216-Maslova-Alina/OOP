package ru.nsu.a.maslova1.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DeckTest {
    private Deck deck;
    private ConsoleOutput output;

    void setUp() {
        output = new ConsoleOutput();
        deck = new Deck(output);
    }

    @Test
    void testDeckCreate() {
        setUp();

        assertEquals(52, deck.deckSize());
    }

    @Test
    void testTakeCard() {
        setUp();
        int deckSize = deck.deckSize();

        deck.takeCard();
        assertEquals(deckSize - 1, deck.deckSize());
    }

    @Test
    void deckSize() {
        setUp();
        assertEquals(52, deck.deckSize());
    }

    @Test
    void testIsEmpty() {
        setUp();
        assertFalse(deck.isEmpty());

        while (!deck.isEmpty()) {
            deck.takeCard();
        }

        assertTrue(deck.isEmpty());
    }
}