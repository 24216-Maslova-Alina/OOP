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
        Deck.deckCreate();

        assertEquals(52, Deck.deckSize());
    }

    @Test
    void testTakeCard() {
        setUp();
        Deck.deckCreate();
        int deckSize = Deck.deckSize();

        deck.takeCard();
        assertEquals(deckSize - 1, Deck.deckSize());
    }

    @Test
    void deckSize() {
        setUp();
        Deck.deckCreate();
        assertEquals(52, Deck.deckSize());
    }

    @Test
    void testIsEmpty() {
        setUp();
        assertFalse(Deck.isEmpty());

        while (!Deck.isEmpty()) {
            deck.takeCard();
        }

        assertTrue(Deck.isEmpty());
    }
}