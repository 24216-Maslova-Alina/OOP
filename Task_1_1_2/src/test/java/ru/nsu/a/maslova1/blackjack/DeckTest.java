package ru.nsu.a.maslova1.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

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