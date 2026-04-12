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
    private Player player;
    private Deck deck;
    private ConsoleOutput output;

    @BeforeEach
    void setUp() {
        output = new ConsoleOutput();
        deck = new Deck(output);
        dealer = new Dealer(output, deck);
        player = new Player(output, deck);

        // Очищаем карты перед каждым тестом
        player.clearCards();
        dealer.clearCards();
    }

    @Test
    void testDistributionCard() {
        final int deckSize = deck.deckSize();
        dealer.distributionCard(player);

        assertEquals(2, player.getPlayerCards().size());
        assertEquals(2, dealer.getDealerCards().size());
        assertEquals(deckSize - 4, deck.deckSize());

        assertNotEquals(dealer.getDealerCards().get(0), dealer.getDealerCards().get(1));
        assertNotEquals(player.getPlayerCards().get(0), player.getPlayerCards().get(1));
    }

    @Test
    void testDealerPlayTakesCardsWhenBelow17() {
        // Создаем ситуацию, когда у дилера мало очков
        dealer.clearCards();
        dealer.addCardToDealer(new Card(Suit.CLUBS, Rank.TWO));
        dealer.addCardToDealer(new Card(Suit.DIAMONDS, Rank.THREE));

        int initialSize = dealer.getDealerCards().size();
        dealer.dealerPlay(player);

        assertTrue(dealer.getDealerCards().size() > initialSize);
        int dealerPoints = dealer.calculatePoints();
        assertTrue(dealerPoints >= 17);
    }

    @Test
    void testDealerPlayStandsAt17() {
        // Создаем ситуацию, когда у дилера ровно 17 очков
        dealer.clearCards();
        dealer.addCardToDealer(new Card(Suit.CLUBS, Rank.TEN));
        dealer.addCardToDealer(new Card(Suit.DIAMONDS, Rank.SEVEN));

        int initialSize = dealer.getDealerCards().size();
        dealer.dealerPlay(player);

        assertEquals(initialSize, dealer.getDealerCards().size());
        assertEquals(17, dealer.calculatePoints());
    }

    @Test
    void testDealerPlayStandsAbove17() {
        // Создаем ситуацию, когда у дилера больше 17 очков
        dealer.clearCards();
        dealer.addCardToDealer(new Card(Suit.CLUBS, Rank.TEN));
        dealer.addCardToDealer(new Card(Suit.DIAMONDS, Rank.EIGHT));

        int initialSize = dealer.getDealerCards().size();
        dealer.dealerPlay(player);

        assertEquals(initialSize, dealer.getDealerCards().size());
        assertEquals(18, dealer.calculatePoints());
    }

    @Test
    void testCalculatePointsWithRegularCards() {
        dealer.clearCards();
        dealer.addCardToDealer(new Card(Suit.CLUBS, Rank.TEN));
        dealer.addCardToDealer(new Card(Suit.DIAMONDS, Rank.SEVEN));
        assertEquals(17, dealer.calculatePoints());
    }

    @Test
    void testCalculatePointsWithAceAs1() {
        dealer.clearCards();
        dealer.addCardToDealer(new Card(Suit.CLUBS, Rank.TEN));
        dealer.addCardToDealer(new Card(Suit.DIAMONDS, Rank.FIVE));
        dealer.addCardToDealer(new Card(Suit.HEARTS, Rank.ACE));
        assertEquals(16, dealer.calculatePoints());
    }

    @Test
    void testCalculatePointsWithAceAs11() {
        dealer.clearCards();
        dealer.addCardToDealer(new Card(Suit.HEARTS, Rank.ACE));
        dealer.addCardToDealer(new Card(Suit.SPADES, Rank.SEVEN));
        assertEquals(18, dealer.calculatePoints());
    }

    @Test
    void testCalculatePointsWithFaceCards() {
        dealer.clearCards();
        dealer.addCardToDealer(new Card(Suit.CLUBS, Rank.KING));
        dealer.addCardToDealer(new Card(Suit.DIAMONDS, Rank.QUEEN));
        assertEquals(20, dealer.calculatePoints());
    }

    @Test
    void testCalculatePointsWithMultipleAces() {
        dealer.clearCards();
        dealer.addCardToDealer(new Card(Suit.HEARTS, Rank.ACE));
        dealer.addCardToDealer(new Card(Suit.SPADES, Rank.ACE));
        dealer.addCardToDealer(new Card(Suit.CLUBS, Rank.NINE));

        // Два туза: один как 11, другой как 1 = 11 + 1 + 9 = 21
        assertEquals(21, dealer.calculatePoints());
    }

    @Test
    void testCalculatePointsEmptyList() {
        dealer.clearCards();
        assertEquals(0, dealer.calculatePoints());
    }

    @Test
    void testCalculatePointsSingleAce() {
        dealer.clearCards();
        dealer.addCardToDealer(new Card(Suit.HEARTS, Rank.ACE));
        assertEquals(11, dealer.calculatePoints());
    }

    @Test
    void testDistributionCardResetsPreviousGame() {
        // Первая раздача
        dealer.distributionCard(player);
        List<Card> firstPlayerCards = new ArrayList<>(player.getPlayerCards());
        List<Card> firstDealerCards = new ArrayList<>(dealer.getDealerCards());

        // Вторая раздача
        dealer.distributionCard(player);

        // Проверяем, что карты новые (не те же самые объекты)
        assertNotEquals(firstPlayerCards, player.getPlayerCards());
        assertNotEquals(firstDealerCards, dealer.getDealerCards());
    }

    @Test
    void testDealerPlayWithBlackjack() {
        // Создаем ситуацию блэкджека у дилера
        dealer.clearCards();
        dealer.addCardToDealer(new Card(Suit.HEARTS, Rank.ACE));
        dealer.addCardToDealer(new Card(Suit.CLUBS, Rank.TEN));

        int initialSize = dealer.getDealerCards().size();
        dealer.dealerPlay(player);

        // Дилер не должен брать карты при блэкджеке
        assertEquals(initialSize, dealer.getDealerCards().size());
        assertEquals(21, dealer.calculatePoints());
    }

    @Test
    void testDealerPlayWithBust() {
        // Создаем ситуацию, когда дилер может перебрать
        dealer.clearCards();
        dealer.addCardToDealer(new Card(Suit.CLUBS, Rank.TEN));
        dealer.addCardToDealer(new Card(Suit.DIAMONDS, Rank.SIX)); // 16 очков

        dealer.dealerPlay(player);

        int dealerPoints = dealer.calculatePoints();
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