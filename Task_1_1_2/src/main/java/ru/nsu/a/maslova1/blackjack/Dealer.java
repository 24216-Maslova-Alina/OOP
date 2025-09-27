package ru.nsu.a.maslova1.blackjack;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, реализующий основную логику действий дилера.
 */
public class Dealer {
    public static List<Card> PlayerCards = new ArrayList<>();
    public static List<Card> DealerCards = new ArrayList<>();

    private final ConsoleOutput output;
    private final Deck deck;

    public Dealer(ConsoleOutput output){
        this.output = output;

        deck = new Deck(output);
    }

    /**
     * Создание колоды и раздача карт.
     */
    public void distributionCard() {
        PlayerCards = new ArrayList<>();
        DealerCards = new ArrayList<>();

        PlayerCards.add(deck.takeCard());
        DealerCards.add(deck.takeCard());
        PlayerCards.add(deck.takeCard());
        DealerCards.add(deck.takeCard());

        output.showDistribution(PlayerCards, DealerCards,this);
    }

    /**
     * Действия дилера при разных ситуациях.
     */
    public void dealerPlay() {
        output.showDealerTurn();
        if (DealerCards.size() == 2) {
            output.showDealerRevealCard(DealerCards.get(1), this);
        }
        while (calculatePoints(DealerCards) < 17) {
            Card newCard = deck.takeCard();


            DealerCards.add(newCard);
            System.out.println("Дилер берет новую карту карту - " + newCard);

            output.showDistribution(PlayerCards, DealerCards, this);
        }
    }

    /**
     * Подсчёт количества очков.
     *
     * @param array массив карт игрока или дилера
     * @return количество очков
     */
    public int calculatePoints(List<Card> array) {
        int count = 0;
        for (Card card : array) {
            if (card.getRank() == Rank.ACE && (count + card.getValue()) >= 21) {
                count += 1;
            } else {
                count += card.getValue();
            }
        }
        return count;
    }

}
