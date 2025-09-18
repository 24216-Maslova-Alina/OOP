package ru.nsu.a.maslova1.blackjack;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DealerTest {
    void setUp() {
        Dealer.PlayerCards.clear();
        Dealer.DealerCards.clear();
        Deck.deckCreate();
    }

    @Test
    void testDistributionCard() {
        setUp();
        int DeckSize = Deck.deckSize();

        Dealer.distributionCard();

        assertEquals(2, Dealer.PlayerCards.size());
        assertEquals(2, Dealer.DealerCards.size());

        assertEquals(DeckSize - 4, Deck.deckSize());

        assertNotEquals(Dealer.DealerCards.get(0), Dealer.DealerCards.get(1));
        assertNotEquals(Dealer.PlayerCards.get(0), Dealer.PlayerCards.get(1));
    }

    @Test
    void testDealerPlay() {
        setUp();
        Dealer.distributionCard();

        Dealer.dealerPlay();

        int dealerPoints = Dealer.calculatePoints(Dealer.DealerCards);
        assertTrue(dealerPoints >= 17);
    }

    @Test
    void testCalculatePoints() {
        // Test 1: Обычные карты
        List<Card> cards1 = List.of(
                new Card(Card.Suits.CLABS, Card.Rank.TEN),
                new Card(Card.Suits.DIAMONDS, Card.Rank.SEVEN)
        );
        assertEquals(17, Dealer.calculatePoints(cards1));

        // Test 2: Туз с перебором (должен считать как 1)
        List<Card> cards2 = List.of(
                new Card(Card.Suits.SPADES, Card.Rank.TEN),
                new Card(Card.Suits.DIAMONDS, Card.Rank.FIVE),
                new Card(Card.Suits.HERTS, Card.Rank.ACE)
        );
        assertEquals(16, Dealer.calculatePoints(cards2)); // 1 + 10 + 5 = 16

        // Test 3: Туз без перебора (должен считать как 11)
        List<Card> cards3 = List.of(
                new Card(Card.Suits.HERTS, Card.Rank.ACE),
                new Card(Card.Suits.SPADES, Card.Rank.SEVEN)
        );
        assertEquals(18, Dealer.calculatePoints(cards3)); // 11 + 7 = 18

        // Test 4: Картинки
        List<Card> cards4 = List.of(
                new Card(Card.Suits.CLABS, Card.Rank.KING),
                new Card(Card.Suits.DIAMONDS, Card.Rank.QUEEN)
        );
        assertEquals(20, Dealer.calculatePoints(cards4)); // 10 + 10 = 20
    }
}