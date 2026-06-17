package TableTennis.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Game {

    // Методы класса нарушают принцип разделения команд и запросов (Command-Query Separation).
        // Который гласит, что метод должен либо изменять состояние (команда), либо возвращать данные (запрос),
        // но не делать и то, и другое. Смешение этих обязанностей усложняет код, его тестирование и понимание.

    private Point firstPlayerPoint = Point.LOVE;
    private Point secondPlayerPoint = Point.LOVE;
    private PlayerNumber advantage;
    private boolean isGameEnded; // Можно назвать isFinished

    // Метод нарушает принцип разделения команд и запросов (Command-Query Separation).
    protected boolean pointWonBy(PlayerNumber playerNumber){
        if(isGameEnded){
            throw new IllegalStateException("Game is already finished");
        }

        // Более подходящим было бы isFirstPlayerWon
        boolean isFirstPlayer = playerNumber == PlayerNumber.FIRST_PLAYER;

        if (isDeuce()) {
            return advantageCase(playerNumber);
        }

        if(firstPlayerPoint == Point.FORTY && isFirstPlayer || secondPlayerPoint == Point.FORTY && !isFirstPlayer){
            isGameEnded = true;
            return true;
        }

        nextPoint(playerNumber);
        return false;
    }

    // Неинфомативное название метода. По сигнатуре метода должно быть понятно, что он делает.
    private void nextPoint(PlayerNumber playerNumber){
        if (playerNumber == PlayerNumber.FIRST_PLAYER) {
            firstPlayerPoint = firstPlayerPoint.next();
        }
        else {
            secondPlayerPoint = secondPlayerPoint.next();
        }
    }

    // Неинфомативное название метода. По сигнатуре метода должно быть понятно, что он делает.
    // Метод нарушает принцип разделения команд и запросов (Command-Query Separation).
    private boolean advantageCase(PlayerNumber playerNumber){
        if (advantage == playerNumber) {
            isGameEnded = true;
            return true;
        } else if (advantage != null) {
            advantage = null;
        } else {
            advantage = playerNumber;
        }
        return false;
    }
    public boolean isDeuce(){
        return firstPlayerPoint == Point.FORTY && secondPlayerPoint == Point.FORTY;
    }
}
