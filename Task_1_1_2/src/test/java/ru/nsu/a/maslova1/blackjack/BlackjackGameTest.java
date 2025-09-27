package ru.nsu.a.maslova1.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BlackjackGameTest {
    private BlackjackGame game;
    private ConsoleOutput output;

    void setUp() {
        ConsoleOutput output = new ConsoleOutput();
        game = new BlackjackGame(output);
        // Очищаем состояние перед каждым тестом
        Dealer.PlayerCards.clear();
        Dealer.DealerCards.clear();
        BlackjackGame.dealerPoint = 0;
        BlackjackGame.playerPoint = 0;
    }

    @Test
    void testDealerWins() {
        setUp();
        // Дилер выигрывает: 20 > 18
        Dealer.PlayerCards.add(new Card(Suit.CLUBS, Rank.QUEEN));
        Dealer.PlayerCards.add(new Card(Suit.DIAMONDS, Rank.EIGHT));

        Dealer.DealerCards.add(new Card(Suit.HERTS, Rank.KING));
        Dealer.DealerCards.add(new Card(Suit.HERTS, Rank.JACK));

        game.determineRoundWinner();

        assertEquals(1, BlackjackGame.dealerPoint);
        assertEquals(0, BlackjackGame.playerPoint);
    }

    @Test
    void testPlayerWins() {
        setUp();
        // Игрок выигрывает: 18 > 17
        Dealer.PlayerCards.add(new Card(Suit.CLUBS, Rank.KING));
        Dealer.PlayerCards.add(new Card(Suit.DIAMONDS, Rank.EIGHT));

        Dealer.DealerCards.add(new Card(Suit.HERTS, Rank.TEN));
        Dealer.DealerCards.add(new Card(Suit.HERTS, Rank.SEVEN));

        game.determineRoundWinner();

        assertEquals(0, BlackjackGame.dealerPoint);
        assertEquals(1, BlackjackGame.playerPoint);
    }

    @Test
    void testDealerBust() {
        setUp();
        // Перебор у дилера
        Dealer.PlayerCards.add(new Card(Suit.CLUBS, Rank.KING));
        Dealer.PlayerCards.add(new Card(Suit.DIAMONDS, Rank.SEVEN));

        Dealer.DealerCards.add(new Card(Suit.HERTS, Rank.KING));
        Dealer.DealerCards.add(new Card(Suit.HERTS, Rank.SEVEN));
        Dealer.DealerCards.add(new Card(Suit.SPADES, Rank.FIVE));

        game.determineRoundWinner();

        assertEquals(0, BlackjackGame.dealerPoint);
        assertEquals(1, BlackjackGame.playerPoint);
    }

    @Test
    void testTie() {
        setUp();
        // Ничья: 17 = 17
        Dealer.PlayerCards.add(new Card(Suit.CLUBS, Rank.KING));
        Dealer.PlayerCards.add(new Card(Suit.DIAMONDS, Rank.SEVEN));

        Dealer.DealerCards.add(new Card(Suit.HERTS, Rank.KING));
        Dealer.DealerCards.add(new Card(Suit.HERTS, Rank.SEVEN));

        game.determineRoundWinner();

        assertEquals(0, BlackjackGame.dealerPoint);
        assertEquals(0, BlackjackGame.playerPoint);
    }

    @Test
    void testPlayerBlackjackOnDeal() {
        setUp();
        // Блэкджек игрока при раздаче
        Dealer.PlayerCards.add(new Card(Suit.CLUBS, Rank.ACE));
        Dealer.PlayerCards.add(new Card(Suit.DIAMONDS, Rank.KING));

        int points = new Dealer(output).calculatePoints(Dealer.PlayerCards);
        assertEquals(21, points);
    }

    @Test
    void testPlayerBustInPlayerMove() {
        setUp();
        Dealer.PlayerCards.add(new Card(Suit.CLUBS, Rank.KING));
        Dealer.PlayerCards.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        Dealer.PlayerCards.add(new Card(Suit.HERTS, Rank.THREE));

        game.determineRoundWinner();

        // Проверяем, что метод корректно обрабатывает перебор
        assertEquals(0, BlackjackGame.dealerPoint);
        assertEquals(0, BlackjackGame.playerPoint);
    }
}