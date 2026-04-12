package ru.nsu.a.maslova1.blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Класс для создания колоды и работы с ней.
 */
public class Deck {

    private List<Card> cards;
    private final ConsoleOutput output;

    public Deck(ConsoleOutput output) {
        this.output = output;
        this.deckCreate();
    }

    /**
     * Создание колоды.
     */
    private void deckCreate() {
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
        if (this.isEmpty()) {
            output.showNewDeckCreated();
            this.deckCreate();
        }
        return cards.remove(0);
    }

    /**
     * Подсчёт размера колоды.
     *
     * @return размер колоды
     */
    public int deckSize() {
        return cards.size();
    }

    /**
     * Проверка пустая колода или нет.
     *
     * @return true если да, false если нет
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
