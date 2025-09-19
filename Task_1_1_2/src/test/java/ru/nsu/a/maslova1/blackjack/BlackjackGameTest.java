package ru.nsu.a.maslova1.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class BlackjackGameTest {
    void setUp() {
        BlackjackGame.dealerPoint = 0;
        BlackjackGame.playerPoint = 0;
    }

    private Card card(Card.Suits suit, Card.Rank rank) {
        return new Card(suit, rank);
    }

    @Test
    void testDealerWins() {
        setUp();
        Dealer.PlayerCards = new ArrayList<>();
        Dealer.PlayerCards.add(card(Card.Suits.CLABS, Card.Rank.TWO));
        Dealer.PlayerCards.add(card(Card.Suits.DIAMONDS, Card.Rank.THREE));

        Dealer.DealerCards = new ArrayList<>();
        Dealer.DealerCards.add(card(Card.Suits.HERTS, Card.Rank.KING));
        Dealer.DealerCards.add(card(Card.Suits.HERTS, Card.Rank.FOUR));

        BlackjackGame.determineRoundWinner();

        assertEquals(1, BlackjackGame.dealerPoint);
        assertEquals(0, BlackjackGame.playerPoint);
    }

    @Test
    void testPlayerWins() {
        setUp();
        Dealer.PlayerCards = new ArrayList<>();
        Dealer.PlayerCards.add(card(Card.Suits.CLABS, Card.Rank.QUEEN));
        Dealer.PlayerCards.add(card(Card.Suits.DIAMONDS, Card.Rank.TEN));

        Dealer.DealerCards = new ArrayList<>();
        Dealer.DealerCards.add(card(Card.Suits.HERTS, Card.Rank.KING));
        Dealer.DealerCards.add(card(Card.Suits.SPADES, Card.Rank.TWO));

        BlackjackGame.determineRoundWinner();

        assertEquals(0, BlackjackGame.dealerPoint);
        assertEquals(1, BlackjackGame.playerPoint);
    }

    @Test
    void testNobodyWins() {
        setUp();
        Dealer.PlayerCards = new ArrayList<>();
        Dealer.PlayerCards.add(card(Card.Suits.CLABS, Card.Rank.ACE));
        Dealer.PlayerCards.add(card(Card.Suits.DIAMONDS, Card.Rank.THREE));

        Dealer.DealerCards = new ArrayList<>();
        Dealer.DealerCards.add(card(Card.Suits.CLABS, Card.Rank.ACE));
        Dealer.DealerCards.add(card(Card.Suits.DIAMONDS, Card.Rank.THREE));

        BlackjackGame.determineRoundWinner();

        assertEquals(0, BlackjackGame.dealerPoint);
        assertEquals(0, BlackjackGame.playerPoint);
    }
    }