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
        dealer.distributionCard(player);

        if (player.calculatePoints() == 21) {
            output.showPlayerBlackjack();
            return;
        }

        output.showPlayerTurn();
        player.playerMove(dealer);

        if (player.calculatePoints() > 21 || player.calculatePoints() == 21) {
            return; // Раунд уже завершен в playerMove()
        }

        if (player.calculatePoints() <= 21) {
            dealer.dealerPlay(player);
            determineRoundWinner();
        }
    }

    /**
     * Определяет победителя раунда на основе очков игрока и дилера.
     * Выводит результаты раунда.
     */
    public void determineRoundWinner() {
        int pointsP = player.calculatePoints();
        int pointsD = dealer.calculatePoints();

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

    /**
     * Геттер для дилера (для тестов).
     */
    public Dealer getDealer() {
        return dealer;
    }

    /**
     * Геттер для игрока (для тестов).
     */
    public Player getPlayer() {
        return player;
    }
}