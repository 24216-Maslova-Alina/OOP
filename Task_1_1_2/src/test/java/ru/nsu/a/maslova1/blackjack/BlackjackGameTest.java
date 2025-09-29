package ru.nsu.a.maslova1.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BlackjackGameTest {
    private BlackjackGame game;
    private ConsoleOutput output;

    @BeforeEach
    void setUp() {
        output = new ConsoleOutput();
        game = new BlackjackGame(output);

        // Очищаем состояние перед каждым тестом
        BlackjackGame.dealerPoint = 0;
        BlackjackGame.playerPoint = 0;
    }

    @Test
    void testDealerWins() {
        // Дилер выигрывает: 20 > 18
        game.getPlayer().clearCards(); // Очищаем карты
        game.getDealer().clearCards(); // Очищаем карты

        game.getPlayer().addCard(new Card(Suit.CLUBS, Rank.QUEEN));
        game.getPlayer().addCard(new Card(Suit.DIAMONDS, Rank.EIGHT));

        game.getDealer().addCardToDealer(new Card(Suit.HEARTS, Rank.KING));
        game.getDealer().addCardToDealer(new Card(Suit.HEARTS, Rank.JACK));

        game.determineRoundWinner();

        assertEquals(1, BlackjackGame.dealerPoint);
        assertEquals(0, BlackjackGame.playerPoint);
    }

    @Test
    void testPlayerWins() {
        // Игрок выигрывает: 18 > 17
        game.getPlayer().clearCards();
        game.getDealer().clearCards();

        game.getPlayer().addCard(new Card(Suit.CLUBS, Rank.KING));
        game.getPlayer().addCard(new Card(Suit.DIAMONDS, Rank.EIGHT));

        game.getDealer().addCardToDealer(new Card(Suit.HEARTS, Rank.TEN));
        game.getDealer().addCardToDealer(new Card(Suit.HEARTS, Rank.SEVEN));

        game.determineRoundWinner();

        assertEquals(0, BlackjackGame.dealerPoint);
        assertEquals(1, BlackjackGame.playerPoint);
    }

    @Test
    void testDealerBust() {
        // Перебор у дилера
        game.getPlayer().clearCards();
        game.getDealer().clearCards();

        game.getPlayer().addCard(new Card(Suit.CLUBS, Rank.KING));
        game.getPlayer().addCard(new Card(Suit.DIAMONDS, Rank.SEVEN));

        game.getDealer().addCardToDealer(new Card(Suit.HEARTS, Rank.KING));
        game.getDealer().addCardToDealer(new Card(Suit.HEARTS, Rank.SEVEN));
        game.getDealer().addCardToDealer(new Card(Suit.SPADES, Rank.FIVE));

        game.determineRoundWinner();

        assertEquals(0, BlackjackGame.dealerPoint);
        assertEquals(1, BlackjackGame.playerPoint);
    }

    @Test
    void testTie() {
        // Ничья: 17 = 17
        game.getPlayer().clearCards();
        game.getDealer().clearCards();

        game.getPlayer().addCard(new Card(Suit.CLUBS, Rank.KING));
        game.getPlayer().addCard(new Card(Suit.DIAMONDS, Rank.SEVEN));

        game.getDealer().addCardToDealer(new Card(Suit.HEARTS, Rank.KING));
        game.getDealer().addCardToDealer(new Card(Suit.HEARTS, Rank.SEVEN));

        game.determineRoundWinner();

        assertEquals(0, BlackjackGame.dealerPoint);
        assertEquals(0, BlackjackGame.playerPoint);
    }

    @Test
    void testPlayerBlackjackOnDeal() {
        // Блэкджек игрока при раздаче
        game.getPlayer().clearCards();

        game.getPlayer().addCard(new Card(Suit.CLUBS, Rank.ACE));
        game.getPlayer().addCard(new Card(Suit.DIAMONDS, Rank.KING));

        int points = game.getPlayer().calculatePoints();
        assertEquals(21, points);
    }

    @Test
    void testPlayerBustInPlayerMove() {
        game.getPlayer().clearCards();
        game.getDealer().clearCards();

        game.getPlayer().addCard(new Card(Suit.CLUBS, Rank.KING));
        game.getPlayer().addCard(new Card(Suit.DIAMONDS, Rank.QUEEN));
        game.getPlayer().addCard(new Card(Suit.HEARTS, Rank.THREE));

        game.determineRoundWinner();

        // Проверяем, что метод корректно обрабатывает перебор
        assertEquals(0, BlackjackGame.dealerPoint);
        assertEquals(0, BlackjackGame.playerPoint);
    }
}