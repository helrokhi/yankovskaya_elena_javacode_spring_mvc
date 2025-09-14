package ru.pro.views;

public class Views {
    /**
     * Базовая информация о пользователе
     */
    public interface UserSummary {
    }

    /**
     * Базовая информация о пользователе включает заказы
     */
    public interface UserDetails extends UserSummary {
    }
}
