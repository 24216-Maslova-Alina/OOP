package ru.nsu.a.maslova1.blackjack;

/**
 * Перечисление мастей карт.
 */
public enum Suit {
    CLUBS("Трефы(♣)"),
    DIAMONDS("Бубны(♦)"),
    HERTS("Червы(♥)"),
    SPADES("Пики(♠)");

    private final String russianName;

    /**
     * Название масти на русском.
     *
     * @param russianName русское название масти с символом
     */
    Suit(String russianName) {
        this.russianName = russianName;
    }

    /**
     * Возвращает русское название.
     *
     * @return русское название
     */
    public String getRussianName() {
        return russianName;
    }
}
