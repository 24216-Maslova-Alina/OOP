package ru.nsu.a.maslova1.blackjack;

/**
 * Класс, представляющий игральные карты.
 *
 * @param suit масть карты
 * @param rank достоинство карты
 */
public record Card(Suits suit, Rank rank) {

    /**
     * Перечисление мастей карт.
     */
    public enum Suits {
        CLABS("Трефы(♣)"),
        DIAMONDS("Бубны(♦)"),
        HERTS("Червы(♥)"),
        SPADES("Пики(♠)");

        private final String russianName;

        /**
         * Название масти на русском.
         *
         * @param russianName русское название масти с символом
         */
        Suits(String russianName) {
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
         * @param value балл
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

    public int getValue() {
        return rank.getValue();
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
}
