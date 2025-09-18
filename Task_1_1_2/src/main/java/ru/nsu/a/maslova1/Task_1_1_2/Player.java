package ru.nsu.a.maslova1.Task_1_1_2;

import java.util.Scanner;

/**
 * Класс, управляющий ходом игрока в игре.
 * Обрабатывает взаимодействие с пользователем.
 */
public class Player {
    /**
     * Управляет ходом игрока в раунде Блэкджека.
     * Предлагает игроку выбор: взять карту или остановиться.
     * Обрабатывает ввод пользователя, добавляет карты в руку игрока,
     * проверяет условия перебора и блэкджека.
     */
    public static void playerMove() {
        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.print("Введите 1, чтобы взять карту, и 0, чтобы остановиться...\n");
            int num = in.nextInt();

            if (num == 1) {
                Card newCard = Deck.takeCard();

                if (Deck.isEmpty()){
                    System.out.println("В колоде закончились карты. Создана новая колода.");
                    Deck.deckCreate();
                }

                Dealer.PlayerCards.add(newCard);

                System.out.println("Вы открыли карту: \n" + newCard);
                System.out.println("\tВаши карты: " + Dealer.PlayerCards + " => " +
                        Dealer.calculatePoints(Dealer.PlayerCards));

                int points = Dealer.calculatePoints(Dealer.PlayerCards);

                if (points > 21) {
                    System.out.print("Перебор! Вы проиграли раунд. ");
                    BlackjackGame.dealerPoint += 1;
                    System.out.printf("Счет: %d:%d\n\n",
                            BlackjackGame.dealerPoint, BlackjackGame.playerPoint);
                    return;
                }

                if (points == 21) {
                    System.out.print("Блэкджек! У вас ровно 21 очко! ");
                    BlackjackGame.playerPoint += 1;
                    System.out.printf("Счет: %d:%d\n\n",
                            BlackjackGame.dealerPoint, BlackjackGame.playerPoint);
                    return;
                }
            }
            else if (num == 0) {
                System.out.print("Вы остановились.\n");
                break;
            }
            else {
                System.out.print("Неверный ввод. Введите 1 или 0.\n");
            }
        }
    }
}