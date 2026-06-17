package TableTennis.model;

import lombok.Getter;

public class TennisSet {

    // Для констант в этом классе достаточно видимости private.

    // Методы класса нарушают принцип разделения команд и запросов (Command-Query Separation).
        // Который гласит, что метод должен либо изменять состояние (команда), либо возвращать данные (запрос),
        // но не делать и то, и другое. Смешение этих обязанностей усложняет код, его тестирование и понимание.

    public static final int WIN_SCORE = 6;
    public static final int TAI_BREAK_WIN_SCORE = 7;
    public static final int WIN_DIFF = 2;
    @Getter private int firstPlayerGames;
    @Getter private int secondPlayerGames;
    private Game game;

    // Класс содержит поле для тай-брейка даже если в матче не будет сыграно ни одного тай-брейка.
    private TieBreak tieBreak;
    public TennisSet(){
        game = new Game();
    }

    // Метод нарушает принцип разделения команд и запросов (Command-Query Separation).
    protected boolean pointWonBy(PlayerNumber playerNumber){
        if(isSetFinished()){
            throw new IllegalStateException("Set is already finished");
        }
        if(isTieBreakStarted()){
            return tieBreakCase(playerNumber);
        }

        boolean isGameWon = game.pointWonBy(playerNumber);
        if(isGameWon){
            nextGame(playerNumber);
        }
        if(firstPlayerGames == WIN_SCORE && secondPlayerGames == WIN_SCORE){
            tieBreak = new TieBreak();
        }
        return isSetFinished();
    }

    // Неинфомативное название метода. По сигнатуре метода должно быть понятно, что он делает.
    private void nextGame(PlayerNumber playerNumber){
        if(playerNumber == PlayerNumber.FIRST_PLAYER){
            firstPlayerGames++;
        }
        else {
            secondPlayerGames++;
        }
        if (!isSetFinished()) {
            game = new Game();
        }
    }

    // Неинфомативное название метода. По сигнатуре метода должно быть понятно, что он делает.
    // Метод нарушает принцип разделения команд и запросов (Command-Query Separation).
    private boolean tieBreakCase(PlayerNumber playerNumber){
        boolean isFinished = tieBreak.pointWonBy(playerNumber);
        if (isFinished){
            if(playerNumber == PlayerNumber.FIRST_PLAYER){
                firstPlayerGames++;
            }
            else {
                secondPlayerGames++;
            }
            return isSetFinished();
        }
        return false;
    }

    // Этот метод может быть private
    protected boolean isSetFinished(){

        // Тело блока if всегда нужно оборачивать в {}
        if (firstPlayerGames == TAI_BREAK_WIN_SCORE || secondPlayerGames == TAI_BREAK_WIN_SCORE) return true;

        return (firstPlayerGames >= WIN_SCORE || secondPlayerGames >= WIN_SCORE)
                && Math.abs(firstPlayerGames - secondPlayerGames) >= WIN_DIFF;
    }
    public Point getFirstPlayerPoints(){
        return game.getFirstPlayerPoint();
    }
    public Point getSecondPlayerPoints(){
        return game.getSecondPlayerPoint();
    }

    // В java принято называть методы глаголами: getFirstPlayerTieBreakScore
    public int firstPlayerTieBreakScore(){
        if(tieBreak == null){
            throw new IllegalStateException("Tie break not started you cant get Tiebreaks score");
        }
        return tieBreak.getFirstPlayerScore();
    }

    // В java принято называть методы глаголами: getSecondPlayerTieBreakScore
    public int secondPlayerTieBreakScore(){
        if(tieBreak == null){
            throw new IllegalStateException("Tie break not started you cant get Tiebreaks score");
        }
        return tieBreak.getSecondPlayerScore();
    }
    public boolean isTieBreakStarted(){
        return tieBreak != null;
    }

}
