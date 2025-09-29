package ru.nsu.a.maslova1.blackjack;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, реализующий основную логику действий дилера.
 */
public class Dealer {
    private List<Card> dealerCards = new ArrayList<>();
    private final ConsoleOutput output;
    private final Deck deck;

    /**
     * Конструктор для Dealer.
     *
     * @param output вывод информации об игре
     */
    public Dealer(ConsoleOutput output) {
        this.output = output;
        this.deck = new Deck(output);
    }

    /**
     * Создание колоды и раздача карт.
     */
    public void distributionCard(Player player) {
        dealerCards.clear();
        player.clearCards();

        player.addCard(deck.takeCard());
        dealerCards.add(deck.takeCard());
        player.addCard(deck.takeCard());
        dealerCards.add(deck.takeCard());

        output.showDistribution(player.getPlayerCards(), dealerCards, this, player);
    }

    /**
     * Действия дилера при разных ситуациях.
     */
    public void dealerPlay(Player player) {
        output.showDealerTurn();
        if (dealerCards.size() == 2) {
            output.showDealerRevealCard(dealerCards.get(1), this, player);
        }
        while (calculatePoints() < 17) {
            Card newCard = deck.takeCard();
            dealerCards.add(newCard);
            System.out.println("Дилер берет новую карту карту - " + newCard);
            output.showDistribution(player.getPlayerCards(), dealerCards, this, player);
        }
    }

    /**
     * Подсчёт количества очков дилера.
     */
    public int calculatePoints() {
        int count = 0;
        for (Card card : dealerCards) {
            if (card.getValue() != 0) { // Пропускаем закрытую карту (если она имеет значение 0)
                if (card.getRank() == Rank.ACE && (count + card.getValue()) >= 21) {
                    count += 1;
                } else {
                    count += card.getValue();
                }
            }
        }
        return count;
    }

    /**
     * Возвращает копию карт дилера.
     */
    public List<Card> getDealerCards() {
        return new ArrayList<>(dealerCards);
    }

    /**
     * Очищает карты дилера для нового раунда.
     */
    public void clearCards() {
        dealerCards.clear();
    }

    /**
     * Добавляет карту дилеру (для тестов).
     */
    public void addCardToDealer(Card card) {
        this.dealerCards.add(card);
    }
}