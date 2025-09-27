package ru.nsu.a.maslova1.blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Класс для создания колоды и работы с ней.
 */
public class Deck {

    private static List<Card> cards;
    private final ConsoleOutput output;

    public Deck(ConsoleOutput output) {
        this.output = output;
    }
    /**
     * Создание колоды.
     */
    public static void deckCreate() {
        cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
        Collections.shuffle(cards);
    }

    /**
     * Взятие карты из начала колоды.
     *
     * @return верхняя карта из колоды
     */
    public Card takeCard() {
        if (Deck.isEmpty()) {
            output.showNewDeckCreated();
            Deck.deckCreate();
        }
        return cards.remove(0);
    }

    /**
     * Подсчёт размера колоды.
     *
     * @return размер колоды
     */
    public static int deckSize() {
        return cards.size();
    }

    /**
     * Проверка пустая колода или нет.
     *
     * @return true если да, false если нет
     */
    public static boolean isEmpty() {
        return cards.isEmpty();
    }
}
