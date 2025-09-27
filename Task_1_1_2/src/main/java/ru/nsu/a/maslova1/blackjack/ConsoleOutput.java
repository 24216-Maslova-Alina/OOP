package ru.nsu.a.maslova1.blackjack;

import java.util.List;

/**
 * Класс для управления всеми выводами в консоль.
 */
public class ConsoleOutput {

    /**
     * Выводит приветственное сообщение в начале игры.
     */
    public void showWelcomeMessage() {
        System.out.println("Добро пожаловать в Блэкджек!");
        System.out.println("Игра продолжается до трёх побед.");
    }

    /**
     * Выводит сообщение о создании колоды.
     */
    public void showDeckCreated() {
        System.out.println("Колода создана и перемешана.\n");
    }

    /**
     * Выводит заголовок раунда.
     *
     * @param roundNumber номер текущего раунда
     */
    public void showRoundHeader(int roundNumber) {
        System.out.printf("Раунд %d\n", roundNumber);
    }

    /**
     * Выводит информацию о раздаче карт.
     *
     * @param playerCards карты игрока
     * @param dealerCards карты дилера
     */
    public void showDistribution(List<Card> playerCards, List<Card> dealerCards, Dealer dealer) {
        System.out.print("\tВаши карты: [");
        for (int i = 0; i < playerCards.size(); i++) {
            System.out.print(playerCards.get(i));
            if (i < playerCards.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.printf("] => %d\n", dealer.calculatePoints(playerCards));

        System.out.print("\tКарты дилера: [");
        if (dealerCards.size() == 2 && BlackjackGame.flagCloseCard == 0) {
            System.out.println(dealerCards.get(0) + ", <закрытая карта>]");
        } else {
            for (int i = 0; i < dealerCards.size(); i++) {
                System.out.print(dealerCards.get(i));
                if (i < dealerCards.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.printf("] => %d\n", dealer.calculatePoints(dealerCards));
        }
    }

    /**
     * Выводит сообщение о ходе игрока.
     */
    public void showPlayerTurn() {
        System.out.print("Ваш ход\n");
        System.out.print("-------\n");
    }

    /**
     * Выводит сообщение о ходе дилера.
     */
    public void showDealerTurn() {
        System.out.println("Ход дилера");
        System.out.println("-------");
    }

    /**
     * Выводит варианты действий для игрока.
     */
    public void showPlayerOptions() {
        System.out.print("Введите 1, чтобы взять карту, и 0, чтобы остановиться...\n");
    }

    /**
     * Выводит сообщение о неверном вводе.
     */
    public void showInvalidInput() {
        System.out.print("Неверный ввод. Введите 1 или 0.\n");
    }

    /**
     * Выводит сообщение о том, что игрок остановился.
     */
    public void showPlayerStopped() {
        System.out.print("Вы остановились.\n");
    }

    /**
     * Выводит открытие закрытой карты дилера.
     *
     * @param hiddenCard закрытая карта
     */
    public void showDealerRevealCard(Card hiddenCard, Dealer dealer) {
        System.out.println("Дилер открывает закрытую карту: " + hiddenCard);
        BlackjackGame.flagCloseCard = 1;
        showDistribution(Dealer.PlayerCards, Dealer.DealerCards, dealer);
    }

    /**
     * Выводит сообщение о переборе игрока.
     */
    public void showPlayerBust() {
        System.out.print("Перебор! Вы проиграли раунд. ");
        BlackjackGame.dealerPoint += 1;
        System.out.printf("Счет: %d:%d\n\n", BlackjackGame.dealerPoint, BlackjackGame.playerPoint);
    }

    /**
     * Выводит сообщение о блэкджеке игрока.
     */
    public void showPlayerBlackjack() {
        System.out.print("Блэкджек! У вас ровно 21 очко! ");
        BlackjackGame.playerPoint++;
        System.out.printf("Счет: %d:%d\n\n", BlackjackGame.dealerPoint, BlackjackGame.playerPoint);
    }

    /**
     * Выводит результаты раунда.
     *
     * @param pointsP очки игрока в раунде
     * @param pointsD очки дилера в раунде
     */
    public void showRoundResult(int pointsP, int pointsD) {
        if (pointsD > 21) {
            BlackjackGame.playerPoint++;
            System.out.printf("У дилера перебор! Вы выиграли раунд! Счет: %d:%d\n\n",
                    BlackjackGame.dealerPoint, BlackjackGame.playerPoint);
        } else if (pointsD > pointsP) {
            BlackjackGame.dealerPoint++;
            System.out.printf("Дилер выиграл раунд! Счет: %d:%d\n\n",
                    BlackjackGame.dealerPoint, BlackjackGame.playerPoint);
        } else if (pointsP > pointsD) {
            BlackjackGame.playerPoint++;
            System.out.printf("Вы выиграли раунд! Счет: %d:%d\n\n",
                    BlackjackGame.dealerPoint, BlackjackGame.playerPoint);
        } else {
            System.out.println("Ничья в раунде!\n");
        }
    }

    /**
     * Выводит сообщение о создании новой колоды.
     */
    public void showNewDeckCreated() {
        System.out.println("В колоде закончились карты. Создана новая колода.");
    }

    /**
     * Выводит финальный результат игры.
     *
     * @param dealerPoints очки дилера
     * @param playerPoints очки игрока
     */
    public void showFinalResult(int dealerPoints, int playerPoints) {
        if (playerPoints == 3) {
            System.out.printf("Вы одержали победу со счетом %d:%d!\n\n",
                    dealerPoints, playerPoints);
        } else {
            System.out.printf("Вы потерпели поражение со счетом %d:%d\n",
                    dealerPoints, playerPoints);
        }
    }
}