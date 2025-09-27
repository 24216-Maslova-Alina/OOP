package ru.nsu.a.maslova1.blackjack;

/**
 * Сбор карты с указанием ранга и масти.
 */
public class Card {
    private final Rank rank;
    private final Suit suit;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    /**
     * Возвращает строковое представление карты в формате: "Достоинство Масть(значение)".
     *
     * @return строковое представление карты
     */
    public String toString() {
        return rank.getRussianName() + ' ' + suit.getRussianName()
                + '(' + rank.getValue() + ')';
    }

    public int getValue() {
        return rank.getValue();
    }


}
