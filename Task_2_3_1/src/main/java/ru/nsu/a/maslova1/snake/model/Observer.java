package ru.nsu.a.maslova1.snake.model;

/**
 * Интерфейс для реализации паттерна "Наблюдатель".
 */
public interface Observer {

    /**
     * Вызывается моделью для уведомления подписчика об изменении игрового состояния.
     *
     * @param state объект, содержащий актуальные данные о состоянии игры.
     */
    void notify (GameState state);
}