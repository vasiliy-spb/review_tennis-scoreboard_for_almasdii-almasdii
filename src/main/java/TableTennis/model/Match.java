package TableTennis.model;

import TableTennis.entity.Player;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Match {

    // TODO: Класс хранит ссылки на JPA-сущности (`Player`). Использование объектов JPA Entity в доменной логике
        // создаёт прямую зависимость доменного слоя от слоя персистентности (долговременного хранения данных)
        // и смешивает слои приложения, что нарушает чистоту архитектуры.
        // Это может привести к проблемам с ленивой загрузкой (`LazyInitializationException`)
        // или к неожиданным изменениям в базе данных, если состояние `Player` будет изменено в ходе бизнес-логики.
        // Доменные модели должны оперировать другими доменными моделями, а не сущностями, привязанными к базе данных.
        // (см. файл "model-types.md" в этом же пакете)

    // Методы класса нарушают принцип разделения команд и запросов (Command-Query Separation).
        // Который гласит, что метод должен либо изменять состояние (команда), либо возвращать данные (запрос),
        // но не делать и то, и другое. Смешение этих обязанностей усложняет код, его тестирование и понимание.

    private final static int WIN_SCORE = 2;
    private TennisSet currentSet;
    @Getter private final UUID id;
    @Getter private final Player firstPlayer;
    @Getter private final Player secondPlayer;
    @Getter private Player winner;
    @Getter private int firstPlayerSets;
    @Getter private int secondPlayerSets;

    
    public Match(UUID id,Player firstPlayer, Player secondPlayer){
        this.id = id;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        currentSet = new TennisSet();
    }

    // Метод нарушает принцип разделения команд и запросов (Command-Query Separation).
    public boolean pointWonBy(Player player){
        if(isFinished()){
            throw new IllegalStateException("Match is already finished");
        }

        if(!player.equals(firstPlayer) && !player.equals(secondPlayer)){
            throw new IllegalArgumentException("Player is not part of this match");
        }

        PlayerNumber playerNumber = (player.equals(firstPlayer))
                ? PlayerNumber.FIRST_PLAYER
                : PlayerNumber.SECOND_PLAYER;

        boolean isSetWon = currentSet.pointWonBy(playerNumber);

        if(isSetWon){
            nextSet(playerNumber);
        }

        return isFinished();

    }

    // Тела блоков if-else всегда нужно оборачивать в {}
    private void nextSet(PlayerNumber playerNumber){
        if(playerNumber == PlayerNumber.FIRST_PLAYER) firstPlayerSets++;
        else secondPlayerSets++;

        if(firstPlayerSets >= WIN_SCORE) winner = firstPlayer;
        else if(secondPlayerSets >= WIN_SCORE) winner = secondPlayer;
        else currentSet = new TennisSet();
    }
    public boolean isFinished(){
        return winner != null;
    }
    public int getFirstPlayerGames(){
        return currentSet.getFirstPlayerGames();
    }
    public int getSecondPlayerGames(){
        return currentSet.getSecondPlayerGames();
    }
    public int getFirstPlayerPoints(){
        return currentSet.getFirstPlayerPoints().getScore();
    }
    public int getSecondPlayerPoints(){
        return currentSet.getSecondPlayerPoints().getScore();
    }

    // В java принято называть методы глаголами: getFirstPlayerTieBreakScore
    public int firstPlayerTieBreakScore(){
        return currentSet.firstPlayerTieBreakScore();
    }

    // В java принято называть методы глаголами: getSecondPlayerTieBreakScore
    public int secondPlayerTieBreakScore(){
        return currentSet.secondPlayerTieBreakScore();
    }
    public boolean isTieBreakStarted(){
        return currentSet.isTieBreakStarted();
    }
}
