package ru.nsu.a.maslova1.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class DealerTest {
    private Dealer dealer;
    private ConsoleOutput output;

    void setUp() {
        output = new ConsoleOutput(); // или mock
        dealer = new Dealer(output);

        Dealer.PlayerCards.clear();
        Dealer.DealerCards.clear();
        Deck.deckCreate();
    }

    @Test
    void testDistributionCard() {
        setUp();
        final int deckSize = Deck.deckSize();
        dealer.distributionCard();

        assertEquals(2, Dealer.PlayerCards.size());
        assertEquals(2, Dealer.DealerCards.size());

        assertEquals(deckSize - 4, Deck.deckSize());

        assertNotEquals(Dealer.DealerCards.get(0), Dealer.DealerCards.get(1));
        assertNotEquals(Dealer.PlayerCards.get(0), Dealer.PlayerCards.get(1));
    }

    @Test
    void testDealerPlay() {
        setUp();
        dealer.distributionCard();

        dealer.dealerPlay();

        int dealerPoints = dealer.calculatePoints(Dealer.DealerCards);
        assertTrue(dealerPoints >= 17);
    }

    @Test
    void testCalculatePoints() {
        setUp();
        // Test 1: Обычные карты
        List<Card> cards1 = new ArrayList<>();
        cards1.add(new Card(Suit.CLUBS, Rank.TEN));
        cards1.add(new Card(Suit.DIAMONDS, Rank.SEVEN));
        assertEquals(17, dealer.calculatePoints(cards1));

        // Test 2: Туз с перебором (должен считать как 1)
        List<Card> cards2 = new ArrayList<>();
        cards2.add(new Card(Suit.CLUBS, Rank.TEN));
        cards2.add(new Card(Suit.DIAMONDS, Rank.FIVE));
        cards2.add(new Card(Suit.HERTS, Rank.ACE));
        assertEquals(16, dealer.calculatePoints(cards2));

        // Test 3: Туз без перебора (должен считать как 11)
        List<Card> cards3 = new ArrayList<>();
        cards3.add(new Card(Suit.HERTS, Rank.ACE));
        cards3.add(new Card(Suit.SPADES, Rank.SEVEN));
        assertEquals(18, dealer.calculatePoints(cards3));

        // Test 4: Картинки
        List<Card> cards4 = new ArrayList<>();
        cards4.add(new Card(Suit.CLUBS, Rank.KING));
        cards4.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        assertEquals(20, dealer.calculatePoints(cards4));
    }
}