package ru.nsu.a.maslova1.blackjack;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, реализующий основную логику действий дилера.
 */
public class Dealer {
    public static List<Card> PlayerCards = new ArrayList<>();
    public static List<Card> DealerCards = new ArrayList<>();

    /**
     * Создание колоды и раздача карт.
     */
    public static void distributionCard() {
        PlayerCards = new ArrayList<>();
        DealerCards = new ArrayList<>();

        if (Deck.deckSize() < 4) {
            System.out.println("В колоде недостаточно карт. Создана новая колода.");
            Deck.deckCreate();
        }

        PlayerCards.add(Deck.takeCard());
        DealerCards.add(Deck.takeCard());
        PlayerCards.add(Deck.takeCard());
        DealerCards.add(Deck.takeCard());

        System.out.print("Дилер раздал карты\n");
        System.out.print("\tВаши карты: [");
        for (int i = 0; i < PlayerCards.size(); i++) {
            System.out.print(PlayerCards.get(i));
            if (i < PlayerCards.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.printf("] => %d\n", calculatePoints(PlayerCards));

        System.out.print("\tКарты дилера: [");
        if (DealerCards.size() == 2) {
            System.out.println(DealerCards.get(0) + ", <закрытая карта>]");
        } else {
            for (Card cardsD : DealerCards) {
                System.out.print(cardsD);
            }
            System.out.printf("] => %d\n", calculatePoints(DealerCards));
        }

    }

    /**
     * Действия дилера при разных ситуациях.
     */
    public static void dealerPlay() {
        System.out.println("Ход дилера");
        System.out.println("-------");
        if (DealerCards.size() == 2) {
            System.out.println("Дилер открывает закрытую карту: " + DealerCards.get(1));
            System.out.print("\tКарты дилера: [");

            for (Card cardsD : DealerCards) {
                System.out.print(cardsD);
            }
            System.out.printf("] => %d\n", calculatePoints(DealerCards));
        }
        while (calculatePoints(DealerCards) < 17) {
            Card newCard = Deck.takeCard();

            if (Deck.isEmpty()) {
                System.out.println("В колоде закончились карты. Создана новая колода.");
                Deck.deckCreate();
            }

            DealerCards.add(newCard);
            System.out.println("Дилер берет новую карту карту - " + newCard);

            System.out.print("\tКарты дилера: [");
            for (int i = 0; i < DealerCards.size(); i++) {
                System.out.print(DealerCards.get(i));
                if (i < PlayerCards.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.printf("] => %d\n", calculatePoints(DealerCards));
        }
    }

    /**
     * Подсчёт количества очков.
     *
     * @param array массив карт игрока или дилера
     * @return количество очков
     */
    public static int calculatePoints(List<Card> array){
        int count = 0;
        for (Card card : array) {
            if (card.rank() == Card.Rank.ACE && (count + card.getValue()) >= 21) {
                count += 1;
            } else {
                count += card.getValue();
            }
        }
        return count;
    }

}
