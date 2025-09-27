package ru.nsu.a.maslova1.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

class PlayerTest {
    private Player player;
    private Dealer dealer;

    void setUp() {
        ConsoleOutput output = new ConsoleOutput();
        player = new Player(output);
        dealer = new Dealer(output);
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

        player.playerMove();

        int dealerPoint = BlackjackGame.dealerPoint;

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

        player.playerMove();

        assertEquals(playerCards + 1, Dealer.PlayerCards.size());
        assertTrue(dealer.calculatePoints(Dealer.PlayerCards) > 0);
    }
}