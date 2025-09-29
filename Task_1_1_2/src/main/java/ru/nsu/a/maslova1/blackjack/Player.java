package ru.nsu.a.maslova1.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Класс, управляющий ходом игрока в игре.
 * Обрабатывает взаимодействие с пользователем.
 */
public class Player {
    private final ConsoleOutput output;
    private final Deck deck;
    private List<Card> playerCards = new ArrayList<>();

    /**
     * Конструктор для Player.
     *
     * @param output вывод информации об игре
     */
    public Player(ConsoleOutput output) {
        this.output = output;
        this.deck = new Deck(output);
    }

    /**
     * Управляет ходом игрока в раунде Блэкджека.
     * Предлагает игроку выбор: взять карту или остановиться.
     * Обрабатывает ввод пользователя, добавляет карты в руку игрока,
     * проверяет условия перебора и блэкджека.
     */
    public void playerMove(Dealer dealer) {
        Scanner in = new Scanner(System.in);

        while (true) {
            output.showPlayerOptions();
            String num = in.next();

            if (num.equals("1")) {
                Card newCard = deck.takeCard();
                playerCards.add(newCard);

                System.out.println("Вы открыли карту: \n" + newCard);
                output.showDistribution(playerCards, dealer.getDealerCards(), dealer, this);

                int points = calculatePoints();

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

    /**
     * Добавляет карту игроку при раздаче.
     */
    public void addCard(Card card) {
        playerCards.add(card);
    }

    /**
     * Очищает карты игрока для нового раунда.
     */
    public void clearCards() {
        playerCards.clear();
    }

    /**
     * Подсчёт количества очков игрока.
     */
    public int calculatePoints() {
        int count = 0;
        for (Card card : playerCards) {
            if (card.getRank() == Rank.ACE && (count + card.getValue()) >= 21) {
                count += 1;
            } else {
                count += card.getValue();
            }
        }
        return count;
    }

    /**
     * Возвращает копию карт игрока.
     */
    public List<Card> getPlayerCards() {
        return new ArrayList<>(playerCards);
    }
}