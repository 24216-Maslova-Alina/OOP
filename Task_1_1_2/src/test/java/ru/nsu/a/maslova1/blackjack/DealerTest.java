package ru.nsu.a.maslova1.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DealerTest {
    private Dealer dealer;
    private ConsoleOutput output;

    @BeforeEach
    void setUp() {
        output = new ConsoleOutput();
        dealer = new Dealer(output);

        // Очищаем статические поля перед каждым тестом
        Dealer.PlayerCards.clear();
        Dealer.DealerCards.clear();
        Deck.deckCreate(); // Пересоздаем колоду
    }

    @Test
    void testDistributionCard() {
        final int deckSize = Deck.deckSize();
        dealer.distributionCard();

        assertEquals(2, Dealer.PlayerCards.size());
        assertEquals(2, Dealer.DealerCards.size());
        assertEquals(deckSize - 4, Deck.deckSize());

        assertNotEquals(Dealer.DealerCards.get(0), Dealer.DealerCards.get(1));
        assertNotEquals(Dealer.PlayerCards.get(0), Dealer.PlayerCards.get(1));
    }

    @Test
    void testDealerPlayTakesCardsWhenBelow17() {
        // Создаем ситуацию, когда у дилера мало очков
        Dealer.DealerCards.clear();
        Dealer.DealerCards.add(new Card(Suit.CLUBS, Rank.TWO));
        Dealer.DealerCards.add(new Card(Suit.DIAMONDS, Rank.THREE));

        int initialSize = Dealer.DealerCards.size();
        dealer.dealerPlay();

        assertTrue(Dealer.DealerCards.size() > initialSize);
        int dealerPoints = dealer.calculatePoints(Dealer.DealerCards);
        assertTrue(dealerPoints >= 17);
    }

    @Test
    void testDealerPlayStandsAt17() {
        // Создаем ситуацию, когда у дилера ровно 17 очков
        Dealer.DealerCards.clear();
        Dealer.DealerCards.add(new Card(Suit.CLUBS, Rank.TEN));
        Dealer.DealerCards.add(new Card(Suit.DIAMONDS, Rank.SEVEN));

        int initialSize = Dealer.DealerCards.size();
        dealer.dealerPlay();

        assertEquals(initialSize, Dealer.DealerCards.size());
        assertEquals(17, dealer.calculatePoints(Dealer.DealerCards));
    }

    @Test
    void testDealerPlayStandsAbove17() {
        // Создаем ситуацию, когда у дилера больше 17 очков
        Dealer.DealerCards.clear();
        Dealer.DealerCards.add(new Card(Suit.CLUBS, Rank.TEN));
        Dealer.DealerCards.add(new Card(Suit.DIAMONDS, Rank.EIGHT));

        int initialSize = Dealer.DealerCards.size();
        dealer.dealerPlay();

        assertEquals(initialSize, Dealer.DealerCards.size());
        assertEquals(18, dealer.calculatePoints(Dealer.DealerCards));
    }

    @Test
    void testCalculatePointsWithRegularCards() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUBS, Rank.TEN));
        cards.add(new Card(Suit.DIAMONDS, Rank.SEVEN));
        assertEquals(17, dealer.calculatePoints(cards));
    }

    @Test
    void testCalculatePointsWithAceAs1() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUBS, Rank.TEN));
        cards.add(new Card(Suit.DIAMONDS, Rank.FIVE));
        cards.add(new Card(Suit.HEARTS, Rank.ACE));
        assertEquals(16, dealer.calculatePoints(cards));
    }

    @Test
    void testCalculatePointsWithAceAs11() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEARTS, Rank.ACE));
        cards.add(new Card(Suit.SPADES, Rank.SEVEN));
        assertEquals(18, dealer.calculatePoints(cards));
    }

    @Test
    void testCalculatePointsWithFaceCards() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUBS, Rank.KING));
        cards.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        assertEquals(20, dealer.calculatePoints(cards));
    }

    @Test
    void testCalculatePointsWithMultipleAces() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEARTS, Rank.ACE));
        cards.add(new Card(Suit.SPADES, Rank.ACE));
        cards.add(new Card(Suit.CLUBS, Rank.NINE));

        // Два туза: один как 11, другой как 1 = 11 + 1 + 9 = 21
        assertEquals(21, dealer.calculatePoints(cards));
    }

    @Test
    void testCalculatePointsEmptyList() {
        List<Card> cards = new ArrayList<>();
        assertEquals(0, dealer.calculatePoints(cards));
    }

    @Test
    void testCalculatePointsSingleAce() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEARTS, Rank.ACE));
        assertEquals(11, dealer.calculatePoints(cards));
    }

    @Test
    void testDistributionCardResetsPreviousGame() {
        // Первая раздача
        dealer.distributionCard();
        List<Card> firstPlayerCards = new ArrayList<>(Dealer.PlayerCards);
        List<Card> firstDealerCards = new ArrayList<>(Dealer.DealerCards);

        // Вторая раздача
        dealer.distributionCard();

        // Проверяем, что карты новые (не те же самые объекты)
        assertNotEquals(firstPlayerCards, Dealer.PlayerCards);
        assertNotEquals(firstDealerCards, Dealer.DealerCards);
    }

    @Test
    void testDealerPlayWithBlackjack() {
        // Создаем ситуацию блэкджека у дилера
        Dealer.DealerCards.clear();
        Dealer.DealerCards.add(new Card(Suit.HEARTS, Rank.ACE));
        Dealer.DealerCards.add(new Card(Suit.CLUBS, Rank.TEN));

        int initialSize = Dealer.DealerCards.size();
        dealer.dealerPlay();

        // Дилер не должен брать карты при блэкджеке
        assertEquals(initialSize, Dealer.DealerCards.size());
        assertEquals(21, dealer.calculatePoints(Dealer.DealerCards));
    }

    @Test
    void testDealerPlayWithBust() {
        // Создаем ситуацию, когда дилер может перебрать
        Dealer.DealerCards.clear();
        Dealer.DealerCards.add(new Card(Suit.CLUBS, Rank.TEN));
        Dealer.DealerCards.add(new Card(Suit.DIAMONDS, Rank.SIX)); // 16 очков

        dealer.dealerPlay();

        int dealerPoints = dealer.calculatePoints(Dealer.DealerCards);
        // Дилер может перебрать, но должен остановиться на >= 17
        assertTrue(dealerPoints >= 17);
    }

    @Test
    void testCardValuesConsistency() {
        // Проверяем, что значения карт соответствуют ожиданиям
        assertEquals(2, new Card(Suit.CLUBS, Rank.TWO).getValue());
        assertEquals(10, new Card(Suit.CLUBS, Rank.TEN).getValue());
        assertEquals(10, new Card(Suit.CLUBS, Rank.JACK).getValue());
        assertEquals(10, new Card(Suit.CLUBS, Rank.QUEEN).getValue());
        assertEquals(10, new Card(Suit.CLUBS, Rank.KING).getValue());
        assertEquals(11, new Card(Suit.CLUBS, Rank.ACE).getValue());
    }
}