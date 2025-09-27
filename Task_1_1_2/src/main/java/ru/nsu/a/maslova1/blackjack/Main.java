package ru.nsu.a.maslova1.blackjack;

/**
 * Метод запускающий игру.
 */
public class Main {
    /**
     * Запускает игру.
     * @param args не вводятся
     */
    public static void main(String[] args) {
        ConsoleOutput output = new ConsoleOutput();

        BlackjackGame game = new BlackjackGame(output);
        game.start();
    }
}
