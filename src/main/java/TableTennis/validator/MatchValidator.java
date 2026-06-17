package TableTennis.validator;

import TableTennis.Exception.BadRequestException;
import TableTennis.Exception.PlayerSearchValidationException;
import TableTennis.Exception.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class MatchValidator {

    // Все повторяющиеся или важные строковые и числовые литералы лучше выносить в `private static final` константы с понятными именами.
        // Именованная константа делает код более семантически понятным.

    // Класс нарушает Принцип единой ответственности (SRP) — объединяет в себе логику валидации для трёх разных бизнес-сценариев.
        // Более чистым решением с точки зрения архитектуры было бы разделение на более мелкие и сфокусированные валидаторы.

    // Несколько проверок дублируются для каждого имени — стоит вынести их в отдельные методы
    public void validateNames(String firstPlayerName, String secondPlayerName) {
        List<String> errors = new ArrayList<>();

        if (firstPlayerName == null || secondPlayerName == null) {
            throw new BadRequestException("Player names must not be null");
        }

        if (firstPlayerName.isEmpty() || secondPlayerName.isEmpty()) {
            errors.add("Player names must not be empty");
        }

        if (!firstPlayerName.matches("^[A-Z]{1}[a-z]+")) {
            errors.add("First player name must start with a capital letter, No spaces are allowed");
        }

        if (!secondPlayerName.matches("^[A-Z]{1}[a-z]+")) {
            errors.add("Second player name must start with a capital letter, No spaces are allowed");
        }

        if (!firstPlayerName.matches("^[a-zA-Z]+$")) {
            errors.add("First player name must contain only letters (A-Z, a-z)");
        }

        if (!secondPlayerName.matches("^[a-zA-Z]+$")) {
            errors.add("Second player name must contain only letters (A-Z, a-z)");
        }

        if (firstPlayerName.equalsIgnoreCase(secondPlayerName)) {
            errors.add("Players must be different");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(String.join("\n", errors));
        }
    }

    public void validateFilterName(String name) {
        if (name == null) {
            return;
        }

        // Нет необходимости создавать список ради одного сообщения
        List<String> errors = new ArrayList<>();

        if (name.length() > 20) {

            // Здесь можно сразу бросать исключение
            errors.add("Player name must not exceed 20 characters");
        }

        if (!errors.isEmpty()) {
            throw new PlayerSearchValidationException(String.join("\n", errors));
        }
    }

    public void validatePage(int page) {
        if (page < 0) {
            throw new BadRequestException("Page number must not be negative");
        }
    }
}
