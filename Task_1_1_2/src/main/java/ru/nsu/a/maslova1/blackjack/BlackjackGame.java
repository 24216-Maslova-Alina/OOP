package ru.nsu.a.maslova1.blackjack;

/**
 * Основной класс игры.
 * Управляет игровым процессом, отслеживает счет и определяет побеителей.
 * Игра продолжается до трех побед.
 */
public class BlackjackGame {
    static int roundCount = 1;
    public static int dealerPoint = 0;
    public static int playerPoint = 0;
    public static int flagCloseCard;

    private final Dealer dealer;
    private final ConsoleOutput output;
    private final Player player;

    /**
     * Конструктор для Blackjack.
     *
     * @param output вывод информации об игре
     */
    public BlackjackGame(ConsoleOutput output) {
        this.output = output;

        dealer = new Dealer(output);
        player = new Player(output);
    }

    /**
     * Начало игры и создание колоды.
     */
    public void start() {
        output.showWelcomeMessage();

        Deck.deckCreate();

        output.showDeckCreated();

        while (dealerPoint < 3 && playerPoint < 3) {
            startGame(roundCount++);
        }
        endGame();
    }

    /**
     * Начало нового раунда игры.
     * Раздает карты, проверяет блэкджек на раздаче.
     *
     * @param count номер раунда
     */
    public void startGame(int count) {
        flagCloseCard = 0;
        output.showRoundHeader(count);
        dealer.distributionCard();

        if (dealer.calculatePoints(Dealer.PlayerCards) == 21) {
            output.showPlayerBlackjack();
            return;
        }

        output.showPlayerTurn();
        player.playerMove();

        if (dealer.calculatePoints(Dealer.PlayerCards) > 21
                || dealer.calculatePoints(Dealer.PlayerCards) == 21) {
            return; // Раунд уже завершен в playerMove()
        }

        if (dealer.calculatePoints(Dealer.PlayerCards) <= 21) {
            dealer.dealerPlay();
            determineRoundWinner();
        }

    }

    /**
     * Определяет победителя раунда на основе очков игрока и дилера.
     * Выводит результаты раунда.
     */
    public void determineRoundWinner() {
        int pointsP = dealer.calculatePoints(Dealer.PlayerCards);
        int pointsD = dealer.calculatePoints(Dealer.DealerCards);

        if (pointsP > 21) {
            // Уже обработано в Player
            return;
        }

        output.showRoundResult(pointsP, pointsD);
    }

    /**
     * Определяет конечный результат игры.
     * Выводит финальный счёт и завершает работу программы.
     */
    private void endGame() {
        output.showFinalResult(dealerPoint, playerPoint);
        System.exit(0);
    }
}