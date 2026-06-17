package TableTennis.model;

import lombok.Getter;

@Getter
public class TieBreak {

    // Для констант в этом классе достаточно видимости private.

    // Все магические числа тоже стоит выносить в именованные константы.
        // Именованная константа делает код более семантически понятным.

    // Метод класса нарушает принцип разделения команд и запросов (Command-Query Separation).
        // Который гласит, что метод должен либо изменять состояние (команда), либо возвращать данные (запрос),
        // но не делать и то, и другое. Смешение этих обязанностей усложняет код, его тестирование и понимание.

    public static final int WIN_SCORE = 7;
    private int firstPlayerScore;
    private int secondPlayerScore;


    protected boolean pointWonBy(PlayerNumber playerNumber){
        if(playerNumber == PlayerNumber.FIRST_PLAYER){
            firstPlayerScore++;
        }
        else {
            secondPlayerScore++;
        }

        // Составные условия из if лучше выносить во вспомогательный метод с понятным названием
        return (firstPlayerScore >= WIN_SCORE || secondPlayerScore >= WIN_SCORE) && Math.abs(firstPlayerScore - secondPlayerScore) >= 2;
    }
}
