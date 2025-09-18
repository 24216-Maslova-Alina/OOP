package ru.nsu.a.maslova1.Task_1_1_2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Класс для создание колоды и работы с ней.
 */
public class Deck {

    private static List<Card> cards;

    /**
     * Создание колоды.
     */
    public static void deckCreate(){
        cards = new ArrayList<>();
        for (Card.Suits suits: Card.Suits.values()) {
            for (Card.Rank rank: Card.Rank.values()) {
                cards.add(new Card(suits, rank));
            }
        }
        Collections.shuffle(cards);
    }

    /**
     * Взятие арты из начала колоды.
     * @return верхняя карта из колоды
     */
    public static Card takeCard(){
        return cards.removeFirst();
    }

    /**
     * Подсчёт размера колоды.
     * @return размер колоды
     */
    public static int deckSize(){
        return cards.size();
    }

    /**
     * Проверка пустая колода или нет
     * @return true если да, false если нет
     */
    public static boolean isEmpty(){
        return cards.isEmpty();
    }
}
