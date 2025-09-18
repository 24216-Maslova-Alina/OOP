package ru.nsu.a.maslova1.blackjack;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    void setUp() {
        Deck.deckCreate();
        BlackjackGame.dealerPoint = 0;
        BlackjackGame.playerPoint = 0;
    }

    @Test
    void testPlayerMoveStopImmediately() {
        setUp();
        String input = "0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        int playerCards = Dealer.PlayerCards.size();
        int playerPoints = BlackjackGame.playerPoint;
        int dealerPoint = BlackjackGame.dealerPoint;

        Player.playerMove();

        assertEquals(playerCards, Dealer.PlayerCards.size());
        assertEquals(playerPoints, BlackjackGame.playerPoint);
        assertEquals(dealerPoint, BlackjackGame.dealerPoint);
    }

    @Test
    void testPlayerMoveTakeOneCard() {
        setUp();
        String input = "1\n0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        int playerCards = Dealer.PlayerCards.size();

        Player.playerMove();

        assertEquals(playerCards + 1, Dealer.PlayerCards.size());
        assertTrue(Dealer.calculatePoints(Dealer.PlayerCards) > 0);
    }
}