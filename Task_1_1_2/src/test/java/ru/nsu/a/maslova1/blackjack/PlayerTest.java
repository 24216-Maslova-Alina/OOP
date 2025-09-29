package ru.nsu.a.maslova1.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {
    private Player player;
    private Dealer dealer;
    private Deck deck;
    private ConsoleOutput output;

    @BeforeEach
    void setUp() {
        output = new ConsoleOutput();
        deck = new Deck(output);
        player = new Player(output, deck);
        dealer = new Dealer(output, deck);
        BlackjackGame.dealerPoint = 0;
        BlackjackGame.playerPoint = 0;
    }

    @Test
    void testPlayerMoveStopImmediately() {
        String input = "0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        int playerCards = player.getPlayerCards().size();
        int playerPoints = BlackjackGame.playerPoint;

        player.playerMove(dealer);

        int dealerPoint = BlackjackGame.dealerPoint;

        assertEquals(playerCards, player.getPlayerCards().size());
        assertEquals(playerPoints, BlackjackGame.playerPoint);
        assertEquals(dealerPoint, BlackjackGame.dealerPoint);
    }

    @Test
    void testPlayerMoveTakeOneCard() {
        String input = "1\n0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        int playerCards = player.getPlayerCards().size();

        player.playerMove(dealer);

        assertEquals(playerCards + 1, player.getPlayerCards().size());
        assertTrue(player.calculatePoints() > 0);
    }

    @Test
    void testPlayerMoveBlackjack() {
        // Игрок получает блэкджек
        String input = "0"; // Останавливаемся сразу
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Создаем руку с блэкджеком
        player.clearCards();
        player.addCard(new Card(Suit.HEARTS, Rank.ACE));
        player.addCard(new Card(Suit.SPADES, Rank.KING));

        player.playerMove(dealer);

        int playerPoints = player.calculatePoints();
        assertEquals(21, playerPoints);
    }

    @Test
    void testPlayerMoveInvalidInputThenValid() {
        // Неправильный ввод, затем правильный
        String input = "5\n2\nabc\n0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        int initialPlayerCards = player.getPlayerCards().size();

        player.playerMove(dealer);

        // Проверяем, что количество карт не изменилось
        assertEquals(initialPlayerCards, player.getPlayerCards().size());
    }

    // Дополнительные тесты

    @Test
    void testPlayerMoveBust() {
        String input = "1\n1\n1\n0"; // Берем несколько карт, чтобы перебрать
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Создаем ситуацию, где игрок почти перебрал
        player.clearCards();
        player.addCard(new Card(Suit.HEARTS, Rank.KING));
        player.addCard(new Card(Suit.DIAMONDS, Rank.QUEEN));

        player.playerMove(dealer);

        // После перебора игра должна завершиться
        assertTrue(player.calculatePoints() > 21);
    }

    @Test
    void testPlayerMoveMultipleCards() {
        String input = "1\n1\n1\n0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        int initialCards = player.getPlayerCards().size();

        player.playerMove(dealer);

        // Должны добавиться 3 карты
        assertEquals(initialCards + 3, player.getPlayerCards().size());
    }

    @Test
    void testCalculatePointsEmptyHand() {
        player.clearCards();
        assertEquals(0, player.calculatePoints());
    }

    @Test
    void testCalculatePointsWithRegularCards() {
        player.clearCards();
        player.addCard(new Card(Suit.CLUBS, Rank.TEN));
        player.addCard(new Card(Suit.DIAMONDS, Rank.SEVEN));
        assertEquals(17, player.calculatePoints());
    }

    @Test
    void testCalculatePointsWithAceAs1() {
        player.clearCards();
        player.addCard(new Card(Suit.CLUBS, Rank.TEN));
        player.addCard(new Card(Suit.DIAMONDS, Rank.FIVE));
        player.addCard(new Card(Suit.HEARTS, Rank.ACE));
        assertEquals(16, player.calculatePoints());
    }

    @Test
    void testCalculatePointsWithAceAs11() {
        player.clearCards();
        player.addCard(new Card(Suit.HEARTS, Rank.ACE));
        player.addCard(new Card(Suit.SPADES, Rank.SEVEN));
        assertEquals(18, player.calculatePoints());
    }

    @Test
    void testCalculatePointsWithFaceCards() {
        player.clearCards();
        player.addCard(new Card(Suit.CLUBS, Rank.KING));
        player.addCard(new Card(Suit.DIAMONDS, Rank.QUEEN));
        assertEquals(20, player.calculatePoints());
    }

    @Test
    void testCalculatePointsWithMultipleAces() {
        player.clearCards();
        player.addCard(new Card(Suit.HEARTS, Rank.ACE));
        player.addCard(new Card(Suit.SPADES, Rank.ACE));
        player.addCard(new Card(Suit.CLUBS, Rank.NINE));

        // Два туза: один как 11, другой как 1 = 11 + 1 + 9 = 21
        assertEquals(21, player.calculatePoints());
    }

    @Test
    void testCalculatePointsSingleAce() {
        player.clearCards();
        player.addCard(new Card(Suit.HEARTS, Rank.ACE));
        assertEquals(11, player.calculatePoints());
    }

    @Test
    void testAddCard() {
        int initialSize = player.getPlayerCards().size();
        Card card = new Card(Suit.HEARTS, Rank.KING);

        player.addCard(card);

        assertEquals(initialSize + 1, player.getPlayerCards().size());
        assertEquals(card, player.getPlayerCards().get(initialSize));
    }

    @Test
    void testClearCards() {
        player.addCard(new Card(Suit.HEARTS, Rank.KING));
        player.addCard(new Card(Suit.SPADES, Rank.QUEEN));

        assertEquals(2, player.getPlayerCards().size());

        player.clearCards();

        assertEquals(0, player.getPlayerCards().size());
    }

    @Test
    void testPlayerMoveWithBustAfterTakingCard() {
        String input = "1\n0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Создаем ситуацию, где после взятия карты будет перебор
        player.clearCards();
        player.addCard(new Card(Suit.HEARTS, Rank.KING)); // 10
        player.addCard(new Card(Suit.SPADES, Rank.QUEEN)); // 10
        player.addCard(new Card(Suit.DIAMONDS, Rank.THREE)); // 3, всего 23

        player.playerMove(dealer);

        // После перебора игра должна завершиться
        assertTrue(player.calculatePoints() > 21);
    }

    @Test
    void testPlayerMoveMixedValidInvalidInputs() {
        String input = "abc\n1\nxyz\n1\ninvalid\n0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        int initialCards = player.getPlayerCards().size();

        player.playerMove(dealer);

        // Должны добавиться 2 карты (игнорируя неправильные вводы)
        assertEquals(initialCards + 2, player.getPlayerCards().size());
    }
}