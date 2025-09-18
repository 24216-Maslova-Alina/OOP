package ru.nsu.a.maslova1.blackjack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void testTakeCard() {
        Deck.deckCreate();
        int deckSize = Deck.deckSize();

        Deck.takeCard();
        assertEquals(deckSize - 1, Deck.deckSize());
    }

    @Test
    void deckSize() {
        Deck.deckCreate();
        assertEquals(52, Deck.deckSize());
    }

    @Test
    void testIsEmpty() {
        assertFalse(Deck.isEmpty());

        while (!Deck.isEmpty()) {
            Deck.takeCard();
        }

        assertTrue(Deck.isEmpty());
    }
}