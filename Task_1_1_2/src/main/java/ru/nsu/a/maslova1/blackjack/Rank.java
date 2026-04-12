package ru.nsu.a.maslova1.blackjack;

/**
 * Перечисления достоинств игральных карт и баллов за них.
 */
public enum Rank {
    TWO("Двойка", 2),
    THREE("Тройка", 3),
    FOUR("Четверка", 4),
    FIVE("Пятерка", 5),
    SIX("Шестерка", 6),
    SEVEN("Семерка", 7),
    EIGHT("Восьмерка", 8),
    NINE("Девятка", 9),
    TEN("Десятка", 10),
    JACK("Валет", 10),
    QUEEN("Дама", 10),
    KING("Король", 10),
    ACE("Туз", 11);

    private final String russianName;
    private final int value;

    /**
     * Перевод в читаемый вид.
     *
     * @param russianName русское название
     * @param value       балл
     */
    Rank(String russianName, int value) {
        this.russianName = russianName;
        this.value = value;
    }

    /**
     * Возвращает русское название.
     *
     * @return русское название
     */
    public String getRussianName() {
        return russianName;
    }

    /**
     * Возвращает балл за карту.
     *
     * @return баллы за карту
     */
    public int getValue() {
        return value;
    }
}

