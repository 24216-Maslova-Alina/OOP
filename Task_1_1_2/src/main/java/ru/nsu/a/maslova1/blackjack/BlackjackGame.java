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

    /**
     * Метод, запускающий игру.
     *
     * @param args аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        System.out.println("Добро пожаловать в Блэкджек!");
        System.out.println("Игра продолжается до трёх побед.");

        Deck.deckCreate();
        System.out.println("Колода создана и перемешана.\n");

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
    private static void startGame(int count) {
        System.out.printf("Раунд %d\n", count);
        Dealer.distributionCard();

        if (Dealer.calculatePoints(Dealer.PlayerCards) == 21) {
            System.out.print("Блэкджек на раздаче! Вы выиграли раунд! ");
            playerPoint++;
            System.out.printf("Счет: %d:%d.\n", dealerPoint, playerPoint);
            return;
        }

        System.out.print("Ваш ход\n");
        System.out.print("-------\n");
        Player.playerMove();

        if (Dealer.calculatePoints(Dealer.PlayerCards) > 21
                || Dealer.calculatePoints(Dealer.PlayerCards) == 21) {
            return; // Раунд уже завершен в playerMove()
        }

        if (Dealer.calculatePoints(Dealer.PlayerCards) <= 21) {
            Dealer.dealerPlay();
            determineRoundWinner();
        }

    }

    /**
     * Определяет победителя раунда на основе очков игрока и дилера.
     * Выводит результаты раунда.
     */
    public static void determineRoundWinner() {
        int pointsP = Dealer.calculatePoints(Dealer.PlayerCards);
        int pointsD = Dealer.calculatePoints(Dealer.DealerCards);

        if (pointsP > 21) {
            // Уже обработано в Player
            return;
        }

        if (pointsD > 21) {
            playerPoint++;
            System.out.printf("У дилера перебор! Вы выиграли раунд! Счет: %d:%d\n\n",
                    dealerPoint, playerPoint);
        } else if (pointsD > pointsP) {
            dealerPoint++;
            System.out.printf("Дилер выиграл раунд! Счет: %d:%d\n\n",
                    dealerPoint, playerPoint);
        } else if (pointsP > pointsD) {
            playerPoint++;
            System.out.printf("Вы выиграли раунд! Счет: %d:%d\n\n",
                    dealerPoint, playerPoint);
        } else {
            System.out.println("Ничья в раунде!\n");
        }
    }

    /**
     * Определяет конечный результат игры.
     * Выводит финальный счёт и завершает работу программы.
     */
    private static void endGame() {
        if (playerPoint == 3) {
            System.out.printf("Вы одержали победу со счетом %d:%d!\n\n", dealerPoint, playerPoint);
        } else {
            System.out.printf("Вы потерпели поражение со счетом %d:%d\n", dealerPoint, playerPoint);
        }
        System.exit(0);
    }
}