package ru.nsu.a.maslova1.blackjack;

import java.util.Scanner;

/**
 * Класс, управляющий ходом игрока в игре.
 * Обрабатывает взаимодействие с пользователем.
 */
public class Player {
    private final ConsoleOutput output;
    private final Deck deck;
    private final Dealer dealer;

    public Player(ConsoleOutput output){
        this.output = output;

        deck = new Deck(output);
        dealer = new Dealer(output);
    }
    /**
     * Управляет ходом игрока в раунде Блэкджека.
     * Предлагает игроку выбор: взять карту или остановиться.
     * Обрабатывает ввод пользователя, добавляет карты в руку игрока,
     * проверяет условия перебора и блэкджека.
     */
    public void playerMove() {
        Scanner in = new Scanner(System.in);

        while (true) {
            output.showPlayerOptions();
            String num = in.next();

            if (num.equals("1")) {
                Card newCard = deck.takeCard();

                Dealer.PlayerCards.add(newCard);

                System.out.println("Вы открыли карту: \n" + newCard);
                output.showDistribution(Dealer.PlayerCards, Dealer.DealerCards, dealer);
                int points = dealer.calculatePoints(Dealer.PlayerCards);

                if (points > 21) {
                    output.showPlayerBust();
                    return;
                }

                if (points == 21) {
                    output.showPlayerBlackjack();
                    return;
                }
            } else if (num.equals("0")) {
                output.showPlayerStopped();
                break;
            } else {
                output.showInvalidInput();
            }
        }
    }
}